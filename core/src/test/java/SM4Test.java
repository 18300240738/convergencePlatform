import com.qr.encryption.SM3;
import com.qr.encryption.SM3Digest;
import com.qr.encryption.SM4Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.cookie.SM;
import org.bouncycastle.util.encoders.Hex;

@Slf4j
public class SM4Test {

	public static void main(String[] args) {
		String plainText = "ererfeiisgod";

		SM4Utils sm4 = new SM4Utils();

		sm4.setSecretKey("JeF8U9wHFOMfs2Y8");
		sm4.setHexString (false);
//
//		log.info("ECB模式加密");
//		String cipherText = sm4.encryptDataECB(plainText);
//		log.info("密文: " + cipherText);
//		log.info("");
//
//		plainText = sm4.decryptDataECB(cipherText);
//		log.info("明文: " + plainText);
//		log.info("");
//
//		log.info("CBC模式加密");
		sm4.setIv("UISwD9fW6cFh9SNS");
//		cipherText = sm4.encryptDataCBC(plainText);
//		log.info("密文: " + cipherText);
//		log.info("");
//
//		plainText = sm4.decryptDataCBC(cipherText);
//		log.info("明文: " + plainText);
//
		log.info("CBC模式解密");
		log.info("密文：4esGgDn/snKraRDe6uM0jQ==");
		String cipherText2 = "4esGgDn/snKraRDe6uM0jQ==";
		plainText = sm4.decryptDataCBC(cipherText2);
		log.info("明文: " + plainText);

		//	SM4密钥生成策略
//		String plainText = "appId" + "~" + "Payload" + "~" + "salt";
//		byte[] md = new byte[32];
//		byte[] msg1 = plainText.getBytes();
//		SM3Digest sm3 = new SM3Digest();
//		sm3.update(msg1, 0, msg1.length);
//		sm3.doFinal(md, 0);
//		String s = new String(Hex.encode(md));
//		log.info("sm3生成sm4的密钥:{}",s.toUpperCase());
	}
}
