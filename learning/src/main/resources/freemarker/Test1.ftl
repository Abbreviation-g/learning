// 测试for循环的使用,使用"_has_next"判断当前元素是否是最后一个元素
int f2(){
    // 最后一个元素后面不接乘号
    return 
    <#list arr as index>f${index}()<#if index_has_next>*</#if>
    </#list>;
}
// 测试for循环的使用,使用"_index"获取当前元素下标
int f2(){
    // 跳过第三个元素
    return 
    <#list arr as index><#if index_index!=3>f${index}()<#if index_has_next>*</#if></#if>
    </#list>;
}