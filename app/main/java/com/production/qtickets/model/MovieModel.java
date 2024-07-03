package com.production.qtickets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Harsh on 5/15/2018.
 */
public class MovieModel implements Serializable {

    //Mall listing for cinemas
    //com.production.qtickets.dashboard movielist
    @SerializedName("id")
    @Expose
    public String id;

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("clickURL")
    @Expose
    public String clickURL;

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("thumbURL")
    @Expose
    public String thumbURL;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("banner")
    @Expose
    public String banner;
    @SerializedName("ipadthumb")
    @Expose
    public String ipadthumb;
    @SerializedName("iphonethumb")
    @Expose
    public String iphonethumb;
    @SerializedName("Genre")
    @Expose
    public String genre;
    @SerializedName("Synopsis")
    @Expose
    public String synopsis;
    @SerializedName("IMDB_rating")
    @Expose
    public String iMDBRating;
    @SerializedName("Language")
    @Expose
    public String c_language;
    @SerializedName("shareURL")
    @Expose
    public String shareURL;
    @SerializedName("user_rating")
    @Expose
    public String user_rating;

    //movielist
    @SerializedName("genre")
    @Expose
    public String m_genre;
    @SerializedName("rdate")
    @Expose
    public String rdate;
    @SerializedName("Languageid")
    @Expose
    public String languageid;
    @SerializedName("Censor")
    @Expose
    public String censor;
    @SerializedName("Duration")
    @Expose
    public String duration;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("CastAndCrew")
    @Expose
    public String castAndCrew;
    @SerializedName("MovieType")
    @Expose
    public String movieType;
    @SerializedName("TrailerURL")
    @Expose
    public String trailerURL;
    @SerializedName("Movieurl")
    @Expose
    public String movieurl;
    @SerializedName("colorcode")
    @Expose
    public String colorcode;
    @SerializedName("bgcolorcode")
    @Expose
    public String bgcolorcode;
    @SerializedName("bgbordercolorcode")
    @Expose
    public String bgbordercolorcode;
    @SerializedName("btncolorcode")
    @Expose
    public String btncolorcode;
    @SerializedName("titlecolorcode")
    @Expose
    public String titlecolorcode;
    @SerializedName("AgeRestrictRating")
    @Expose
    public String ageRestrictRating;
    @SerializedName("Age_restrict_rating")
    @Expose
    public String Age_restrict_rating;
    @SerializedName("thumb")
    @Expose
    public String thumb;

    //booking
    @SerializedName("movie")
    @Expose
    public String movie;
    @SerializedName("theater")
    @Expose
    public String theater;
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("bookedtime")
    @Expose
    public String bookedtime;
    @SerializedName("showdate")
    @Expose
    public String showdate;
    @SerializedName("seats")
    @Expose
    public String seats;
    @SerializedName("seatsCount")
    @Expose
    public String seatsCount;
    @SerializedName("confirmationCode")
    @Expose
    public String confirmationCode;
    @SerializedName("barcodeURL")
    @Expose
    public String barcodeURL;
    @SerializedName("ticket_Cost")
    @Expose
    public String ticketCost;
    @SerializedName("total_Cost")
    @Expose
    public String totalCost;
    @SerializedName("bookingfees")
    @Expose
    public String bookingfees;
    @SerializedName("movieImageURL")
    @Expose
    public String movieImageURL;
    @SerializedName("moviebannerURL")
    @Expose
    public String moviebannerURL;
    @SerializedName("movieServerID")
    @Expose
    public String movieServerID;
    @SerializedName("checkcancelstatus")
    @Expose
    public String checkcancelstatus;

    //commingsoon
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("censor_rating")
    @Expose
    public String censorRating;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("willnotwatch")
    @Expose
    public String willnotwatch;
    @SerializedName("willwatch")
    @Expose
    public String willwatch;
    @SerializedName("display_order")
    @Expose
    public String displayOrder;

    //LoginData
    @SerializedName("status")
    @Expose
    public String status;
    /* @SerializedName("name")
     @Expose
     public String login_name;
     @SerializedName("id")
     @Expose
     public String login_id;*/
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("verify")
    @Expose
    public String verify;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("nationality")
    @Expose
    public String nationality;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("errorcode")
    @Expose
    public String errorcode;
    @SerializedName("errormsg")
    @Expose
    public String errormsg;


    //EventsBookings
    @SerializedName("Eventid")
    @Expose
    public String eventid;
    @SerializedName("BookedOn")
    @Expose
    public String bookedOn;

    @SerializedName("Event")
    @Expose
    public String event;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("eventDate")
    @Expose
    public String eventDate;
    @SerializedName("eventTime")
    @Expose
    public String eventTime;
    @SerializedName("eventImageURL")
    @Expose
    public String eventImageURL;


    //REEL_CINEMA_MOVIE_LIST
    @SerializedName("ID")
    @Expose
    public String iD;
    @SerializedName("ShortCode")
    @Expose
    public String shortCode;
    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("Rating")
    @Expose
    public String rating;
    @SerializedName("RatingDescription")
    @Expose
    public String ratingDescription;
    /*@SerializedName("Synopsis")
    @Expose
    public String synopsis;*/
    @SerializedName("ShortSynopsis")
    @Expose
    public String shortSynopsis;
    @SerializedName("HOFilmCode")
    @Expose
    public String hOFilmCode;
    @SerializedName("CorporateFilmId")
    @Expose
    public String corporateFilmId;
    @SerializedName("RunTime")
    @Expose
    public Integer runTime;
    @SerializedName("OpeningDate")
    @Expose
    public String openingDate;
    @SerializedName("TrailerUrl")
    @Expose
    public String trailerUrl;
    @SerializedName("IsComingSoon")
    @Expose
    public Boolean isComingSoon;
    @SerializedName("IsScheduledAtCinema")
    @Expose
    public Boolean isScheduledAtCinema;
    @SerializedName("TitleAlt")
    @Expose
    public String titleAlt;
    @SerializedName("RatingAlt")
    @Expose
    public String ratingAlt;
    @SerializedName("RatingDescriptionAlt")
    @Expose
    public String ratingDescriptionAlt;
    @SerializedName("SynopsisAlt")
    @Expose
    public String synopsisAlt;
    @SerializedName("ShortSynopsisAlt")
    @Expose
    public String shortSynopsisAlt;
    @SerializedName("WebsiteUrl")
    @Expose
    public String websiteUrl;
    @SerializedName("GenreId")
    @Expose
    public String genreId;
    @SerializedName("EDICode")
    @Expose
    public Object eDICode;
    @SerializedName("TwitterTag")
    @Expose
    public String twitterTag;
    @SerializedName("TitleTranslations")
    @Expose
    public List<Object> titleTranslations = null;
    @SerializedName("SynopsisTranslations")
    @Expose
    public List<Object> synopsisTranslations = null;
    @SerializedName("RatingDescriptionTranslations")
    @Expose
    public List<Object> ratingDescriptionTranslations = null;
    @SerializedName("CustomerRatingStatistics")
    @Expose
    public CustomerRatingStatistics customerRatingStatistics;
    @SerializedName("CustomerRatingTrailerStatistics")
    @Expose
    public CustomerRatingTrailerStatistics customerRatingTrailerStatistics;
    @SerializedName("FilmWebId")
    @Expose
    public String filmWebId;


    //REEL_CINEMA_MOVIE_DETAILS
    @SerializedName("CinemaId")
    @Expose
    public String cinemaId;
    @SerializedName("ScheduledFilmId")
    @Expose
    public String scheduledFilmId;
    @SerializedName("SessionId")
    @Expose
    public String sessionId;
    @SerializedName("AreaCategoryCodes")
    @Expose
    public List<Object> areaCategoryCodes = null;
    @SerializedName("Showtime")
    @Expose
    public String showtime;
    @SerializedName("IsAllocatedSeating")
    @Expose
    public Boolean isAllocatedSeating;
    @SerializedName("AllowChildAdmits")
    @Expose
    public Boolean allowChildAdmits;
    @SerializedName("SeatsAvailable")
    @Expose
    public Integer seatsAvailable;
    @SerializedName("AllowComplimentaryTickets")
    @Expose
    public Boolean allowComplimentaryTickets;
    @SerializedName("EventId")
    @Expose
    public String eventId;
    @SerializedName("PriceGroupCode")
    @Expose
    public String priceGroupCode;
    @SerializedName("ScreenName")
    @Expose
    public String screenName;
    @SerializedName("ScreenNameAlt")
    @Expose
    public String screenNameAlt;
    @SerializedName("ScreenNumber")
    @Expose
    public Integer screenNumber;
    @SerializedName("CinemaOperatorCode")
    @Expose
    public String cinemaOperatorCode;
    @SerializedName("FormatCode")
    @Expose
    public String formatCode;
    @SerializedName("SalesChannels")
    @Expose
    public String salesChannels;
    @SerializedName("SessionAttributesNames")
    @Expose
    public List<String> sessionAttributesNames = null;
    @SerializedName("ConceptAttributesNames")
    @Expose
    public List<Object> conceptAttributesNames = null;
    @SerializedName("AllowTicketSales")
    @Expose
    public Boolean allowTicketSales;
    @SerializedName("HasDynamicallyPricedTicketsAvailable")
    @Expose
    public Boolean hasDynamicallyPricedTicketsAvailable;
    @SerializedName("PlayThroughId")
    @Expose
    public Object playThroughId;
    @SerializedName("SessionBusinessDate")
    @Expose
    public String sessionBusinessDate;
    @SerializedName("SessionDisplayPriority")
    @Expose
    public Integer sessionDisplayPriority;
    @SerializedName("GroupSessionsByAttribute")
    @Expose
    public Boolean groupSessionsByAttribute;

}
