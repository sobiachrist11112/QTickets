package com.production.qtickets.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.production.qtickets.BuildConfig;
import com.production.qtickets.R;
import com.production.qtickets.dashboard.DashboardContracter;
import com.production.qtickets.dashboard.DashboardPresenter;
import com.production.qtickets.eventQT5.EventHomeActivity;
import com.production.qtickets.model.CustomModel;
import com.production.qtickets.model.DeleteAccount;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.RecyclerItemClickListener;
import com.production.qtickets.utils.SessionManager;


import java.util.ArrayList;

import com.production.qtickets.adapters.NavigationAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.production.qtickets.cmspages.CMSPagesActivity;
import com.production.qtickets.dashboard.DashBoardActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import com.production.qtickets.login.LoginActivity;
import com.production.qtickets.mybookings.MyBookingsActivity;
import com.production.qtickets.myprofile.MyProfile;
import com.production.qtickets.notifications.MyNotificationActivity;
import com.production.qtickets.signup.SignUpActivity;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationContracter.View {

    @BindView(R.id.img_user)
    CircleImageView imgUser;
    @BindView(R.id.welcome)
    TextView welcome;
    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.txt_signin)
    TextView txtSignin;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.close)
    ImageView close;
    private ArrayList<NavigationModel> navigationList = new ArrayList<>();
    private LinearLayoutManager recyclerViewLayoutManager;
    private NavigationAdapter navigationItemsAdapter;
    private String user_id, version;
    private SessionManager sessionManager;
    private Intent i;
    private AlertDialog alert11;
    public static boolean fLogin = false;
    ArrayList<CustomModel> str = new ArrayList<>();
    CircleImageView btn_facebook, btn_youtube;


    NavigationPresenter presenter;
    TextView txt_signout;

    private LinearLayout ll_home, ll_movies, ll_events, ll_more;

    private ImageView ic_home, ic_movies, ic_event, menu_icon;
    private TextView tv_hometittle, tv_moviewtittle, tv_eventtittle, tv_more;
    AlertDialog alertDialog = null;

    GoogleSignInClient mGoogleSignInClient;

    GoogleSignInOptions gso;
    TextView tv_versioncode;



    private String currentTab="morescreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);
        QTUtils.setStatusBarGradiant(this);
        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getParcelableArrayList("headerList") != null) {
                str = b.getParcelableArrayList("headerList");
            }
        }
        init();
        googleSignInOptions();
        setAllHomescreenicons();
        presenter = new NavigationPresenter();
        presenter.attachView(this);
        // this is the activity where we will list all the features which are listing in the navigation drawer,
        // we are not using default navigation drawer in the app, according to the designs we created this activity

    }


    private void setAllHomescreenicons() {
        ic_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        tv_hometittle.setTextColor(getResources().getColor(R.color.plane_white));

        ic_movies.setImageDrawable(getResources().getDrawable(R.drawable.ic_movies));
        tv_moviewtittle.setTextColor(getResources().getColor(R.color.plane_white));


        ic_event.setImageDrawable(getResources().getDrawable(R.drawable.ic_events));
        tv_eventtittle.setTextColor(getResources().getColor(R.color.plane_white));

        menu_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_moreyellow));
        tv_more.setTextColor(getResources().getColor(R.color.colorAccent));

    }

    public void init() {
        tv_versioncode = findViewById(R.id.tv_versioncode);
        txtSignin.setText(" " + getString(R.string.signup));
        sessionManager = new SessionManager(NavigationDrawerActivity.this);
        //we are checking the user id
        user_id = sessionManager.getUserId();
        if(str.size()>0){
            sessionManager.setCatList(str);
        }
        // if user id is availabe than we will list according to that
        btn_facebook = findViewById(R.id.btn_facebook);
        btn_youtube = findViewById(R.id.btn_youtube);
        txt_signout = findViewById(R.id.txt_signout);

        ll_home = findViewById(R.id.ll_home);
        ll_movies = findViewById(R.id.ll_movies);
        ll_events = findViewById(R.id.ll_events);
        ll_more = findViewById(R.id.ll_more);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            tv_versioncode.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationDrawerActivity.this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ll_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentssss = new Intent(NavigationDrawerActivity.this, MainActivity.class);
                intentssss.putExtra("categoryId", "15");
                if(str.size()>0){
                    intentssss.putParcelableArrayListExtra("headerList", str);
                }else {
                    str=sessionManager.getCatList();
                }
                intentssss.putExtra("position", 0);
                intentssss.putExtra("ImageType", "others");
                startActivity(intentssss);
                finish();
            }
        });
        ll_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iii = new Intent(NavigationDrawerActivity.this, EventHomeActivity.class);
                iii.putParcelableArrayListExtra("headerList", str);
                iii.putExtra("position", 1);
                iii.putExtra("ImageType", "others");
                startActivity(iii);
                finish();
            }
        });
        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTab.equals("morescreen")){
                    return;
                }
                i = new Intent(NavigationDrawerActivity.this, NavigationDrawerActivity.class);
                i.putParcelableArrayListExtra("headerList", str);
                startActivity(i);
            }
        });

        tv_hometittle = findViewById(R.id.tv_hometittle);
        tv_moviewtittle = findViewById(R.id.tv_moviewtittle);
        tv_eventtittle = findViewById(R.id.tv_eventtittle);
        tv_more = findViewById(R.id.tv_more);


        ic_home = findViewById(R.id.ic_home);
        menu_icon = findViewById(R.id.menu_icon);
        ic_movies = findViewById(R.id.ic_movies);
        ic_event = findViewById(R.id.ic_event);

        if (!TextUtils.isEmpty(user_id)) {
            txtLogin.setText(sessionManager.getName());
            txt_signout.setVisibility(View.VISIBLE);
            if (sessionManager.getGender().equalsIgnoreCase("Male")) {
                imgUser.setImageResource(R.mipmap.qticketsicon);
            } else {
                imgUser.setImageResource(R.mipmap.qticketsicon);
            }
            txtSignin.setVisibility(View.GONE);
            //    ll_deleteaccount.setVisibility(View.VISIBLE);
        } else {
            txtLogin.setVisibility(View.VISIBLE);
            txt_signout.setVisibility(View.GONE);
            txtSignin.setVisibility(View.VISIBLE);
            //  ll_deleteaccount.setVisibility(View.GONE);
        }
        try {
            PackageInfo pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        recyclerViewLayoutManager = new LinearLayoutManager(NavigationDrawerActivity.this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        // list of options in navigation drawer
        initItems();
        txt_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlerttoSignout("Are you sure you want to Sign out?");
            }
        });
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/QTickets")));
            }
        });
        btn_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     https://www.youtube.com/hashtag/qtickets%22
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/hashtag/qtickets")));
            }
        });
        //listeners for items
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        if (position == 0) {
                                if (!TextUtils.isEmpty(sessionManager.getUserId())) {
                                    i = new Intent(NavigationDrawerActivity.this, MyProfile.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                } else {
                                    QTUtils.LoginAlertDailog(NavigationDrawerActivity.this,
                                            getString(R.string.e_login_myprofile));
                                }



                        } else if (position == 1) {
                                if (!TextUtils.isEmpty(sessionManager.getUserId())) {
                                    i = new Intent(NavigationDrawerActivity.this, MyBookingsActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                } else {
                                    QTUtils.LoginAlertDailog(NavigationDrawerActivity.this,
                                            getString(R.string.e_login_booking));
                                }


                        } else if (position == 2) {
                                Bundle b = new Bundle();
                                b.putString("cmsType", "AboutUS");
                                i = new Intent(NavigationDrawerActivity.this, CMSPagesActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtras(b);
                                startActivity(i);



                        } else if (position == 3) {
                                i = new Intent(NavigationDrawerActivity.this, ContactUsActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                        } else if (position == 4) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mobile.android.qticketsapp")));
                        } else if (position == 5) {
                            shareAPP();
                        } else if (position == 6) {
                            //27sept....
                            Bundle b = new Bundle();
                            b.putString("cmsType", "TermsAndConditions");
                            i = new Intent(NavigationDrawerActivity.this, CMSPagesActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(b);
                            startActivity(i);
                        } else if (position == 7) {
                            //27sept....
                            Bundle b = new Bundle();
                            b.putString("cmsType", "FAQs");
                            i = new Intent(NavigationDrawerActivity.this, CMSPagesActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(b);
                            startActivity(i);
                        } else if (position == 8) {
                            //27sept....
                            Bundle b = new Bundle();
                            b.putString("cmsType", "PrivacyPolicy");
                            i = new Intent(NavigationDrawerActivity.this, CMSPagesActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtras(b);
                            startActivity(i);
                        } else if (position == 9) {
                            showPopupDeleteMessage();
                        }

//                        } else if (position == 2) {
//                            i = new Intent(NavigationDrawerActivity.this, ContactUsActivity.class);
//                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(i);
//                        } else if (position == 3) {
//                            i = new Intent(NavigationDrawerActivity.this, MyNotificationActivity.class);
//                            i.putStringArrayListExtra("headerList",str);
//                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(i);
//                        } else if (position == 4) {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mobile.android.qticketsapp")));
//                        } else if (position == 5) {
//                            shareAPP();
//
//                        } else if (position == 6) {
//                            Bundle b = new Bundle();
//                            b.putString("cmsType", "TermsAndConditions");
//                            i = new Intent(NavigationDrawerActivity.this, CMSPagesActivity.class);
//                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            i.putExtras(b);
//                            startActivity(i);
//
//                        } else if (position == 7) {
//                            Bundle b = new Bundle();
//                            b.putString("cmsType", "AboutUS");
//                            i = new Intent(NavigationDrawerActivity.this, CMSPagesActivity.class);
//                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            i.putExtras(b);
//                            startActivity(i);
//                        }
//
                        else if (position == 9) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(NavigationDrawerActivity.this);
                            builder1.setTitle("Log Out");
                            builder1.setMessage("Are you sure you want to Log Out?");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
//                                            recyclerView.findViewHolderForAdapterPosition(8).itemView.setVisibility(View.GONE);
//                                            recyclerView.findViewHolderForAdapterPosition(0).itemView.setVisibility(View.GONE);
//                                            recyclerView.findViewHolderForAdapterPosition(1).itemView.setVisibility(View.GONE);
                                            sessionManager.setUserId("");
                                            sessionManager.setName("");
                                            sessionManager.setEmail("");
                                            sessionManager.setNumber("");
                                            sessionManager.setPrefix("");
                                            sessionManager.setNatinality("");
                                            sessionManager.setFb_gplus_user("false");
                                            alert11.dismiss();
                                            if (fLogin) {
                                                LoginManager.getInstance().logOut();
                                                fLogin = false;
                                            }
                                            i = new Intent(NavigationDrawerActivity.this, DashBoardActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(i);
                                            Toast.makeText(NavigationDrawerActivity.this, "Logout Successfully!", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                            alert11 = builder1.create();
                            alert11.show();
                            Button nbutton = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
                            nbutton.setTextColor(Color.GRAY);
                            alert11.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.GRAY);
                        }
                    }
                }));

    }


    private void googleSignInOptions() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    private void showAlerttoSignout(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawerActivity.this);
        builder.setMessage(message);
        builder.setTitle(R.string.alerttosingout);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
            sessionManager.clearAllSession();
            txt_signout.setVisibility(View.GONE);
            txtLogin.setVisibility(View.VISIBLE);
            txtLogin.setText(getResources().getString(R.string.nav_login));
            txtSignin.setVisibility(View.VISIBLE);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    user_id = "";
                    LoginManager.getInstance().logOut();
                    signOut();
                }
            });


        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();


//
//
//
//
//
//
//
//       // QTUtils.showProgressDialog(this,true);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        final AlertDialog show = builder.show();
//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which){
//                    case DialogInterface.BUTTON_POSITIVE:
//                        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//                        lp.alpha=0.5f;
//                        dialog.getWindow().setAttributes(lp);
//
////                        try {
////                            dialog.dismiss();
////                            sessionManager.clearAllSession();
////                            txt_signout.setVisibility(View.GONE);
////                            txtLogin.setVisibility(View.VISIBLE);
////                            txtLogin.setText(getResources().getString(R.string.nav_login));
////                            txtSignin.setVisibility(View.VISIBLE);
////                           // recreate();
////                            AsyncTask.execute(new Runnable() {
////                                @Override
////                                public void run() {
////                                    user_id="";
////                                     LoginManager.getInstance().logOut();
////                                     signOut();
////                                }
////                            });
////                           // recreate();
////                           // QTUtils.showProgressDialog(NavigationDrawerActivity.this,false);
////                        }catch (Exception e){
////                            Toast.makeText(NavigationDrawerActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
////                        }
//
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        show.dismiss();
//                        break;
//                }
//            }
//        };
//        builder.setTitle(R.string.alerttosingout);
//        builder.setMessage(message).setPositiveButton("Ok", dialogClickListener)
//                .setNegativeButton("Dismiss", dialogClickListener).show();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        sessionManager.clearAllSession();
                        initItems();
                    }
                });
    }

    @Override
    public void dismissPb() {

    }

    @Override
    public void showPb() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {

    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {

    }

    // items list
    private void initItems() {
        navigationList.clear();
        navigationList.add(new NavigationModel(getResources().getString(R.string.nav_myaccount),
                String.valueOf(R.drawable.account)));
        navigationList.add(new NavigationModel(getResources().getString(R.string.nav_mybook), String.valueOf(R.drawable.bookings)));
        navigationList.add(new NavigationModel(getResources().getString(R.string.about), String.valueOf(R.drawable.aboutus)));

        navigationList.add(new NavigationModel(getResources().getString(R.string.nav_contactus), String.valueOf(R.drawable.contact)));
        navigationList.add(new NavigationModel(getResources().getString(R.string.nav_rateus), String.valueOf(R.drawable.rate)));
        navigationList.add(new NavigationModel(getResources().getString(R.string.share), String.valueOf(R.drawable.share)));
        navigationList.add(new NavigationModel(getResources().getString(R.string.nav_tc), String.valueOf(R.drawable.terms)));
        navigationList.add(new NavigationModel(getResources().getString(R.string.faqs), String.valueOf(R.drawable.faq)));
        navigationList.add(new NavigationModel(getResources().getString(R.string.privacyandpolicy), String.valueOf(R.drawable.privacypolicy)));

        if (!TextUtils.isEmpty(user_id)) {
            navigationList.add(new NavigationModel(getResources().getString(R.string.deleteaccount), String.valueOf(R.drawable.del)));
        }
        navigationItemsAdapter = new NavigationAdapter(NavigationDrawerActivity.this, navigationList);
        recyclerView.setAdapter(navigationItemsAdapter);
    }

    // adding click listeners for login, signup and close the navigation drawer
    @OnClick({R.id.txt_login, R.id.txt_signin, R.id.close})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {

//            case R.id.ll_deleteaccount:
//                showPopupDeleteMessage();
//                break;


            case R.id.txt_login:
              /*  if (TextUtils.isEmpty(sessionManager.getName())) {

                }*/
                i = new Intent(this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(i, AppConstants.LOGIN);
                break;
            case R.id.txt_signin:
                i = new Intent(this, SignUpActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(i, AppConstants.LOGIN);
                break;
            case R.id.close:
                finish();
                break;

        }
    }

    private void showPopupDeleteMessage() {
        showDeleteMessageToAccount(getResources().getString(R.string.messagefordeleteaccount), "Delete Account Confirmation");
    }

    public void showDeleteMessageToAccount(String message, String tittle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog show = builder.show();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        presenter.deleteAccount(sessionManager.getUserId());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //show.dismiss();
                        break;
                }
            }
        };
        builder.setTitle(R.string.deleteaccountconfirmation);
        builder.setMessage(message).setPositiveButton("Delete", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    // share the app via the options available in the device
    private void shareAPP() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "QTicket");
            String sAux = "\n" + getResources().getString(R.string.share_title) +
                    "\n\nhttps://play.google.com/store/apps/details?id=com.mobile.android.qticketsapp\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share Via"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    // listener for the user to get the gender of the user
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.LOGIN) {
            user_id = sessionManager.getUserId();
            if (!TextUtils.isEmpty(user_id)) {
                txtLogin.setText(sessionManager.getName());
                if (sessionManager.getGender().equalsIgnoreCase("Male")) {
                    imgUser.setImageResource(R.mipmap.qticketsicon);
                } else {
                    imgUser.setImageResource(R.mipmap.qticketsicon);
                }
                txtSignin.setVisibility(View.GONE);
            } else {
                txtLogin.setVisibility(View.VISIBLE);
                txtSignin.setVisibility(View.VISIBLE);
            }
            navigationList.clear();
            initItems();

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        navigationList.clear();
        //listing the navigation items based on the user status, whether the user is loggedin or not
        initItems();
        //here we updating the user details whether the user is logged in or not.
        user_id = sessionManager.getUserId();
        if (!TextUtils.isEmpty(user_id)) {
            txtLogin.setText(sessionManager.getName());
            if (sessionManager.getGender().equalsIgnoreCase("Male")) {
                imgUser.setImageResource(R.mipmap.qticketsicon);
            } else {
                imgUser.setImageResource(R.mipmap.qticketsicon);
            }
            txt_signout.setVisibility(View.VISIBLE);
            navigationItemsAdapter.notifyDataSetChanged();
            //     ll_deleteaccount.setVisibility(View.VISIBLE);
            txtSignin.setVisibility(View.GONE);
        } else {
            txtLogin.setVisibility(View.VISIBLE);
            txtSignin.setVisibility(View.VISIBLE);
            //     ll_deleteaccount.setVisibility(View.GONE);
            txt_signout.setVisibility(View.GONE);
            navigationItemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setDeleteAccount(DeleteAccount response) {
        if (response.status.equals("OK")) {
            sessionManager.clearAllSession();
            showDialogbox(this, getResources().getString(R.string.deletesucessfully));
        } else {
            QTUtils.showDialogbox(this, response.message);
        }
    }

    public void showDialogbox(Context context, String message) {

        if (!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            recreate();
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

}
