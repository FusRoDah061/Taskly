package br.com.ifsp.aluno.allex.taskly.views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class NovaTarefaActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener {

    private static final int TAB_TAREFA      = 0;
    private static final int TAB_SINCRONIZAR = 1;
    private static final int TAB_CONCLUIR    = 2;

    private BottomNavigationView navView;
    private NavController navController;

    private final Tarefa tarefa = new Tarefa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemReselectedListener(this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tarefa, R.id.navigation_sincronizar, R.id.navigation_concluir)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        navView.getMenu().getItem(TAB_SINCRONIZAR).setEnabled(false);
        navView.getMenu().getItem(TAB_CONCLUIR).setEnabled(false);
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void nextFragment() {
        int proximo = 0;

        if(navView.getSelectedItemId() == R.id.navigation_tarefa){
            proximo = R.id.navigation_sincronizar;
        }
        else if(navView.getSelectedItemId() == R.id.navigation_sincronizar){
            proximo = R.id.navigation_concluir;
        }

        navController.navigate(proximo);
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {}
}
