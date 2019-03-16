package com.bdhanbang.weixin.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
 
/**   
 * @Title: MD5Utils.java 
 * @Package com.fendo.MD5 
 * @Description: TODO
 * @author fendo
 * @date 2017年9月11日 上午11:56:20 
 * @version V1.0   
*/
public class MD5Utils {
 
    /** 16进制的字符串数组 */
    private final static String[] hexDigitsStrings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };
    
    /** 16进制的字符集 */
    private final static char [] hexDigitsChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8',  
            '9', 'A', 'B', 'C', 'D', 'E', 'F'}; 
    
    /** 
     * MD5加密字符串
     * 
     * @param source 源字符串 
     * 
     * @return 加密后的字符串 
     * 
     */  
    public static String getMD5(String source) {  
        String mdString = null;  
        if (source != null) {  
            try {  
                mdString = getMD5(source.getBytes("UTF-8"));  
            } catch (UnsupportedEncodingException e) {  
                e.printStackTrace();  
            }  
        }  
        return mdString;  
    }
    
    /** 
     * MD5加密以byte数组表示的字符串
     * 
     * @param source 源字节数组 
     * 
     * @return 加密后的字符串 
     */  
    public static String getMD5(byte[] source) {  
        String s = null;  
 
        final int temp = 0xf;  
        final int arraySize = 32;  
        final int strLen = 16;  
        final int offset = 4;  
        try {  
            java.security.MessageDigest md = java.security.MessageDigest  
                    .getInstance("MD5");  
            md.update(source);  
            byte [] tmp = md.digest();  
            char [] str = new char[arraySize];  
            int k = 0;  
            for (int i = 0; i < strLen; i++) {  
                byte byte0 = tmp[i];  
                str[k++] = hexDigitsChar[byte0 >>> offset & temp];  
                str[k++] = hexDigitsChar[byte0 & temp];  
            }  
            s = new String(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return s;  
    }
    
    /**
     * * 获取文件的MD5值
     * 
     * @param file
     *            目标文件
     * 
     * @return MD5字符串
     * @throws Exception 
     */
    public static String getFileMD5String(File file) throws Exception {
        String ret = "";
        FileInputStream in = null;
        FileChannel ch = null;
        try {
            in = new FileInputStream(file);
            ch = in.getChannel();
            ByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                    file.length());
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            messageDigest.update(byteBuffer);
            ret = byteArrayToHexString(messageDigest.digest());
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ch != null) {
                try {
                    ch.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
 
    /**
     * * 获取文件的MD5值
     * 
     * @param fileName
     *            目标文件的完整名称
     * 
     * @return MD5字符串
     * @throws Exception 
     */
    public static String getFileMD5String(String fileName) throws Exception {
        return getFileMD5String(new File(fileName));
    }
    
    /**
     * 加密
     * 
     * @param source
     *            需要加密的原字符串
     * @param encoding
     *            指定编码类型
     * @param uppercase
     *            是否转为大写字符串
     * @return
     */
    public static String MD5Encode(String source, String encoding, boolean uppercase) {
        String result = null;
        try {
            result = source;
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信息
            messageDigest.update(result.getBytes(encoding));
            // messageDigest.digest()获得16位长度
            result = byteArrayToHexString(messageDigest.digest());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uppercase ? result.toUpperCase() : result;
    }
    
    
    /**
     * 转换字节数组为16进制字符串
     * 
     * @param bytes
     *            字节数组
     * @return
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tem : bytes) {
            stringBuilder.append(byteToHexString(tem));
        }
        return stringBuilder.toString();
    }
    
    /**
     * * 将字节数组中指定区间的子数组转换成16进制字符串
     * 
     * @param bytes
     *            目标字节数组
     * 
     * @param start
     *            起始位置（包括该位置）
     * 
     * @param end
     *            结束位置（不包括该位置）
     * 
     * @return 转换结果
     */
    public static String bytesToHex(byte bytes[], int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < start + end; i++) {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }
    
    /**
     * 转换byte到16进制
     * 
     * @param b
     *            要转换的byte
     * @return 16进制对应的字符
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigitsStrings[d1] + hexDigitsStrings[d2];
    }
    
    
    
    /**
     * * 校验密码与其MD5是否一致
     * 
     * @param pwd
     *            密码字符串
     * 
     * @param md5
     *            基准MD5值
     * 
     * @return 检验结果
     */
    public static boolean checkPassword(String pwd, String md5) {
        return getMD5(pwd).equalsIgnoreCase(md5);
    }
    
    /**
     * * 校验密码与其MD5是否一致
     * 
     * @param pwd
     *            以字符数组表示的密码
     * 
     * @param md5
     *            基准MD5值
     * 
     * @return 检验结果
     */
    public static boolean checkPassword(char[] pwd, String md5) {
        return checkPassword(new String(pwd), md5);
    }
    
    
    /**
     * * 检验文件的MD5值
     * 
     * @param file
     *            目标文件
     * 
     * @param md5
     *            基准MD5值
     * 
     * @return 检验结果
     * @throws Exception 
     */
    public static boolean checkFileMD5(File file, String md5) throws Exception {
        return getFileMD5String(file).equalsIgnoreCase(md5);
    }
    
    /**
     * * 检验文件的MD5值
     * 
     * @param fileName
     *            目标文件的完整名称
     * 
     * @param md5
     *            基准MD5值
     * 
     * @return 检验结果
     * @throws Exception 
     */
    public static boolean checkFileMD5(String fileName, String md5) throws Exception {
        return checkFileMD5(new File(fileName), md5);
    }
}
