import club.fuwenhao.utils.AESUtils;
import club.fuwenhao.utils.DateUtil;
import club.fuwenhao.utils.Md5Util;
import org.testng.annotations.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2021/2/5 4:29 下午
 */
public class UtilsTest {
    @Test
    public void md5Test() {
        System.out.println(Md5Util.getMD5("test"));
    }

    @Test
    public void dateTest() {
        System.out.println(DateUtil.dateToISODate(new Date()));
    }

    @Test
    public void aesTest() throws NoSuchAlgorithmException {
        String test = "123456";
        String secretKeyStr = AESUtils.getSecretKeyStr(test);
        String encrypt = AESUtils.aesEncrypt("zhangsan", secretKeyStr);
        System.out.println("密文：" + encrypt);

        String decrypt = AESUtils.aesDecrypt(encrypt, secretKeyStr);
        System.out.println("明文：" + decrypt);
    }
}
