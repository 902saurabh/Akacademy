package com.example.akacademy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.akacademy.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {


    private EditText name;
    private EditText email;
    private EditText password;

    private EditText confirmPassword;
    private Button reg_btn;
    private Button log_btn;
    private FirebaseAuth auth;
    private DatabaseReference myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.sign_name);
        email = findViewById(R.id.sign_email);
        password = findViewById(R.id.sign_password);
        confirmPassword = findViewById(R.id.confirm_sign_password);
        reg_btn = findViewById(R.id.btn_register);
        log_btn = findViewById(R.id.btn_login);

        auth = FirebaseAuth.getInstance();
        myData = FirebaseDatabase.getInstance().getReference("Users");

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_name = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_confirm_password = confirmPassword.getText().toString();

                if(!TextUtils.isEmpty(txt_name) ||
                        !TextUtils.isEmpty(txt_email) ||
                        !TextUtils.isEmpty(txt_password) ||
                        !TextUtils.isEmpty(txt_confirm_password)
                ){

                    if (txt_email.length()<6){
                        Toast.makeText(RegistrationActivity.this, "Password should be at least 6 character!", Toast.LENGTH_SHORT).show();
                    }else if(!txt_password.equals(txt_confirm_password)){
                        Toast.makeText(RegistrationActivity.this, "Password miss-match", Toast.LENGTH_SHORT).show();
                    }else{
                        registerUser(txt_name,txt_email,txt_password);
                    }
                }else{
                    Toast.makeText(RegistrationActivity.this, "All Fields are manadatory!", Toast.LENGTH_SHORT).show();
                }



            }
        });


        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                finish();
            }
        });



    }

    private void registerUser(final String txt_name, final String email, final String password) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(txt_name,email);
                    myData.push().setValue(user);
                    Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegistrationActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
