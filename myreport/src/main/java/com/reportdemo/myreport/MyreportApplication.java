package com.reportdemo.myreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @desc @ServletComponentScan注解：Servlet、Filter、Listener可以直接
 *       通过@WebServlet、@WebFilter、@WebListener注解自动注册，无需其他代码
 */
@SpringBootApplication
@ServletComponentScan
//@MapperScan("com.reportdemo.myreport.mapper")
public class MyreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyreportApplication.class, args);
    }

}
