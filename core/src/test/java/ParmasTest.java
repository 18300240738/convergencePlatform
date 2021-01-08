import com.qr.encryption.SM3Digest;
import com.qr.encryption.SM4Utils;
import org.bouncycastle.util.encoders.Hex;

public class ParmasTest {

	public static void main(String[] args) {
		//生成sm4密钥
//		byte[] md = new byte[32];
//		byte[] msg1 = "0CA2D5010127AC7EE3BE5BA5302E4B2CA07E1D58E794E6210D40C6482E23F3DB".getBytes();
//
//		SM3Digest sm3 = new SM3Digest();
//		sm3.update(msg1, 0, msg1.length);
//		sm3.doFinal(md, 0);
//		String s = new String(Hex.encode(md));
//		System.out.println(s.toUpperCase());


		String plainText = "{\"dataName\":\"PERSON\",\"data\":{\"IDType\":\"1\",\"IDNumber\":\"12314141111\",\"Name\":\"张三\",\"GenderCode\":\"1\",\"SpecialPersonClass\":\"1\",\"SpecialPersonClassStr\":\"12324\",\"HouseholdType\":\"10\",\"RegistrationType\":\"1\",\"PopulationType\":\"1\",\"LXDH\":\"18312345679\",\"DataSourceID\":\"1\",\"ADDRESS\":\"1是犯法付\",\"COMMUNITY_CODE\":\"1是犯法付\"}}";

		SM4Utils sm4 = new SM4Utils();
		sm4.setSecretKey("13C4EC68EF3C78C2F7D5868E6179A5B4A2D43377C66349409E4D7F1E6CB1C4BA");
		sm4.setHexString (false);

		System.out.println("ECB模式加密");
		String cipherText = sm4.encryptDataECB(plainText);
		System.out.println("密文: " + cipherText);
		System.out.println("");

		String plainText1 = sm4.decryptDataECB(cipherText);
		System.out.println("明文: " + plainText1);
		System.out.println("");

	}

}
