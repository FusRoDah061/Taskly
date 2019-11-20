package br.com.ifsp.aluno.allex.taskly.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import br.com.ifsp.aluno.allex.taskly.R;

public class SobreActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSobreOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        btnSobreOk = (Button) findViewById(R.id.btnSobreOk);
        btnSobreOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnSobreOk.getId()) {
            finish();
        }
    }
}
