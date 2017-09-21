package com.zhouchatian.voicetotext;


/**
 * 在线识别，用于展示在线情况下的识别参数和效果。
 */
public class ActivityOnline extends ActivityRecog{

    @Override
    protected CommonRecogParams getApiParams() {
        return new OnlineRecogParams(this);
    }


}