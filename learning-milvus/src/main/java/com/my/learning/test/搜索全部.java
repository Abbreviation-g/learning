package com.my.learning.test;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.SearchResults;
import io.milvus.param.ConnectParam;
import io.milvus.param.R;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.response.SearchResultsWrapper;
import java.util.List;

public class 搜索全部 {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        testMilvus();
    }
    private static final String COLLECTION_NAME = "java_sdk_example_simple_0422";
    private static final String ID_FIELD = "book_id";
    private static final String VECTOR_FIELD = "book_intro";
    private static final String TITLE_FIELD = "book_title";
    private static final Integer VECTOR_DIM = 4;

    //http://139.227.105.53:19530
    private static  void testMilvus(){
        // 连接milvus数据库
        MilvusServiceClient milvusClient = new MilvusServiceClient(ConnectParam.newBuilder()
                .withHost("139.227.105.53")
                .withPort(19530)
                .build());

        // 连接表java_sdk_example_simple_0422
        milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());

        // 构造一个向量来搜索前5个类似的记录，为我们返回书名。
        // 这个向量等于3号记录，我们假设3号记录是最相似的。
        R<SearchResults> searchRet = milvusClient.search(SearchParam.newBuilder()
                .withCollectionName(COLLECTION_NAME).withVectorFieldName(VECTOR_FIELD).withTopK(1)
                .build());
        if (searchRet.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException("Failed to search! Error: " + searchRet.getMessage());
        }
        // 打印相似结果，3号书籍是最相似的
        SearchResultsWrapper resultsWrapper = new SearchResultsWrapper(searchRet.getData().getResults());
        List<SearchResultsWrapper.IDScore> scores = resultsWrapper.getIDScore(0);
        System.out.println("The result of No.0 target vector:");
        for (SearchResultsWrapper.IDScore score:scores) {
            System.out.println(score);
        }

        // 关闭连接
        milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
    }

}