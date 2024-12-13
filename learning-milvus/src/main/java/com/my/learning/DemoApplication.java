package com.my.learning;

import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
@RequestMapping("/milvus")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Resource
    MilvusService milvusService;

    @GetMapping("/exist/{database_name}")
    @ResponseBody
    public Object isExist(@PathVariable("database_name") String databaseName) {
        System.out.println("----------" + ">>>>>>");
        return milvusService.hasCollect(databaseName);
    }

    @PostMapping("/create")
    @ResponseBody
    public Object createDatabase(@RequestParam("collectionName") String collectionName) {
        return milvusService.create(collectionName, "desc: " + collectionName);
    }

    @PostMapping("/insert")
    @ResponseBody
    public Object insert(@RequestBody InsertVO insertVO) {
        return milvusService.insert(insertVO.getCollectionName(), insertVO.getTextIds(), insertVO.getVectorList());
    }
}
