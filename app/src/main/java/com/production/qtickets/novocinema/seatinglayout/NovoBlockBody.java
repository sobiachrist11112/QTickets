package com.production.qtickets.novocinema.seatinglayout;

/**
 * Created by Harsh on 7/17/2018.
 */
public class NovoBlockBody {
    public String countryId;
    public String cinemaid;
    public String usersessionId;
    public String sessionId;
    public String selectedTicketTypes;
    public String selectedSeats;
    public String ticketInfo;

    public NovoBlockBody(String countryId,String cinemaid, String usersessionId, String sessionId, String selectedTicketTypes, String selectedSeats, String ticketInfo) {
        this.countryId = countryId;
        this.cinemaid = cinemaid;
        this.usersessionId = usersessionId;
        this.sessionId = sessionId;
        this.selectedTicketTypes = selectedTicketTypes;
        this.selectedSeats = selectedSeats;
        this.ticketInfo = ticketInfo;
    }



}
