package activity.app.gxj.com.customkeyboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class KeyboardActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    private Button commit;
    KeyBoardUtils keyBoardUtils;

    private TextView close_keyboard;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText1.setOnTouchListener(this);
        editText2.setOnTouchListener(this);
        editText3.setOnTouchListener(this);

        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);

        close_keyboard = (TextView) findViewById(R.id.close_keyboard);
        close_keyboard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.commit:
                Toast.makeText(this,"姓名为：" + editText1.getText(),Toast.LENGTH_SHORT).show();
                keyBoardUtils.hideKeyBoard();
                break;

            case R.id.close_keyboard:
                Toast.makeText(this,"密码为：" + editText2.getText(),Toast.LENGTH_SHORT).show();
                keyBoardUtils.hideKeyBoard();
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP) {
            switch (v.getId()) {
                case R.id.editText1:
                    editText1.requestFocus();
                    keyBoardUtils = new KeyBoardUtils(this, editText1,false);
                    keyBoardUtils.hintSystemSoftKeyboard();
                    keyBoardUtils.setKeyboardType(true);//设置为数字键盘
                    keyBoardUtils.showKeyBoard();
                    break;


                case R.id.editText2:
                    editText2.requestFocus();
                    keyBoardUtils = new KeyBoardUtils(this, editText2,false);
                    keyBoardUtils.hintSystemSoftKeyboard();
                    keyBoardUtils.setKeyboardType(true);//设置为数字键盘
                    keyBoardUtils.showKeyBoard();
                    break;

                case R.id.editText3:
                    editText3.requestFocus();
                    keyBoardUtils = new KeyBoardUtils(this, editText3,true);
                    keyBoardUtils.hintSystemSoftKeyboard();
                    keyBoardUtils.setKeyboardType(true);//设置为数字键盘
                    keyBoardUtils.showKeyBoard();
                    break;
                default:
                    break;
            }

            return true;
        }
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(this,"KEYCODE_BACK",Toast.LENGTH_SHORT).show();
            if(null != keyBoardUtils){
                if(keyBoardUtils.isKeyboardVisble()){
                    keyBoardUtils.hideKeyBoard();
                    return true;
                }
            }

            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);
    }
}
