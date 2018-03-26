package indi.zhuyst.wxrun_demo.pojo;

import lombok.Data;

@Data
public class EncryptedData {

    private String encryptedData;

    private String sessionKey;

    private String iv;
}
