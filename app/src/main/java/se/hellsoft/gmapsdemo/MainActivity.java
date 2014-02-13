package se.hellsoft.gmapsdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private PolygonOptions mPolygonOptions = null;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_layout);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(this, this, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.disconnect();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geofence.Builder builder = new Geofence.Builder();
        Geofence geofence = builder
                .setRequestId("MyGeofence")
                .setCircularRegion(latLng.latitude,
                        latLng.longitude, 25)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setExpirationDuration(1000 * 60 * 5)
                .build();
        List<Geofence> geofenceList = new ArrayList<Geofence>();
        geofenceList.add(geofence);
        Intent showGeofenceToast =
                new Intent(MyReceiver.ACTION_GEOFENCE_TOAST);
        PendingIntent pendingIntent
                = PendingIntent
                .getBroadcast(this, 0, showGeofenceToast, 0);
        mLocationClient.addGeofences(geofenceList, pendingIntent, 
                new LocationClient.OnAddGeofencesResultListener() {
            @Override
            public void onAddGeofencesResult(int i, String[] strings) {
                // TODO Possible error handling...
                Log.e("GeofenceDemo", "Geofences added!");
            }
        });

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng).radius(25).fillColor(Color.RED);
        mMap.addCircle(circleOptions);
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
