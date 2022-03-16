package com.reportdemo.myreport.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author jli
 * @description 参数接收model
 * @createTime 2022/3/13 下午1:10
 */
@Data
public class ReportParam implements Serializable {
    private String template;
    private Map<String,Object> description;
    private List<Map<String,Object>> dataList;

}
