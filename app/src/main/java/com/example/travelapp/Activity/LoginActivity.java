package com.example.travelapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.JsonDetails;
import com.example.travelapp.Model.UserData;
import com.example.travelapp.PreferenceSettings;
import com.example.travelapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private EditText mEditUsername, mEditPassword;
    private CheckBox mRememberPassword;
    private Button mBtnLogin;
    private String userName, password;
    private boolean rememberMe;
    private PreferenceSettings mPreferenceSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditPassword = (EditText) findViewById(R.id.ed_password);
        mEditUsername = (EditText) findViewById(R.id.ed_username);
        mRememberPassword = (CheckBox) findViewById(R.id.remember_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mPreferenceSettings = new PreferenceSettings(LoginActivity.this);
        if (mPreferenceSettings.getIsLogin()) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    public void loginPressed(View view) {

        this.userName = mEditUsername.getText().toString();
        this.password = mEditPassword.getText().toString();
        this.rememberMe = mRememberPassword.isChecked();

        String fileData = JsonDetails.loadDataFromFile(LoginActivity.this, "UserData.json");
        if (fileData == null) {
            Log.d(TAG, "Error reading data from file");
            return;
        }
        JSONArray jsonArray = JsonDetails.convertToJSONArray(fileData);
        if (jsonArray == null) {
            Log.d(TAG, "Error converting to JSON");
            return;
        }
        parseJSONData(jsonArray);


    }

    public void parseJSONData(JSONArray jsonArray) {

        String username = null, password = null;
        int id = 0;

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObject = jsonArray.getJSONObject(i);
                id = currentObject.getInt("id");
                Log.d(TAG, "Parsed id: " + id);

                username = currentObject.getString("username");
                password = currentObject.getString("password");

                if (username.equals(this.userName) && password.equals(this.password)) {

                    UserData userData = new UserData(id, userName, password, rememberMe);
                    Log.d(TAG, userData.toString());
                    mPreferenceSettings.setUserName(userName);
                    mPreferenceSettings.setUserId(id);
                    if (rememberMe) {
                        mPreferenceSettings.setIslogin(true);
                    }


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    mPreferenceSettings.setUserName(userName);
                    startActivity(intent);
                    finish();
                    break;
                }
            }

            if (!username.equals(this.userName) && !password.equals(this.password)) {
                Toast.makeText(LoginActivity.this, "Please enter valid credentials.", Toast.LENGTH_SHORT).show();
            } else if (!username.equals(this.userName) && password.equals(this.password)) {
                Toast.makeText(LoginActivity.this, "Please enter valid credentials.", Toast.LENGTH_SHORT).show();
            } else if (username.equals(this.userName) && !password.equals(this.password)) {
                Toast.makeText(LoginActivity.this, "Please enter valid credentials.", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

}