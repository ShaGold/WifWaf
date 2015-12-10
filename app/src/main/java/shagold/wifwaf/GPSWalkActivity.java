package shagold.wifwaf;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import shagold.wifwaf.manager.MenuManager;

public class GPSWalkActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Marker myLocation;
    private PolylineOptions lines = new PolylineOptions();
    private PolylineOptions testLines = new PolylineOptions();
    private LinkedList<LatLng> linesLatLng = new LinkedList<LatLng>();
    private List<PolylineOptions> pl = new ArrayList<PolylineOptions>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_walk);
        setUpMapIfNeeded();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        createLocationRequest();
        LocationRequest mLocationRequest;
        mMap.addPolyline(lines);

        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(37.35, -122.0),
                        new LatLng(37.45, -122.0),
                        new LatLng(37.45, -122.2),
                        new LatLng(37.35, -122.2),
                        new LatLng(37.35, -122.0));

        rectOptions.strokeColor(Color.RED);
        rectOptions.fillColor(Color.BLUE);

        // Get back the mutable Polygon
        Polygon polygon = mMap.addPolygon(rectOptions);


        testLines.add(new LatLng(10, 10));
        testLines.add(new LatLng(20, 20));
        testLines.add(new LatLng(30, 30));
        testLines.add(new LatLng(40, 40));
        testLines.add(new LatLng(50, 50));

        Polyline pl = mMap.addPolyline(testLines);

        mMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuManager.defaultMenu(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        myLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).title("Gold"));
        Log.d("TT", "onCreate ..............................." + mLastLocation.toString());
        startLocationUpdates();
    }

    // demande la position courante
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        if(myLocation != null) {
            LatLng ll = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            Log.d("POS", "MOVE ..............................." + ll.toString());
            myLocation.setPosition(ll);
            lines.add(ll);
            linesLatLng.add(ll);

            if(linesLatLng.size() > 2) { // TODO remove pl
                PolylineOptions temp = new PolylineOptions()
                        .add(linesLatLng.getLast())
                        .add(linesLatLng.get(linesLatLng.size() - 2));

                temp.color(Color.BLUE);
                temp.visible(true);
                temp.width(10);
                pl.add(temp);
                Polyline t = mMap.addPolyline(temp);
            }
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        Log.d("TTT", "Start ...............................");
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
