package cn.allms.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by limi on 2017/10/15.
 */
public class MD5Utils {

    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String code(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int temp;
            StringBuilder buf = new StringBuilder("");
            for (byte b : byteDigest) {
                temp = b;
                if (temp < 0) {
                    temp += 256;
                }
                if (temp < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(temp));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void main(String[] args) {
        System.out.println(code("qfmx0722"));
    }
}
