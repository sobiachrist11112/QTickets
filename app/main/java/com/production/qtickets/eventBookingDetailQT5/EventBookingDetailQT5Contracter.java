package com.production.qtickets.eventBookingDetailQT5;
import android.widget.LinearLayout;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.CustomFieldQT5Model;
import com.production.qtickets.model.EventBookingQT5Model;
import com.production.qtickets.model.EventCustomFieldQT5Model;
import com.production.qtickets.model.EventTicketQT5Model;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.model.EventTimeQT5Model;
import com.production.qtickets.model.GetSublistview;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class EventBookingDetailQT5Contracter {

    public interface View extends BaseView{
        void  setEventBookingDetail(final EventBookingQT5Model eventBookingQT5Model);
        void  setEventShowTimeByEventDate(final EventTimeQT5Model eventTimeQT5Model);
        void  setEventTicketsByDate(final EventTicketQT5Model eventTicketQT5Model);
        void  CheckCustomField(final CustomFieldQT5Model customFieldQT5Model);
        void setUploadDocumentFile(String imagename);
        void setCustomchildcount(List<GetSublistview.CustomCHild> childlist, LinearLayout contanier,Boolean isForRemove,int postion);
        void  setEventCustomFieldsList(final EventCustomFieldQT5Model eventCustomFieldQT5Model, int ticketcount, int postion, boolean isFirstTime, EventTicketQT5Type type,boolean isANychild);
    }

    interface Presenter extends BasePresenter<View>{
        void uploadAttachment(@Part MultipartBody.Part part);
        void  getEventBookingDetailsById(int ID);
        void  getEventShowTimeByEventDate(int ID, String date);
        void  getEventTicketsByDate(int ID, String date, String sTime, String eTime);
        void  CheckCustomField(int eventID,int ticketID);
        void GetCustomFieldSubOptionsList(int fieldID,int optionID,LinearLayout container,Boolean isForRemove);
        void  GetEventCustomFieldsList(int eventID,int ticket_ID,int ticket_count,int postion,boolean isFirstTime, EventTicketQT5Type type,boolean isANychild);
    }
}
