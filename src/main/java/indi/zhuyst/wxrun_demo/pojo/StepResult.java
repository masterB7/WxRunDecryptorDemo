package indi.zhuyst.wxrun_demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class StepResult {

    private List<StepInfo> stepInfoList;

    private Watermark watermark;
}
