package br.com.ifsp.aluno.allex.taskly.views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class NovaTarefaActivity extends AppCompatActivity {

    private static final int TAB_TAREFA      = 0;
    private static final int TAB_SINCRONIZAR = 1;
    private static final int TAB_CONCLUIR    = 2;

    private BottomNavigationView navView;

    private final Tarefa tarefa = new Tarefa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tarefa, R.id.navigation_sincronizar, R.id.navigation_concluir)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        navView.getMenu().getItem(TAB_SINCRONIZAR).setEnabled(false);
        navView.getMenu().getItem(TAB_CONCLUIR).setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

}
