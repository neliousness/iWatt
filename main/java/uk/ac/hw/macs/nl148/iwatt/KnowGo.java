package uk.ac.hw.macs.nl148.iwatt;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.HashedMap;

import java.util.ArrayList;
import java.util.List;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class KnowGo extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    DBHelper dbHelper;
    RuntimeExceptionDao<Student, Object> studentDao = null;
    RuntimeExceptionDao<LocalProgramme, Object> localProgrammesDao = null;
    TextView programeName;
    TextView studentName;
    String name = "";
    String programme = "";
    String surname = "";


    private boolean mPermissionDenied = false;
    AutoCompleteTextView findMap;

    private GoogleMap mMap;
    private static final LatLng MAIN_RECEPTION = new LatLng(55.909586, -3.320346);
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
    private static final LatLng GC_LIBRARY = new LatLng(55.908802, -3.322166);
    private static final LatLng JWC_CENTER = new LatLng(55.909603, -3.319513);
    private static final LatLng JWC2_CENTER = new LatLng(55.909710, -3.318976);
    private static final LatLng SU_BUILDING = new LatLng(55.911260, -3.318268);
    private static final LatLng CHAPLAINCY = new LatLng(55.91067, -3.32364);
    private static final LatLng THE_PIECE = new LatLng(55.91048, -3.32232);
    private static final LatLng CAFE_BRIO = new LatLng(55.91034, -3.32211);
    private static final LatLng C_BUILDING = new LatLng( 55.911953, -3.317108);
    private static final LatLng SA = new LatLng(55.908698, -3.316520);
    private  static final LatLng EF_BUILDING =  new LatLng(55.908143, -3.321318);
    private static final LatLng HP_BUILDING= new LatLng(55.908386, -3.321807);
    private static final LatLng GC_BUILDING = new LatLng(55.908670, -3.322475);
    private static final LatLng NURSERY= new LatLng(55.912627, -3.320212);
    private  static  final LatLng MEDICAL_C = new LatLng(55.909905, -3.318577);
    private  static  final LatLng EBS = new LatLng(55.908528, -3.320221);
    private  static  final LatLng BUS1 = new LatLng(55.908997, -3.319817);
    private  static  final LatLng BUS2 = new LatLng(55.908997, -3.319817);
    private  static  final LatLng BUS3 = new LatLng(55.910220, -3.317677);
    private  static  final LatLng BUS4 = new LatLng(55.912917, -3.323330);
    private  static  final LatLng DEWEYS = new LatLng(55.908777, -3.321679);
    private  static  final LatLng DAVINCI = new LatLng(55.912093, -3.323650);
    private  static  final LatLng  ELEMENTS = new LatLng(55.910110, -3.322082);
    private  static  final LatLng  CENTRAL = new LatLng(55.909914, -3.322246);






    Button earth;
    Button search;
    Button question;
    IterableMap<LatLng, String> locations = new HashedMap();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a);

        earth = (Button) findViewById(R.id.earth);
        findMap = (AutoCompleteTextView) findViewById(R.id.mapfind);
        search = (Button) findViewById(R.id.search);



        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_know);


        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title2);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        toolbar.setTitle("");
        toolbar_title.setTypeface(tf);

        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);



        locations.put(MAIN_RECEPTION, "Main Reception");
        locations.put(EM_BUILDING, "Earl Mountbatten");
        locations.put(CM_BUILDING, "Colin Maclaurin");
        locations.put(WP_BUILDING, "William Perking");
        locations.put(JM_BUILDING, "John Muir");
        locations.put(JC_BUILDING, "John Coulson");
        locations.put(JN_BUILDING, "James Nasmyth");
        locations.put(SR_BUILDING, "Scott Russel");
        locations.put(DB_BUILDING, "David Brewster");
        locations.put(EC_BUILDING, "Edwin Chadwick");
        locations.put(WA_BUILDING, "William Arrol");
        locations.put(WA2_BUILDING, "William Arrol");
        locations.put(PG_CENTER, "PG Centre");
        locations.put(MB_BUILDING, "Mary Burton");
        locations.put(GC_LIBRARY, "Library");
        locations.put(JWC_CENTER, "James Watt I");
        locations.put(JWC2_CENTER, "James Watt II");
        locations.put(SU_BUILDING, "Student Union ");
        locations.put(CHAPLAINCY, "Chaplaincy");
        locations.put(THE_PIECE, "The Piece ");
        locations.put(CAFE_BRIO, "Cafe Brio ");
        locations.put(C_BUILDING, "Conoco");
        locations.put(SA, "Sports Academy");
        locations.put(EF_BUILDING, "Esmée Fairbairn");
        locations.put(HP_BUILDING, "Henry Prais");
        locations.put(GC_BUILDING, "Gibson Graig Wing");
        locations.put(NURSERY, "Nursery");
        locations.put(MEDICAL_C, "Medical Centre");
        locations.put(EBS, "EdinBurgh Business School");
        locations.put(BUS1, "Bus Stop");
        locations.put(BUS2, "Bus Stop");
        locations.put(BUS3, "Bus Stop");
        locations.put(BUS4, "Bus Stop");
        locations.put(DEWEYS, "Dewey's");
        locations.put(DAVINCI, "da Vinci's");
        locations.put(ELEMENTS, "Elements");
        locations.put(CENTRAL, "Central");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, locations.values().toArray());
        findMap.setThreshold(1);
        findMap.setTypeface(tf);
        findMap.setAdapter(arrayAdapter);

        search.setOnClickListener(this);
        earth.setOnClickListener(this);
        mapFragment.getMapAsync(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_know);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_know);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);

        mMap.addMarker(new MarkerOptions().position(EM_BUILDING).title("Earl MountBatton Building"));//.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EM_BUILDING, 16.0f));

        mMap.addMarker(new MarkerOptions().position(CM_BUILDING).title("Collin Maclaurin Building"));


        mMap.addMarker(new MarkerOptions().position(WP_BUILDING).title("William Perking Building"));

        mMap.addMarker(new MarkerOptions().position(MAIN_RECEPTION).title("Heriot Watt University : Main Reception")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


        mMap.addMarker(new MarkerOptions().position(JM_BUILDING).title("John Muir Building"));

        mMap.addMarker(new MarkerOptions().position(JC_BUILDING).title("John Coulson Building"));


        mMap.addMarker(new MarkerOptions().position(JN_BUILDING).title("James Nasmyth Building"));

        mMap.addMarker(new MarkerOptions().position(SR_BUILDING).title("Scott Russel Building"));


        mMap.addMarker(new MarkerOptions().position(DB_BUILDING).title("David Brewster Building"));


        mMap.addMarker(new MarkerOptions().position(EC_BUILDING).title("Edwin Chadwick Building"));

        mMap.addMarker(new MarkerOptions().position(WA_BUILDING).title("William Arrol Building"));

        mMap.addMarker(new MarkerOptions().position(WA2_BUILDING).title("William Arrol Building"));

        mMap.addMarker(new MarkerOptions().position(PG_CENTER).title("Postgraduate Centre"));


        mMap.addMarker(new MarkerOptions().position(MB_BUILDING).title("Mary Burton Building"));


        mMap.addMarker(new MarkerOptions().position(GC_LIBRARY).title("Cameron Small Library").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mMap.addMarker(new MarkerOptions().position(JWC_CENTER).title("James Watt Centre I"));

        mMap.addMarker(new MarkerOptions().position(JWC2_CENTER).title("James Watt Centre II"));

        mMap.addMarker(new MarkerOptions().position(SU_BUILDING).title("Heriot Watt Student Union : Student Union").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        mMap.addMarker(new MarkerOptions().position(CHAPLAINCY).title("Heriot Watt University: The Chaplaincy").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        mMap.addMarker(new MarkerOptions().position(THE_PIECE).title("The Piece").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions().position(CAFE_BRIO).title("Cafe Brio").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions().position(C_BUILDING).title("Conoco Building"));

        mMap.addMarker(new MarkerOptions().position(SA).title("Sports Academy").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        mMap.addMarker(new MarkerOptions().position(EF_BUILDING).title("Esmée Fairbairn Building"));

        mMap.addMarker(new MarkerOptions().position(GC_BUILDING).title("Gibson Craig Wing"));

        mMap.addMarker(new MarkerOptions().position(HP_BUILDING).title("Henry Prais Building"));

        mMap.addMarker(new MarkerOptions().position(NURSERY).title("Pinocchio's Children's Nurseries").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        mMap.addMarker(new MarkerOptions().position(MEDICAL_C).title("The Riccarton Medical Centre").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions().position(EBS).title("Edinburgh Business School"));

        mMap.addMarker(new MarkerOptions().position(BUS1).title("Bus Stop").
                icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

        mMap.addMarker(new MarkerOptions().position(BUS2).title("Bus Stop").
                icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

        mMap.addMarker(new MarkerOptions().position(BUS3).title("Bus Stop").
                icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

        mMap.addMarker(new MarkerOptions().position(BUS4).title("Bus Stop").
                icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));

        mMap.addMarker(new MarkerOptions().position(DAVINCI).title("da Vinci's").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions().position(DEWEYS).title("Dewey's").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions().position(CENTRAL).title("Central").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions().position(ELEMENTS).title("Elements").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));


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
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    AbsListView.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    @Override
    public void onClick(View v) {

        if(earth == v && on == false)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            on = true;
        }
        else if(earth == v && on == true)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            on = false;
        }

        if(search == v)
        {
            MapIterator<LatLng,String> it = locations.mapIterator();
            while (it.hasNext()) {
                LatLng key = it.next();
                String value = it.getValue();
                if(findMap.getText().toString().contentEquals(value))
                {

                    mMap.clear();

                    mMap.addMarker(new MarkerOptions().position(key).title(value)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(key, 17.0f));



                }

            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_know);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        studentName = (TextView) findViewById(R.id.student_name_nav);
        programeName = (TextView) findViewById(R.id.student_programme_nav);

        DBHelper dbHelper_prog = OpenHelperManager.getHelper(this, DBHelper.class);
        localProgrammesDao = dbHelper_prog.getProgrammeExceptionDao();
        studentDao = dbHelper_prog.getStudentExceptionDao();
        List<LocalProgramme> programmes = localProgrammesDao.queryForAll();
        List<Student> students = studentDao.queryForAll();
        Typeface name_tf = Typeface.createFromAsset(getAssets(), "Simple tfb.ttf");
        programme = programmes.get(0).getProgDesc();

        name = students.get(0).getName();
        surname = students.get(0).getSurname();

        studentName.setText(name + " " + surname );
        programeName.setText(programme);
        studentName.setTypeface(name_tf);
        programeName.setTypeface(name_tf);

        //commented out to remove settings icon
        //getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //Intent i = new Intent(this,MainActivity.class);
            finish();


        } else if (id == R.id.nav_preferences) {
            /*fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new SecondFragment())
                    .commit();*/

            Toast.makeText(this, "preferences are unavailable",Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.nav_map_key) {

            fragmentManager.beginTransaction()
                    .replace(R.id.map, new MapKey())
                    .commit();

            search.setVisibility(View.GONE);
            earth.setVisibility(View.GONE);
            findMap.setVisibility(View.GONE);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_know);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}