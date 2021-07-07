package demo.utils;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

public class HuCryptoUtil {

    public static String encryptData(String data, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.encryptBase64(data, KeyType.PublicKey);
    }

    public static String decryptData(String decodedData, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(decodedData, KeyType.PrivateKey);
    }

    public static void main(String[] args) {
//        RSA rsa = new RSA();
////        System.out.println(rsa.getPrivateKeyBase64());
////        System.out.println(rsa.getPublicKeyBase64());
//        String data = "111";
//        String decodedData = rsa.encryptBase64(data, KeyType.PublicKey);
//        System.out.println(decodedData);
//        System.out.println(rsa.decryptStr(decodedData, KeyType.PrivateKey));

////        生成公私钥
//        RSA rsa = new RSA();
//        String publicKey = rsa.getPublicKeyBase64();
//        String privateKey = rsa.getPrivateKeyBase64();
////        公钥加密
//        String content = "111";
//        String decodedContent = encryptData(content, publicKey);
//        System.out.println(decodedContent);
////        私钥解密
//        System.out.println(decryptData(decodedContent, privateKey));

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnBApXCLZ54Evy/xTZA0rjMybJ3/VtYPh1z1F7nxQ0UtsDCfLDxPdZn0NarlOAwS2i3E6eCEYDUWfDr4tcEo0bPIimDgplMjq73rCrfPajyCOWKVKbdgU17rfhsHC7kxFmj2y1EDDqlK5N6jFI6rEgHO1R8cIIIteJH2RYK4jW2QIDAQAB";
        RSA rsa = new RSA(null, publicKey);
        System.out.println(rsa.encryptBase64("111", KeyType.PublicKey));
    }
}
