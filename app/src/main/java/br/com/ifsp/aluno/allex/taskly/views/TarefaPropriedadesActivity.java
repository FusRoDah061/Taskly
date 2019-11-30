package br.com.ifsp.aluno.allex.taskly.views;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.ifsp.aluno.allex.taskly.Constantes;
import br.com.ifsp.aluno.allex.taskly.R;
import br.com.ifsp.aluno.allex.taskly.databinding.ActivityTarefaPropriedadesBinding;
import br.com.ifsp.aluno.allex.taskly.model.Tarefa;
import br.com.ifsp.aluno.allex.taskly.viewmodels.TarefaPropriedadesViewModel;

public class TarefaPropriedadesActivity extends AsyncActivity implements OnMapReadyCallback {
    private Tarefa tarefa;
    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTarefaPropriedadesBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_tarefa_propriedades);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            tarefa = (Tarefa) extras.getSerializable(Constantes.EXTRA_TAREFA);
        }

        bind.setViewModel(new TarefaPropriedadesViewModel(tarefa, this));
        bind.executePendingBindings();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapView = (MapView) findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constantes.EXTRA_MAPVIEW);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(TarefaPropriedadesActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

                            googleMap.addMarker(new MarkerOptions()
                                    .position(position)
                                    .title(getString(R.string.str_tarefa_user_coord))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if(tarefa.getLatitudeCriacao() == null && tarefa.getLongitudeCriacao() == null) {
            Toast.makeText(this, getResources().getString(R.string.str_tarefa_sem_coord), Toast.LENGTH_LONG).show();
            return;
        }

        LatLng localCriacao = new LatLng(tarefa.getLatitudeCriacao(), tarefa.getLongitudeCriacao());

        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.addMarker(new MarkerOptions()
                .position(localCriacao)
                .title(String.format(getResources().getString(R.string.str_tarefa_coord), Constantes.DATE_FORMAT.format(tarefa.getDataCriacao()))));

        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(localCriacao));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(Constantes.EXTRA_MAPVIEW);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(Constantes.EXTRA_MAPVIEW, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }


    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
