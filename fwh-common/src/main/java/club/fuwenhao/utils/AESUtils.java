package club.fuwenhao.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @program: fwh-parent
 * @description: AES工具类
 * @author: fwh
 * @date: 2021-04-29 17:19
 **/
public class AESUtils {
    private static final Logger log = LoggerFactory.getLogger(AESUtils.class);
    private static final String KEY_ALGORITHMS = "AES";
    private static final String CHAR_SETS = "UTF-8";
    private static final Integer SECRET_KEY_LENGTHS = 128;
    private static final String DEFAULT_CIPHER_ALGORITHMS = "AES/ECB/PKCS5Padding";

    public AESUtils() {
    }

    /**
     * 转为十六进制
     */
    private static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    /**
     * 转为二进制
     */
    private static byte[] asBin(String src) {
        if (src.length() < 1) {
            return null;
        }
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    /**
     * 生成秘钥
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKeySpec getSecretKey(String password) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(SECRET_KEY_LENGTHS, new SecureRandom(password.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }

    /**
     * 生成秘钥-string
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSecretKeyStr(String password) throws NoSuchAlgorithmException {
        SecretKeySpec secretKey = getSecretKey(password);
        return asHex(secretKey.getEncoded());
    }

    /**
     * 加密
     *
     * @param sSrc
     * @param privateKey
     * @return
     */
    public static String aesEncrypt(String sSrc, String privateKey) {
        if (sSrc != null && !"".equals(sSrc)) {
            byte[] encrypted = new byte[0];

            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                byte[] raw = privateKey.getBytes();
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                cipher.init(1, skeySpec);
                encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            } catch (NoSuchAlgorithmException var6) {
                var6.printStackTrace();
            } catch (NoSuchPaddingException var7) {
                var7.printStackTrace();
            } catch (InvalidKeyException var8) {
                var8.printStackTrace();
            } catch (IllegalBlockSizeException var9) {
                var9.printStackTrace();
            } catch (BadPaddingException var10) {
                var10.printStackTrace();
            } catch (UnsupportedEncodingException var11) {
                var11.printStackTrace();
            }

            return Base64.encodeBase64String(encrypted);
        } else {
            return null;
        }
    }

    public static String aesDecrypt(String sSrc, String privateKey) {
        if (sSrc != null && !"".equals(sSrc)) {
            try {
                byte[] raw = privateKey.getBytes("ASCII");
                SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(2, skeySpec);
                byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes("UTF-8"));
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception var8) {
                var8.printStackTrace();
                log.error(var8.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
}
