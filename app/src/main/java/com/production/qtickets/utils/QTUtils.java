package com.production.qtickets.utils;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.production.qtickets.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.production.qtickets.login.LoginActivity;



import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by hemanth on 4/9/2018.
 */

public class QTUtils {

    public static String dohaBankOffer="";

    //add com.production.qtickets.login alert dialog box
    public static AlertDialog loginAlertDialog;
    public static AlertDialog covidAlertDialog;
    public static Dialog progressDialog;
    private static AlertDialog alertDialog;
    public static void showProgressDialog(Context context, boolean isShow) {
        if(!((Activity) context).isFinishing()) {
            progressDialog = new Dialog(context);
            progressDialog.setContentView(R.layout.dialog_progressbar);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (isShow) {
                progressDialog.show();
            }
        }
    }
    //movies
    public static void showmovies_ProgressDialog(Context context, boolean isShow) {
        if(!((Activity) context).isFinishing()) {
            progressDialog = new Dialog(context);
            progressDialog.setContentView(R.layout.dialog_progressbar);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setCanceledOnTouchOutside(true);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (isShow) {
                if(!progressDialog.isShowing()) {
                    progressDialog.show();
                }

            }

            try {
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        progressDialog.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report

                    }
                }, 3000); // after 2 second (or 2000 miliseconds), the task will be active.
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }


    //retry
    public static void showAlertDialogbox(final Object object1, final Object object3, Context context, String message){
        if(!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.AlertDialogCustom);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            ((Call<?>) object1).clone().enqueue((Callback) object3);
                            alertDialog.dismiss();
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);

        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.button_background);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);*/
     //   }
    }
    //CommonDilaogue
    public static void showDialogbox(Context context, String message){
            if(!((Activity) context).isFinishing()) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.AlertDialogCustom);
                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                alertDialog.dismiss();
                                }
                        });

                alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
                alertDialog.show();

            }
    }
    //activity to finish
    public static void finishshowDialogbox(final Context context, String message){
        if(!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.AlertDialogCustom);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialog.dismiss();
                            ((Activity) context).finish();
                        }
                    });
            alertDialog = alertDialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
        }
    }
    // (?:youtube(?:-nocookie)?\.com\/(?:[^\/\n\s]+\/\S+\/|(?:v|e(?:mbed)?)\/|\S*?[?&]v=)|youtu\.be\/)([a-zA-Z0-9_-]{11})
    final static String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";

    public static String getVideoId(@NonNull String videoUrl) {
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
            return matcher.group(1);
        return null;
    }


    public static String changeDateFormat(String dates){
        DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = originalFormat.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    public static String getvideoID222(String ytUrl) {
            String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

            Pattern compiledPattern = Pattern.compile(pattern,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = compiledPattern.matcher(ytUrl);
            if (matcher.find()) {
                return matcher.group(1);
            }/*from w  w  w.  j a  va  2 s .c om*/
            return null;
    }

    public static String getVideoUrl(@NonNull String videoId) {
        return "http://youtu.be/" + videoId;
    }

    //com.production.qtickets.login
    public static void LoginAlertDailog(final Context context,String message) {
        LayoutInflater li = LayoutInflater.from(context);
        View popupDialog = li.inflate(R.layout.login_alert_dailog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(popupDialog);
        loginAlertDialog = alertDialogBuilder.create();
        loginAlertDialog.setCancelable(false);
        loginAlertDialog.setCanceledOnTouchOutside(false);
        loginAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageView iv_clostdialog = (ImageView) popupDialog.findViewById(R.id.iv_closedialog);
        TextView signupButton = popupDialog.findViewById(R.id.button_signin);
        TextView alert_text = popupDialog.findViewById(R.id.alert_text);
        alert_text.setText(message);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(context, LoginActivity.class);
                context.startActivity(loginIntent);
                loginAlertDialog.dismiss();
            }
        });

        iv_clostdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAlertDialog.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        loginAlertDialog.setCancelable(false);
        loginAlertDialog.show();
    }

    public static void CovidAlertDailog(final Context context) {
        LayoutInflater li = LayoutInflater.from(context);
        View popupDialog = li.inflate(R.layout.dialog_covid_alert, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(popupDialog);
        covidAlertDialog = alertDialogBuilder.create();
        covidAlertDialog.setCancelable(false);
        covidAlertDialog.setCanceledOnTouchOutside(false);
        covidAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        TextviewBold btn_ok = (TextviewBold) popupDialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                covidAlertDialog.dismiss();
            }
        });

        alertDialogBuilder.setCancelable(false);
        covidAlertDialog.setCancelable(false);
        covidAlertDialog.show();
    }

    public String getFromSharedPreference(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    private static QTUtils utils;
    public static QTUtils getInstance() {
        if (utils == null) {
            utils = new QTUtils();
        }
        return utils;
    }

    public static void saveToSharedPreference(Context context, String key,
                                              Object value) {
        String localValue;
        if (value != null && context != null) {
            localValue = value.toString();
        } else {
            localValue = null;
        }
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, localValue);
        editor.commit();
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
        String line = "";
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line + "\n");
        inputStream.close();
        result = stringBuilder.toString();
        return result;

    }
    public static void dismissProgressDialog(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            }

    }

    public static String parseDouble(Double aDouble){
        DecimalFormat format = new DecimalFormat("#.##",
                DecimalFormatSymbols.getInstance(
                        Locale.ENGLISH));
        format.setDecimalSeparatorAlwaysShown(false);

        return format.format(aDouble);
    }

}
