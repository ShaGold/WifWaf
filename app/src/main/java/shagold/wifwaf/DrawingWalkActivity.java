package shagold.wifwaf;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.Constants;

public class DrawingWalkActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Marker myLocation;
    private PolylineOptions lines = new PolylineOptions();
    private LinkedList<LatLng> linesLatLng = new LinkedList<LatLng>();
    private List<PolylineOptions> pl = new ArrayList<PolylineOptions>();
    private AddressResultReceiver mResultReceiver;
    private boolean startPoint = true;


    private Socket mSocket;

    private Walk walk;

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler h) {
            super(h);
        }

        @Override
        protected void onReceiveResult(int codeResultat, Bundle donneesResult) {
            if (codeResultat == Constants.SUCCESS_RESULT)
                walk.setCity(donneesResult.getString(Constants.RESULT_DATA_KEY));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_walk);
        setUpMapIfNeeded();

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        createLocationRequest();

        walk = (Walk) getIntent().getSerializableExtra("WALK");
        mSocket = SocketManager.getMySocket();
        mSocket.on("RTryAddWalk", onRTryAddWalk);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                linesLatLng.add(point);

                if(startPoint) {
                    Intent intent = new Intent(DrawingWalkActivity.this, AddressLocationService.class);
                    mResultReceiver = new AddressResultReceiver(new Handler());
                    intent.putExtra(Constants.RECEIVER, mResultReceiver);
                    intent.putExtra(Constants.LOCATION_DATA_EXTRA, point);
                    startService(intent);
                    startPoint = false;
                }

                if(linesLatLng.size() > 1) {
                    PolylineOptions temp = new PolylineOptions()
                            .add(linesLatLng.getLast())
                            .add(linesLatLng.get(linesLatLng.size() - 2));
                    temp.color(Color.BLUE);
                    temp.visible(true);
                    temp.width(10);
                    pl.add(temp);
                    mMap.addPolyline(temp);
                }
            }
        });


    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        myLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).title("Start walk"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 16));
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void finishWalk(View view) {
        for(LatLng p : linesLatLng) {
            walk.addLocationToWalk(p.latitude, p.longitude);
        }
        try {
            JSONObject walkJson = walk.toJson();
            mSocket.emit("TryAddWalk", walkJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onRTryAddWalk = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            DrawingWalkActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent resultat = new Intent(DrawingWalkActivity.this, UserWalksActivity.class);
                    startActivity(resultat);
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuManager.defaultMenu(this, item) || super.onOptionsItemSelected(item);
    }
}
