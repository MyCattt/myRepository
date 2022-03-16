package com.reportdemo.myreport.util;

import com.reportdemo.myreport.model.ReportParam;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.bouncycastle.util.test.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author jli
 * @description Jasperreports处理工具类
 * @createTime 2022/3/14 下午1:07
 */
public class FileOutputUtil {
    
    /**
     * @desc 编译jrxml模板文件
     * @createTime 2022/3/14 下午1:40
     * @params [fileName] 
     * @return net.sf.jasperreports.engine.JasperReport
     */
    public static JasperReport compileJrxml(ReportParam reportParam) throws JRException {
        //File file = ResourceUtils.getFile("classpath:ireportTemplate/" + fileName);
        //jar部署方式需要以以下方式获取文件
        InputStream is = Test.class.getResourceAsStream("/templates/" + reportParam.getTemplate() + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(is);
        return jasperReport;
    }

    /**
     * @desc JasperReport填充
     * @createTime 2022/3/16 下午4:28
     * @params [jasperReport, reportParam]
     * @return net.sf.jasperreports.engine.JasperPrint
     */
    public static JasperPrint fillReport(JasperReport jasperReport, ReportParam reportParam) throws JRException {
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport,reportParam.getDescription(), new JRBeanCollectionDataSource(reportParam.getDataList()));
        return jasperPrint;
    }
    
    /**
     * @desc pdf预览
     * @createTime 2022/3/14 下午1:19
     * @params [response, jasperReport, map, source, fileName]
     */
    @SuppressWarnings("deprecation")
    public static void showPdf(HttpServletResponse response, List<JasperPrint> jasperPrintList,
                               byte[] bytes, String pdfName) throws IOException, JRException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,jasperPrintList);//设置多个报表模版
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);//获取pdf文件流
        exporter.exportReport();
        bytes= baos.toByteArray();

        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition", "inline:filename=" + java.net.URLEncoder.encode(pdfName, "UTF-8") + ".pdf");
        response.setContentLength(bytes.length);
        //获取outputStream
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes, 0, bytes.length);
        outputStream.flush();
        outputStream.close();
    }
    
    /**
     * @desc pdf下载
     * @createTime 2022/3/14 下午1:27
     * @params [jasperReport, map, source, fileName] 
     * @return void
     */
    @SuppressWarnings("deprecation")
    public static void downPdf(JasperReport jasperReport, Map<String,Object> map,
                               JRBeanCollectionDataSource source, String pdfName) throws JRException, FileNotFoundException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,source);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("/Users/jli/IReportTemplate/" + pdfName+".pdf"));
        exporter.exportReport();
    }
}
