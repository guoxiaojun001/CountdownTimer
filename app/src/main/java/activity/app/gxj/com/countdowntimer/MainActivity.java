package activity.app.gxj.com.countdowntimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by gxj on 2016/8/8.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button timer;
    private Button keyboard;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (Button) findViewById(R.id.timer);
        keyboard = (Button) findViewById(R.id.keyboard);

        timer.setOnClickListener(this);
        keyboard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.timer:
                intent = new Intent(this,CountDownActivity.class);
                startActivity(intent);
                break;

            case R.id.keyboard:
                intent = new Intent(this,CountDownActivity.class);
                startActivity(intent);
                break;

        }
    }
}
