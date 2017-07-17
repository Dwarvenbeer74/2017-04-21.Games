package dwarvenbeer.a1ninja;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Павел on 25.04.2017.
 */

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 2000;
    private ImageView splash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button startButton = (Button)findViewById(R.id.button1);
        startButton.setOnClickListener(this);
        Button exitButton = (Button)findViewById(R.id.button2);
        exitButton.setOnClickListener(this);

        splash = (ImageView)findViewById(R.id.splashscreen);
        Message message = new Message();
        message.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(message, SPLASHTIME);

        startService(new Intent(this, MyService.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                finish();
                break;
            default:
                break;
        }
    }

    private Handler splashHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case STOPSPLASH:
                    splash.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(message);
        }
    };

    @Override
    public void onBackPressed() {
        stopService(new Intent(this, MyService.class));
        super.onBackPressed();
    }
}
