package com.zhs.demo.utils;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 */
@Service
public class CommonUtils {


    /**
     * 校验邮箱的格式
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * 获取UUID
     * @return
     */
    public String getUUID(){
        String uuid= UUID.randomUUID().toString().replace("-","");
        return uuid;
    }


    /**
     * 对数据进行加密
     * @param data 要加密的数据
     * @param salt 加密的盐值
     * @return
     */
    public String encryptDataMD5(String data, String salt){

        ByteSource newSalt = ByteSource.Util.bytes(salt);

        /*
         * MD5加密：
         * 使用SimpleHash类对原始密码进行加密。
         * 第一个参数代表使用MD5方式加密
         * 第二个参数为原始密码
         * 第三个参数为盐值，即用户名
         * 第四个参数为加密次数
         * 最后用toHex()方法将加密后的密码转成String
         */

        String newData = new SimpleHash("MD5", data, newSalt, 1024).toHex();

        return newData;
    }


    /**
     * 获取当前时间
     * @return
     */
    public String getDateString(){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String date=format.format(new Date());
        return date;
    }


    /**
     * 生成文件名
     * @param type
     * @param suffix  后缀
     * @return
     */
    public String getFileName(String type, String suffix){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String date=format.format(new Date());
        String fileName=type + date + suffix;
        return fileName;
    }

}
