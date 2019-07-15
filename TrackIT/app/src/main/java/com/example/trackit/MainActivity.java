package com.example.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bLogout;
    EditText etName,etAge,etUsername,etEmail,etPassword;
    UserLocalStore userLocalStore;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String Password;
    private String Email;
    private MapView mapView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "YOUR_MAPBOX_ACCESS_TOKEN");
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);
        etName =(EditText)findViewById(R.id.etName);
        etAge =(EditText)findViewById(R.id.etAge);
        etUsername =(EditText)findViewById(R.id.etUsername);
        etEmail =(EditText)findViewById(R.id.etEmail);
        etPassword =(EditText)findViewById(R.id.etPassword);
        bLogout =(Button)findViewById(R.id.etLogout);

        bLogout.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void registeredUser(){
        String Username = etUsername.getText().toString().trim();
        String Name = etName.getText().toString().trim();
        String Age= etAge.getText().toString().trim();

        if(TextUtils.isEmpty(Username)){
            Toast.makeText(this, "Please enter your username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Please enter your name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Age)){
            Toast.makeText(this, "Please enter your Age",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("You are Registering");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                }else {


                    Toast.makeText(MainActivity.this, "Could not Register, Please try again", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void addOnCompleteListener(MainActivity mainActivity, OnCompleteListener<AuthResult> authResultOnCompleteListener) {
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(authenticate()== true){
            displayUserDetails();
        }
    }
    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }
    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();

        etUsername.setText(user.username);
        etName.setText(user.name);
        etAge.setText(user.age +"");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);


                startActivity(new Intent(this, Login.class));

                break;
        }
    }
}
