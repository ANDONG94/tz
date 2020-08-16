package com.tdkj.util;

import com.tdkj.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author:
 * @date:
 */
public class listSplit {

    /*public static void main(String[] args) {
       List<Object> list = new ArrayList();
        for (int i = 1; i <= 16; i++) {
            EmploymentSituationTmp employmentSituationTmp = new EmploymentSituationTmp();
            employmentSituationTmp.setEys001(i+"--------------");
            list.add(employmentSituationTmp);
        }
        List<List<Object>> result = splitListOBJ(list, 3);
        for (List<Object> list1:result) {

                for ( Object ew:
                        list1) {
                    EmploymentSituationTmp ee=  (EmploymentSituationTmp)ew;
                    System.out.println( ee.getEys001());
                }
            System.out.println(list1);
        }
        System.out.println(result);
        System.out.println("分隔后List个数：\t" + result.size());
    }*/

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分 就业
     *
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<T>> splitListES(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        //返回结果
        List<List<T>> result = new ArrayList<>();
        //传入集合长度
        int size = list.size();
        //分隔后的集合个数
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分 培训
     *
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<TrainingSituationTmp>> splitListTS(List<TrainingSituationTmp> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        //返回结果
        List<List<TrainingSituationTmp>> result = new ArrayList<>();
        //传入集合长度
        int size = list.size();
        //分隔后的集合个数
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<TrainingSituationTmp> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分 创业
     *
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<EntrepreneurshipTmp>> splitListEP(List<EntrepreneurshipTmp> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        //返回结果
        List<List<EntrepreneurshipTmp>> result = new ArrayList<>();
        //传入集合长度
        int size = list.size();
        //分隔后的集合个数
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<EntrepreneurshipTmp> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分 技校
     *
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<TechnicalSchoolsTmp>> splitListTHS(List<TechnicalSchoolsTmp> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        //返回结果
        List<List<TechnicalSchoolsTmp>> result = new ArrayList<>();
        //传入集合长度
        int size = list.size();
        //分隔后的集合个数
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<TechnicalSchoolsTmp> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }


    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分 技校
     *
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<Object>> splitListOBJ(List<Object> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        //返回结果
        List<List<Object>> result = new ArrayList<>();
        //传入集合长度
        int size = list.size();
        //分隔后的集合个数
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<Object> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
    public static void kill() {
        List<PoorLaborForce> list = new ArrayList<PoorLaborForce>();
        while (true){
            new Thread(){
                @Override
                public void run(){
                    PoorLaborForce poorLaborForce = new PoorLaborForce();
                    poorLaborForce.setPlf001(UUIDGenerator.generate()+"");
                    list.add(poorLaborForce);
                    System.out.println(list.size());
                }
            }.start();
        }

    }

    public static void main(String[] args) throws IOException {

      startProgram("D:\\Workspaces\\idea\\springboot\\src\\main\\resources\\static\\css\\demo\\c.bat");
    }


    public static void startProgram(String programPath) throws IOException {

        if (StringUtils.isNotBlank(programPath)) {
            try {
                Desktop.getDesktop().open(new File(programPath));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

}
