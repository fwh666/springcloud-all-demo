package club.fuwenhao.utils;

import org.apache.commons.lang3.ArrayUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @program: fwh-parent
 * @description: TokenGenerateUtil
 * @author: fwh
 * @date: 2021-04-29 17:53
 **/
public class TokenGenerateUtil {
    public TokenGenerateUtil() {
    }

    public static final String md5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");

            try {
                mdTemp.update(s.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException var9) {
                mdTemp.update(s.getBytes());
            }

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return (new String(str)).toUpperCase();
        } catch (Exception var10) {
            return null;
        }
    }

    public static final String buildToken(String url, String paramJson, String secret) {
        String tempUrl = null;
        tempUrl = url.substring("http://".length());
        int index = tempUrl.indexOf("/");
        String URI = tempUrl.substring(index);
        String[] ss = URI.split("\\?");
        return ss.length > 1 ? md5(ss[0] + ss[1] + secret) : md5(ss[0] + paramJson + secret);
    }

    public static final String buildToken(String[] parm, String paramJson, String secret) {
        return parm.length > 1 ? md5(parm[0] + parm[1] + secret) : md5(parm[0] + paramJson + secret);
    }

    public static final String jdkBase64(String parm) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(parm.getBytes());
        return encode;
    }

    public static final String getSHA1(String... params) throws Exception {
        try {
            StringBuffer sb = new StringBuffer();
            Arrays.sort(params);

            for (int i = 0; i < params.length; ++i) {
                sb.append(params[i]);
            }

            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";

            for (int i = 0; i < digest.length; ++i) {
                shaHex = Integer.toHexString(digest[i] & 255);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }

                hexstr.append(shaHex);
            }

            return hexstr.toString();
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }
    }

    public static final String[] getSeSeedKeyPairs(String seed) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed(seed.getBytes());
            keyPairGenerator.initialize(1024, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("publicKey : " + (new BASE64Encoder()).encode(publicKey.getEncoded()));
            System.out.println("privateKey : " + (new BASE64Encoder()).encode(privateKey.getEncoded()));
            String[] keyArr = new String[]{(new BASE64Encoder()).encode(publicKey.getEncoded()), (new BASE64Encoder()).encode(privateKey.getEncoded())};
            return keyArr;
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static String encodeBase64(String str) throws Exception {
        String base64Str = (new BASE64Encoder()).encode(str.getBytes("UTF-8"));
        return base64Str;
    }

    public static String decodeBase64(String base64Str) throws Exception {
        byte[] bytes = (new BASE64Decoder()).decodeBuffer(base64Str);
        String generalStr = new String(bytes, "UTF-8");
        return generalStr;
    }

    public static String getPrivateData(String publicCode, String data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PublicKey pubKey = getPublicKey(publicCode);
            cipher.init(1, pubKey);
            byte[] cipherText = null;

            for (int i = 0; i < data.getBytes().length; i += 100) {
                byte[] var = ArrayUtils.subarray(data.getBytes(), i, i + 100);
                byte[] doFinal = cipher.doFinal(var);
                if (cipherText == null) {
                    cipherText = doFinal;
                } else {
                    cipherText = ArrayUtils.addAll(cipherText, doFinal);
                }
            }

            return (new BASE64Encoder()).encode(cipherText);
        } catch (NoSuchAlgorithmException var8) {
            var8.printStackTrace();
        } catch (NoSuchPaddingException var9) {
            var9.printStackTrace();
        }

        return "";
    }

    public static String getDecryptDta(String privateCode, String data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PrivateKey privKey = getPrivateKey(privateCode);
            cipher.init(2, privKey);
            byte[] dataBates = (new BASE64Decoder()).decodeBuffer(data);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < dataBates.length; i += 128) {
                byte[] var1 = ArrayUtils.subarray(dataBates, i, i + 128);
                byte[] doFinal = cipher.doFinal(var1);
                sb.append(new String(doFinal));
            }

            String dataReturn = sb.toString();
            return dataReturn;
        } catch (NoSuchAlgorithmException var9) {
            var9.printStackTrace();
        } catch (NoSuchPaddingException var10) {
            var10.printStackTrace();
        }

        return "";
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
}
