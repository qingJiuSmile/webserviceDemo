package cn.webservice.demo.service;

import cn.webservice.demo.util.Base64Decoder;
import cn.webservice.demo.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Calendar;

public class GTService {

    private static final Logger logger = LoggerFactory.getLogger(GTService.class);

    public static void main(String[] args) throws Exception {
        String privatekey = "3n4DdO47LWH2Co/WfpbdyA==";
        String token = getToken(privatekey);
        System.out.println(token+"");

        String json = "AIedhAcbnhXD9p4BOCK4Lwspz5yDQerVO8li4Bm27WCGRgIOHL5kCVZso09poY3Bt7O8yeS9Zfz0%0D%0A\n" +
                "        6sOT9DzHZ9ObZ9cJRN3jPUZjVg7t3BY%2BSczZinQd%2BgaWE6Ogx00t%0D%0A";
        String urlDecodeStr = URLDecoder.decode(json, "utf-8");
        boolean tokens = checkToken(token, privatekey);
        System.out.println("-------------"+tokens);
        // Base64 编码方式解码urlDecodeStr
        byte[] base64DecodeStr = Base64Decoder.decode(urlDecodeStr);
        // AES/ECB/PKCS5Padding 算法解密base64DecodeStr
        byte[] aesDecryptStr = decrypt(base64DecodeStr, Base64Decoder.decode(privatekey));
        // 将解密后的aesDecryptStr byte数组转为String类型 得到最终用
        String userJSON = new String(aesDecryptStr, "utf-8");
        System.out.println(userJSON);
    }
    private static byte[] getEncodeData(String jmysgz) {
        // 获取当前服务器时间戳
        Calendar cal = Calendar.getInstance();
        String datetime = DateUtil.dateToStr(cal.getTime(), "yyyyMMddHHmmss");
        long tokentimestamp = Long.parseLong(datetime);
        Integer ysgz = Integer.parseInt(jmysgz);
        // 例如时间戳为
        // 20190419132849*30=605712573985470
        String tokenTime = Long.toString(tokentimestamp * ysgz);
        System.out.println(tokenTime);
        return tokenTime.getBytes();
    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] data, byte[] privatekey)
            throws Exception {
        // 实例化
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key key = new SecretKeySpec(privatekey, "AES");
        // 使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 执行操作
        return cipher.doFinal(data);
    }
    private static String getToken(String privatekey) {
        //Base64解密密钥
        byte[] key = Base64Decoder.decode(privatekey);
        //获取系统时间戳根据运算规则运算
        byte[] data = getEncodeData("30");
        String token = null;
        try {
            // AES加密
            byte[] aesBytes = encrypt(data, key);
            // Base64加密
            String base64String = Base64Decoder.encode(aesBytes);
            // urlencode编码
            token = URLEncoder.encode(base64String, "UTF-8");
        } catch (Exception e) {
            logger.error("encode token error", e.getMessage());
        }
        return token;
    }
    /**
     * 解密
     */
    public static byte[] decrypt(byte[] data, byte[] privatekey)
            throws Exception {
        // 实例化
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key key = new SecretKeySpec(privatekey, "AES");
        // 使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 执行操作
        return cipher.doFinal(data);
    }


    private static boolean checkToken(String token, String privatekey) throws Exception {
        //Base64解密密钥
        byte[] key = Base64Decoder.decode(privatekey);
        //urldecode解码
        String urlDecodeString = URLDecoder.decode(token, "UTF-8");
        System.out.println(urlDecodeString);
        //Base64解密
        byte[] base64DecodeBytes = Base64Decoder.decode(urlDecodeString);
        //AES解密
        byte[] aesDecryptBytes = decrypt(base64DecodeBytes, key);
        //bytes to string
        String decryptToken = new String(aesDecryptBytes);
        //string to long
        long tokenTime = Long.parseLong(decryptToken);
        //获取校验误差开始时间
        long starttime = getStartData("30");
        //获取校验误差开始时间
        long endtime = getStartData("30");
        //校验误差token时间是否在误差范围中
        System.out.println("decryptToken========="+decryptToken);
        System.out.println("tokenTime========="+tokenTime);
        System.out.println("tokenTime========="+endtime);
        return starttime <= tokenTime && tokenTime <= endtime;
    }

    private static long getStartData(String jmysgz) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar. MINUTE, -5);
        String datetime = DateUtil.dateToStr(cal.getTime(), "yyyyMMddHHmmss");
        long tokentimestamp = Long.parseLong(datetime);
        Integer ysgz = Integer.parseInt(jmysgz);
        return tokentimestamp * ysgz;
    }
}
