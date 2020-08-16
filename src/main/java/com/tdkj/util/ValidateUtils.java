package com.tdkj.util;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;
/**
 * @Desc 用于后台手机号的验证工具类
 * @Date 2019/3/12 19:21
 * @Author cui_yl
 */
public class ValidateUtils {

    private static final String REGEX_MOBILE ="((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";
    private static final String regex1 = "0\\d{2,3}-\\d{7,8}|\\(0\\d{2,3}\\)\\d{7,8}";
    private static final String regex2 = "^((\\d{4})?(\\-)?\\d{7,8}|\\d{3}\\-\\d{6}|(\\d{3}\\-\\d{7}-\\d{3}))$";

    /**
         * 判断是否是手机号
         * @param tel 手机号
         * @return boolean true:是  false:否
         */
        public static boolean isMobile(String tel) {
            if (StringUtils.isEmpty(tel)){ return false;}
            boolean bookean= Pattern.matches(REGEX_MOBILE, tel);
            boolean bookean2= Pattern.matches(regex1, tel);
            boolean bookean3= Pattern.matches(regex2, tel);
            System.out.println("bookean=="+bookean);
            System.out.println("bookean2=="+bookean2);
            System.out.println("bookean3=="+bookean3);
            if(bookean||bookean2||bookean3){
                return true;
            }
            return false;
        }

    public static void main(String[] args) {
        System.out.println(isMobile("19992321456"));
    }
    }


