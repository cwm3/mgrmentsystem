package org.cwm3.mgrsystem.common.entily;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @author mrcwm
 */
@Data
@ToString
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    // 当前页面数据量
    private int pageSize = 10;
    // 当前页码
    private int pageNum = 1;
    // 排序字段
    private String field;
    // 排序规则，asc升序，desc降序
    private String order;
    //模糊搜索内容（或自定义搜索格式）

    private String searchTxt;
    //指定名值对的搜索内容（key指定搜索目标，value指定搜索内容，可能根据key值的不同意义不同）
    private Map<String, String> searchTexts;

}
