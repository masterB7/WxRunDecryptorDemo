package indi.zhuyst.wxrun_demo.pojo;

import lombok.Data;

@Data
public class WxRunEncryptedData {

    private String encryptedData;

    private String sessionKey;

    private String iv;
}
