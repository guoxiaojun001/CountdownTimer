package activity.app.gxj.com.countdowntimer;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;

import activity.app.gxj.com.countdowntimer.tools.SystemTool;

public class CountDownActivity extends AppCompatActivity implements View.OnClickListener {

    private Timer timer;
    private final int NUM = 60;//重新得到验证码间隔时间
    private int currentTime;
    private TextView get_code;//得到验证码

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {

            switch (msg.what){
                case 10:
                    get_code.setText("获取验证码");
                    get_code.setTextColor(Color.GRAY);
                    get_code.setEnabled(true);
                    break;

                case 20:
                    get_code.setText(msg.arg1 +" s");
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        get_code = (TextView) findViewById(R.id.get_code);
        get_code.setOnClickListener(this);

        currentTime = SystemTool.getTime();

        if(currentTime <= 0){
            //如果倒计时已经结束，设置为可点击就行，再在点击事件中设置参数
            get_code.setEnabled(true);
            get_code.setText("获取验证码");
            get_code.setTextColor(Color.GRAY);

        }else{
            //倒计时没有结束 继续执行
            get_code.setEnabled(false);
            get_code.setText(currentTime+" s");
            get_code.setTextColor(Color.RED);

            SystemTool.setHandler(mHandler);
            SystemTool.setTime(currentTime);
            SystemTool.startTask();//第一种方式
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.get_code://获取验证码

                get_code.setEnabled(false);

                get_code.setText(NUM + " s");
                get_code.setTextColor(Color.RED);

                SystemTool.setTime(NUM);
                SystemTool.setHandler(mHandler);
                SystemTool.startTask();

                break;

        }

    }
}
