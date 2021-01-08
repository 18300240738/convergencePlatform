package com.qr.encryption;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

@Slf4j
public class SM4Utils {
	String secretKey = "";
	String iv = "";
	boolean hexString = false;
	String patternStr = "\\s*|\t|\r|\n";

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public void setHexString(boolean hexString) {
		this.hexString = hexString;
	}

	public String encryptDataECB1(byte[] inputStream){
        try
        {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString)
            {
                keyBytes = Util.hexStringToBytes(secretKey);
            }
            else
            {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            String cipherText = new Base64().encodeToString(inputStream);
            if (cipherText != null && cipherText.trim().length() > 0)
            {
                Pattern p = Pattern.compile(patternStr);
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            return cipherText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

	public String encryptDataECB(String plainText) {
		try
		{
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_ENCRYPT;

			byte[] keyBytes;
			if (hexString)
			{
				keyBytes = Util.hexStringToBytes(secretKey);
			}
			else
			{
				keyBytes = secretKey.getBytes();
			}

			SM4 sm4 = new SM4();
			sm4.sm4_setkey_enc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("GBK"));
			String cipherText = new Base64().encodeToString(encrypted);
			if (cipherText != null && cipherText.trim().length() > 0)
			{
				Pattern p = Pattern.compile(patternStr);
				Matcher m = p.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public String decryptDataECB(String cipherText) {
		try {
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_DECRYPT;

			byte[] keyBytes;
			if (hexString) {
				keyBytes = Util.hexStringToBytes(secretKey);
			} else {
				keyBytes = secretKey.getBytes();
			}

			SM4 sm4 = new SM4();
			sm4.sm4_setkey_dec(ctx, keyBytes);
			byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new Base64().decode(cipherText));
			return new String(decrypted, "GBK");
		} catch (Exception e) {
			log.error("SM4解密失败:{}",e.getMessage());
			return null;
		}
	}

	public String encryptDataCBC(String plainText) {
		try
		{
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_ENCRYPT;

			byte[] keyBytes;
			byte[] ivBytes;
			if (hexString)
			{
				keyBytes = Util.hexStringToBytes(secretKey);
				ivBytes = Util.hexStringToBytes(iv);
			}
			else
			{
				keyBytes = secretKey.getBytes();
				ivBytes = iv.getBytes();
			}

			SM4 sm4 = new SM4();
			sm4.sm4_setkey_enc(ctx, keyBytes);
			byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("GBK"));
			String cipherText = new Base64().encodeToString(encrypted);
			if (cipherText != null && cipherText.trim().length() > 0)
			{
				Pattern p = Pattern.compile(patternStr);
				Matcher m = p.matcher(cipherText);
				cipherText = m.replaceAll("");
			}
			return cipherText;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *  CBC模式加密
	 * @Author wd
	 * @Date 15:37 2020/9/16
	 **/
	public String decryptDataCBC(String cipherText) {
		try
		{
			SM4_Context ctx = new SM4_Context();
			ctx.isPadding = true;
			ctx.mode = SM4.SM4_DECRYPT;

			byte[] keyBytes;
			byte[] ivBytes;
			if (hexString)
			{
				keyBytes = Util.hexStringToBytes(secretKey);
				ivBytes = Util.hexStringToBytes(iv);
			}
			else
			{
				keyBytes = secretKey.getBytes();
				ivBytes = iv.getBytes();
			}

			SM4 sm4 = new SM4();
			sm4.sm4_setkey_dec(ctx, keyBytes);
			byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new Base64().decode(cipherText));
			return new String(decrypted, "GBK");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
