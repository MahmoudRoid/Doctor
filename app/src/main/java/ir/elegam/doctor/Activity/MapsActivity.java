package ir.elegam.doctor.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ir.elegam.doctor.R;

public class MapsActivity extends Activity {

    private GoogleMap mMap;
    public LatLng tehran_latLng =  null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        String lat = getIntent().getStringExtra("lat");
        String lng = getIntent().getStringExtra("lng");
        tehran_latLng = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));

        try {

            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            CameraUpdate cam= CameraUpdateFactory.newLatLngZoom(tehran_latLng,17);
            mMap.animateCamera(cam);

            Marker marker=mMap.addMarker(new MarkerOptions().position(tehran_latLng)
                    .title("")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        } // end try
        catch (Exception e) { e.printStackTrace(); }

    }// end onCreate()

}// end class
