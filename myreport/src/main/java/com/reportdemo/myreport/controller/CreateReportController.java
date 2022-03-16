package com.reportdemo.myreport.controller;

import com.reportdemo.myreport.model.ReportParam;
import com.reportdemo.myreport.service.MyReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jli
 * @description 报表生成
 * @createTime 2022/3/11 17:25
 */

@RestController
@RequestMapping("/applyReports")
public class CreateReportController {

    @Resource
    private MyReportService myReportService;
    
    /**
     * @desc 报表预览
     * @createTime 2022/3/12 09:56
     * @params [response, businessUuid] 
     * @return void
     */
    @PostMapping("/showPdf")
   public void showPdf(HttpServletResponse response, @RequestBody List<ReportParam> param)throws Exception {
        myReportService.optPdf(response,param);
    }
    
    /**
     * @desc 报表下载
     * @createTime 2022/3/12 10:00
     * @params [response, businessUuid] 
     * @return void
     */
    @PostMapping("/downPdf")
    public void downPdf(HttpServletResponse response, @RequestBody List<ReportParam> param)throws Exception {
        myReportService.optPdf(response,param);
    }

}
