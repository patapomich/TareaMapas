package com.example.tareamapas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MapView map;
    private static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_main);

        solicitarPermisos();

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        map.getController().setZoom(12.0);
        map.getController().setCenter(new GeoPoint(-17.7833, -63.1821));

        agregarMarcadores();
    }

    private void solicitarPermisos() {
        String[] permisos = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        List<String> permisosNecesarios = new ArrayList<>();
        for (String permiso : permisos) {
            if (ContextCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
                permisosNecesarios.add(permiso);
            }
        }

        if (!permisosNecesarios.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permisosNecesarios.toArray(new String[0]),
                    REQUEST_PERMISSIONS);
        }
    }

    private void agregarMarcadores() {
        // Familiares cercanos (ROJO) - grado 1
        agregarMarcador("Jael\nMama ", -17.377859, -66.280764, 1);
        agregarMarcador("Abraham\nPapa", -17.396472, -66.137454, 1);
        agregarMarcador("Jaasiel\nHermano", -17.791273, -63.201886, 1);

        // 2do grado (AMARILLO) - grado 2
        agregarMarcador("Pedro", -17.7900, -63.1900, 2);
        agregarMarcador("Ana", -17.7750, -63.1750, 2);

        // 3er grado (VERDE) - grado 3
        agregarMarcador("Luis", -17.7700, -63.1700, 3);
        agregarMarcador("Carmen", -17.7950, -63.1950, 3);
    }

    private void agregarMarcador(String nombre, double lat, double lon, int grado) {
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(lat, lon));
        marker.setTitle(nombre);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        // Asignar icono según grado
        if (grado == 1) {
            marker.setIcon(getResources().getDrawable(R.drawable.marker_rojo));
        } else if (grado == 2) {
            marker.setIcon(getResources().getDrawable(R.drawable.marker_amarillo));
        } else if (grado == 3) {
            marker.setIcon(getResources().getDrawable(R.drawable.marker_verde));
        }

        map.getOverlays().add(marker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) {
            map.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) {
            map.onPause();
        }
    }
}