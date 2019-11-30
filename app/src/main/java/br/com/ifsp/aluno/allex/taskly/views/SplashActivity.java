package br.com.ifsp.aluno.allex.taskly.views;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import br.com.ifsp.aluno.allex.taskly.R;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        handler = new Handler();
        executaThread();
    }

    public void executaThread(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i+=10) {
                    final int value = i;

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            progressBar.setProgress(value, true);

                            if(value >= 100) {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Ringtone r = RingtoneManager.getRingtone(SplashActivity.this.getApplicationContext(), notification);
                                r.play();

                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        };

        new Thread(runnable).start();
    }
}
