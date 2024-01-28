package com.example.a310frontend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Register extends AppCompatActivity {
    private EditText editText_Reg_username;
    private EditText editText_Reg_email;
    private EditText editText_Reg_Password;
    private EditText editText_Reg_PasswordConfirm;
    private Button registerButton;
    private ImageButton backButton;
    private User_Register_Repo userRegisterRepo;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText_Reg_username = findViewById(R.id.username_reg_input);
        editText_Reg_email = findViewById(R.id.email_reg_input);
        editText_Reg_Password = findViewById(R.id.password_reg_input);
        editText_Reg_PasswordConfirm = findViewById(R.id.editTextRPassword);
        registerButton = findViewById(R.id.button);
        backButton = findViewById(R.id.backButton);

        executorService = Executors.newFixedThreadPool(2);
        userRegisterRepo = new User_Register_Repo();

        backButton.setOnClickListener(v -> finish());

        registerButton.setOnClickListener(v -> {
            String username = editText_Reg_username.getText().toString().trim();
            String email = editText_Reg_email.getText().toString().trim();
            String password = editText_Reg_Password.getText().toString().trim();
            String confirmPassword = editText_Reg_PasswordConfirm.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            userRegisterRepo.registerUser(executorService, new Handler(getMainLooper()), username, email, password, new User_Register_Repo.RegistrationCallback() {
                @Override
                public void onRegistrationSuccess(User user) {
                    // Registration was successful, handle it here
                    Toast.makeText(Register.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, mainrcycle.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onRegistrationFailed(String errorMessage) {
                    // Registration failed, handle the error here
                    Toast.makeText(Register.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
