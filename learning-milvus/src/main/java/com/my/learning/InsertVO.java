package com.my.learning;

import java.util.List;
import lombok.Data;

@Data
public class InsertVO {
    String collectionName;
    List<Long> textIds;
    List<List<Float>> vectorList;
}
