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
    public final LatLng tehran_latLng =  new LatLng(35.7112131,51.3707238);
    public final LatLng karaj_latLng =  new LatLng(35.8194156,50.9939849);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(getIntent().getExtras().getString("city").equals("tehran")){


            try {

                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                CameraUpdate cam= CameraUpdateFactory.newLatLngZoom(tehran_latLng,17);
                mMap.animateCamera(cam);

                Marker marker=mMap.addMarker(new MarkerOptions().position(tehran_latLng)
                        .title("کلینیک دکتر مفرد")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {


            try {

                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                CameraUpdate cam= CameraUpdateFactory.newLatLngZoom(karaj_latLng,17);
                mMap.animateCamera(cam);

                Marker marker=mMap.addMarker(new MarkerOptions().position(karaj_latLng)
                        .title("کلینیک دکتر مفرد")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }
}
