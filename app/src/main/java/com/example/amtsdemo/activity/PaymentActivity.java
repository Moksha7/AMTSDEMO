package com.example.amtsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.pojo.User_Insert;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends Activity implements PaymentResultWithDataListener {
    private static final String TAG = com.example.amtsdemo.activity.PaymentActivity.class.getSimpleName();
    Order order;
    TextView orderId,currency;
    int uid,passid;
    String strTotaldays;
    int totaldays;
    EditText etEmail,etPhone;
    boolean isEmail,isPhone,isConfirm;
    String strEmail,strPhone;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        uid = getIntent().getIntExtra("uid", 0);
        passid = getIntent().getIntExtra("passid", 0);
        strTotaldays = getIntent().getStringExtra("totaldays");
        totaldays = Integer.parseInt(strTotaldays);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        orderId = findViewById(R.id.orderId);
        currency = findViewById(R.id.currencyRs);

        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        // Checkout checkout = new Checkout();
        Checkout.preload(getApplicationContext());
        if (totaldays <= 30) {
            currency.setText("100");
        } else if (totaldays>31 && totaldays <= 60){
            currency.setText("200");
        }
        else if (totaldays>=61 && totaldays <= 90) {
            currency.setText("300");
        }
        else if (totaldays>=91 && totaldays <=120 ) {
            currency.setText("400");
        }
        else if (totaldays>=121 && totaldays <= 150) {
            currency.setText("500");
        }
        else{
            currency.setText("600");
        }
        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getData()) {
                    try {
                        // RazorpayClient razorpay = new RazorpayClient("rzp_test_WVBC5SP442bRFl", "H5uPESmR1V56ucOHPMW6mJvn");

                        JSONObject orderRequest = new JSONObject();
                        orderRequest.put("amount", 100); // amount in the smallest currency unit
                        orderRequest.put("currency", "INR");
                        orderRequest.put("receipt", "order_rcptid_11");
                        order = new DemoTask().execute(orderRequest).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startPayment();
                }
            }
        });

        TextView privacyPolicy = (TextView) findViewById(R.id.txt_privacy_policy);

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("https://razorpay.com/sample-application/"));
                startActivity(httpIntent);
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_WVBC5SP442bRFl");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");
            options.put("order_id", order.get("id"));
            JSONObject preFill = new JSONObject();
            preFill.put("email", strEmail);
            preFill.put("contact", strPhone);
            options.put("prefill", preFill);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try{
            String status = "SUCCESS";
            String generated_signature = com.example.amtsdemo.activity.Signature.calculateRFC2104HMAC(paymentData.getOrderId() + "|" +paymentData.getPaymentId(),"H5uPESmR1V56ucOHPMW6mJvn");
            if (generated_signature == paymentData.getSignature()) {
                String orderId = paymentData.getOrderId();
                String paymentId = paymentData.getPaymentId();
                onPaymentInsert(orderId,paymentId,status);
               Toast.makeText(com.example.amtsdemo.activity.PaymentActivity.this,"payment is successful",Toast.LENGTH_LONG).show();
            }
            else{
                String orderId = paymentData.getOrderId();
                String paymentId = paymentData.getPaymentId();
                onPaymentInsert(orderId,paymentId,status);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void onPaymentInsert(String orderId, String paymentId,String status) {
        int amount = Integer.parseInt(currency.getText().toString());
        Call<User_Insert> call = RetrofitClient.getInstance().getInterPreter().insertPayment(uid,passid,orderId,paymentId,status,amount);
        call.enqueue(new Callback<User_Insert>() {
            @Override
            public void onResponse(Call<User_Insert> call, Response<User_Insert> response) {
                User_Insert response1 = response.body();
                if (response1.error) {
                    Toast.makeText(PaymentActivity.this, "Error while Payment", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PaymentActivity.this,response1.message,Toast.LENGTH_LONG).show();
                    //Toast.makeText(UploadImage.this,"Image Successfully Uploaded",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User_Insert> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Error while Payment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try{
            String status = "FAIL";
            String generated_signature = com.example.amtsdemo.activity.Signature.calculateRFC2104HMAC(paymentData.getOrderId() + "|" +paymentData.getPaymentId(),"H5uPESmR1V56ucOHPMW6mJvn");
            if (generated_signature == paymentData.getSignature()) {
                String orderId = paymentData.getOrderId();
                String paymentId = paymentData.getPaymentId();
                onPaymentInsert(orderId,paymentId,status);
                Toast.makeText(com.example.amtsdemo.activity.PaymentActivity.this,"payment is successful",Toast.LENGTH_LONG).show();
            }
            else{
                String orderId = paymentData.getOrderId();
                String paymentId = paymentData.getPaymentId();
                onPaymentInsert(orderId,paymentId,status);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean getData(){
        strEmail = etEmail.getText().toString();
        strPhone = etPhone.getText().toString();
        if(etEmail.getText().toString().length()==0)
        {
            etEmail.setError(getResources().getString(R.string.email));
            etEmail.requestFocus();
            isEmail = false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError(getResources().getString(R.string.email_error1));
            etEmail.requestFocus();
            isEmail = false;
        }
        else
        {
            isEmail = true;
        }
        if(etPhone.getText().toString().length()==0)
        {
            etPhone.setError(getResources().getString(R.string.mobileno));
            etPhone.requestFocus();
            isPhone = false;
        }
        else if(!Patterns.PHONE.matcher(etPhone.getText().toString()).matches()){
            etPhone.setError(getResources().getString(R.string.mobileno));
            etPhone.requestFocus();
            isPhone=false;
        }
        else
        {
            isPhone=true;
        }
        if(isEmail && isPhone){
            return true;
        }
        else{
            return false;
        }
    }

    class DemoTask extends AsyncTask<JSONObject, Void, Order> {

        @Override
        protected Order doInBackground(JSONObject... jsonObjects) {
            try {
                RazorpayClient razorpay = new RazorpayClient("rzp_test_WVBC5SP442bRFl", "H5uPESmR1V56ucOHPMW6mJvn");
                Order order = razorpay.Orders.create(jsonObjects[0]);
                return order;
            } catch (RazorpayException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}
