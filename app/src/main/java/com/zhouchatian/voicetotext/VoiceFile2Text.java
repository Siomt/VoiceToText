package com.zhouchatian.voicetotext;


import android.os.Environment;
import com.baidu.speech.asr.SpeechConstant;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Robot on 2017/9/21.
 * https://github.com/TheSadFrog
 */

public class VoiceFile2Text {

    private static final String TAG = "VoiceFile2Text";


    // Map<String, Object> params =  "accept-audio-data":false,"disable-punctuation":false,"accept-audio-volume":true,"pid":1736
    public static String getVoice() {
        Map<String, Object> map = new HashMap<String, Object>();
        //添加pid
//        PidBuilder builder = new PidBuilder();
//        map = builder.addPidInfo(map); // 生成PID， PID 在线有效
        map.put(SpeechConstant.PID,1536);
        map.put(SpeechConstant.ACCEPT_AUDIO_DATA,false);
        map.put(SpeechConstant.DISABLE_PUNCTUATION,false);
        map.put(SpeechConstant.ACCEPT_AUDIO_VOLUME,true);
        String path = Environment.getExternalStorageDirectory().toString();
        //语音文件路径
        map.put(SpeechConstant.IN_FILE, path + "/goldfallen.pcm");
//        map.put(SpeechConstant.OUT_FILE, path + "/outfile.mp3");
        Logger.info(TAG, "语音录音文件将保存在：" + path + "/goldfallen.mp3");

        //发送给百度后台
        String json = new JSONObject(map).toString();
        Logger.info(TAG + ".Debug", "asr params(反馈请带上此行日志):" + json);

        return json;
    }
}
