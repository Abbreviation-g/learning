package com.my.learning;

import java.util.List;

/**
 * milvus向量数据库相关业务接口
 *
 * @author Jx
 * @version 2024-3-18
 */
public interface MilvusService {
    String COLLECTION_NAME = "java_sdk_example_simple_0423";

    Boolean hasCollect(String collectionName);

    Boolean create(String collectionName, String desc);

    Boolean insert(String collectionName, List<Long> textIds, List<List<Float>> vectorList);

    List<Long> search(String collectionName, int topK, List<List<Float>> vectorList);

    void dropCollect(String collectionName);

    void createIndex(String collectionName);

    void dropVectors(String collectionName, List<Long> indexIds);
}