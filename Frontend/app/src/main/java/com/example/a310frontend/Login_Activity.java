package com.example.a310frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login_Activity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button button;
    private ImageButton backButton;
    private User_Login_Repo userLoginRepo;
    private ExecutorService executorService;
    private RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email_input);
        editTextPassword = findViewById(R.id.password_input);
        button = findViewById(R.id.button);
        backButton = findViewById(R.id.backButton);

        userLoginRepo = new User_Login_Repo();
        executorService = Executors.newFixedThreadPool(2);
        requestHandler = new RequestHandler(this) {
            @Override
            public void handleLoginSuccess(User user) {
                Toast.makeText(Login_Activity.this, "Successful Login.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_Activity.this, mainrcycle.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void handleLoginFailure() {
                Toast.makeText(Login_Activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        };

        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(intent);

        });

        button.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            userLoginRepo.authenticateUser(executorService, requestHandler.getUiHandler(), email, password);
        });
    }


}
