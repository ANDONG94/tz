package com.tdkj.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by len on 2018-03-08.
 */
public class FileUtil {


    /**
     * 文件上传的公共方法
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
