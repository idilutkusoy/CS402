package tr.edu.ozu.ozunaviclient;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    LocationManager lman = null;
    double latitude = 0;
    double longitude = 0;
    MapView mapv;
    Context current;
    Marker curmarker=null;
    View map;
    View settings;
    View event_list;
    ListView listView ;
    ListView listView2 ;

    Button refresher;
    String url = "http://192.168.1.63:8080";
    int current_user = -1;
    int cur_setting = 1;
    //Spinner spinner;
    RadioButton b1;
    RadioButton b2;
    RadioButton b3;
    RadioButton b4;
    HashMap<Integer,Marker> eventMarkers;

    double cur_latitude = -1;
    double cur_longitude = -1;

    Subscriber subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        current=this;
        subscriber = (Subscriber) getIntent().getSerializableExtra("subscriber");



        map = findViewById(R.id.content_main);
        settings = findViewById(R.id.settings);
        event_list = findViewById(R.id.eventlist);
        map.setVisibility(View.VISIBLE);
        settings.setVisibility(View.GONE);
        event_list.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        lman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        marshmallowGPSPremissionCheck();
        //spinner = (Spinner)findViewById(R.id.userlist);
        mapv = (MapView)findViewById(R.id.map);
        mapv.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.list);
        listView2 = (ListView) findViewById(R.id.list2);
        refresher = (Button) findViewById(R.id.refresh);
        b1 = (RadioButton) findViewById(R.id.radioButton1);
        b1.setChecked(true);
        b2 = (RadioButton) findViewById(R.id.radioButton2);
        b3 = (RadioButton) findViewById(R.id.radioButton3);
        b4 = (RadioButton) findViewById(R.id.radioButton4);
        eventMarkers = new HashMap<Integer,Marker>();
        refresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEvents();
            }
        });
        mapv.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.031202, 29.258801), 17.0f));
                IconGenerator icnGenerator = new IconGenerator(current);
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Harvest Cafe")))
                        .position(new LatLng(41.030460, 29.258986))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Subway")))
                        .position(new LatLng(41.032738, 29.258407))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Kasıkla")))
                        .position(new LatLng(41.032823, 29.257575))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Kahve Dünyası")))
                        .position(new LatLng(41.032143, 29.257983))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Cafe Nero")))
                        .position(new LatLng(41.031488, 29.259083))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Sok Market")))
                        .position(new LatLng(41.031423, 29.259463))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Oz Hairdresser")))
                        .position(new LatLng( 41.031516, 29.259421))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Yemekhane")))
                        .position(new LatLng(41.031815, 29.259163))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Forum Alanı")))
                        .position(new LatLng(41.031621, 29.259340))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Hukuk Oditoryumu")))
                        .position(new LatLng(41.032876, 29.257801))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Hukuk Oditoryumu")))
                        .position(new LatLng(41.032876, 29.257801))
                        .flat(false));
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon("Spor Merkezi")))
                        .position(new LatLng(41.034002, 29.258050))
                        .flat(false));


            }
        });
         /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem()!=null) {
                    Log.w("SELECTED!", position + String.valueOf(spinner.getSelectedItem()));
                    getUserId(String.valueOf(spinner.getSelectedItem()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new Thread(new NotificationPuller()).start();

       postXMLTask signin = new postXMLTask();
        signin.execute(new String[]{url + "/Ozu/Signin"});*/
    }

    class NotificationPuller implements Runnable {

        public void run() {
            while(true) {
                getNotifications();
                try {
                    //every 4 seconds
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void getNotifications(){
            if(current_user!=-1) {
                GetXMLTask task = new GetXMLTask(9);
                if (cur_setting == 1 || cur_setting==3) {
                    task.execute(new String[]{url + "/Ozu/Notification?usr=" + current_user + "&set=" + cur_setting});
                } else if (cur_setting == 2) {
                    task.execute(new String[]{url + "/Ozu/Notification?usr=" + current_user + "&set=" + cur_setting + "&lat=" + cur_latitude + "&lon=" + cur_longitude});
                }
            }
        }

    }

    public void sendNotification(String title,String Text) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_menu_send)
                        .setContentTitle(title)
                        .setContentText(Text);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
    }

    public void processNotifications(String output) {
        Log.e("NOTIFICATION", output);
        if (output.length() > 0) {
            String lines[] = output.split("\n");
            String content = "";
            for (int i = 0; i < lines.length; i++) {
                String parts[] = lines[i].split(";");
                int eventid = Integer.parseInt(parts[0]);
                String venueName = parts[1];
                String eventName = parts[2];
                String hour = (parts[3].split(" "))[1];
                double lat = Double.parseDouble(parts[4]);
                double lon = Double.parseDouble(parts[5]);
                content += eventName + "\n" + "Yer: " + venueName + " Saat:" + hour;
                content += "\n";
                if (!eventMarkers.containsKey(eventid)) {
                    mapv.getMapAsync(new eventMarkerCallback(eventid, eventName + "\n" + "Yer: " + venueName + " Saat:" + hour, lat, lon));
                    Toast.makeText(current, "Yeni Etkinlik: " + eventName + "\n" + "Yer: " + venueName + " Saat:" + hour,
                            Toast.LENGTH_SHORT).show();
                }

            }
            sendNotification("Yeni Bir Etkinliğiniz Var!", content);
        }
    }

    class eventMarkerCallback implements OnMapReadyCallback {
        String content;
        double lat,lon;
        int id;
        public eventMarkerCallback(int id,String content,double lat,double lon){
            this.content = content;
            this.lat = lat;
            this.lon = lon;
            this.id = id;
        }
        @Override
        public void onMapReady(GoogleMap googleMap) {
            IconGenerator icnGenerator = new IconGenerator(current);
            icnGenerator.setColor(Color.RED);
            Marker ev =googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon(content)))
                    .position(new LatLng(lat,lon))
                    .flat(false));
            eventMarkers.put(id,ev);
        }
    }

    private void marshmallowGPSPremissionCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        else{
            Criteria finloc = new Criteria();
            finloc.setAccuracy(Criteria.ACCURACY_COARSE);
            lman.requestLocationUpdates(0L, 0.0f, finloc, this, null);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        for (Map.Entry<Integer, Marker> entry : eventMarkers.entrySet())
        {
            entry.getValue().remove();
        }
        eventMarkers.clear();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
              //  if (!checked){
                    b1.setChecked(true);
                    b2.setChecked(false);
                    b3.setChecked(false);
                    b4.setChecked(false);
           //     }
                    cur_setting=1;
                    break;
            case R.id.radioButton2:
             //   if (!checked){
                    b1.setChecked(false);
                    b2.setChecked(true);
                    b3.setChecked(false);
                    b4.setChecked(false);
                    cur_setting=2;
              //  }
                    break;
            case R.id.radioButton3:
             //   if (!checked){
                    b1.setChecked(false);
                    b2.setChecked(false);
                    b3.setChecked(true);
                    b4.setChecked(false);
                    cur_setting=3;
                // }
                    break;
            case R.id.radioButton4:
               // if (!checked){
                    b1.setChecked(false);
                    b2.setChecked(false);
                    b3.setChecked(false);
                    b4.setChecked(true);
                    cur_setting=4;
             //   }
                    break;
        }
    }

    public void getNames() {
        GetXMLTask task = new GetXMLTask(1);
        task.execute(new String[] { url+"/Ozu/UserList" });
    }

    public void logLocation(Location location) {
        if(current_user!=-1) {
            GetXMLTask task = new GetXMLTask(3);
            task.execute(new String[]{url + "/Ozu/logLocation?usr="+current_user+"&lat="+location.getLatitude()+"&lon="+location.getLongitude()});
        }
    }

    public void getUserInterests(){
        GetXMLTask task = new GetXMLTask(5);
        task.execute(new String[]{url + "/Ozu/UserIdInterest?usr="+current_user});
    }

    public void processUserInteresets(String output){
        String parts[] = output.split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, parts);
        listView2.setAdapter(adapter);
    }

    public void getEvents() {
            GetXMLTask task = new GetXMLTask(4);
            task.execute(new String[]{url + "/Ozu/EventList"});

    }

    public void processEventList(String output){
        String parts[] = output.split("\n");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, parts);
        listView.setAdapter(adapter);
    }

/*
    public void processNames(String output){
        String parts[] = output.split("\n");

        List<String> list = new ArrayList<String>();
        for(int i=0;i<parts.length;i++) {
            list.add(parts[i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }*/

    public void getUserId(String name) {
            String parts[] = name.split(" ");
            GetXMLTask task = new GetXMLTask(2);
        if(parts.length>0) {
            if (parts.length == 3) {
                task.execute(new String[]{url + "/Ozu/UsernameUserId?name=" + parts[0] + " " + parts[1] + "&surname=" + parts[2]});
            } else {
                task.execute(new String[]{url + "/Ozu/UsernameUserId?name=" + parts[0] + "&surname=" + parts[1]});
            }
        }
    }

    public void processUserId(String output){
        Log.w("USERID",output);
        current_user = Integer.parseInt(output.replace("\n",""));
        getUserInterests();
    }

    @Override
    public void onResume() {
        mapv.onResume();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Criteria finloc = new Criteria();
                    finloc.setAccuracy(Criteria.ACCURACY_COARSE);
                    lman.requestLocationUpdates(0L, 0.0f, finloc, this, null);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.map_view) {
            map.setVisibility(View.VISIBLE);
            settings.setVisibility(View.GONE);
            event_list.setVisibility(View.GONE);
        } else if (id == R.id.settings) {
            map.setVisibility(View.GONE);
            settings.setVisibility(View.VISIBLE);
            event_list.setVisibility(View.GONE);
            getNames();

        } else if (id == R.id.event_list) {
            map.setVisibility(View.GONE);
            settings.setVisibility(View.GONE);
            event_list.setVisibility(View.VISIBLE);
            getEvents();

        }else if(id== R.id.interests){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("user_id", subscriber.getId());
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(final Location location) {
        mapv.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                LatLng mapCenter = new LatLng(location.getLatitude(),location.getLongitude());

              //  map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 17));

                // Flat markers will rotate when the map is rotated,
                // and change perspective when the map is tilted.
                if(curmarker!=null){
                    curmarker.remove();
                }
                curmarker = map.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.arrow,50,50)))
                        .position(mapCenter)
                        .flat(false));



            }
        });
        logLocation(location);
        cur_latitude = location.getLatitude();
        cur_longitude = location.getLongitude();
    }

    public Bitmap resizeMapIcons(int iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),iconName);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetXMLTask extends AsyncTask<String, Void, String> {
        int id;
        public GetXMLTask(int id){
            this.id = id;
        }
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
           if(output!=null) {
               Log.w("OUTPUT", output);
               if (id == 1) {
                    //processNames(output);
               } else if(id == 2){
                    processUserId(output);
               }
               else if(id==3){

               }
               else if(id==4){
                   processEventList(output);
               }
               else if(id==5){
                   processUserInteresets(output);
               }
               else if(id==9){
                   processNotifications(output);
               }

           }
        }
    }


    /*
    private class postXMLTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                ArrayList<NameValuePair> postParameters;


                postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("username", "idil.utkusoy@ozu.edu.tr"));
                postParameters.add(new BasicNameValuePair("password", "MTIzNDU2"));

                httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));


                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if(output!=null) {
                try {
                    JSONObject model = new JSONObject(output);
                    model.get("Username");
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                Log.w("OUTPUT", output);
            }
        }
    }

    */
}




