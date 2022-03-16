package com.reportdemo.myreport.service;

import com.reportdemo.myreport.model.ReportParam;
import com.reportdemo.myreport.util.FileOutputUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jli
 * @description report报表生成
 * @createTime 2022/3/12 10:00
 */
@Service
public class MyReportServiceImpl implements MyReportService {

    public static Logger logger = LoggerFactory.getLogger(MyReportServiceImpl.class);

    /**
     * @desc 报表预览
     * @createTime 2022/3/12 11:49
     * @params [response, businessUuid, optType] 
     * @return void
     */
    @Override
    public void optPdf(HttpServletResponse response, List<ReportParam> param) throws Exception {
        logger.info("PDF开始处理......");
        /* ---------测试使用数据----------- */
        List<ReportParam> paramList = new ArrayList<ReportParam>();
        ReportParam reportParam1 = new ReportParam();
        ReportParam reportParam2 = new ReportParam();

        reportParam1.setTemplate("applyOrder");
//        reportParam1.setTemplate("applyOrder01");
//        reportParam2.setTemplate("applyOrder02");

        //获取二维码
        File file = ResourceUtils.getFile("classpath:static/image/56652854.jpg");
        InputStream in = new FileInputStream(file);
        System.out.println();

        Map<String, Object> map1 = new HashMap<String,Object>();
        map1.put("title","北京住宅专项维修资金小区登记业务凭证回单");
        map1.put("busNo","3123123618417");
        map1.put("count",1000);
        map1.put("communityName","海天小区");
        map1.put("jbglb","XXXXXXX");
        map1.put("jbgy","XXX");
        map1.put("ywrq","XXXX年XX月XX日");
        map1.put("QrCode",in);//二维码输入流
        reportParam1.setDescription(map1);
        Map<String, Object> map2 = map1;
        reportParam2.setDescription(map2);

        List<Map<String,Object>> dataList1 = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 20; i++) {
            Map<String,Object> rowMap = new HashMap<String,Object>();
            rowMap.put("XH",i+1);
            rowMap.put("XQMC","海天小区"+i);
            rowMap.put("LZMC","1幢"+i);
            rowMap.put("DYH","1单元"+i);
            rowMap.put("CH","北京市朝阳区文学馆路16号"+i);
            dataList1.add(rowMap);
        }
        List<Map<String,Object>> dataList2 = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 20; i++) {
            Map<String,Object> rowMap = new HashMap<String,Object>();
            rowMap.put("XHB",i+1);
            rowMap.put("LZMCB","1幢"+i);
            rowMap.put("LZDZ","北京市朝阳区文学馆路16号"+i);
            rowMap.put("LZLX","高层"+i);
            rowMap.put("GHYT","住宅+车位"+i);
            dataList2.add(rowMap);
        }
//        System.out.println(dataList1);
        map1.put("dataList",dataList1);
        map2.put("dataList",dataList2);


        reportParam1.setDataList(dataList1);
        reportParam2.setDataList(dataList2);

        paramList.add(reportParam1);
//        paramList.add(reportParam2);
        /* ------------测试使用数据---------- */

        String pdfName = "";
        byte[] bytes = new byte[0];
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        try {
            for (ReportParam reportParam : paramList) {
                //设置pdf文件名
                pdfName = reportParam.getTemplate();
                //编译.jrxml文件,生成JasperReport
                JasperReport jasperReport = FileOutputUtil.compileJrxml(reportParam);
                //设置填充参数
                reportParam.getDescription().put("dataList", reportParam.getDataList());
                //JasperReport填充
                JasperPrint jasperPrint = FileOutputUtil.fillReport(jasperReport,reportParam);

                /* 将多个模板生成一份PDF文件 */
                jasperPrintList.add(jasperPrint);
            }
            //输出PDF
            FileOutputUtil.showPdf(response, jasperPrintList, bytes, pdfName);
            logger.info("预览PDF成功");
        }catch (JRException | IOException e) {
            e.printStackTrace();
            logger.info("PDF处理失败");
        }
    }
}
