package se.hellsoft.gmapsdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class MainActivity extends Activity implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private PolygonOptions mPolygonOptions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_layout);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        if(mPolygonOptions != null) {
            mPolygonOptions.add(latLng);
            mPolygonOptions.fillColor(Color.RED);
            mMap.addPolygon(mPolygonOptions);

            mPolygonOptions = null;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(mPolygonOptions == null) {
            mPolygonOptions = new PolygonOptions();
        }
        mPolygonOptions.add(latLng);
    }
}
