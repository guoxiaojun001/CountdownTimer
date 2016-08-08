package activity.app.gxj.com.customkeyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * xml文件  定义的键盘
 */
public class KeyBoardUtils implements KeyboardView.OnKeyboardActionListener {

    private EditText editText; //当前  需要添加内容的EditText

    private Keyboard k1,k2;  //数字键盘 和字母键盘

    protected KeyboardView keyboardView;//存放键盘的  布局
    RelativeLayout keyboard_layout;

    private boolean isNum = true;//默认是数字键盘

    private long startTime,endTime,distinct;//开始 结束时间，时间间隔

    private Context context;
    private Activity mActivity;

    Animation come_in,leave_off;

    //自定义长按事件
//    private Handler timeHandler = new Handler(){
//        @Override
//        public void dispatchMessage(Message msg) {
//            switch (msg.what){
//                case 0:
//                    timeHandler.sendEmptyMessageDelayed(1,300);//长按事件，超过300毫秒 清空
//                    break;
//                case 1:
//                    editText.setText("");
//                    break;
//            }
//
//            super.dispatchMessage(msg);
//        }
//    };

    public void setKeyboardType(boolean isNum){
        this.isNum = isNum;
    }

   public boolean isKeyboardVisble(){
//       if(keyboardView == null){
//           return false;
//       }
//
//       int visibility = keyboardView.getVisibility();
//       if(visibility == View.VISIBLE){
//           return true;
//       }

       if(keyboard_layout == null){
           return false;
       }

       int visibility = keyboard_layout.getVisibility();
       if(visibility == View.VISIBLE){
           return true;
       }

       return false;
    }

    //定义  构造方法一： 传入当前activity
    public KeyBoardUtils(Activity activity, EditText editText,boolean idCard){
        context = (Context)activity;
        mActivity = activity;
        this.editText = editText;

        //初始化动画
        come_in = AnimationUtils.loadAnimation(context,R.anim.push_up_in);
        leave_off = AnimationUtils.loadAnimation(context,R.anim.push_up_out);
        //获取 数字键盘 和英文键盘：
        if(idCard){
            k1 = new Keyboard(activity.getApplicationContext(),R.xml.id_card_keys);//身份证输入
        }else {
            k1 = new Keyboard(activity.getApplicationContext(),R.xml.numskeys);//普通数字输入
        }
        k2 = new Keyboard(activity.getApplicationContext(), R.xml.wordskeys);

        keyboardView = (KeyboardView) activity.findViewById(R.id.my_keyboard_view);
        keyboard_layout = (RelativeLayout) activity.findViewById(R.id.keyboard_layout);

        //填充布局:
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);//设置是否需要输入预览
        keyboardView.setOnKeyboardActionListener(this);  //添加监听事件键盘的
    }

    //定义  构造方法二： 传入当前activity
    public KeyBoardUtils(Context context, View view, EditText editText){
        mActivity = (Activity) context;
        this.context = context;

        this.editText = editText;

        //初始化动画
        come_in = AnimationUtils.loadAnimation(context,R.anim.push_up_in);
        leave_off = AnimationUtils.loadAnimation(context,R.anim.push_up_out);

        //获取 数字键盘 和英文键盘：
        k1 = new Keyboard(context,R.xml.numskeys);
        k2 = new Keyboard(context,R.xml.wordskeys);
        keyboardView = (KeyboardView) view.findViewById(R.id.my_keyboard_view);
        keyboard_layout = (RelativeLayout) view.findViewById(R.id.keyboard_layout);

        //填充布局:
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(this);  //添加监听事件键盘的
    }


    //显示键盘
    public void showKeyBoard(){
//        int visibility = keyboardView.getVisibility();
//        if(visibility == View.GONE || visibility == View.INVISIBLE){
//            keyboardView.setVisibility(View.VISIBLE);
//        }

        int visibility = keyboard_layout.getVisibility();
        if(visibility == View.GONE || visibility == View.INVISIBLE){
            keyboard_layout.setVisibility(View.VISIBLE);
            keyboard_layout.setAnimation(come_in);

//            Animator animator = AnimatorInflater.loadAnimator(context, R.anim.anim_file);
//            animator.setTarget(keyboard_layout);
//            animator.start();
        }

    }
    //隐藏键盘：
    public void hideKeyBoard(){
//        int visibility = keyboardView.getVisibility();
//        if(visibility == View.VISIBLE){
//            keyboardView.setVisibility(View.GONE);
//        }

        int visibility = keyboard_layout.getVisibility();
        if(visibility == View.VISIBLE){
            keyboard_layout.setVisibility(View.GONE);
            keyboard_layout.setAnimation(leave_off);

        }

        //timeHandler.removeCallbacksAndMessages(null);

    }

    private boolean isUpper = false;  //默认  为小写不是大写
    //大小写切换
    public void  changeKey(){

        List<Keyboard.Key> keyList = k2.getKeys(); //获取字母键盘   所有字母

        if(isUpper){ //是大写默认
            isUpper = false;
            for(Keyboard.Key key:keyList){
                if(key.label!=null && isword(key.label.toString())){
                    key.label = key.label.toString().toLowerCase();//转换为小写
                    key.codes[0] =  key.codes[0] + 32; //转换为小写
                }
            }
        }else{ //小写   转为大写
            isUpper = true;
            for(Keyboard.Key key:keyList){
                if(key.label !=null && isword(key.label.toString())){
                    key.label = key.label.toString().toUpperCase();//转换为大写
                    key.codes[0] = key.codes[0] - 32;//
                }
            }
        }
    }

    //判断  k2键盘中是否为英文：
    private boolean isword(String str){
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if(wordstr.indexOf(str.toLowerCase())>-1){
            return  true;
        }
        return  false;
    }

    //------------------以下为键盘的监听事件--------------------
    @Override
    public void onPress(int primaryCode) {//按下事件
        if (primaryCode == Keyboard.KEYCODE_DELETE){
            startTime = System.currentTimeMillis();
            //timeHandler.sendEmptyMessage(0);
        }
    }
    @Override
    public void onRelease(int primaryCode) {//释放事件

    }
    @Override
    public void onText(CharSequence text) {
    }
    @Override
    public void swipeLeft() {
    }
    @Override
    public void swipeRight() {
    }
    @Override
    public void swipeDown() {
    }
    @Override
    public void swipeUp() {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        //添加监听事件：
        Editable edittable = editText.getText();//获取   当前EditText的可编辑对象
        int start = editText.getSelectionStart();//
        if(primaryCode == Keyboard.KEYCODE_CANCEL){//完成  按键
            hideKeyBoard();//隐藏键盘
        } else if (primaryCode == Keyboard.KEYCODE_DELETE){//回退
            if(edittable != null && edittable.length() > 0){
                if(start>0){
                    edittable.delete(start - 1,start);
                    //timeHandler.removeCallbacksAndMessages(null);
                }
            }
        }else if(primaryCode == Keyboard.KEYCODE_SHIFT){  //大小写  切换
            changeKey();//切换大小写
            keyboardView.setKeyboard(k2);//设置  为字母键盘
        }else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE){//数字和英文键盘切换
            if(isNum){//当前为   数字键盘
                isNum = false;
                keyboardView.setKeyboard(k2);// 设置为  字母键盘
            }else{
                isNum = true;
                keyboardView.setKeyboard(k1);//设置为数字键盘
            }
        }else if(primaryCode == 57419){  //go left
            if (start > 0) {
                editText.setSelection(start - 1);
            }
        }else if(primaryCode == 57421){ //go right
            if (start < editText.length()) {
                editText.setSelection(start + 1);
            }
        } else{
            edittable.insert(start,Character.toString((char)primaryCode));
        }
    }


    /**
     * 隐藏系统键盘  让输入框 不条用系统键盘
     */
    public void hintSystemSoftKeyboard(){
        if (editText.getWindowToken() != null) {
            ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(editText.getWindowToken(), 2);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            try{
                Class[] arrayOfClass = new Class[1];
                arrayOfClass[0] = Boolean.TYPE;
                Method localMethod = null;
                if (Build.VERSION.SDK_INT < 17){
                    localMethod = EditText.class.getMethod("setSoftInputShownOnFocus", arrayOfClass);
                }else{
                    localMethod = EditText.class.getMethod("setShowSoftInputOnFocus", arrayOfClass);
                }
                localMethod.setAccessible(true);
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = Boolean.valueOf(false);
                localMethod.invoke(this, arrayOfObject);
                return;
            }
            catch (SecurityException localSecurityException) {
                localSecurityException.printStackTrace();
                return;
            }
            catch (NoSuchMethodException localNoSuchMethodException) {
                localNoSuchMethodException.printStackTrace();
                return;
            }
            catch (Exception localException) {
                localException.printStackTrace();
                return;
            }
        }
        editText.setInputType(InputType.TYPE_NULL);
    }


    //第二种方式
    public void hideSoftInputMethod() {
        mActivity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (NoSuchMethodException e) {
                editText.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
