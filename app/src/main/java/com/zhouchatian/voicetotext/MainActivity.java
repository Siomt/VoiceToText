package com.zhouchatian.voicetotext;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import static com.zhouchatian.voicetotext.VoiceFile2Text.getVoice;

public class MainActivity extends AppCompatActivity {

    private EventManager asr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asr = EventManagerFactory.create(getApplicationContext(), "asr");
        EventListener yourListener = new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
                if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)){
                    // 引擎就绪，可以说话，一般在收到此事件后通过UI通知用户可以说话了
                }
                if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)){
                    // 识别结束
                    TextView tv = (TextView) findViewById(R.id.text_view);
                    tv.setText(params);
                    Log.d("MainActivity",params);
                }
                // ... 支持的输出事件和事件支持的事件参数见“输入和输出参数”一节
            }
        };
        asr.registerListener(yourListener);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json = VoiceFile2Text.getVoice();
                asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
            }
        });
    }
}
