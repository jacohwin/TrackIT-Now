package com.example.trackit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.file.attribute.PosixFileAttributeView;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etName,etAge,etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName =(EditText)findViewById(R.id.etName);
        etAge =(EditText)findViewById(R.id.etAge);
        etUsername =(EditText)findViewById(R.id.etUsername);
        etPassword =(EditText)findViewById(R.id.etPassword);
        bRegister = (Button)findViewById(R.id.etRegister);
        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etRegister:

                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                try{
                    int age = Integer.parseInt(etAge.getText().toString());

                    User registeredData = new User(name,age,username, password);
                }
                catch (NumberFormatException e){
                    System.out.println("Number format not applicable");
                }



                break;
        }
    }
}

