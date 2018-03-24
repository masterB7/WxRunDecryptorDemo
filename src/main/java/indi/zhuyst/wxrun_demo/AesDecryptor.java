package indi.zhuyst.wxrun_demo;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

public class AesDecryptor {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] decrypt(byte[] encryptedData,byte[] key,byte[] iv) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        Key sKeySpec = new SecretKeySpec(key, "AES");

        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);
        return cipher.doFinal(encryptedData);
    }
}
