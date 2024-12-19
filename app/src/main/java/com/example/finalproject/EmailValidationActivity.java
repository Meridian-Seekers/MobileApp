package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.interfaces.ResponseCallBack;
import com.example.finalproject.models.EmailRequest;
import com.example.finalproject.models.LoginResponse;
import com.example.finalproject.services.AuthService;

public class EmailValidationActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button nextButton;
    private Button cancelButton;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_validation);

        dbHelper = new DBHelper(this);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        nextButton = findViewById(R.id.button7);
        cancelButton = findViewById(R.id.button8);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmailValidation();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleEmailValidation() {
        String email = emailEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        AuthService authService=new AuthService();
        EmailRequest emailRequest = new EmailRequest(email);
        authService.validateEmail(email, new ResponseCallBack() {
            @Override
            public void onSuccess(LoginResponse loginResponse) throws Exception {
                Intent intent = new Intent(EmailValidationActivity.this, ResetPasswordActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();

            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(EmailValidationActivity.this, "Email not registered", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
