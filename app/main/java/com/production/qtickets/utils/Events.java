package com.production.qtickets.utils;

import com.production.qtickets.model.DateModel;

import java.util.List;


/**
 * Created by Harsh on 4/6/2018.
 */

public class Events {
    // Event used to send message from fragment to activity.
    public static class FragmentActivityMessage {
        private String message;

        public FragmentActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to fragment.
    public static class ActivityFragmentMessage {
        private String message;
        private String id;

        public String getId() {
            return id;
        }

        public ActivityFragmentMessage(String message, String id) {
            this.message = message;
            this.id = id;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class AdapterFragmentMessage {
        private int ticketsCount;
        private int ticketPrice;
        private int totalCost;
        private double ticketServicePrice;
        private String countandTicketPriceID;
        private String ticketID;

        public String getTicketID() {
            return ticketID;
        }

        public void setTicketID(String ticketID) {
            this.ticketID = ticketID;
        }

        public int getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(int ticket_id) {
            this.ticket_id = ticket_id;
        }

        private int ticket_id;

        public int getTicketsCount() {
            return ticketsCount;
        }

        public int getTicketPrice() {
            return ticketPrice;
        }

        public int getTotalCost() {
            return totalCost;
        }

        public double getTicketServicePrice() {
            return ticketServicePrice;
        }

        public String getCountandTicketPriceID() {
            return countandTicketPriceID;
        }

        public void setCountandTicketPriceID(String countandTicketPriceID) {
            this.countandTicketPriceID = countandTicketPriceID;
        }

        public AdapterFragmentMessage(int ticketsCount, int ticketPrice, int totalCost, double ticketServicePrice, int ticket_id, String countandTicketPriceID,String ticketID) {
            this.ticketsCount = ticketsCount;
            this.ticketPrice = ticketPrice;
            this.totalCost = totalCost;
            this.ticketServicePrice = ticketServicePrice;
            this.ticket_id = ticket_id;
            this.countandTicketPriceID = countandTicketPriceID;
            this.ticketID = ticketID;
        }
    }


    public static class DiscountTotalMessage{
        private String discountPrice;


        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }


        public DiscountTotalMessage(String discountPrice){
            this.discountPrice = discountPrice;

        }
    }

    // Event used to send message from activity to adapter as a list.
    public static class ActivityAdapterMessageList {
        private String message;

        public ActivityAdapterMessageList(List<DateModel> list) {
            this.list = list;
        }

        public List<DateModel> getList() {
            return list;
        }

        private List<DateModel> list;
    }

}
