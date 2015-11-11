package org.lunchtalk.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public static final String PARAM_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameField = (EditText) findViewById(R.id.username);
        final EditText passwordField = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String enteredPassword = passwordField.getText().toString();
                String enteredUsername = usernameField.getText().toString();
                if (enteredPassword.isEmpty() || enteredUsername.isEmpty()) {
                    // TODO: show some error
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(PARAM_USERNAME, enteredUsername);
                    startActivity(intent);
                }
            }
        });
    }
}
