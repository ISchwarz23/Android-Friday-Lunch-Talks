package org.lunchtalk.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    public static final String PARAM_USERNAME = "username";
    private static final int PICK_CONTACT_REQUEST_CODE = 10;

    private EditText usernameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = (EditText) findViewById(R.id.username);
        final EditText passwordField = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button chooseUsernameButton = (Button) findViewById(R.id.chooseUsername);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String enteredPassword = passwordField.getText().toString();
                String enteredUsername = usernameField.getText().toString();
                if (enteredPassword.isEmpty() || enteredUsername.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Missing Username or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(PARAM_USERNAME, enteredUsername);
                    startActivity(intent);
                }
            }
        });

        chooseUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivityForResult(intent, PICK_CONTACT_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && reqCode == PICK_CONTACT_REQUEST_CODE) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int nameColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String chosenUsername = cursor.getString(nameColumn);
            usernameField.setText(chosenUsername);
        }
    }

}
