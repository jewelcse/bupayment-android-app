package com.bupayment.bupayment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView roll,reg,name,mname,fname,email,phone,semester,dept,fac;

    RequestParams params;
    AsyncHttpClient client;
    String URL="http://192.168.56.1:9095/DevelopmentFees/restApiProfileController";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ///////////////////////Calling get request and fetching profile data//////////////////////////////

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("login",0);
        final SharedPreferences.Editor editor = preferences.edit();

        String rollno = preferences.getString("stu_login_roll",null);


        params = new RequestParams();
        params.put("rollno",rollno );


        roll = findViewById(R.id.rollno);
        reg = findViewById(R.id.regno);
        name = findViewById(R.id.studentname);
        mname = findViewById(R.id.mothername);
        fname = findViewById(R.id.fathername);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        semester = findViewById(R.id.semester);
        dept = findViewById(R.id.department);
        fac = findViewById(R.id.faculty);


        client = new AsyncHttpClient();
        Toast.makeText(this, URL, Toast.LENGTH_SHORT).show();
        client.get(URL,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                String stu_roll = null;
                String stu_reg = null;
                String stu_name = null;
                String stu_mname = null;
                String stu_fname = null;
                String stu_email  = null;
                String stu_phone = null;
                String stu_semester = null;
                String stu_dept = null;
                String stu_fac = null;


                try {

                    stu_roll = response.getString("d_roll");
                    stu_reg = response.getString("d_reg");
                    stu_name  = response.getString("d_name");
                    stu_fname = response.getString("d_fname");
                    stu_mname = response.getString("d_mname");
                    stu_email = response.getString("d_email");
                    stu_phone = response.getString("d_phone");
                    stu_semester = response.getString("d_semester");
                    stu_dept = response.getString("d_dept");
                    stu_fac = response.getString("d_faculty");

                    saveStudentInfo(stu_roll,stu_reg,stu_name,stu_mname,stu_fname,stu_semester,stu_dept,stu_fac);


                    Log.d("Home activity 109 line",stu_mname);
                    roll.setText(stu_roll);
                    reg.setText(stu_reg);
                    name.setText(stu_name);
                    mname.setText(stu_mname);
                    fname.setText(stu_fname);
                    email.setText(stu_email);
                    phone.setText(stu_phone);
                    semester.setText(stu_semester);
                    dept.setText(stu_dept);
                    fac.setText(stu_fac);


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(HomeActivity.this,"Failed to load user profile",Toast.LENGTH_SHORT).show();
            }
        });

        ////////////////////////////end////////////////////////////////////////////////////////////////////


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //////////////////////////student info/////////////////////////////////////////


    public void saveStudentInfo(String roll,String reg,String name,String mname,String fname,String semester,String dept,String fac){

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("profile",0);
        final SharedPreferences.Editor editor = preferences.edit();

        Log.d("Home Activity 157 line",mname);
        editor.putString("stu_roll",roll);
        editor.putString("stu_reg",reg);
        editor.putString("stu_name",name);
        editor.putString("stu_mnane",mname);
        editor.putString("stu_fname",fname);
        editor.putString("stu_semester",semester);
        editor.putString("stu_dept",dept);
        editor.putString("stu_fac",fac);

        editor.commit();


    }




    ////////////////////////////////end student info//////////////////////////////////

    public void logOut(){

        SharedPreferences preferences  = getSharedPreferences("checkbox",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember","false");
        editor.apply();
        finish();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.developmentFee) {
            // Handle the camera action
            Intent devIntent = new Intent(HomeActivity.this,DevelopmentFeeActivity.class);
            startActivity(devIntent);



        } else if (id == R.id.formfillupFee) {
            Intent formIntent = new Intent(HomeActivity.this,FormfillupFeeActivity.class);
            startActivity(formIntent);

        } else if (id == R.id.admissionFee) {
            Intent adIntent = new Intent(HomeActivity.this,AdmissionFeeActivity.class);
            startActivity(adIntent);

        } else if (id == R.id.applicationForm) {

        } else if (id == R.id.logout) {

            logOut();

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
