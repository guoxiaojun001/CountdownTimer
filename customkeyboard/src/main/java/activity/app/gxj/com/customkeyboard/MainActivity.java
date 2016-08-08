package activity.app.gxj.com.customkeyboard;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button keyboard1;
    private Button keyboard2;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyboard1 = (Button) findViewById(R.id.keyboard1);
        keyboard2 = (Button) findViewById(R.id.keyboard2);

        keyboard1.setOnClickListener(this);
        keyboard2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.keyboard1:
                intent = new Intent(this, KeyboardActivity.class);
                startActivity(intent);
                break;

            case R.id.keyboard2:
                intent = new Intent(this,KeyboardActivity.class);
                startActivity(intent);
                break;

        }
    }
}
