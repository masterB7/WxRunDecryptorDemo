package indi.zhuyst.wxrun_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.zhuyst.wxrun_demo.pojo.StepInfo;
import indi.zhuyst.wxrun_demo.pojo.StepResult;
import indi.zhuyst.wxrun_demo.pojo.WxRunEncryptedData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class WeChatController {

    private static final String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    private static final String APP_ID = "wx2e999f269c68e438";
    private static final String APP_SECRET = "b9a3226d1ea8939a6c0a8881cba76824";

    private static OkHttpClient client = new OkHttpClient();

    @GetMapping("get")
    public String getUserDetails(String code) throws IOException {
        String url = URL.replace("APPID",APP_ID)
                .replace("SECRET",APP_SECRET)
                .replace("JSCODE",code);

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @PostMapping("run")
    public StepResult getWxRunResult(WxRunEncryptedData data) throws Exception{
        byte[] result = AesDecryptor.decrypt(Base64.decodeBase64(data.getEncryptedData()),
                Base64.decodeBase64(data.getSessionKey()),
                Base64.decodeBase64(data.getIv()));

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

        return stepResult;
    }
}
