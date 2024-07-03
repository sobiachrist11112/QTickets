package com.production.qtickets.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.production.qtickets.model.AllCategoryQT5Model;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.model.BannerModel;
import com.production.qtickets.model.BookingQT5Model;
import com.production.qtickets.model.CarouselBannerModel;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.ChangePwdModel;
import com.production.qtickets.model.ContactResponse;
import com.production.qtickets.model.CouponQT5Model;
import com.production.qtickets.model.CreateDTCMUserModel;
import com.production.qtickets.model.CuponCodeModel;
import com.production.qtickets.model.CustomFieldQT5Model;
import com.production.qtickets.model.DeleteAccount;
import com.production.qtickets.model.EventBookingQT5Model;
import com.production.qtickets.model.EventCustomFieldQT5Model;
import com.production.qtickets.model.EventDetailQT5Model;
import com.production.qtickets.model.EventTicketQT5Model;
import com.production.qtickets.model.EventTimeQT5Model;
import com.production.qtickets.model.GetHistoryData;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.GetNewBannerEvents;
import com.production.qtickets.model.GetSimilarEventData;
import com.production.qtickets.model.GetSublistview;
import com.production.qtickets.model.ImageUploadResponse;
import com.production.qtickets.model.Liesure;
import com.production.qtickets.model.LiesureData;
import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.MovieAndEventSearchResult;
import com.production.qtickets.model.PromoCode;
import com.production.qtickets.model.RegisterRequest;
import com.production.qtickets.model.ResetPasswordSucessFully;
import com.production.qtickets.model.StaticLinksDataModel;
import com.production.qtickets.model.StaticPageLinksModel;
import com.production.qtickets.model.TicketsModel;
import com.production.qtickets.model.EventModel;
import com.production.qtickets.model.EventTicketPriceRangeModel;
import com.production.qtickets.model.OfferList;
import com.production.qtickets.model.MallBydateModel;
import com.production.qtickets.model.ShowDateModel;
import com.production.qtickets.model.TokenModel;
import com.production.qtickets.model.UserINfo;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.SeatModel;
import com.production.qtickets.model.MyProfileModel;
import com.production.qtickets.model.NewsFeedModel;
import com.production.qtickets.model.NovoTicketSeletionModel;
import com.production.qtickets.model.VerifyBookingDetail;
import com.production.qtickets.model.WhatsAppChargeResponse;
import com.production.qtickets.novocinema.NovoTicketPostBody;
import com.production.qtickets.novocinema.seatinglayout.NovoBlockBody;
import com.production.qtickets.model.NovoBlockSeatsModel;
import com.production.qtickets.model.NovoSeatingModel;
import com.production.qtickets.model.NovoTicketConfirmationModel;
import com.production.qtickets.model.NovoTicketDetailesModel;
import com.production.qtickets.model.NovoTicketPaymentModel;
import com.production.qtickets.model.NovoTicketPaymentStausModel;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.model.SearchListItem;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.model.BookingDetaileModel;
import com.production.qtickets.model.StatusModel;
import com.production.qtickets.model.CategoryModel;
import com.production.qtickets.model.DashboardModel;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.NotificationModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import com.production.qtickets.ticketbookingdetaile.NovoBookingConfirmationModel;
import com.production.qtickets.model.TicketConfirmItem;
import com.production.qtickets.model.TicketConfirmationEventModel;
import com.production.qtickets.model.VerifyMobileModel;
import com.production.qtickets.ticketbookingdetaile.NovoBookingConfirmationModel1;

public interface APIInterface {
    //banners
    @GET("V5.0/getbannereventmovies_banner")
    Call<BannerModel> getBanners(@Query("country") String Country);

    //banners
    @GET("api/Event/GetCarouselBanners")
    Call<GetNewBannerEvents> getNewEventsBanners(
            @Header("ApiKey") String Authorization,
            @Query("CountryId") String Country);

    //CMS-Pages
    @GET("V2.0/getcmsjson")
    Call<StatusModel> getCMSPages();

    //DashboardModel
    @GET("V5.0/apphomepageapi")
    Call<DashboardModel> getDashboard(@Query("country") String Country);

    //location
    @GET("V3.0/getqticketscountries")
    Call<List<CategoryModel>> getLocation();

    //staticlinks
    @GET("api/Master/GetStaticPageLinks")
    Call<StaticLinksDataModel> getStaticLinks(
            @Header("ApiKey") String Authorization
    );

    //Events
    @GET("V5.0/getalleventsdetailsbycountryjson")
    Call<EventModel> getEvents(@Query("Country") String Country);

    //Movies
    @GET("V5.0/getmoviesjson")
    Call<List<MovieModel>> getMovies();

    //Movies
    @GET("V5.0/getmoviesjson_cnt")
    Call<List<MovieModel>> getMoviesCnt(@Query("country") String Country);

    //MyBookingsActivity
    @GET("V5.0/userbookingsjson")
    Call<Items> getMyBookings(@Query("userid") String userid);

    //Top Movies
    @GET("V5.0/topmovies_cnt")
    Call<Items> getTopMovies(@Query("country") String Country);

    @GET("api/Event/SearchMovieEventAsync")
    Call<MovieAndEventSearchResult> getMoviesandEvents(
            @Header("ApiKey") String Authorization,
            @Query("countryId") Integer countryId,
            @Query("searchText") String searchText
    );

    //Comming soon
    @GET("V5.0/getupcomingmoviesjson_cnt")
    Call<List<MovieModel>> getCommingSoon(@Query("country") String Country);

    /*//Notifications
     @GET("V5.0/getpushnotification")
     Call<List<NotificationModel>> getNotifications();*/
    //Notifications
    @GET("V5.0/getpushnotification")
    Call<List<NotificationModel>> getNotifications(@Query("country") String Country);

    //NewsFeeds
    @GET("V5.0/getnewsfeed")
    Call<List<NewsFeedModel>> getNewsFeeds();


    //filter movie by laungauges
    @GET("V5.0/getmoviesbylanguagejson")
    Call<List<MovieModel>> getMovieListBYLaung(@Query("language") String laungauge);


    //filter movie by cinema
    @GET("V5.0/getmoviesjson_cnt")
    Call<List<MovieModel>> getMovieListBYCinema(@Query("theaterID") String theaterID, @Query("country") String Country);


    //mall list
    @GET("V5.0/getcinemas_cnt")
    Call<Items> getCinemas(@Query("country") String Country);


//    //Login
//    @GET("V5.0/loginmobile")
//    Call<LoginResponse> getLogin(@Query("username") String username,
//                                 @Query("password") String password,
//                                 @Query("source") String source,
//                                 @Query("token") String token);


    @POST("api/Account/Login")
    Call<LoginResponse> getLogin(
            @Header("ApiKey") String Authorization,
            @Header("Content-Type") String content,
            @Header("Accept") String accept,
            @Query("Email") String email,
            @Query("Password") String password
    );


    @POST("api/Account/FacebookLogin")
    Call<LoginResponse> getFacebookLogin(
            @Header("ApiKey") String Authorization,
            @Body RequestBody body
    );

    @POST("api/Account/GoogleLogin")
    Call<LoginResponse> getGoogleLogin(
            @Header("ApiKey") String Authorization,
            @Body RequestBody body
    );

    //Get User Details
    @GET("api/Account/GetUserDetailsById")
    Call<UserINfo> getUserDetails(
            @Header("ApiKey") String Authorization,
            @Query("userId") String userID);


    //deleteaccount
    @GET("api/Account/DeleteAccountByUserId")
    Call<DeleteAccount> deleteAccount(
            @Header("ApiKey") String Authorization,
            @Query("userId") String userID);


    //ForgotPassword
    @GET("api/Account/ForgetPassword")
    Call<LoginResponse> getForgotPassword(
            @Header("ApiKey") String Authorization,
            @Query("Email") String email_id);


    //Mall list according to dates
  /*  @GET("V5.0/get_movie_details_and_dates_withflik")
    Call<MallBydateModel> getshowbymoviedate(@Query("Country_id") String Country_id,
                                             @Query("City_Id") String City_Id,
                                             @Query("Cinema_Id") String Cinema_Id,
                                             @Query("Movie_Date") String Movie_Date,
                                             @Query("Movie_Id") String Movie_Id);*/

  /*  change before making live test api
    @GET("V5.0/get_movie_testapi")
    Call<MallBydateModel> getshowbymoviedate(@Query("Country_id") String Country_id,
                                             @Query("City_Id") String City_Id,
                                             @Query("Cinema_Id") String Cinema_Id,
                                             @Query("Movie_Date") String Movie_Date,
                                             @Query("Movie_Id") String Movie_Id);*/

  /*  @GET("V5.0/get_movie_allapi")
    Call<MallBydateModel> getshowbymoviedate(@Query("Country_id") String Country_id,
                                               @Query("City_Id") String City_Id,
                                               @Query("Cinema_Id") String Cinema_Id,
                                               @Query("Movie_Date") String Movie_Date,
                                               @Query("Movie_Id") String Movie_Id);*/

    /*qtickets*/
    @GET("V5.0/get_movies_qt")
    Call<MallBydateModel> getshowbymoviedateQt(@Query("Country_id") String Country_id,
                                               @Query("City_Id") String City_Id,
                                               @Query("Cinema_Id") String Cinema_Id,
                                               @Query("Movie_Date") String Movie_Date,
                                               @Query("Movie_Id") String Movie_Id);

    /*flick*/
    @GET("V5.0/get_movies_flik")
    Call<MallBydateModel> getshowbymoviedateFlick(@Query("Country_id") String Country_id,
                                                  @Query("City_Id") String City_Id,
                                                  @Query("Cinema_Id") String Cinema_Id,
                                                  @Query("Movie_Date") String Movie_Date,
                                                  @Query("Movie_Id") String Movie_Id);

    /*novo*/
    @GET("V5.0/get_movies_novo")
    Call<MallBydateModel> getshowbymoviedateNovo(@Query("Country_id") String Country_id,
                                                 @Query("City_Id") String City_Id,
                                                 @Query("Cinema_Id") String Cinema_Id,
                                                 @Query("Movie_Date") String Movie_Date,
                                                 @Query("Movie_Id") String Movie_Id);

    //showdates
    @GET("V5.0/get_shows_date_bymovieidcnt")
    Call<ShowDateModel> getshowDates(@Query("MovieId") String movieId, @Query("country") String countryName);


    //EventDetails
    @GET("/V5.0/getalleventsdetailsbyeventidjson")
    Call<TicketsModel> getTicketDetails(@Query("event_id") String event_id);

    //EventDetails
    @GET("/V5.0/getalleventsdetailsbyeventidjson")
    Call<OfferList> getTicketDetailss(@Query("event_id") String event_id);


    //EventTicketPriceRange
    @GET("V5.0/getminmaxpriceevent")
    Call<EventTicketPriceRangeModel> getPriceRange();


    //EventsFilter
    @GET("V5.0/app_events_details_bycountry_json_date")
    Call<EventModel> getEventsFilter(@Query("Country") String Country,
                                     @Query("range") String range);


    //EventDetailFilter
    @GET("V5.0/geteventsbyfiltersjson")
    Call<EventModel> getEventDetailFilter(@Query("minPrice") String minPrice,
                                          @Query("maxPrice") String maxPrice,
                                          @Query("startDate") String startDate,
                                          @Query("endDate") String endDate,
                                          @Query("country") String country);


    //get user reviews
    @GET("V5.0/getusermoviereviewbymovieid")
    Call<List<UserReviewModel>> getusermoviereviewbymovieid(@Query("movie_id") String laungauge);


    //add user reviews
    @GET("V5.0/addusermovieratings")
    Call<List<UserReviewModel>> adduserreviews(@Query("movie_id") String movie_id,
                                               @Query("user_id") String user_id,
                                               @Query("user_review") String user_review,
                                               @Query("User_Rating") String User_Rating,
                                               @Query("review_title") String review_title);

    //SeatLayout
    @GET("V5.0/GetSeatLayout_json")
    Call<SeatModel> getSeatLayout(@Query("showTimeId") String showTimeId);


    //blocking seat
    @GET("V5.0/block_seats_qtickets_json")
    Call<List<BookingDetaileModel>> doblockseat(@Query("showid") String showId,
                                                @Query("seats") String seats,
                                                @Query("AppSource") String AppSource);


    @GET("V10.0/getwhatsappchargejson")
    Call<List<WhatsAppChargeResponse>> getWhatsAppPrice(@Query("country") String country);


    //apply coupon code
    @GET("V5.0/checkcouponstatusjson")
    Call<List<BookingDetaileModel>> doapplycouponcode(@Query("email") String email,
                                                      @Query("coupon") String coupon
                                                      //  @Query("deviceId") String deviceId

    );


    //Seat lock
    @GET("V5.0/send_lock_request_json")
    Call<List<BookingDetaileModel>> doseatlock(@Query("Transaction_Id") String Transaction_Id,
                                               @Query("name") String name,
                                               @Query("email") String email,
                                               @Query("mobile") String mobile,
                                               @Query("prefix") String prefix,
                                               @Query("VoucherCodes") String VoucherCodes);


    //Seat Lock confirmation
//    @GET("V5.0/lock_confirm_request_json")
//    Call<List<BookingDetaileModel>> doSeatLock(@Query("Transaction_Id") String Transaction_Id,
//                                               @Query("AppSource") String AppSource,
//                                               @Query("AppVersion") String AppVersion);


    @GET("V5.0/lock_confirm_request_json_whatsapp")
    Call<List<BookingDetaileModel>> doSeatLock(@Query("Transaction_Id") String Transaction_Id,
                                               @Query("AppSource") String AppSource,
                                               @Query("wacharge") int wacharge,
                                               @Query("AppVersion") String AppVersion);


    //  static let UserRegistrationAPI = "/Account/UserRegistration"


    //Reggister
    @POST("api/Account/UserRegistration")
    Call<RegisterModel> callRegister(
            @Header("ApiKey") String Authorization,
            @Header("Content-Type") String contrent,
            @Body RegisterRequest body
    );


    @POST("api/Account/UserRegistration")
    Call<RegisterModel> updateproFile(
            @Header("ApiKey") String Authorization,
            @Header("Content-Type") String contrent,
            @Body RegisterRequest body
    );


//                                           @Query("fid") String fid,
//
//
//
//                                           @Query("source") String source,
//                                           @Query("token") String token,
//                                           @Query("deviceId") String androidDeviceId);


//    //Reggister
//    @GET("V5.0/registration_json")
//    Call<List<RegisterModel>> callRegister(@Query("firstname") String firstname,
//                                           @Query("lastname") String lastname,
//                                           @Query("prefix") String prefix,
//                                           @Query("phone") String phone,
//                                           @Query("mailid") String mailid,
//                                           @Query("pwd") String pwd,
//                                           @Query("confirmpwd") String confirmpwd,
//                                           @Query("fid") String fid,
//                                           @Query("nationality") String nationality,
//                                           @Query("dob") String dob,
//                                           @Query("gender") String gender,
//                                           @Query("source") String source,
//                                           @Query("token") String token,
//                                           @Query("deviceId") String androidDeviceId);


    //Change Password
    @POST("api/Account/ChangePassword")
    Call<ChangePasswordData> getChangePwd(@Header("ApiKey") String Authorization,
                                          @Body RequestBody body);

    @POST("/api/Master/ContactUs")
    Call<ContactResponse> sendContactUsDetails(
            @Header("ApiKey") String Authorization,
            @Body RequestBody body
    );


    @POST("api/Account/ChangeForgotPassword")
    Call<ResetPasswordSucessFully> resetPassword(
            @Header("ApiKey") String Authorization,
            @Body RequestBody body);


    //Search result
    //@GET("V5.0/getsearchresult_new")
    @GET("V5.0//getsearchresult?")
    Call<SearchListItem> callSearchList(@Query("Country") String Country, @Query("search") String search);


    //OTP Genarate
    Call<List<VerifyMobileModel>> callOtp(@Query("userid") String userid,
                                          @Query("mobile") String mobile,
                                          @Query("prefix") String prefix);


    //OTP verifivation
    @GET("V5.0/PhoneVerificationjson")
    Call<List<VerifyMobileModel>> getVerified(@Query("prefix") String prefix,
                                              @Query("phone") String phone,
                                              @Query("code") String code);


    //movie detaile
    @GET("V5.0/getmoviesbymovieidjson")
    Call<List<MovieModel>> getMoviesDetailes(@Query("Movie_Id") String Movie_Id);


    //MyEventsBookings
    @GET("api/Event/GetUserEventBookingHistory")
    Call<ArrayList<GetHistoryData>> getMyEventsBookings(
            @Header("ApiKey") String apiKey,
            @Query("userId") String userId);

//    @GET("V5.0/usereventbookingsjson")
//    Call<Items> getMyEventsBookings(@Query("userid") String userId);
//
//


    /*start from here*/
    //EventTicketBooking
    @GET("V5.0/eventbookings")
    Call<List<BookingDetaileModel>> getEventTicketBooking(@Query("eventid") String eventid,
                                                          @Query("ticket_id") String ticket_id,
                                                          @Query("amount") String amount,
                                                          @Query("tkt_count") String tkt_count,
                                                          @Query("NoOftktPerid") String NoOftktPerid,
                                                          @Query("camount") String camount,
                                                          @Query("email") String email,
                                                          @Query("name") String name,
                                                          @Query("phone") String phone,
                                                          @Query("prefix") String prefix,
                                                          @Query("bdate") String bdate,
                                                          @Query("btime") String btime,
                                                          @Query("balamount") String balamount,
                                                          @Query("couponcodes") String couponcodes,
                                                          @Query("AppSource") String AppSource,
                                                          @Query("AppVersion") String AppVersion);


    //MyProfile
    @GET("V5.0/profileupdationjson")
    Call<List<MyProfileModel>> getProfileDetails(@Query("uid") String uid,
                                                 @Query("name") String name,
                                                 @Query("email") String email,
                                                 @Query("mno") String mno,
                                                 @Query("prefix") String prefix,
                                                 @Query("nationality") String nationality);


    //sports list
    @GET("V5.0/getalleventsdetailsbycountrybycategoryjson")
    Call<DataModel> getSportsList(@Query("Country") String Country,
                                  @Query("categoryid") String categoryid);


    //eventticketConfirmation
    @GET("V5.0/bookingconfirmaioneventsjson")
    Call<List<TicketConfirmationEventModel>> getEventTicketConfirmation(@Query("booking_id") String booking_id);


    //movieticketconfirmation
    @GET("V5.0/bookingconfirmaionmoviejson")
    Call<TicketConfirmItem> getMovieTicketConfirmation(@Query("booking_id") String booking_id);


    //novocinematicketselection
    @POST("TicketType/GetTicketTypeDetails")
    Call<NovoTicketSeletionModel> getTicketDetailes(@Body NovoTicketPostBody task);


    //novoseatinglayout
    @POST("Seat/GetSeatLayout")
    Call<NovoSeatingModel> getSeating(@Body NovoTicketPostBody task);


    //novoblockseats
    @POST("Seat/BlockSeat")
    Call<NovoBlockSeatsModel> getblockSeats(@Body NovoBlockBody task);


    //novobookingblock
    @FormUrlEncoded
    @POST("novo_qticketsapi.aspx")
    Call<NovoBookingConfirmationModel> getNovoConfirmation(@Field("type") String type,
                                                           @Field("UserId") String user_id,
                                                           @Field("CinemId") String CinemId,
                                                           @Field("CinemaName") String CinemaName,
                                                           @Field("MovieId") String MovieId,
                                                           @Field("MovieName") String MovieName,
                                                           @Field("MovieUrl") String MovieUrl,
                                                           @Field("SessionId") String SessionId,
                                                           @Field("bookingInfoId") String bookingInfoId,
                                                           @Field("userSessionID") String userSessionID,
                                                           @Field("Show_date") String Show_date,
                                                           @Field("Show_time") String Show_time,
                                                           @Field("No_of_tickets") String No_of_tickets,
                                                           @Field("service_fees") String service_fees,
                                                           @Field("ticket_price") String ticket_price,
                                                           @Field("total_paid") String total_paid,
                                                           @Field("seats_info") String seats_info,
                                                           @Field("seats_id") String seats_id,
                                                           @Field("status") String status,
                                                           @Field("countryCode") String countryCode);


    //novocouponcode
    @FormUrlEncoded
    @POST("coupon_ajax.aspx")
    Call<List<BookingDetaileModel>> doNovoapplycouponcode(@Field("type") String type,
                                                          @Field("coupon") String coupon,
                                                          @Field("email") String email);


    //novopayconfirmation //NovoBookingConfirmationModel1
    @FormUrlEncoded
    @POST("novo_qticketsapi.aspx")
    Call<NovoBookingConfirmationModel1> getNovoproceddtopay(@Field("type") String type,
                                                            @Field("UserId") String user_id,
                                                            @Field("CinemId") String CinemId,
                                                            @Field("CinemaName") String CinemaName,
                                                            @Field("MovieId") String MovieId,
                                                            @Field("MovieName") String MovieName,
                                                            @Field("MovieUrl") String MovieUrl,
                                                            @Field("SessionId") String SessionId,
                                                            @Field("bookingInfoId") String bookingInfoId,
                                                            @Field("userSessionID") String userSessionID,
                                                            @Field("Show_date") String Show_date,
                                                            @Field("Show_time") String Show_time,
                                                            @Field("No_of_tickets") String No_of_tickets,
                                                            @Field("service_fees") String service_fees,
                                                            @Field("ticket_price") String ticket_price,
                                                            @Field("total_paid") String total_paid,
                                                            @Field("seats_info") String seats_info,
                                                            @Field("seats_id") String seats_id,
                                                            @Field("status") String status,
                                                            @Field("countryCode") String countryCode,
                                                            @Field("BookingID") String BookingID,
                                                            @Field("Customer_name") String Customer_name,
                                                            @Field("Customer_phone") String Customer_phone,
                                                            @Field("Customer_email") String Customer_email,
                                                            @Field("Customer_prefix") String Customer_prefix,
                                                            @Field("wacharge") int wacharge);


    //novoupdatecouponcode
    @FormUrlEncoded
    @POST("coupon_ajax.aspx")
    Call<BookingDetaileModel> doNovoupdateouponcode(@Field("type") String type,
                                                    @Field("coupon") String coupon,
                                                    @Field("email") String email,
                                                    @Field("bookID") String bookingid);


    //novoConfiramationstatusa
    @FormUrlEncoded
    @POST("novo_qticketsapi.aspx")
    Call<NovoTicketConfirmationModel> doNovoconfirmationStatus(@Field("type") String type,
                                                               @Field("bookID") String bookID);


    //novoPaymentresponse
    @POST("Payment/PaymentResponse")
    Call<List<NovoTicketPaymentModel>> doNovoPaymentResponse(@Body NovoTicketPostBody task);


    //confirmNovoBooking
    @FormUrlEncoded
    @POST("novo_qticketsapi.aspx")
    Call<NovoTicketPaymentStausModel> doNovo_confirm_booking(@Field("type") String type,
                                                             @Field("novo_id") String novo_id,
                                                             @Field("Screen_name") String Screen_name,
                                                             @Field("Language") String Language,
                                                             @Field("Rating") String Rating,
                                                             @Field("bookID") String bookID,
                                                             @Field("Cinema_Class") String Cinema_Class,
                                                             @Field("barcodeImgUrl") String barcodeImgUrl);


    //novoqticketsTicketdetailes
    @FormUrlEncoded
    @POST("novo_qticketsapi.aspx")
    Call<NovoTicketDetailesModel> do_novo_ticket_detailes(@Field("type") String type,
                                                          @Field("booking_id") String booking_id);



    /*          REEL CINEMA            */

    //ReelCinemaMovieList
    @GET("reelcinemaapi.aspx?")
    Call<ReelCinemaMovieListModel> getReelCinemaList(@Query("type") String type);


    //ReelCinemaMovieList
    @GET("reelcinemaapi.aspx?")
    Call<ReelCinemaMovieListModel> getReelCinemaMovieDetails(@Query("type") String type,
                                                             @Query("cinemaid") String cinemaid);

    //Notification token
    @GET("V5.0/addmobiletoken")
    Call<TokenModel> getToken(@Query("androidToken") String token,
                              @Query("Nationality") String nationality);


    //Country list
    @GET("api/Master/GetNationalityList")
    Call<GetNationalityData> getCountryDropdown(@Header("ApiKey") String apiKey,
                                                @Header("Accept") String accept);


    @GET("rayanaapi/raynaevent.aspx")
    Call<Liesure> getLiesure(@Query("type") int type,
                             @Query("pageNumber") int pagenumber);

    //DashboardModel
    @GET("V5.0/apphomepagemoviesevents_cnt")
    Call<DashboardModel> getNewDashboard(@Query("country") String Country);

    //Events
    @GET("V5.0/getalleventsdetailsbycountryjson")
    Call<EventModel> getFreeToGo(@Query("Country") String Country);


//    QT5 16-032022

    //Carousel Banners
    @GET("api/Event/GetCarouselBanners")
    Call<CarouselBannerModel> getCarouselBanners(
            @Header("ApiKey") String Authorization,
            @Query("CountryId") int CountryId,
            @Query("categoryId") String categoryID
    );

    //GetAllCountries
    @GET("api/Master/GetAllCountries")
    Call<AllCountryQT5Model> GetAllCountries(
            @Header("ApiKey") String Authorization,
            @Query("Id") int Id
    );

    //GetAllEvents
    @GET("api/Event/GetAllEvents")
    Call<AllEventQT5Model> getAllEvents(
            @Header("ApiKey") String Authorization,
            @Query("CountryId") int CountryId,
            @Query("CategoryId") int CategoryId,
            @Query("IsActive") int IsActive,
            @Query("IsFreeEvent") String IsFreeEvent,
            @Query("IsOfflineEvent") int IsOfflineEvent,
            @Query("StartDate") String StartDate,
            @Query("EndDate") String EndDate,
            @Query("EventVenue") String EventVenue,
            @Query("MinPrice") int min_price,
            @Query("MaxPrice") int max_price
    );

    //GetSimilarevents
    @GET("api/Event/GetSimilarEvents")
    Call<GetSimilarEventData> getSimilarEvent(
            @Header("ApiKey") String Authorization,
            @Query("eventId") int eventID
    );

    //GetAllCategories
    @GET("api/Master/GetAllCategories")
    Call<AllCategoryQT5Model> getAllCategories(
            @Header("ApiKey") String Authorization,
            @Query("Id") int Id,
            @Query("Name") String Name
    );

    //GetAllCategories
    @GET("api/Event/New_GetEventDetailsById")
    Call<EventDetailQT5Model> getEventDetailsById(
            @Header("ApiKey") String Authorization,
            @Query("EventId") int EventId
    );

    //GetEventBookingDetailsById
    @GET("api/Event/GetEventBookingDetailsById")
    Call<EventBookingQT5Model> getEventBookingDetailsById(
            @Header("ApiKey") String Authorization,
            @Query("EventId") int EventId
    );

    @Multipart
    @POST("api/Event/SaveCustomFormAttachment")
    Call<ImageUploadResponse> sendAttachmentToserver(
            @Header("ApiKey") String Authorization,
            @Part MultipartBody.Part image);


    //GetEventBookingDetailsById
    @GET("api/Event/CheckCustomField")
    Call<CustomFieldQT5Model> CheckCustomField(
            @Header("ApiKey") String Authorization,
            @Query("EventId") int EventId,
            @Query("TicketId") int TicketId
    );


    @GET("api/Event/GetCustomFieldSubOptionsList")
    Call<GetSublistview> GetcustomFieldschild(
            @Header("ApiKey") String Authorization,
            @Query("fieldId") int fieldId,
            @Query("optionId") int optionId
    );


    //GetEventCustomFieldsList
    @GET("api/Event/GetEventCustomFieldsList")
    Call<EventCustomFieldQT5Model> GetEventCustomFieldsList(
            @Header("ApiKey") String Authorization,
            @Query("EventId") int EventId,
            @Query("TicketId") int ticketID
    );

    //GetEventShowTimeByEventDate
    @GET("api/Event/GetEventShowTimeByEventDate")
    Call<EventTimeQT5Model> getEventShowTimeByEventDate(
            @Header("ApiKey") String Authorization,
            @Query("EventId") int EventId,
            @Query("Date") String EventDate
    );

    //GetEventTicketsByDate
    @GET("api/Event/GetEventTicketsByDate")
    Call<EventTicketQT5Model> getEventTicketsByDate(
            @Header("ApiKey") String Authorization,
            @Query("EventId") int EventId,
            @Query("Date") String Date,
            @Query("startTime") String startTime,
            @Query("endTime") String endTime
    );

    //GetEventCouponDetailsForApp
//    @POST("api/Event/GetEventCouponDetailsForApp")
//    Call<PromoCode> getEventCouponDetails(
//            @Header("ApiKey") String Authorization,
//            @Header("Content-Type") String ContentType,
//            @Body RequestBody body
//    );


//    /api/Event/ApplyVoucher
//    @GET("api/Event/GetVoucher")
//    Call<CuponCodeModel> getEventCouponDetails(
//            @Header("ApiKey") String Authorization,
//            @Query("VoucherCode") String vouchercode,
//            @Query("UserId") String UserId,
//            @Query("EventId") String EventId
//    );


    @POST("api/Event/ApplyVoucher")
    Call<CuponCodeModel> getEventCouponDetails(
            @Header("ApiKey") String Authorization,
            @Body RequestBody body);

//            @Query("VoucherCode") String vouchercode,
//            @Query("UserId") String UserId,
//            @Query("EventId") String EventId


    //GetEventPromocodeDetailsForApp
    @POST("api/Event/ApplyPromoCode")
    Call<PromoCode> getApplyPromocode(
            @Header("ApiKey") String Authorization,
            @Header("Content-Type") String ContentType,
            @Body RequestBody body
    );


    //GetEventTicketsByDate
    @GET("api/Event/GetVoucher?")
    Call<JsonObject> getCouponCode(
            @Header("ApiKey") String Authorization,
            @Query("VoucherCode") String vouchercode,
            @Query("UserId") String userID,
            @Query("EventId") int eventID
    );

    //BookTicketsForApp
    @POST("api/Event/BookTicketsForApp")
    Call<BookingQT5Model> BookTicketsForApp(
            @Header("ApiKey") String Authorization,
            @Header("Content-Type") String ContentType,
            @Body RequestBody body
    );

    //BookTicketsForApp
    @POST("api/Event/VarifyBookingDetails")
    Call<VerifyBookingDetail> verifyBookingDetails(
            @Header("ApiKey") String Authorization,
            @Body RequestBody body
    );


    @POST("api/DTCM/CreateCustomer")
    Call<CreateDTCMUserModel> createDTCMEvent(
            @Header("ApiKey") String Authorization,
            @Header("Content-Type") String content_type,
            @Body RequestBody body
    );


    //GetEventCouponDetailsForApp
    @POST("api/Event/BookTickets")
    Call<CouponQT5Model> BookTickets(
            @Header("ApiKey") String Authorization,
            @Header("Content-Type") String ContentType,
            @Body RequestBody body
    );

}
