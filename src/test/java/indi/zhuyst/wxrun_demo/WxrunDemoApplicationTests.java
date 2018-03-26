package indi.zhuyst.wxrun_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.zhuyst.wxrun_demo.pojo.StepInfo;
import indi.zhuyst.wxrun_demo.pojo.StepResult;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class WxrunDemoApplicationTests {

	@Test
	public void contextLoads() throws Exception {
		String sessionKey = "4KMSupTtoewRpHGDE0WEMA==";
		String encryptedData = "huOEc5wd/EIoqcupX4diA/NWrRO4p7SNBl7igEh2MLXHCpXV9KxlTfTCntlu3uDmEfS7kj9GkatUcDrEUducRm9Z0vdomlW7Jp1r19bMPXlrEMRkhjdfUZqEit7RAIqTJIULgwssUfrF1j5LrGEHwM4i99l8CiQNr/rlvWy3H+HfeBdCjpXqWAu711YCRzsgibKYuniZqrmql0zB3PtOcbwjJP5+dOl8lX/mmoQZ94W+h1aEHRhPUi8yHerJyw2Qyhw3D9CVdN3rmYbUb6r73vcQZSwh3BiZs+fbVEsdB/UKsKD631nMOprfjhY0Y3/YM80pvcWDc+MAtRJudU/gjD8T2qLrjYFgY9SmlhqD8gKUICNcPVAjU+NqVzTDtPWVGrfnQQFNgq7WCCxD4mUly6aXvi6JtOE6Y0nx5effwEn8VMmVGS9bhVGwYWVcKop7HGvsf77lEiA/9fo0UnLGLxvWk5S+Jmmu7DSaTTPvm4/Hql/69hbGW2WcKU4ld6lySnDKsdnH5rx+7yKYL1n6dhzWBwYJgfC2eJluQqYyxpatUNdGRDyoBPCNnTvFtHi6YrU/pH8jv7gyCmk9BuWCI3I4Z57BF3j0NTcCAUFOV4vVY5leFhDOBPPFL9Y4h2FcWuIbFdJGyWoOQQ5qM/8vnt/JPsimwdgyrp7PwSMOrGBZWxPjFNiaUjrsI9MFRh5WxRpJ9GhAtJyLSHRzX6xPFfai5LV9vYRKoL7snbTpycHR4bQo56t8o3IicB0C5LBs+FtWZh7jfIy3nFX6EkZTuESNQ/e50Vp04ZvLvb6KN9XHYUTCDpmF0k7/uqjv7OfIGsAbj9aNotExhm8U0ZMfUjXOtRP7ALGOB5BxoTIQskBJFCBBDksbYTfE6oZ2Gm0mljzE5B4AM6Qu9AXGSmRjZKRBzs08XW3napiXvNlzaBQ4qNBv6nts2GM/YwVK4LungVIBaHp0VHX9MUBLTxy9PJI4k6uyvKoMRRKlbGCDhMTILxF7g2PEHXDYwib8aobMrjWPTlxkJhbLw8brdY25w9eRxhw+SuQHfFjiZbhrZDRD9CEwCztIuHZ7PULIkxGnYlAacXDQtAft4/XYvg4G3wMAWkdukMYa7Ilu93MZPejAyPVq61BBXxuMOz3wHMmxdyVRD17cAKPT8nSFHV7dhRvO5CAf5xyh/5eaf0mlywbrAgrFK+eKyVarFHMCX1POqRvDqPqEkwGZdmf0A0RriDm8Lx/2HRnQdTrZSk7mslGV4kK5xguU3mL3IJsEHTYRHFqYGFbw9F2SoHebWXoyQd/H0sRdoi1J6uQvqafDT/ZVLKoWD72Qo2Bsx0sh6emSAAk7UNDmKJRqQUQ0EcAGjUd54RjgIZvkL5fK9x2zlCAUi45FN+LDulRrGFBWaxYctd+TEETlMfWZHAZQXT8rSKKoOo3WJwyp0FEDuDh9j188APqhXnoKzM5iaktkvTxLOVGMFXsSsMnlXqJOgYg0yBHPSypc4zQpuD+XchieZMk8sy0h8jvhZAWTlu+Iih2CRzwmBWbK2R14wW2FTfnUKF0xRn1w3VpUduW12L4p2ier3z8/raXbtkQQZ9sVyZpzb64SXg/LH/KwFLyb6Xq8ta95e4H+jO+Ip4BS0PhTT88=";
		String iv = "OVpesf2UxMRPcgEzsqY5yg==";

		byte[] result = AesDecryptor.decrypt(Base64.decodeBase64(encryptedData),
				Base64.decodeBase64(sessionKey),
				Base64.decodeBase64(iv));

		String resultStr = new String(result,"UTF-8");
		System.out.println(resultStr);

		ObjectMapper objectMapper = new ObjectMapper();
		StepResult stepResult = objectMapper.readValue(resultStr,StepResult.class);

		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		for(StepInfo info : stepResult.getStepInfoList()){
			Date date = new Date(info.getTimestamp() * 1000L);

			System.out.println(format.format(date)
					+ " —— " + info.getStep() + "步");
		}
	}

	@Test
	public void test() throws Exception{
		String sessionKey = "W3NxSxKqlqWsFZvQoco1wg==";
		String encryptedData = "e52FZc2z10RJNrTpiAczIkrzai966XxCIt0Ao1Q0s9BGnQ3NO/I7JZ3huE1xgL7wZlPSxazFND6zBq5m+TEsB2bWQEf0I3iApJ0/1rkPMp4LTypiTN2AuImuEFrTQMpJsV9JSgCQtboNOLCorPgXmct3I7W28S0kRfh9WrZ6Fel1KgBIFsoPJ/Scrjhi915GwdQpUODawNlUzbv4xrcbTWiwEeojnfxlDshTwdOgNYBN0DqLfVpbZ/hRiiYiHaP33ag+Sn9Km4rEswq+mN6FXhkmOPW+pcNcPpWEyCM1V1j05a3sQfgIotUODziKb2qmjsEuzJ1XqTsRaF03akM64SzQPTy5pb0wx0ja9+bElbibbZoM8pf2D2jHFMmhGu09HKY9WUjIexfn9vpRcMGAKEwJI32L21vzLJE0quHWBWPoU4FB24Ek+YX+wOgNKxt5VNjn9JO8Z+yW8m+n8AUkVLP135ARtw//oV0vJaicNVSUXlqtREoTccaB/mxKp8BqosLQrBgg+mwMakJAlS4sIA==";
		String iv = "ShnlQ7Sonv1gsTiHoLxjzg==";

		byte[] result = AesDecryptor.decrypt(Base64.decodeBase64(encryptedData),
				Base64.decodeBase64(sessionKey),
				Base64.decodeBase64(iv));

		String resultStr = new String(result,"UTF-8");
		System.out.println(resultStr);
	}

}
