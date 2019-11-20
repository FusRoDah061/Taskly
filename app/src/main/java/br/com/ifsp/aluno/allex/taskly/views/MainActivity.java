package br.com.ifsp.aluno.allex.taskly.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.repository.TarefaRepository;
import br.com.ifsp.aluno.allex.taskly.ui.tarefa.TarefaRecyclerViewAdapter;
import br.com.ifsp.aluno.allex.taskly.viewhelper.TarefaViewHelper;
import br.com.ifsp.aluno.allex.taskly.ui.TarefaLongtouchOptionsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private final TarefaViewHelper tarefaViewHelper = new TarefaViewHelper();

    private RecyclerView rvTarefas;
    private Spinner      spDiaFiltroTarefas;
    private List<Tarefa> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tarefas = new TarefaRepository().findAll();
        initComponents();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_tarefa) {
            abrirNovaTarefaActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_google) {

        } else if (id == R.id.nav_tarefa) {
            abrirNovaTarefaActivity();
        } else if (id == R.id.nav_sobre) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO: Filtrar tarefas pela dia. Se for a opção "Selecionar...", abrir um datepicker
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void initComponents() {
        // Define a toolbar padrão
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa a ação o FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddTarefa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNovaTarefaActivity();
            }
        });

        //Cria o menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Define o listener da lista do menu lateral
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rvTarefas = (RecyclerView) findViewById(R.id.rvTarefas);
        TarefaRecyclerViewAdapter tarefaRecyclerViewAdapter = new TarefaRecyclerViewAdapter(tarefas);

        tarefaRecyclerViewAdapter.setOnTarefaStatusChangedListener(new TarefaRecyclerViewAdapter.OnTarefaStatusChangedListener() {
            @Override
            public void onTarefaStatusChanged(View view, int position, boolean isChecked, Tarefa tarefa) {
                tarefa.setStatus(isChecked ? EStatusTarefa.CONCLUIDA : EStatusTarefa.PENDENTE);
                // TODO: Realizar demais processos de conclusão ou não da tarefa
            }
        });

        tarefaRecyclerViewAdapter.setOnLongClickListener(new TarefaRecyclerViewAdapter.OnTarefaLongClickListener() {
            @Override
            public void onTarefaLongClicked(View view, int position, Tarefa tarefa) {
                TarefaLongtouchOptionsFragment bottomSheetFragment = new TarefaLongtouchOptionsFragment();
                Bundle args = new Bundle(1);

                args.putSerializable(Constantes.EXTRA_TAREFA, tarefa);
                bottomSheetFragment.setArguments(args);

                bottomSheetFragment.setOnTarefaActionListener(new TarefaLongtouchOptionsFragment.OnTarefaActionListener() {
                    @Override
                    public void onEditarTarefa(Tarefa tarefa) {
                        Toast.makeText(MainActivity.this, "Editar tarefa " + tarefa.getDescricao(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onExcluirTarefa(Tarefa tarefa) {
                        Toast.makeText(MainActivity.this, "Excluir tarefa " + tarefa.getDescricao(), Toast.LENGTH_LONG).show();
                    }
                });

                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        rvTarefas.setAdapter(tarefaRecyclerViewAdapter);
        rvTarefas.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Preencher lista com tarefas criadas
        // TODO: No longtouch dos itens, abrir a bottom sheet

        //Inicializa o dropdown de filtro
        spDiaFiltroTarefas = (Spinner) findViewById(R.id.spDiaFiltroTarefas);
        spDiaFiltroTarefas.setAdapter(tarefaViewHelper.getDiasSpinnerAdapter(this));
        spDiaFiltroTarefas.setOnItemSelectedListener(this);
    }

    private void abrirNovaTarefaActivity() {
        Intent intent = new Intent(this, NovaTarefaActivity.class);
        startActivity(intent);
    }
}
