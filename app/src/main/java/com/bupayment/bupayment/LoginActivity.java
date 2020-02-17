package com.bupayment.bupayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    RequestParams params;
    AsyncHttpClient client;
    String URL="http://192.168.56.1:9095/DevelopmentFees/restApiLoginController";

    Button loginBtn ;
    CheckBox rem;
    EditText studentRollNo,studentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        studentRollNo = findViewById(R.id.studentRollNo);
        studentPassword = findViewById(R.id.studentPassword);
        rem = findViewById(R.id.rememberMe);
        loginBtn = findViewById(R.id.loginBtn);


        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");

        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        else if(checkbox.equals("false")) {

            Toast.makeText(LoginActivity.this,"Login please",Toast.LENGTH_SHORT).show();
        }

        studentLogin();
        rememberMe();


    }

    public void rememberMe()
    {
        rem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Checked",Toast.LENGTH_SHORT).show();
                }
                else if(!compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Unchecked",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void studentLogin(){

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rollno = studentRollNo.getText().toString();
                String pass = studentPassword.getText().toString();
                rememberMe();
                callLoginRestAPI(rollno,pass);


            }
        });




    }



    public void callLoginRestAPI(final String rollno, String pass) {
        params = new RequestParams();
        params.put("rollno", rollno);
        params.put("pass", pass);

        client = new AsyncHttpClient();
        //Toast.makeText(this, URL, Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("login",0);
        final SharedPreferences.Editor editor = preferences.edit();

        client.post(URL,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);


                //String res = response.getString("roll");
                //responseText.setText(res);
                showToastMsg("Login-Success");
                Log.d("LOGIN_SUCCESS", "Success");

                editor.putString("stu_login_roll",rollno);

                editor.commit();

                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                showToastMsg("Login-Failed");
            }
        });

    }

    public void showToastMsg(String msg){

        Toast.makeText(LoginActivity.this,msg+" ",Toast.LENGTH_SHORT).show();

    }



}
