package br.com.ifsp.aluno.allex.taskly.views;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.enums.EStatusTarefa;
import br.com.ifsp.aluno.allex.taskly.events.OnAsyncTaskFinishListener;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaActionListener;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaLongClickListener;
import br.com.ifsp.aluno.allex.taskly.events.OnTarefaStatusChangedListener;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.notifications.TarefaNotificationReceiver;
import br.com.ifsp.aluno.allex.taskly.persistence.repository.TarefaRepository;
import br.com.ifsp.aluno.allex.taskly.tasklyweb.api.tasks.ListarTarefasAsyncTask;
import br.com.ifsp.aluno.allex.taskly.ui.TarefaLongtouchOptionsFragment;
import br.com.ifsp.aluno.allex.taskly.ui.tarefa.TarefaRecyclerViewAdapter;

public class MainActivity extends AsyncActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, OnTarefaStatusChangedListener, OnTarefaLongClickListener, OnTarefaActionListener, DrawerLayout.DrawerListener {

    private final TarefaRepository tarefaRepository = new TarefaRepository(this);

    private RecyclerView rvTarefas;
    private Spinner      spDiaFiltroTarefas;
    private List<Tarefa> tarefas = new ArrayList<>();
    private NavigationView navigationView;

    private TarefaRecyclerViewAdapter tarefaRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tarefas.addAll(tarefaRepository.findAll());

        initComponents();

        SharedPreferences prefs = getSharedPreferences(Constantes.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constantes.PREF_CONTA_PADRAO, "allexxrodriguess@gmail.com");
        editor.commit();

        removeTarefasAntigas();
    }

    private void removeTarefasAntigas() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.mainActivityRoot);
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, Html.fromHtml("<font color=\"#FFC614\">Removendo tarefas passadas...</font>", Html.FROM_HTML_MODE_LEGACY), Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TarefaRepository tarefaRepository = new TarefaRepository(MainActivity.this);

                List<Tarefa> tarefasPassadas = tarefaRepository.findAllBeforeDate(new Date());

                for(Tarefa tarefa : tarefasPassadas) {
                    tarefaRepository.delete(tarefa);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences preferences = getSharedPreferences(Constantes.PREF_NAME, MODE_PRIVATE);
                        String conta = preferences.getString(Constantes.PREF_CONTA_PADRAO, null);

                        if(conta != null) {
                            ListarTarefasAsyncTask listarTarefasAsyncTask = new ListarTarefasAsyncTask(MainActivity.this);
                            listarTarefasAsyncTask.setOnAsyncTaskFinishListener(new OnAsyncTaskFinishListener() {

                                @Override
                                public void onAsyncTaskFinished(Object result, @Nullable Exception error) {
                                    snackbar.dismiss();
                                }

                            });

                            listarTarefasAsyncTask.execute(conta);
                        }
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarTarefas(spDiaFiltroTarefas.getSelectedItemPosition());
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

        if (id == R.id.action_sobre) {
            Intent intent = new Intent(this, SobreActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_sair) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_conta) {
            //TODO: Mostrar input para e-mail do taskly
        } else if (id == R.id.nav_tarefa) {
            abrirNovaTarefaActivity(null);
        } else if (id == R.id.nav_sobre) {
            Intent intent = new Intent(this, SobreActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constantes.REQ_CODE_DIALOG_CONTA_TASKLY:
                if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);

                    if (accountName != null) {

                        SharedPreferences prefs = getSharedPreferences(Constantes.PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(Constantes.PREF_CONTA_PADRAO, accountName);
                        editor.commit();

                        atualizarContaGoogle();

                        Toast.makeText(this, accountName + getResources().getString(R.string.str_default_account_set), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        buscarTarefas(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddTarefa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNovaTarefaActivity(null);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rvTarefas = (RecyclerView) findViewById(R.id.rvTarefas);
        tarefaRecyclerViewAdapter = new TarefaRecyclerViewAdapter(tarefas);
        tarefaRecyclerViewAdapter.setOnTarefaStatusChangedListener(this);
        tarefaRecyclerViewAdapter.setOnLongClickListener(this);

        rvTarefas.setAdapter(tarefaRecyclerViewAdapter);
        rvTarefas.setLayoutManager(new LinearLayoutManager(this));

        spDiaFiltroTarefas = (Spinner) findViewById(R.id.spDiaFiltroTarefas);
        spDiaFiltroTarefas.setAdapter(getDiasSpinnerAdapter());
        spDiaFiltroTarefas.setOnItemSelectedListener(this);
    }

    private SpinnerAdapter getDiasSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapterDia = ArrayAdapter.createFromResource(this,
                R.array.dias_array, android.R.layout.simple_spinner_item);

        adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapterDia;
    }

    private void abrirNovaTarefaActivity(Tarefa tarefa) {
        Intent intent = new Intent(this, NovaTarefaActivity.class);

        if(tarefa != null){
            intent.putExtra(Constantes.EXTRA_TAREFA, tarefa);
        }

        startActivity(intent);
    }

    @Override
    public void onTarefaStatusChanged(View view, int position, boolean isChecked, Tarefa tarefa) {
        TarefaNotificationReceiver tarefaNotificationReceiver = new TarefaNotificationReceiver();

        if(isChecked) {
            tarefa.setStatus(EStatusTarefa.CONCLUIDA);
            tarefaNotificationReceiver.cancelNotification(this, tarefa);
        }
        else {
            tarefa.setStatus(EStatusTarefa.PENDENTE);
            tarefaNotificationReceiver.scheduleNotification(this, tarefa);
        }

        tarefaRepository.save(tarefa);
        // TODO: Atualizar no taskly
        atualizarEstatisticasTarefas();
    }

    @Override
    public void onTarefaLongClicked(View view, int position, Tarefa tarefa) {
        TarefaLongtouchOptionsFragment bottomSheetFragment = new TarefaLongtouchOptionsFragment();
        Bundle args = new Bundle(1);

        args.putSerializable(Constantes.EXTRA_TAREFA, tarefa);
        bottomSheetFragment.setArguments(args);

        bottomSheetFragment.setOnTarefaActionListener(this);

        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onEditarTarefa(Tarefa tarefa) {
        abrirNovaTarefaActivity(tarefa);
    }

    @Override
    public void onExcluirTarefa(Tarefa tarefa) {

        if(tarefaRepository.delete(tarefa)) {
            TarefaNotificationReceiver tarefaNotificationReceiver = new TarefaNotificationReceiver();
            tarefaNotificationReceiver.cancelNotification(this, tarefa);

            if(tarefa.isSincronizada()) {
                //TODO: Remover do taskly
            }

            if (tarefas.remove(tarefa))
                tarefaRecyclerViewAdapter.notifyDataSetChanged();

            atualizarEstatisticasTarefas();
        }
    }

    private void buscarTarefas(int positionFiltro) {
        int qtdDias = Constantes.DIAS_HOJE;

        switch (positionFiltro) {
            case 1:
                qtdDias = Constantes.DIAS_AMANHA;
                break;

            case 2:
                qtdDias = Constantes.DIAS_SEMANA;
                break;

            case 3:
                qtdDias = Constantes.DIAS_MES;
                break;
        }

        tarefas.clear();
        tarefas.addAll(tarefaRepository.findForDays(qtdDias, (qtdDias == Constantes.DIAS_AMANHA)));
        tarefaRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        atualizarEstatisticasTarefas();
        atualizarContaGoogle();
    }

    private void atualizarContaGoogle() {
        TextView tvContaPadrao = (TextView) navigationView.findViewById(R.id.tvContaPadrao);

        if(tvContaPadrao == null) return;

        SharedPreferences preferences = getSharedPreferences(Constantes.PREF_NAME, MODE_PRIVATE);
        String conta = preferences.getString(Constantes.PREF_CONTA_PADRAO, null);

        tvContaPadrao.setText(conta != null ? conta : getResources().getString(R.string.label_conta_padrao_undef));
    }

    private void atualizarEstatisticasTarefas() {
        TextView tvPctTarefasConcluidas = (TextView) navigationView.findViewById(R.id.tvPctTarefasConcluidas);

        if(tvPctTarefasConcluidas == null) return;

        TarefaRepository tarefaRepository = new TarefaRepository(this);

        int qtdTotalTarefas = tarefaRepository.findAll().size();
        int qtdTarefasConcluidas = tarefaRepository.findByStatus(EStatusTarefa.CONCLUIDA).size();
        int pctConcluidas = 0;

        try {
            pctConcluidas = (qtdTarefasConcluidas * 100) / qtdTotalTarefas;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        tvPctTarefasConcluidas.setText(String.format("%d%%", pctConcluidas));
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {}

    @Override
    public void onDrawerStateChanged(int newState) {}

}
