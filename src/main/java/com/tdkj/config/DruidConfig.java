package com.tdkj.config;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * @ClassName: DruidConfig
 * @Description TODO
 * @Author and
 * @Date 2019/5/10 8:51
 * @Version 1.0
 */

@Configuration
public class DruidConfig {


        @ConfigurationProperties(prefix = "spring.datasource")
        @Bean
        public DataSource druid(){
            return new DruidDataSource();
        }

        //配置Druid的监控
        //1、配置一个管理后台的Servlet
        @Bean
        public ServletRegistrationBean statViewServlet(){
            ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
            Map<String,String> initParams = new HashMap<>();

            initParams.put("loginUsername","administrator");
            initParams.put("loginPassword","fptz2019");
            initParams.put("allow","false"); //默认就是允许所有访问
            //白名单：
          /*  initParams.put("allow","192.168.6.195");
            //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
            initParams.put("deny","192.168.6.73");
            //是否能够重置数据.
            initParams.put("resetEnable", "true");*/


            registrationBean.setInitParameters(initParams);
            return registrationBean;
        }

        //2、配置一个web监控的filter
        @Bean
        public FilterRegistrationBean webStatFilter(){
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
            filterRegistrationBean.setFilter(new WebStatFilter());
            Map<String,String> initParams = new HashMap<>();
            initParams.put("exclusions","*.js,*.css,/druid/*");
            filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
            filterRegistrationBean.setInitParameters(initParams);
            return filterRegistrationBean;
        }
    }


