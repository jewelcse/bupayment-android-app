package com.bupayment.bupayment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FormfillupFeeActivity extends AppCompatActivity {

    EditText roll,reg,name,mname,fname,semester,dept,fac,mainFee,misceFee,totalFee;
    Button formFilluppayBtn;
    Boolean validity_status;
    RequestParams params;
    AsyncHttpClient client;
    String URL="http://192.168.56.1:9095/DevelopmentFees/restApiFormfillupFeeController";


    Button btnOk;
    Dialog errDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formfillup_fee);



        errDialog = new Dialog(this);

        roll = findViewById(R.id.stu_roll);
        reg = findViewById(R.id.stu_reg);
        name = findViewById(R.id.stu_name);
        mname = findViewById(R.id.stu_mname);
        fname = findViewById(R.id.stu_fname);
        semester = findViewById(R.id.stu_semester);
        dept = findViewById(R.id.stu_dept);
        fac = findViewById(R.id.stu_fac);
        mainFee = findViewById(R.id.mainFee);
        misceFee = findViewById(R.id.misceFee);
        totalFee = findViewById(R.id.totalFee);
        formFilluppayBtn = findViewById(R.id.formfillupPaymentBtn);


        roll.setText(null);
        reg.setText(null);
        name.setText(null);
        mname.setText(null);
        fname.setText(null);
        semester.setText(null);
        dept.setText(null);
        fac.setText(null);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("login",0);
        final SharedPreferences.Editor editor = preferences.edit();

        String rollno = preferences.getString("stu_login_roll",null);


        params = new RequestParams();
        params.put("rollno",rollno );


        client = new AsyncHttpClient();
        Toast.makeText(this, URL, Toast.LENGTH_SHORT).show();
        client.get(URL,params,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    roll.setText(response.getString("d_roll"));
                    reg.setText(response.getString("d_reg"));
                    name.setText(response.getString("d_name"));
                    mname.setText(response.getString("d_mname"));
                    fname.setText(response.getString("d_fname"));
                    semester.setText(response.getString("d_semester"));
                    dept.setText(response.getString("d_dept"));
                    fac.setText(response.getString("d_faculty"));

                    mainFee.setText(response.getString("main_fee"));
                    misceFee.setText(response.getString("misce_fee"));

                    validity_status = Boolean.valueOf(response.getString("validity_status"));
                    //validity_status=true;
                    int m1  = Integer.parseInt(response.getString("main_fee"));
                    int m2  = Integer.parseInt(response.getString("misce_fee"));
                    String total  = String.valueOf(m1+m2);
                    totalFee.setText(total);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        formFilluppayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validity_status == true){

                    Toast.makeText(FormfillupFeeActivity.this,"TRUE",Toast.LENGTH_SHORT).show();

                }else{

                    errDialog.setContentView(R.layout.error_popup);
                    errDialog.show();
                    btnOk = (Button) errDialog.findViewById(R.id.btnOk);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            errDialog.dismiss();
                        }
                    });
                }


            }
        });


    }







}
