package com.tdkj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 配置跳转页面
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(MvcConfig.class);


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        logger.trace("MvcConfig.addViewControllers()");
        // 设置登录页面112
        registry.addViewController("/login")
                .setViewName("login/login");
        // 登陸成功後跳轉
        registry.addViewController("/indexa")
                .setViewName("redirect:/success");
        //再次跳转
        registry.addViewController("/success")
                .setViewName("index");
        //设置默认访问页面
        registry.addViewController("/")
                .setViewName("login/login");
        //进入招聘会
        registry.addViewController("/JobFair/list")
                .setViewName("JobFair/jobFair");
        //进入年度任务
        registry.addViewController("/ndrw/list")
                .setViewName("ndrw/ndrw");
        //进入扶贫基地
        registry.addViewController("/povertyBase/list")
                .setViewName("PovertyAlleviationBase/Poverty_alleviation_base");
        //进入招聘会
        registry.addViewController("/CommunityFactory/list")
                .setViewName("CommunityFactory/community_factory");

        //进入信用乡村台账
        registry.addViewController("/CreditVillageAccount/list")
                .setViewName("accountManagement/credit_village_account_list");

        //进入信用乡村  待完善
        registry.addViewController("/CreditVillageToComplete/list")
                .setViewName("tobecomplete/credit_village_tobe_complete_list");

        //进入招聘会台账
        registry.addViewController("/JobFairAccount/list")
                .setViewName("accountManagement/job_fair_account_list");

        //进入招聘会   待完善
        registry.addViewController("/JobFairTobeComplete/list")
                .setViewName("tobecomplete/job_fair_tobe_complete_list");
        //-----------------------------------导入--------------------------------
        //贫困户信息导入
        registry.addViewController("/poorhouseholds/list")
                .setViewName("importExcel/poorhouseholds_import");

        //劳动力信息导入
        registry.addViewController("/poorLaborForce/list")
                .setViewName("importExcel/poorLaborForce_import");

        //就业信息导入
        registry.addViewController("/EmploymentSintuation/list")
                .setViewName("importExcel/EmploymentSintuation_import");

        //创业信息导入
        registry.addViewController("/Entrepreneurship/list")
                .setViewName("importExcel/Entrepreneurship_import");

        //技工院校信息导入
        registry.addViewController("/TechnicalSchools/list")
                .setViewName("importExcel/TechnicalSchools_import");

        // 培训信息导入
        registry.addViewController("/TrainingSituation/list")
                .setViewName("importExcel/TrainingSituation_import");
        //--------------------------------------统计----------------------
        // 贫困户统计
        registry.addViewController("/PoorHouseholdStatistics/list")
                .setViewName("DataStatistics/PoorHouseholdStatistics");

        // 异地搬迁统计
        registry.addViewController("/RelocationStatistics/list")
                .setViewName("DataStatistics/RelocationStatistics");

        // 就业
        registry.addViewController("/EploymentStatistics/list")
                .setViewName("DataStatistics/EploymentStatistics");

        //苏陕协作
        registry.addViewController("/CooperationStatistics/list")
                .setViewName("DataStatistics/CooperationStatistics");

        //综合信息
        registry.addViewController("/ComprehensiveStatistics/list")
                .setViewName("DataStatistics/ComprehensiveStatistics");

        //-----------------------------------报表-------------------------------------------
        //报表就业创业统计
        registry.addViewController("/Report1/list")
                .setViewName("Freport/Report1");
        //职业培训教育统计
        registry.addViewController("/Report2/list")
                .setViewName("Freport/Report2");
        //公益性岗位统计
        registry.addViewController("/Report3/list")
                .setViewName("Freport/Report3");
        //社区工厂
        registry.addViewController("/Report4/list")
                .setViewName("Freport/Report4");
        //就业扶贫基地统计
        registry.addViewController("/Report5/list")
                .setViewName("Freport/Report5");
        //创业基地园区中心统计
        registry.addViewController("/Report6/list")
                .setViewName("Freport/Report6");
        //信用乡村
        registry.addViewController("/Report7/list")
                .setViewName("Freport/Report7");
        //招聘会统计
        registry.addViewController("/Report8/list")
                .setViewName("Freport/Report8");
        //综合信息统计
        registry.addViewController("/Report9-1/list")
                .setViewName("Freport/Report9");
        //综合信息统计
        registry.addViewController("/Report9-2/list")
                .setViewName("Freport/Report9-1");
        //综合信息统计
        registry.addViewController("/Report9-3/list")
                .setViewName("Freport/Report9-3");
        //目标任务完成情况统计
        registry.addViewController("/Report10-1/list")
                .setViewName("Freport/Report10-1");
        //目标任务完成情况统计
        registry.addViewController("/Report10-2/list")
                .setViewName("Freport/Report10-2");
        //目标任务完成情况统计
        registry.addViewController("/Report11/list")
                .setViewName("Freport/Report11");
        //待完善统计
        registry.addViewController("/Report13/list")
                .setViewName("Freport/Report13");
        //技工院校教育统计
        registry.addViewController("/Report14/list")
                .setViewName("Freport/Report14");
        //弱劳动力
        registry.addViewController("/Report16/list")
                .setViewName("Freport/Report16");

    }


    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }

}
