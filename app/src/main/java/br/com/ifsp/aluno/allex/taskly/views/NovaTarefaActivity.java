package br.com.ifsp.aluno.allex.taskly.views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;

public class NovaTarefaActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener, NavController.OnDestinationChangedListener {

    private static final int TAB_TAREFA      = 0;
    private static final int TAB_SINCRONIZAR = 1;
    private static final int TAB_CONCLUIR    = 2;

    private BottomNavigationView navView;
    private NavController navController;

    private Tarefa tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        Bundle extras = getIntent().getExtras();
        tarefa = (Tarefa) extras.getSerializable(Constantes.EXTRA_TAREFA);

        if(tarefa == null)
            tarefa = new Tarefa();

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemReselectedListener(this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tarefa, R.id.navigation_sincronizar, R.id.navigation_concluir)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(this);

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

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

        switch (destination.getId()){
            case R.id.navigation_tarefa:
                navView.getMenu().getItem(TAB_TAREFA).setEnabled(true);
                navView.getMenu().getItem(TAB_SINCRONIZAR).setEnabled(false);
                navView.getMenu().getItem(TAB_CONCLUIR).setEnabled(false);
                break;

            case R.id.navigation_sincronizar:
                navView.getMenu().getItem(TAB_TAREFA).setEnabled(false);
                navView.getMenu().getItem(TAB_SINCRONIZAR).setEnabled(true);
                navView.getMenu().getItem(TAB_CONCLUIR).setEnabled(false);
                break;

            case R.id.navigation_concluir:
                navView.getMenu().getItem(TAB_TAREFA).setEnabled(false);
                navView.getMenu().getItem(TAB_SINCRONIZAR).setEnabled(false);
                navView.getMenu().getItem(TAB_CONCLUIR).setEnabled(true);
                break;
        }
    }
}
