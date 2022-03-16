package com.reportdemo.myreport.service;

import com.reportdemo.myreport.model.ReportParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author jli
 * @description report报表生成
 * @createTime 2022/3/12 09:58
 */
public interface MyReportService {

    public void optPdf(HttpServletResponse response, List<ReportParam> param) throws UnsupportedEncodingException, Exception;

}
