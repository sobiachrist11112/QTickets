package com.production.qtickets.novocinema;

/**
 * Created by Harsh on 7/12/2018.
 */
public class NovoTicketPostBody {
    private String countryid;
    private String cinemaid;
    private String sessionid;
    private String movierating;
    private String selectedtickettypes;
    private String userSessionId;
    private String response;
    private String sendEmail;
    private String sendPhone;
    private String CountryCode;

    public NovoTicketPostBody(String userSessionId, String response, String sendEmail, String sendPhone, String customerName, String customerEmail, String customerPhoneNo,String CountryCode) {
        this.userSessionId = userSessionId;
        this.response = response;
        this.sendEmail = sendEmail;
        this.sendPhone = sendPhone;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhoneNo = customerPhoneNo;
        this.CountryCode = CountryCode;
    }

    private String customerName;
    private String customerEmail;
    private String customerPhoneNo;

    public NovoTicketPostBody(String countryid, String cinemaid, String sessionid) {
        this.countryid = countryid;
        this.cinemaid = cinemaid;
        this.sessionid = sessionid;
    }

    public NovoTicketPostBody(String countryid, String cinemaid, String sessionid, String movierating, String selectedtickettypes) {
        this.countryid = countryid;
        this.cinemaid = cinemaid;
        this.sessionid = sessionid;
        this.movierating = movierating;
        this.selectedtickettypes = selectedtickettypes;
    }

}
