package booking.online.bus.Controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import booking.online.bus.Models.ActiveBusModel;
import booking.online.bus.R;

/**
 * Created by DatNT on 6/28/2016.
 */
public class ActiveVehicleAdapter extends RecyclerView.Adapter<ActiveVehicleAdapter.VehicleViewHolder> {

    private ArrayList<ActiveBusModel> vehicles;
    private Context mContext;
    private onClickListener onClick;
    public ActiveVehicleAdapter(Context mContext, ArrayList<ActiveBusModel> vehicles){
        this.vehicles = vehicles;
        this.mContext = mContext;
    }
    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_vehicle_detail, parent, false);
        VehicleViewHolder pvh = new VehicleViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, final int position) {
        holder.vehicleName.setText(vehicles.get(position).getName());
        DecimalFormat df = new DecimalFormat("#.#");
        if ((int)vehicles.get(position).getDistance() ==0)
            holder.distance.setText(df.format(vehicles.get(position).getDistance()*1000)+ " m");
        else
            holder.distance.setText(df.format(vehicles.get(position).getDistance())+ " km");
        holder.phone.setText(vehicles.get(position).getTelephone());
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + vehicles.get(position).getTelephone()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mContext.startActivity(intent);
            }
        });
        holder.txtStart.setText(vehicles.get(position).getStart());
        holder.txtEnd.setText(vehicles.get(position).getEnd());
    }
    public void setOnItemClickListener(final onClickListener onClick)
    {
        this.onClick = onClick;
    }
    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder {
        CardView  cardview;
        TextView  vehicleName;
        TextView  distance;
        TextView  phone;
        ImageView btnCall;
        TextView  txtStart;
        TextView  txtEnd;
        VehicleViewHolder(View itemView) {
            super(itemView);
            cardview            = (CardView)        itemView.findViewById(R.id.card_view);
            vehicleName         = (TextView)        itemView.findViewById(R.id.txt_vehicle);
            distance            = (TextView)        itemView.findViewById(R.id.txt_gap);
            phone               = (TextView)        itemView.findViewById(R.id.txt_phone);
            btnCall             = (ImageView)       itemView.findViewById(R.id.btn_call);
            txtStart            = (TextView)        itemView.findViewById(R.id.txt_start);
            txtEnd              = (TextView)        itemView.findViewById(R.id.txt_end);
        }

    }
    public interface onClickListener
    {
        public void onItemClick(int position);

    }
}
