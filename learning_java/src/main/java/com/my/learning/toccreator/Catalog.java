package com.my.learning.toccreator;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Catalog {
    private static Gson gson = new GsonBuilder().create();
    private List<Catalog> childCatalogs = new ArrayList<>();
    private String toc;
    private String title;

    public Catalog(String toc, String title) {
        super();
        this.toc = toc;
        this.title = title;
    }

    public Catalog() {
        super();
    }

    public String getToc() {
        return toc;
    }

    public void setToc(String toc) {
        this.toc = toc;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Catalog> getChildCatalogs() {
        return childCatalogs;
    }

    public boolean addChildCatalog(Catalog catalog) {
        return this.childCatalogs.add(catalog);
    }

    @Override
    public String toString() {
        String jsonResult = gson.toJson(this);
        return jsonResult;
    }
}