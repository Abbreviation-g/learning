package com.my.learning;

import io.milvus.client.MilvusServiceClient;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.grpc.DataType;
import io.milvus.grpc.GetLoadStateResponse;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.GetLoadStateParam;
import io.milvus.param.collection.HasCollectionParam;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.DeleteParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.highlevel.dml.DeleteIdsParam;
import io.milvus.param.highlevel.dml.response.DeleteResponse;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.response.SearchResultsWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MilvusServiceImpl implements MilvusService {
    @Autowired
    private MilvusServiceClient milvusServiceClient;


    final IndexType INDEX_TYPE = IndexType.IVF_FLAT;   // IndexType
    final String INDEX_PARAM = "{\"nlist\":1024}";     // ExtraParam

    /**
     * 创建集合的字段
     * text_id  对应的文本id
     * vector  向量字段
     * tag  标签
     */
    private final String TEXTID = "text_id";
    private final String VECTOR = "vector";
    private final String TAG = "tag";

    private final int dimension = 1024;

    /**
     * 集合是否存在
     *
     * @return
     */
    @Override
    public Boolean hasCollect(String collectionName) {
        R<Boolean> hasResult = milvusServiceClient.hasCollection(
                HasCollectionParam.newBuilder()
                        .withCollectionName(collectionName)
                        .build());
        if (hasResult.getStatus() == R.Status.Success.getCode()) {
            return hasResult.getData();
        }
        return false;
    }

    @Override
    public Boolean create(String collectionName, String desc) {
        log.info("Miluvs create collectionName:{}, desc:{}", collectionName, desc);
        boolean has = hasCollect(collectionName);
        log.info("Miluvs hasCollect:{}", has);
        if (has) {
            return false;
        }
        // 不存在此集合才进行创建集合
        log.info("Create Collection:{}", collectionName);
        //  创建集合 设置索引 加载集合到内存中
        FieldType fieldType1 = FieldType.newBuilder()
                .withName(TEXTID)
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(false)
                .build();
        FieldType fieldType2 = FieldType.newBuilder()
                .withName(VECTOR)  // 设置向量名称
                .withDataType(DataType.FloatVector)  // 设置向量类型
                .withDimension(dimension) // 设置向量维度
                .build();
        FieldType fieldType3 = FieldType.newBuilder()
                .withName(TAG)
                .withDataType(DataType.Int64)
                .build();
        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .withDescription(desc)
                .withShardsNum(2)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .addFieldType(fieldType3)
                .withEnableDynamicField(true)
                .withConsistencyLevel(ConsistencyLevelEnum.BOUNDED)
                .build();
        R<RpcStatus> response = milvusServiceClient.createCollection(createCollectionReq);
        if (response.getStatus() != R.Status.Success.getCode()) {
            log.info("milvus create fail message:{}", response.getMessage());
            return false;
        } else {
            // 创建集合索引并加载集合到内存  插入数据和搜索的前置操作！！
            createIndex(collectionName);
        }
        return true;
    }


    /**
     * 创建集合索引 -- 加在向量字段上
     *
     * @param collectionName
     */
    public void createIndex(String collectionName) {
        milvusServiceClient.createIndex(
                CreateIndexParam.newBuilder()
                        .withCollectionName(collectionName)
                        .withFieldName(VECTOR)
                        .withIndexType(INDEX_TYPE)
                        .withMetricType(MetricType.L2)
                        .withExtraParam(INDEX_PARAM)
                        .withSyncMode(Boolean.FALSE)
                        .build()
        );
        // 加载所创建的集合
        loadCollection(collectionName);
    }


    /**
     * 加载集合
     *
     * @param collectionName
     */
    public void loadCollection(String collectionName) {
        milvusServiceClient.loadCollection(
                LoadCollectionParam.newBuilder()
                        .withCollectionName(collectionName)
                        .build()
        );
        // You can check the loading status
        GetLoadStateParam param = GetLoadStateParam.newBuilder()
                .withCollectionName(collectionName)
                .build();
        R<GetLoadStateResponse> stateResponse = milvusServiceClient.getLoadState(param);
        if (stateResponse.getStatus() != R.Status.Success.getCode()) {
            System.out.println(stateResponse.getMessage());
        }
    }


    /**
     * 向量库中插入数据
     */
    @Override
    public Boolean insert(String collectionName, List<Long> textIds, List<List<Float>> vectorList) {
        log.info("milvus insert collectionName:{}, textIds:{}, vectorList:{}", collectionName, textIds, vectorList);
        List<Long> tagList = new ArrayList<>();
        for (Long textId : textIds) {
            tagList.add(0L);
        }
        List<InsertParam.Field> fieldsInsert = new ArrayList<>();
        fieldsInsert.add(new InsertParam.Field(TEXTID, textIds));  // 文本对应的ids数据list
        fieldsInsert.add(new InsertParam.Field(VECTOR, vectorList));  // 转换后的向量数据list
        fieldsInsert.add(new InsertParam.Field(TAG, tagList));  // 标签占位符  给个0
        InsertParam param = InsertParam.newBuilder()
                .withCollectionName(collectionName)
                .withFields(fieldsInsert)
                .build();
        R<MutationResult> response = milvusServiceClient.insert(param);
        if (response.getStatus() != R.Status.Success.getCode()) {
            log.info("milvus insert vector fail! message:{}", response.getMessage());
            return false;
        } else {
            return true;
        }

    }


    /**
     * 删除集合
     *
     * @param collectionName
     */
    @Override
    public void dropCollect(String collectionName) {
        milvusServiceClient.dropCollection(
                DropCollectionParam.newBuilder()
                        .withCollectionName(collectionName)
                        .build()
        );
    }


    /**
     * 根据ids删除向量
     *
     * @param collectionName
     * @param indexIds
     */
    @Override
    public void dropVectors(String collectionName, List<Long> indexIds) {
        String expr = TEXTID + " in " + indexIds;
        DeleteParam param = DeleteParam.newBuilder()
                .withCollectionName(collectionName)
                .withExpr(expr)
                .build();
        R<MutationResult> response = milvusServiceClient.delete(param);
        if (response.getStatus() != R.Status.Success.getCode()) {
            System.out.println(response.getMessage());
        }
    }


    /**
     * 向量搜索 - 向量库中用具体向量搜索 - 返回indexIds
     */
    @Override
    public List<Long> search(String collectionName, int topK, List<List<Float>> vectorList) {
        // 构建查询条件  进行向量字段查询   待测试1024维度向量
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName)
                .withVectorFieldName(VECTOR)
                .withOutFields(new ArrayList<>(Arrays.asList("*")))
                .withVectors(vectorList)
                .withTopK(topK)
                .build();
        R<SearchResults> searchResults = milvusServiceClient.search(searchParam);
        if (searchResults.getStatus() != R.Status.Success.getCode()) {
            log.info(searchResults.getMessage());
        }
        List<Long> textIdList = new ArrayList<>();
        SearchResultsWrapper wrapper = new SearchResultsWrapper(searchResults.getData().getResults());
        for (int i = 0; i < vectorList.size(); ++i) {
            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
            for (SearchResultsWrapper.IDScore score : scores) {
                Map<String, Object> filedsMap = score.getFieldValues();
                textIdList.add(Long.valueOf(String.valueOf(filedsMap.get(TEXTID))));
            }
        }
        return textIdList;
    }


    /**
     * 删除集合中的 id对应的向量
     */
    public void deleteEmbedingById() {
        List<String> ids = new ArrayList<>(Arrays.asList("441966745769900131", "441966745769900133"));
        DeleteIdsParam param = DeleteIdsParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPrimaryIds(ids)
                .build();
        R<DeleteResponse> response = milvusServiceClient.delete(param);
        if (response.getStatus() != R.Status.Success.getCode()) {
            System.out.println(response.getMessage());
        }

        for (Object deleteId : response.getData().getDeleteIds()) {
            System.out.println(deleteId);
        }

    }


    // 测试用的向量数据类型
    public List<List<Float>> getListVector() {
        List<Float> vectorData = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            vectorData.add((float) Math.random());
        }
        List<List<Float>> vectors = new ArrayList<>();
        vectors.add(vectorData);

        return vectors;
    }
}