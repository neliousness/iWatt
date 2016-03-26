package uk.ac.hw.macs.nl148.iwatt;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.HashedMap;

public class ClassMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {


    private boolean mPermissionDenied = false;
    AutoCompleteTextView findMap;

    private GoogleMap mMap;

    private static final LatLng EM_BUILDING = new LatLng(55.912208, -3.322321);
    private static final LatLng CM_BUILDING = new LatLng(55.91255, -3.32189);
    private static final LatLng WP_BUILDING = new LatLng(55.911761, -3.321696);
    private static final LatLng JM_BUILDING = new LatLng(55.912006, -3.321173);
    private static final LatLng JC_BUILDING = new LatLng(55.911913, -3.324239);
    private static final LatLng JN_BUILDING = new LatLng(55.911466, -3.323557);
    private static final LatLng SR_BUILDING = new LatLng(55.911149, -3.322361);
    private static final LatLng DB_BUILDING = new LatLng(55.911432, -3.322478);
    private static final LatLng EC_BUILDING = new LatLng(55.911504, -3.325113);
    private static final LatLng WA_BUILDING = new LatLng(55.911101, -3.324534);
    private static final LatLng WA2_BUILDING = new LatLng(55.910809, -3.324319);
    private static final LatLng PG_CENTER = new LatLng(55.912180, -3.323622);
    private static final LatLng MB_BUILDING = new LatLng(55.908651, -3.321012);
    private static final LatLng JWC_CENTER = new LatLng(55.909603, -3.319513);
    private static final LatLng JWC2_CENTER = new LatLng(55.909710, -3.318976);
    private static final LatLng LT = new LatLng(55.91028, -3.32130);


    Button earth;
    IterableMap<LatLng, String> locations = new HashedMap();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_map);

        earth = (Button) findViewById(R.id.earth);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.classmap);


        locations.put(EM_BUILDING, "EM");
        locations.put(CM_BUILDING, "CM");
        locations.put(WP_BUILDING, "WP");
        locations.put(JM_BUILDING, "JM");
        locations.put(JC_BUILDING, "JC");
        locations.put(JN_BUILDING, "JN");
        locations.put(SR_BUILDING, "SR");
        locations.put(DB_BUILDING, "DB");
        locations.put(EC_BUILDING, "EC");
        locations.put(WA_BUILDING, "WA");
        locations.put(WA2_BUILDING, "WA2");
        locations.put(PG_CENTER, "PG");
        locations.put(MB_BUILDING, "MB");
        locations.put(JWC_CENTER, "JW");
        locations.put(JWC2_CENTER, "JW2");
        locations.put(LT, "LT");


        // Log.d("This is what we got " ,room1);


        earth.setOnClickListener(this);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);

        String r1 = getIntent().getStringExtra("room1");
        String r2 = getIntent().getStringExtra("room2");

        String room1 = r1.substring(0, Math.min(r1.length(), 2));
        String room2 = r2.substring(0, Math.min(r2.length(), 2));

        MapIterator<LatLng, String> it = locations.mapIterator();
        while (it.hasNext()) {
            LatLng key = it.next();
            String value = it.getValue();

            if (room1.contentEquals(value)) {

                mMap.addMarker(new MarkerOptions().position(key).title(r1)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(key, 17.0f));
            }

            if (room2.contentEquals(value)) {

                mMap.addMarker(new MarkerOptions().position(key).title(r2)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(key, 17.0f));

            }

        }

        mMap.getUiSettings();
        enableMyLocation();


    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {

        // Permission to access the location is missing.
        // Access to the location has been granted to the app.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "This is your current location", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        // Display the missing permission error dialog when the fragments resume.
        mPermissionDenied = true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    boolean on = false;

    @Override
    public void onClick(View v) {

        if (earth == v && on == false) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            on = true;
        } else if (earth == v && on == true) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            on = false;
        }




    }
}