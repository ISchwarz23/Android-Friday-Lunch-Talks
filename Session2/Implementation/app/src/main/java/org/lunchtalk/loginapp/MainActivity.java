package org.lunchtalk.loginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String enteredUsername = getIntent().getStringExtra(LoginActivity.PARAM_USERNAME);

        TextView usernameField = (TextView) findViewById(R.id.username);
        usernameField.setText(enteredUsername);
    }
}
