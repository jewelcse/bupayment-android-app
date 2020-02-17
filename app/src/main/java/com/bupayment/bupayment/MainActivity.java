package com.bupayment.bupayment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




//    TextView responseText = findViewById(R.id.responseView);
//    public void callingGetRequest(){
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(URL, new JsonHttpResponseHandler(){
//
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                String data = null;
//                try {
//                    data = response.getString("id");
//                    responseText.setText(data);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                responseText.setText(data);
//
//                //showToastMsg("Login Success");
//            }
//
//
//        });
//
//
//    }








}
