package booking.online.bus.Controller;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import booking.online.bus.Models.BusInfor;
import booking.online.bus.Models.PassengerModel;
import booking.online.bus.R;
import booking.online.bus.Utilities.BaseService;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.Utilites;
import booking.online.bus.database.LocalVideoDataSource;
import booking.online.bus.database.LocalVideoDatabase;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by DatNT on 6/28/2016.
 */
public class PassengerAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {

    private ArrayList<PassengerModel> passengers;
    private Context mContext;
    private onClickListener onClick;
    private ProgressDialog dialog;
    private LocalVideoDataSource database;
    private ArrayList<String> mSectionLetters;
    private ArrayList<Integer> mSectionIndices;
    private LayoutInflater mInflater;
    public PassengerAdapter(Context mContext, ArrayList<PassengerModel> passengers,LocalVideoDataSource database){
        this.passengers = passengers;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.database = database;
        mSectionIndices = getSelectionIndices();
        mSectionLetters = getSelectionLetter();
    }

    private ArrayList<String> getSelectionLetter() {
        ArrayList<String> mLetter = new ArrayList<>();
        mLetter.add(passengers.get(0).getUiid().substring(0,8));
        String lastFirtId = passengers.get(0).getUiid().substring(0,8);
        for (int j = 0 ; j < passengers.size() ; j++)
            if (!passengers.get(j).getUiid().substring(0,8).equals(lastFirtId)){
                mLetter.add(passengers.get(j).getUiid().substring(0,8));
                lastFirtId = passengers.get(j).getUiid().substring(0,8);
            }
        return mLetter;
    }

    private ArrayList<Integer> getSelectionIndices() {
        ArrayList<Integer> mSelection = new ArrayList<>();
        mSelection.add(0);

        String lastFirtId = passengers.get(0).getUiid().substring(0,8);
        for (int j = 0 ; j < passengers.size() ; j++)
            if (!passengers.get(j).getUiid().substring(0,8).equals(lastFirtId)){
                mSelection.add(j);
                lastFirtId = passengers.get(j).getUiid().substring(0,8);
            }
        return mSelection;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String date = passengers.get(position).getUiid().substring(0,8);
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        CharSequence headerChar = day +" - "+month+" - "+year;
        holder.text.setText(headerChar);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        String date = passengers.get(position).getUiid().substring(0,8);
        return Integer.valueOf(date);
    }

    @Override
    public int getCount() {
        return passengers.size();
    }

    @Override
    public Object getItem(int position) {
        return passengers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PassengerViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.passenger_detail, parent, false);
            holder = new PassengerViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PassengerViewHolder) convertView.getTag();
        }
        holder.txtname.setText(passengers.get(position).getName());
        holder.txtPhone.setText(passengers.get(position).getPhone());
        holder.txtNote.setText(passengers.get(position).getNote());
        if(passengers.get(position).isConfirm()) {
            holder.toolbarCard.getMenu().clear();
            holder.toolbarCard.inflateMenu(R.menu.deny_menu);
            holder.layoutConfirm.setVisibility(View.INVISIBLE);
        }
        else {
            holder.toolbarCard.getMenu().clear();
            holder.toolbarCard.inflateMenu(R.menu.confirm_menu);
            holder.layoutConfirm.setVisibility(View.VISIBLE);
        }
        holder.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + passengers.get(position).getPhone()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mContext.startActivity(intent);
            }
        });
        //holder.toolbarCard.inflateMenu(R.menu.confirm_menu);
        holder.toolbarCard.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_accept:
                        accept(passengers.get(position));
                        break;
                    case R.id.action_deny:
                        deny(passengers.get(position));
                        break;
                    case R.id.deny:
                        deny(passengers.get(position));
                        break;
                }
                return true;
            }
        });
        return convertView;
    }

    @Override
    public Object[] getSections() {
        String[] sSectionLetters = new String[mSectionLetters.size()];
        for (int i = 0 ; i <mSectionLetters.size();i++)
            sSectionLetters[i] = mSectionLetters.get(i);
        return sSectionLetters;
    }

    @Override
    public int getPositionForSection(int section) {
        if (mSectionIndices.size() == 0) {
            return 0;
        }

        if (section >= mSectionIndices.size()) {
            section = mSectionIndices.size() - 1;
        } else if (section < 0) {
            section = 0;
        }
        return mSectionIndices.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < mSectionIndices.size(); i++) {
            if (position < mSectionIndices.get(i)) {
                return i - 1;
            }
        }
        return mSectionIndices.size() - 1;
    }
    public class HeaderViewHolder {
        TextView text;
    }
    public static class PassengerViewHolder {
        CardView cardview;
        TextView txtname;
        TextView txtPhone;
        TextView txtNote;
        Toolbar toolbarCard;
        LinearLayout layoutConfirm;
        ImageView imgCall;
        PassengerViewHolder(View itemView) {
            cardview            = (CardView)        itemView.findViewById(R.id.card_view);
            txtname             = (TextView)        itemView.findViewById(R.id.txt_name);
            txtPhone            = (TextView)        itemView.findViewById(R.id.txt_phone);
            txtNote             = (TextView)        itemView.findViewById(R.id.txt_note);
            toolbarCard         = (Toolbar)         itemView.findViewById(R.id.toolbarCard);
            layoutConfirm       = (LinearLayout)    itemView.findViewById(R.id.layout_confirm);
            imgCall             = (ImageView)       itemView.findViewById(R.id.img_call);
        }

    }
    public interface onClickListener
    {
        public void onItemClick(int position);

    }
    private void deny(final PassengerModel passenger) {
        RequestParams params;
        params = new RequestParams();
        params.put("uiid", passenger.getUiid());
        params.put("id", passenger.getDriverId());
        params.put("type", 0);
        Log.i("params deleteDelivery", params.toString());
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Đang xử lý");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        BaseService.getHttpClient().post(Defines.URL_ACCEPT, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"
                Log.i("JSON", new String(responseBody));
                try {
                    JSONObject content = new JSONObject(new String(responseBody));
                    int result = content.getInt("success");
                    if (result > 0)
                    {
                        new AlertDialog.Builder(mContext)
                                .setTitle("Thông báo")
                                .setMessage("Đã từ chối thành công")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        database.deletePassenger(passenger.getUiid());
                                        passengers = database.getAllPassenger();
                                        notifyDataSetChanged();
                                        int notificationId = Integer.valueOf(passenger.getUiid().substring(7,passenger.getUiid().length()));
                                        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                                        manager.cancel(notificationId);
                                    }
                                })

                                .show();
                    }else{
                        Utilites.showDialog(mContext,"Thông báo","Chưa thể từ chối");
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("JSON", new String(responseBody));
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }

    private void accept(final PassengerModel passenger) {
        RequestParams params;
        params = new RequestParams();
        params.put("uiid", passenger.getUiid());
        params.put("id", passenger.getDriverId());
        params.put("type", 1);
        Log.i("params deleteDelivery", params.toString());
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Đang xử lý");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        BaseService.getHttpClient().post(Defines.URL_ACCEPT, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"
                Log.i("JSON", new String(responseBody));
                try {
                    JSONObject content = new JSONObject(new String(responseBody));
                    int result = content.getInt("success");
                    if (result > 0)
                    {
                        new AlertDialog.Builder(mContext)
                                .setTitle("Thông báo")
                                .setMessage("Chấp nhận thành công")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        database.updatePassenger(passenger.getUiid());
                                        passengers = database.getAllPassenger();
                                        notifyDataSetChanged();
                                        int notificationId = Integer.valueOf(passenger.getUiid().substring(7,passenger.getUiid().length()));
                                        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                                        manager.cancel(notificationId);
                                    }
                                })

                                .show();
                    }else{
                        Utilites.showDialog(mContext,"Thông báo","Chấp nhận thất bại");
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("JSON", new String(responseBody));
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }
    public void setOnItemClickListener(final onClickListener onClick)
    {
        this.onClick = onClick;
    }
}
