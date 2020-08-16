package com.tdkj.util;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 74267
 * @date: 2020-06-13 10:53
 */
public class text {

    public static void main(String[] args) throws InterruptedException {


        TimeUnit.SECONDS.sleep(3);
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println(Thread.currentThread().getName());

    }
}
