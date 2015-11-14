package org.lunchtalk.storeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCE_ENTERED_TEXT = "sharedPreferencesText";
    private static final String STORAGE_FILENAME = "storage.txt";
    private static final String EMPTY_STING = "";

    private SharedPreferences preferences;

    private EditText sharedPreferenceEditText;
    private EditText fileStorageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(Context.MODE_PRIVATE);

        sharedPreferenceEditText = (EditText) findViewById(R.id.editTextSharedPreferences);
        fileStorageEditText = (EditText) findViewById(R.id.editTextFileStorage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromSharedPreferences();
        loadFromFile();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveToSharedPreferences();
        saveToFile();
    }

    private void saveToSharedPreferences() {
        String textToSave = sharedPreferenceEditText.getText().toString();
        preferences.edit().putString(SHARED_PREFERENCE_ENTERED_TEXT, textToSave).apply();
    }

    private void saveToFile() {
        String textToSave = fileStorageEditText.getText().toString();
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(openFileOutput(STORAGE_FILENAME, Context.MODE_PRIVATE));
            out.writeObject(textToSave);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    private void loadFromSharedPreferences() {
        String loadedText = preferences.getString(SHARED_PREFERENCE_ENTERED_TEXT, EMPTY_STING);
        sharedPreferenceEditText.setText(loadedText);
    }

    private void loadFromFile() {
        String loadedText = "";
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(openFileInput(STORAGE_FILENAME));
            loadedText = (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        fileStorageEditText.setText(loadedText);
    }

}
