import com.baomidou.mybatisplus.extension.api.IErrorCode;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.util.Assert;

import static com.qr.utils.AppUtils.getAppId;
import static com.qr.utils.AppUtils.getAppSecret;
import static org.junit.Assert.assertNull;

public class AppTest {

	@Test
	public void test(){
		assertNull(getAppId());
		String appId = getAppId();
		String appSecret = getAppSecret(appId,"1234567890");
		System.out.println("appId: "+appId);
		System.out.println("appSecret: "+appSecret);
	}
}
