package com.brandycamacho.userpreferencetest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static final String PREF_FILE_NAME="user_pref";
    public static final String KEY_USER_TOKEN="user_token";
    public static final String KEY_USER_ID="user_id";
    public static final String KEY_USER_PW="user_pw";
    public static final String KEY_USER_TOKEN_EXPIRE="user_token_expire";

    private String mUserToken;
    private boolean mUserTokenExpired;
    private String mUserID;
    private String mUserPassword;
    private boolean mFromSavedInstanceState;
    private boolean isUserAuthenticated=false;

    EditText et_1,et_2,et_3;
    TextView tv_1;

   String data;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Preference Data
        mUserToken=readFromPreferences(this,KEY_USER_TOKEN,"false");
        mUserTokenExpired=Boolean.valueOf(readFromPreferences(this,KEY_USER_TOKEN_EXPIRE,"false"));
        mUserID=readFromPreferences(this, KEY_USER_ID, "false");
        mUserPassword=readFromPreferences(this, KEY_USER_PW, "false");

        if(savedInstanceState!=null) {
            mFromSavedInstanceState = true;
        // end

        }


        data = "User ID = "+mUserID+"\nToken = "+mUserToken+"\nToken Expired = "+String.valueOf(mUserTokenExpired);
        tv_1 = (TextView)findViewById(R.id.tv_1);
        et_1 = (EditText) findViewById(R.id.et_1);
        et_2 = (EditText) findViewById(R.id.et_2);
        et_3 = (EditText) findViewById(R.id.et_3);


        Button btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserID = et_1.getText().toString();
                mUserPassword = MD5(et_2.getText().toString());
                mUserTokenExpired = Boolean.valueOf(et_3.getText().toString());



                saveToPreferences(getBaseContext(), KEY_USER_ID, mUserID);
                saveToPreferences(getBaseContext(), KEY_USER_PW, mUserPassword);
                saveToPreferences(getBaseContext(), KEY_USER_TOKEN_EXPIRE, String.valueOf(mUserTokenExpired));

                data = "User ID = "+mUserID+"\nUser Password = "+ mUserPassword+"\nToken = "+mUserToken+"\nToken Expired = "+String.valueOf(mUserTokenExpired);
                tv_1.setText(data);
            }
        });

        tv_1.setText(data);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        // we setup a default value that way if there is no data in shared preferences the default value is then used until a new value is declared
        public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString (preferenceName, preferenceValue);
//        editor.commit()
            editor.apply();
        }

        public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString (preferenceName,defaultValue);
        }

    // generate MD5 Hash String for storage
    public String MD5(String md5) {
        try {

            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    }
