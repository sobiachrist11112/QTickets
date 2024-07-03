package com.production.qtickets.events.EventDetails;

import com.production.qtickets.model.TicketsData;

import java.util.List;

public class Tickets {
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

    // Event used to send message from activity to adapter as a list.
    public static class ActivityAdapterMessageList {
        private String message;
        public ActivityAdapterMessageList(List<TicketsData> list) {
            this.list = list;
        }
        public List<TicketsData> getList() {
            return list;
        }

        private List<TicketsData> list;
    }
}
