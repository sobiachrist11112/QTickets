package com.production.qtickets.eventBookingDetailQT5;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.CustomFieldQT5Model;
import com.production.qtickets.model.EventBookingQT5Model;
import com.production.qtickets.model.EventCustomFieldQT5Model;
import com.production.qtickets.model.EventTicketQT5Model;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.model.EventTimeQT5Model;
import com.production.qtickets.model.GetSublistview;
import com.production.qtickets.model.ImageUploadResponse;
import com.production.qtickets.utils.AppConstants;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class EventBookingDetailQT5Presenter implements EventBookingDetailQT5Contracter.Presenter {

    public EventBookingDetailQT5Contracter.View mView;


    @Override
    public void attachView(EventBookingDetailQT5Contracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    @Override
    public void uploadAttachment(MultipartBody.Part part) {
        try {
            if (mView != null) mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).sendAttachmentToserver(AppConstants.Authorization_QT5, part).enqueue(new Callback<ImageUploadResponse>() {
                @Override
                public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                    if (mView != null) {
                        mView.dismissPb();
                        if (response.isSuccessful() && response.body() != null) {
                            mView.setUploadDocumentFile(response.body().data.toString());
                        } else {
                            mView.onFailure(call, null, this, null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ImageUploadResponse> call, Throwable throwable) {
                    if (mView != null) {
                        mView.dismissPb();
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void getEventBookingDetailsById(int ID) {
        try {
            if (mView != null) mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).getEventBookingDetailsById(AppConstants.Authorization_QT5, ID)
                    .enqueue(new Callback<EventBookingQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventBookingQT5Model> call, @NonNull retrofit2.Response<EventBookingQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventBookingDetail(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventBookingQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void CheckCustomField(int eventID, int ticketId) {
        try {
            if (mView != null) mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).CheckCustomField(AppConstants.Authorization_QT5, eventID, ticketId)
                    .enqueue(new Callback<CustomFieldQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<CustomFieldQT5Model> call, @NonNull retrofit2.Response<CustomFieldQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.CheckCustomField(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<CustomFieldQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void GetCustomFieldSubOptionsList(int fieldID, int optionID, LinearLayout container, Boolean isForRemove) {
        try {
            if (mView != null) mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).GetcustomFieldschild(AppConstants.Authorization_QT5, fieldID, optionID)
                    .enqueue(new Callback<GetSublistview>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<GetSublistview> call, @NonNull retrofit2.Response<GetSublistview> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCustomchildcount(response.body().data, container, isForRemove, optionID - 1);
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GetSublistview> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void GetEventCustomFieldsList(int eventID, int ticketID, int ticketcount, int postion, boolean isFirstTime, EventTicketQT5Type type, boolean isAnyChild) {
        try {
            if (mView != null) {
                mView.dismissPb();
                mView.showPb();
            }
            APIClient.getClientQT5().create(APIInterface.class).GetEventCustomFieldsList(AppConstants.Authorization_QT5, eventID, ticketID)
                    .enqueue(new Callback<EventCustomFieldQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventCustomFieldQT5Model> call, @NonNull retrofit2.Response<EventCustomFieldQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventCustomFieldsList(response.body(), ticketcount, postion, isFirstTime, type, isAnyChild);
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                                Log.d("jsonResponse: ", response.body().message);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventCustomFieldQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getEventShowTimeByEventDate(int ID, String date) {
        try {
            if (mView != null) mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).getEventShowTimeByEventDate(AppConstants.Authorization_QT5, ID, date)
                    .enqueue(new Callback<EventTimeQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventTimeQT5Model> call, @NonNull retrofit2.Response<EventTimeQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventShowTimeByEventDate(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventTimeQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getEventTicketsByDate(int ID, String date, String sTime, String eTime) {
        try {
            if (mView != null) mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).getEventTicketsByDate(AppConstants.Authorization_QT5, ID, date, sTime, eTime)
                    .enqueue(new Callback<EventTicketQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventTicketQT5Model> call, @NonNull retrofit2.Response<EventTicketQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventTicketsByDate(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventTicketQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
