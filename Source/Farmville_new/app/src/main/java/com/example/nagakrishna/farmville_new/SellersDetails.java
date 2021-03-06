package com.example.nagakrishna.farmville_new;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

//http://farmville.kwkpfawsu2.us-west-2.elasticbeanstalk.com/emailcheck/EmailCheck?email=nagakrishna@gmail.com

public class SellersDetails extends AppCompatActivity {

    private TextView nameText, numberText, addressText;
    private TextView txtnameText, txtnumberText, txtaddressText;
    private String returnValues;
    private String fullname, address, number, reviews;
    private TextView btnChat;
    private LinearLayout ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        nameText = (TextView) findViewById(R.id.txtFullnameValue);
        numberText = (TextView) findViewById(R.id.txtNumberValue);
        addressText = (TextView) findViewById(R.id.txtAddressValue);
        ly = (LinearLayout)findViewById(R.id.lySellerDetails);
//        txtnameText = (TextView) findViewById(R.id.txtFullname);
//        txtnumberText = (TextView) findViewById(R.id.txtNumber);
//        txtaddressText = (TextView) findViewById(R.id.txtAddress);
        btnChat = (TextView)findViewById(R.id.btnChat);
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("SellerEmail");
        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        new GetSellerDetails(new LoginServiceListener() {
            @Override
            public void servicesuccess(String str) {
                try {
                    progressDialog.dismiss();
                    returnValues = str;
//                    txtnameText.setVisibility(View.VISIBLE);
//                    txtnumberText.setVisibility(View.VISIBLE);
//                    txtaddressText.setVisibility(View.VISIBLE);
                    ly.setVisibility(View.VISIBLE);
//                    btnChat.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = new JSONArray(returnValues);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    fullname = jsonObject.getString("fullname");
                    address = jsonObject.getString("address");
                    number = jsonObject.getString("number");
                    nameText.setText(fullname);
                    numberText.setText(number);
                    addressText.setText(address);
                    btnChat.setText("  Chat with " + fullname+"  ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, this).execute(email);


//        GetSellerDetails task = new GetSellerDetails(getBaseContext());
//        try {
//            returnValues = task.execute(email).get();
//            JSONArray jsonArray = new JSONArray(returnValues);
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            fullname = jsonObject.getString("fullname");
//            address = jsonObject.getString("address");
//            number = jsonObject.getString("number");
//            nameText.setText(fullname);
//            numberText.setText(number);
//            addressText.setText(address);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


    public void Chat(View view){
        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("chatName", fullname);
        startActivity(intent);
    }

    public void CallNumberIntent(View v) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+" + numberText.getText().toString().trim()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    public void StartMapsIntent(View v){
        if(addressText.getText().toString().equals("no address")){
            return;
        }
        Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
                +addressText.getText().toString()));
        startActivity(geoIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }



}

class GetSellerDetails extends AsyncTask<String, String, String>{

    static String server_output = null;
    static String temp_output = null;
    StringBuilder result = null;
    Context context;
    LoginServiceListener listener;
    public GetSellerDetails(LoginServiceListener listener, Context context){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
//        String urlNew = "http://farmville.kwkpfawsu2.us-west-2.elasticbeanstalk.com/emailcheck/EmailCheck?email=" + email;
        String url1 = context.getResources().getString(R.string.sellerDetails);
        String urlNew = url1 + email;
        try {
            URL url = new URL(urlNew);

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            result = new StringBuilder();
            while ((temp_output = br.readLine()) != null) {
                result.append(temp_output);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.servicesuccess(s);
    }
}

//class SellerDetailsActivity{
//    String adderss;
//    String name;
//    String number;
//    String reviews;
//}
