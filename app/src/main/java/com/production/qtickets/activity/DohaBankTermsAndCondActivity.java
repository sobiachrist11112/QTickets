//package com.production.qtickets.activity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.text.Html;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//
//import com.production.qtickets.R;
//import com.production.qtickets.events.EventFilterActivity;
//import com.production.qtickets.utils.QTUtils;
//import com.production.qtickets.utils.TextviewBold;
//import com.production.qtickets.utils.TextviewRegular;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class DohaBankTermsAndCondActivity extends AppCompatActivity {
//
//    private ImageView iv_back_arrow;
//    private TextviewBold tv_heading;
//
//    private TextviewRegular tv_discription;
//    private TextviewRegular tv_howtoavail;
//    private TextviewRegular tv_termsandcond;
//
//    private ScrollView main_layout;
//
//    private LinearLayout clickproceed_layout;
//
//    private Intent i;
//    private Bundle b;
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        QTUtils.setStatusBarGradiant(DohaBankTermsAndCondActivity.this);
//        setContentView(R.layout.dohabank_terms_conditions_activity);
//
//        //get the terms and conditions data
//        getTermsAndConditions();
//
//        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
//        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DohaBankTermsAndCondActivity.this.finish();
//            }
//        });
//
//        tv_heading = (TextviewBold) findViewById(R.id.tv_heading);
//        tv_discription = (TextviewRegular) findViewById(R.id.tv_discription);
//        tv_howtoavail = (TextviewRegular) findViewById(R.id.tv_howtoavail);
//        tv_termsandcond = (TextviewRegular) findViewById(R.id.tv_termsandcond);
//
//        main_layout = (ScrollView) findViewById(R.id.main_layout);
//
//        clickproceed_layout = (LinearLayout) findViewById(R.id.clickproceed_layout);
//        clickproceed_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                QTUtils.dohaBankOffer = "yes";
//                b = new Bundle();
//                b.putInt("position", 0);
//                i = new Intent(DohaBankTermsAndCondActivity.this, MainActivity.class);
//                i.putExtras(b);
//                startActivity(i);
//                DohaBankTermsAndCondActivity.this.finish();
//            }
//        });
//    }
//
//    // this is the url for getting terms and conditions for doha bank offer
//    private void getTermsAndConditions() {
//        String termsAndConditionsURL = "https://api.q-tickets.com/V5.0/dohabanktermsandconditions";
//        Log.v("termsAndConditionsURL", " == " + termsAndConditionsURL);
//        new termsAndConditionsAsuncTask().execute(termsAndConditionsURL);
//    }
//
//    // here we are calling the asyncTask to get the Doha bank terms and conditions
//    private class termsAndConditionsAsuncTask extends AsyncTask<String, Void, String> {
//        private ProgressDialog dialog;
//
//        @Override
//        protected String doInBackground(String... urls) {
//            return QTUtils.GET(urls[0]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(DohaBankTermsAndCondActivity.this);
//            dialog.setMessage("Loading Please Wait...");
//            dialog.setCancelable(false);
//            dialog.show();
//
//        }
//
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//            Log.v("dohaOffer", "==" + result);
//            try {
//                JSONObject jsonObject = new JSONObject(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("Doha");
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    main_layout.setVisibility(View.VISIBLE);
//
//                    String offerHeading = jsonArray.getJSONObject(i).getString("Heading");
//                    tv_heading.setText(offerHeading);
//
//                    String description = jsonArray.getJSONObject(i).getString("Para");
//                    tv_discription.setText("    " + description);
//                    String howtovail = jsonArray.getJSONObject(i).getString("How to Avail");
//                    tv_howtoavail.setText(Html.fromHtml(howtovail));
//                    String terms_and_conditions = jsonArray.getJSONObject(i).getString("Terms and Conditions");
//                    tv_termsandcond.setText(Html.fromHtml(terms_and_conditions));
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
