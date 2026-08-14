package com.example.mysql.rcp.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
    public static final String ID = "com.example.mysql.rcp.perspective";

    @Override
    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);
        layout.addStandaloneView(MySqlConnectionView.ID, false, IPageLayout.LEFT, 1.0f, editorArea);
    }
}
