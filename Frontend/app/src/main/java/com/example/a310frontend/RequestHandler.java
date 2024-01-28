package com.example.a310frontend;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;

public abstract class RequestHandler {

    private Handler uiHandler;
    private Activity activity; // Reference to the activity

    public RequestHandler(Activity activity) {
        this.activity = activity;

        uiHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                User user = (User) msg.obj; // Expecting a single User object

                if (user != null) {
                    // If user object is not null, login is successful
                    handleLoginSuccess(user);
                } else {
                    // If user object is null, login failed
                    handleLoginFailure();
                }

                return true;
            }
        });
    }

    public Handler getUiHandler() {
        return uiHandler;
    }

    // Abstract methods to be implemented in Login_Activity
    public abstract void handleLoginSuccess(User user);
    public abstract void handleLoginFailure();
}
