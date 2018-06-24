package com.example.kotytoger.memgame2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    StringBuffer buffer;
    DataBaseHelper myDb;

    private String nameEditString="";
    private Button buttonStart;

    private TextView locationTextView;
    private LocationManager locationManager;
    private double longitude;
    private double altitude;
    private String countryName;
    private String cityName="";
    private String lastScore="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db
        myDb = new DataBaseHelper(this);





        //showMessage("data", buffer.toString());





        //start
        buttonStart = findViewById(R.id.button_start);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = findViewById(R.id.nameText);
                EditText ageEditText = findViewById(R.id.ageText);

                String ageEditString = ageEditText.getText().toString();
                nameEditString = nameEditText.getText().toString();

                Intent intent = new Intent(view.getContext(), DifficultyChoiceActivity.class);
                intent.putExtra("outputName", nameEditString);
                intent.putExtra("outputAge", ageEditString);
                startActivity(intent);
            }
        });


        //fragments menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //GPS
        locationTextView = findViewById(R.id.location_text_view);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        loc_func(location);  //for country , city

    }

    @Override
    protected void onResume() {

        super.onResume();
        lastScore = getIntent().getStringExtra("outputScore");
        //Toast.makeText(MainActivity.this,lastScore,Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AddData();


        //showMessage("Data", buffer.toString());
    }

    public void AddData(){
        //save data
        boolean isInserted = myDb.insertData(nameEditString, lastScore, cityName);
        if (isInserted)
            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
    }





    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    android.support.v4.app.Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.switch_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.switch_table:

                            Cursor res = myDb.getAllData();
                            if(res.getCount() == 0) {
                                showMessage("Error", "Empty");
                            }
                            buffer = new StringBuffer();
                            while (res.moveToNext()){
                                //buffer.append("Id: " + res.getString(0));
                                buffer.append("" + res.getString(1));
                                buffer.append("    " + res.getString(2));
                                buffer.append("    " + res.getString(3)+"\n");
                            }

                            selectedFragment = new BlankFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", buffer.toString());
                            selectedFragment.setArguments(bundle);

                            break;
                        case R.id.switch_map:
                            selectedFragment = new ScoreMapFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                    return true;

                }
            };

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        //locationTextView.setText("lo: " + longitude + ", al: " +altitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private void loc_func(Location location){

        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;
            addresses = geocoder.getFromLocation(altitude,longitude,1);
            countryName = addresses.get(0).getCountryName();
            cityName = addresses.get(0).getLocality();

        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),"Error:" + e, Toast.LENGTH_SHORT).show();
        }
    }
}