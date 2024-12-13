package com.my.learning.test;

import com.alibaba.fastjson.JSONObject;
import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.grpc.*;
import io.milvus.param.collection.FlushParam;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.response.SearchResultsWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
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

        // 定义字段
        List<FieldType> fieldsSchema = Arrays.asList(
                FieldType.newBuilder()
                        .withName(ID_FIELD)
                        .withDataType(DataType.Int64)
                        .withPrimaryKey(true)
                        .withAutoID(false)
                        .build(),
                FieldType.newBuilder()
                        .withName(VECTOR_FIELD)
                        .withDataType(DataType.FloatVector)
                        .withDimension(VECTOR_DIM)
                        .build(),
                FieldType.newBuilder()
                        .withName(TITLE_FIELD)
                        .withDataType(DataType.VarChar)
                        .withMaxLength(64)
                        .build()
        );

        // 创建数据库表
        R<RpcStatus> ret = milvusClient.createCollection(CreateCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldTypes(fieldsSchema)
                .build());
        if (ret.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException("Failed to create collection! Error: " + ret.getMessage());
        }
        System.out.println(ret);

        // 为向量字段创建索引
        ret = milvusClient.createIndex(CreateIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .withIndexType(IndexType.FLAT)
                .withMetricType(MetricType.L2)
                .build());
        if (ret.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException("Failed to create index on vector field! Error: " + ret.getMessage());
        }

        // 为book_title字段创建索引
        ret = milvusClient.createIndex(CreateIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(TITLE_FIELD)
                .withIndexType(IndexType.TRIE)
                .build());
        if (ret.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException("Failed to create index on varchar field! Error: " + ret.getMessage());
        }

        // 连接表java_sdk_example_simple_0422
        milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());

        // 创建10条数据
        List<JSONObject> rows = new ArrayList<>();
        for (long i = 1L; i <= 10; ++i) {
            JSONObject row = new JSONObject();
            row.put(ID_FIELD, i);
            List<Float> vector = Arrays.asList((float)i, (float)i, (float)i, (float)i);
            row.put(VECTOR_FIELD, vector);
            row.put(TITLE_FIELD, "NAME OF BOOK " + i);
            rows.add(row);
        }
        // 往表java_sdk_example_simple_0422中插入10条数据
        R<MutationResult> insertRet = milvusClient.insert(InsertParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withRows(rows)
                .build());
        if (insertRet.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException("Failed to insert! Error: " + insertRet.getMessage());
        }
        // 调用flush以确保插入的记录被Milvus服务器使用，以便可立即搜索。只是这个例子中的一个特殊动作。        在实践中，您不需要频繁调用flush（）。
        milvusClient.flush(FlushParam.newBuilder()
                .addCollectionName(COLLECTION_NAME)
                .build());

        // 构造一个向量来搜索前5个类似的记录，为我们返回书名。
        // 这个向量等于3号记录，我们假设3号记录是最相似的。
        List<Float> vector = Arrays.asList(3.0f, 3.0f, 3.0f, 3.0f);
        R<SearchResults> searchRet = milvusClient.search(SearchParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withMetricType(MetricType.L2)
                .withTopK(5)
                .withFloatVectors(Arrays.asList(vector))
                .withVectorFieldName(VECTOR_FIELD)
                .withParams("{}")
                .addOutField(TITLE_FIELD)
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