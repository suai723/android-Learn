package com.example.ai.mapdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;


public class MainActivity extends Activity {

    MapView mapView=null;
    BaiduMap mBaidumap = null;
    Overlay  mCircle=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(15);



        mapView = (MapView)findViewById(R.id.bmapView);
        mBaidumap = mapView.getMap();
        mBaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaidumap.animateMapStatus(u);
        mapView.showZoomControls(false);

        LatLng point = new LatLng(39.963175,116.400244);
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
        BitmapDescriptor bitmap3 = BitmapDescriptorFactory.fromResource(R.drawable.icon_markc);

        giflist.add(bitmap);
        giflist.add(bitmap2);
        giflist.add(bitmap3);

        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icons(giflist)
                .zIndex(9)
                .draggable(true);
       mBaidumap.addOverlay(option);


        mBaidumap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
//                LatLng point=marker.getPosition();
//                CharSequence p=String.valueOf(point.latitude)+" "+String.valueOf(point.longitude);
//                Toast.makeText(getBaseContext(),p,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng point=marker.getPosition();
                CharSequence p=String.valueOf(point.latitude)+" "+String.valueOf(point.longitude);
                OverlayOptions circleOption = new CircleOptions()
                                                    .center(point)
                                                    .radius(50)
                                                    .fillColor(0xAAFFFF00)
                                                    .stroke(new Stroke(5,0xAA00FF00));
                Toast.makeText(getBaseContext(),p,Toast.LENGTH_SHORT).show();
                mCircle=mBaidumap.addOverlay(circleOption);



            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                if (mCircle !=null)
                    mCircle.remove();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
