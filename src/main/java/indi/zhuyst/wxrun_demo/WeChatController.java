package indi.zhuyst.wxrun_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.zhuyst.wxrun_demo.pojo.*;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class WeChatController {

    private static final String APP_ID = "wx2e999f269c68e438";
    private static final String APP_SECRET = "b9a3226d1ea8939a6c0a8881cba76824";

    private static final String OPEN_ID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid="
            + APP_ID + "&secret=" + APP_SECRET + "&js_code=JSCODE&grant_type=authorization_code";

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
            + APP_ID + "&secret=" + APP_SECRET;

    private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static OkHttpClient client = new OkHttpClient();

    @GetMapping("get")
    public String getUserDetails(String code) throws IOException {
        String url = OPEN_ID_URL.replace("JSCODE",code);

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @GetMapping("token")
    public AccessToken getAccessToken() throws IOException {
        Request request = new Request.Builder().url(ACCESS_TOKEN_URL).build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        return objectMapper.readValue(json,AccessToken.class);
    }

    @PostMapping("template")
    public String sendTemplate(String formId) throws IOException {
        AccessToken accessToken = getAccessToken();

        String url = TEMPLATE_URL.replace("ACCESS_TOKEN",accessToken.getAccessToken());
        Template template = new Template();
        template.setToUser("ouDgh0Tejfj96iQfUQBCPVOZOYTw");
        template.setFormId(formId);
        template.setTemplateId("V2eQxgz4iyEBIPMxbKM87cbxo-cY9UlVR_BE77eQC8E");
        template.putData("keyword1","活动名称测试");
        template.putData("keyword2","温馨提示测试");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        template.putData("keyword3",format.format(new Date()));

        String json = objectMapper.writeValueAsString(template);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @PostMapping("decrypt")
    public String getDecryptData(@org.springframework.web.bind.annotation.RequestBody  EncryptedData data) throws Exception{
        byte[] result = AesDecryptor.decrypt(Base64.decodeBase64(data.getEncryptedData()),
                Base64.decodeBase64(data.getSessionKey()),
                Base64.decodeBase64(data.getIv()));

        String resultStr = new String(result,"UTF-8");
        System.out.println(resultStr);
        return resultStr;
    }

    @PostMapping("run")
    public StepResult getWxRunResult(@org.springframework.web.bind.annotation.RequestBody EncryptedData data) throws Exception{
        String resultStr = getDecryptData(data);

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
