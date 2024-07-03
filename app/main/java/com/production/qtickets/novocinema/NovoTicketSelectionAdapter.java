package com.production.qtickets.novocinema;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.production.qtickets.R;
import com.production.qtickets.model.TicketlistModel;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.utils.TextviewRegular;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 7/12/2018.
 */
public class NovoTicketSelectionAdapter extends RecyclerView.Adapter<NovoTicketSelectionAdapter.MyViewHolder> {


    private List<TicketlistModel> nList;
    private Context context;
    private String str_max_tickets;
    private int sum_of_tickets = 0, sum_of_admits = 0;
    private double sum_of_amount = 0.0;
    private TextView txtNoOfTickets, txtTotalAmount;
    private onTicketsChangedListener mListener;
    com.production.qtickets.utils.SessionManager sessionManager;
    String currencyCode = "";
    String tickettypecode;
    boolean iscliked = false;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_ticket_type)
        TextviewRegular txtTicketType;
        @BindView(R.id.txt_cost)
        TextviewBold txtCost;
        @BindView(R.id.iv_decrease_count)
        ImageView ivDecreaseCount;
        @BindView(R.id.tv_ticket_count)
        TextView tvTicketCount;
        @BindView(R.id.iv_increase_count)
        ImageView ivIncreaseCount;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }


    public NovoTicketSelectionAdapter(Context context, List<TicketlistModel> nList, String str_max_tickets, TextView txtNoOfTickets, TextView txtTotalAmount,onTicketsChangedListener mListener) {
        this.nList = nList;
        this.context = context;
        this.str_max_tickets = str_max_tickets;
        this.txtNoOfTickets = txtNoOfTickets;
        this.txtTotalAmount = txtTotalAmount;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_novo_ticket_selection_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TicketlistModel m_list = nList.get(position);
        holder.txtTicketType.setText(m_list.description);
        holder.txtCost.setText(m_list.currency + " " + m_list.ticketPrice);
        m_list.ticketcount = 0;
        m_list.totalamount = 0.0;

        sessionManager = new com.production.qtickets.utils.SessionManager(context);
        currencyCode = sessionManager.getCountryCurrency();

        holder.ivDecreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sum_of_tickets = 0;
                Double amount = 0.0;
                if (m_list.ticketcount > 0) {
                    m_list.ticketcount--;
                    holder.tvTicketCount.setText(String.valueOf(m_list.ticketcount));
                    for (int i = 0; i < nList.size(); i++) {
                        sum_of_tickets = sum_of_tickets + nList.get(i).ticketcount;
                    }
                    sum_of_amount = Double.valueOf(nList.get(position).ticketcount) * Double.valueOf(nList.get(position).ticketPrice);
                    m_list.totalamount = sum_of_amount;
                    for (int j = 0; j < nList.size(); j++) {
                        if (nList.get(j).totalamount != 0.0) {
                            amount = amount + nList.get(j).totalamount;
                        }
                    }
                    if (amount != 0.0) {
                        txtTotalAmount.setText(context.getString(R.string.totalAmount) + "\n" + amount + " " + currencyCode);
                    } else {
                        txtTotalAmount.setText(context.getString(R.string.totalAmount));
                    }
                    if (sum_of_tickets > 1) {
                        if (m_list.description.contains("2 ADMITS") || m_list.description.contains("2TICKETS")) {// 2tickets added by sudhir
                            sum_of_admits = sum_of_admits - 2;
                            nList.get(position).noOfSeats=nList.get(position).noOfSeats-1;
                        } else {
                            sum_of_admits = sum_of_admits - 1;
                            nList.get(position).noOfSeats=nList.get(position).noOfSeats-1;
                        }
                        txtNoOfTickets.setText(context.getString(R.string.novonooftickets) + "\n" + sum_of_tickets + " " +
                                "( " + sum_of_admits + " " + context.getString(R.string.persons) + " )");
//                        nList.get(position).noOfSeats=sum_of_admits;
                        mListener.ticketsCount(sum_of_tickets,sum_of_admits);
                        Log.e(" sum_of_admits========",""+nList.get(position).noOfSeats);
                    } else if (sum_of_tickets == 1) {
                        if (m_list.description.contains("2 ADMITS") || m_list.description.contains("2TICKETS")) {// 2tickets added by sudhir
                            sum_of_admits = sum_of_admits - 2;
                            nList.get(position).noOfSeats=nList.get(position).noOfSeats-2;
                        } else {
                            sum_of_admits = sum_of_admits - 1;
                            nList.get(position).noOfSeats=nList.get(position).noOfSeats-1;
                        }
                        txtNoOfTickets.setText(context.getString(R.string.novonoofticket) + "\n" + sum_of_tickets + " " +
                                "( " + sum_of_admits + " " + context.getString(R.string.person) + " )");

                        mListener.ticketsCount(sum_of_tickets,sum_of_admits);
                        Log.e(" sum_of_admits========",""+nList.get(position).noOfSeats);

                    } else {
                        txtNoOfTickets.setText(context.getString(R.string.nooftickets));
                    }
                    if (m_list.ticketcount == 0) {
                        iscliked = false;
                    }
                }
            }
        });
        holder.ivIncreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*when user selects maljis/7star allow them to select single ticket type*/
//                if (!iscliked) {
//                    tickettypecode = nList.get(position).ticketTypeCode;
//                    iscliked = true;
//                }
//                if (tickettypecode.equals("0358")) {
//                    if (tickettypecode.equals(nList.get(position).ticketTypeCode)) {
//                        increaseCount(m_list, holder, position);
//                    } else {
//                        alertbox();
//                    }
//                } else {
//                    if (!nList.get(position).ticketTypeCode.equals("0358")) {
//                        increaseCount(m_list, holder, position);
//                    } else {
//                        alertbox();
//                    }
//                }
                increaseCount(m_list, holder, position);


            }
        });
    }

    private void increaseCount(TicketlistModel m_list, MyViewHolder holder, int position) {
        sum_of_tickets = 0;
        Double amount = 0.0;
        m_list.ticketcount++;
        for (int i = 0; i < nList.size(); i++) {
            sum_of_tickets = sum_of_tickets + nList.get(i).ticketcount;
        }
        if (sum_of_tickets - 1 < Integer.parseInt(str_max_tickets)) {
            //   if (m_list.ticketcount < 10) {
            holder.tvTicketCount.setText(String.valueOf(m_list.ticketcount));
            sum_of_amount = Double.valueOf(nList.get(position).ticketcount) * Double.valueOf(nList.get(position).ticketPrice);
            m_list.totalamount = sum_of_amount;
            for (int j = 0; j < nList.size(); j++) {
                if (nList.get(j).totalamount != 0.0) {
                    amount = amount + nList.get(j).totalamount;
                }
            }
            if (amount != 0.0) {
                txtTotalAmount.setText(context.getString(R.string.totalAmount) + "\n" + amount + " " + currencyCode);
            } else {
                txtTotalAmount.setText(context.getString(R.string.totalAmount));
            }
            if (sum_of_tickets > 1) {
                if (m_list.description.contains("2 ADMITS") || m_list.description.contains("2TICKETS")) {// 2tickets added by sudhir
                    sum_of_admits = sum_of_admits + 2;
                    nList.get(position).noOfSeats=nList.get(position).noOfSeats+2;
                } else {
                    sum_of_admits = sum_of_admits + 1;
                    nList.get(position).noOfSeats=nList.get(position).noOfSeats+1;
                }
                txtNoOfTickets.setText(context.getString(R.string.novonooftickets) + "\n" + sum_of_tickets + " " +
                        "( " + sum_of_admits + " " + context.getString(R.string.persons) + " )");
//                nList.get(position).noOfSeats=sum_of_admits;
                mListener.ticketsCount(sum_of_tickets,sum_of_admits);
                Log.e(" sum_of_admits========",""+nList.get(position).noOfSeats);
            } else if (sum_of_tickets == 1) {
                if (m_list.description.contains("2 ADMITS") || m_list.description.contains("2TICKETS")) {// 2tickets added by sudhir
                    sum_of_admits = 2;
                    nList.get(position).noOfSeats=nList.get(position).noOfSeats+2;
                } else {
                    sum_of_admits = 1;
                    nList.get(position).noOfSeats=nList.get(position).noOfSeats+1;
                }
                txtNoOfTickets.setText(context.getString(R.string.novonoofticket) + "\n" + sum_of_tickets + " " +
                        "( " + sum_of_admits + " " + context.getString(R.string.person) + " )");
//                nList.get(position).noOfSeats=sum_of_admits;
                Log.e(" sum_of_admits========",""+nList.get(position).noOfSeats);
                mListener.ticketsCount(sum_of_tickets,sum_of_admits);
            } else {
                txtNoOfTickets.setText(context.getString(R.string.nooftickets));
            }
        } else {
            m_list.ticketcount--;
            Toast.makeText(context, context.getString(R.string.n_error_msg), Toast.LENGTH_SHORT).show();
        }
    }

    public interface onTicketsChangedListener {
        void ticketsCount(int noOfTickets, int noOfPerson);
    }

    @Override
    public int getItemCount() {
        return nList.size();
    }

    private void alertbox() {
        android.app.AlertDialog mid_dialog = new android.app.AlertDialog.Builder(context).setTitle("NOTE:").
                setMessage(context.getString(R.string.n_ticket_type_error))
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

        mid_dialog.show();
    }
}