package activity.app.gxj.com.countdowntimer.tools;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gxj on 2016/8/2.
 */
public class SystemTool {

    public static Timer timer;
    public static int time = 0;
    public static Handler otherHandler;

    public static int FINISH_TIMER = 10;//结束标志
    public static int TIMERING = 20;//正在倒计时

    public static int getTime(){
        return time;
    }



    public static void setHandler(Handler mHandler){
        SystemTool.otherHandler = mHandler;
    }


    public static void setTime( int time){
        SystemTool.time = time;
    }

    public static void startTask() {
        //全局只要一个timer即可，下次需要只需要启动定时器
        if (timer == null) {
            timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (time <= 0) {
                        //倒计时结束
                        otherHandler.sendMessage(Message.obtain(otherHandler, FINISH_TIMER));
                    } else {
                        otherHandler.sendMessage(Message.obtain(otherHandler, TIMERING, time--, 0));
                    }

                }
            }, 1000, 1000);
        }
    }


//    public static void startHandler(){
//        if(timerHandler == null){
//            timerHandler = new Handler(){
//                @Override
//                public void dispatchMessage(Message msg) {
//
//                    switch (msg.what){
//                        case 5:
//                            if(time <= 0){
//                                //倒计时结束
//                                LogUtils.Log("QQQQ","stop");
//                                otherHandler.sendMessage(Message.obtain(otherHandler,FINISH_TIMER));
//                                timerHandler.removeCallbacksAndMessages(null);
//                            }else {
//                                LogUtils.Log("QQQQ","goon");
//                                timerHandler.sendEmptyMessageDelayed(HANDLER_TAG ,1000);
//                                otherHandler.sendMessage(Message.obtain(otherHandler,TIMERING,time--,0));
//                            }
//                            break;
//
//                    }
//
//                }
//            };
//        }
//
//        timerHandler.sendEmptyMessageDelayed(HANDLER_TAG ,1000);
//    }

}
