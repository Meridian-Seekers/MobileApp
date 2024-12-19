package com.example.finalproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.interfaces.ResponseCallBack;
import com.example.finalproject.interfaces.passwordresponceCallBack;
import com.example.finalproject.models.LoginResponse;
import com.example.finalproject.models.PasswordResetResponse;
import com.example.finalproject.services.AuthService;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button submitButton;
    DBHelper dbHelper;
    private String email;
    private String resetToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        dbHelper = new DBHelper(this);

        newPasswordEditText = findViewById(R.id.editTextTextPassword2);
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword);
        submitButton = findViewById(R.id.button);

        email = getIntent().getStringExtra("email");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePasswordReset();
            }
        });
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        dbHelper = new DBHelper(this);

        newPasswordEditText = findViewById(R.id.editTextTextPassword2);
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword);
        submitButton = findViewById(R.id.button);

        email = getIntent().getStringExtra("email");
        resetToken = getIntent().getStringExtra("reset_token"); // Ensure this is received

        if (resetToken == null) {
            Toast.makeText(this, "Reset token is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePasswordReset();
            }
        });
    }*/


    /*private void handlePasswordReset() {
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError("New password is required");
            newPasswordEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Confirm password is required");
            confirmPasswordEditText.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }
        AuthService authService = new AuthService();
        authService.resetPassword(email, newPassword, resetToken, new passwordresponceCallBack() {
            @Override
            public void onSuccess(PasswordResetResponse response) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset successful", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onError(Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    /*private void handlePasswordReset() {
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError("New password is required");
            newPasswordEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Confirm password is required");
            confirmPasswordEditText.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }
        AuthService authService = new AuthService();
        authService.resetPassword(email, newPassword, resetToken, new passwordresponceCallBack() {
            @Override
            public void onSuccess(PasswordResetResponse response) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset successful", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onError(Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    /*private void handlePasswordReset() {
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError("New password is required");
            newPasswordEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Confirm password is required");
            confirmPasswordEditText.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }

        AuthService authService = new AuthService();
        authService.resetPassword(email, newPassword, resetToken, new passwordresponceCallBack() {
            @Override
            public void onSuccess(PasswordResetResponse response) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset successful", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void handlePasswordReset() {
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();


        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError("New password is required");
            newPasswordEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Confirm password is required");
            confirmPasswordEditText.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }

        AuthService authService = new AuthService();
        authService.resetPassword(email,confirmPassword, newPassword, new passwordresponceCallBack() {
            @Override
            public void onSuccess(PasswordResetResponse response) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset successful", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
