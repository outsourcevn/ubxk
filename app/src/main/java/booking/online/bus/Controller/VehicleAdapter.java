package booking.online.bus.Controller;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import booking.online.bus.Models.BusInfor;
import booking.online.bus.R;
import booking.online.bus.Utilities.Utilites;

import java.util.ArrayList;

/**
 * Created by DatNT on 6/28/2016.
 */
public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private ArrayList<BusInfor> vehicles;
    private Context mContext;
    private onClickListener onClick;
    public VehicleAdapter(Context mContext, ArrayList<BusInfor> vehicles){
        this.vehicles = vehicles;
        this.mContext = mContext;
    }
    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_detail, parent, false);
        VehicleViewHolder pvh = new VehicleViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, final int position) {
        holder.vehicleName.setText(vehicles.get(position).getCarOwner());
        holder.vehiclePromote.setText(vehicles.get(position).getCarPromote());
        holder.fromPlace.setText(vehicles.get(position).getFromPlace());
        holder.toPlace.setText(vehicles.get(position).getToPlace());
        holder.startTime.setText(vehicles.get(position).getStartTime());
        holder.startTimeofDay.setText(vehicles.get(position).getStartTimeofDay());
        holder.vehicleType.setText(vehicles.get(position).getVehicleType());
        holder.price.setText(Utilites.convertCurrency(vehicles.get(position).getPrice()));
        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null )
                    onClick.onItemClick(position);
            }
        });

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
        CardView cardview;
        TextView vehicleName;
        TextView vehiclePromote;
        TextView fromPlace;
        TextView toPlace;
        TextView startTime;
        TextView startTimeofDay;
        TextView vehicleType;
        TextView price;
        RelativeLayout layoutContent;
        VehicleViewHolder(View itemView) {
            super(itemView);
            layoutContent       = (RelativeLayout)  itemView.findViewById(R.id.layout_content);
            cardview            = (CardView)        itemView.findViewById(R.id.card_view);
            vehicleName         = (TextView)        itemView.findViewById(R.id.txt_vehicle);
            vehiclePromote      = (TextView)        itemView.findViewById(R.id.txt_vehicle_promote);
            fromPlace           = (TextView)        itemView.findViewById(R.id.txt_start_place);
            toPlace             = (TextView)        itemView.findViewById(R.id.txt_end_place);
            startTime           = (TextView)        itemView.findViewById(R.id.txt_start_time);
            startTimeofDay      = (TextView)        itemView.findViewById(R.id.txt_start_time_of_day);
            vehicleType         = (TextView)        itemView.findViewById(R.id.txt_vehicle_type);
            price               = (TextView)        itemView.findViewById(R.id.txt_price);
        }

    }
    public interface onClickListener
    {
        public void onItemClick(int position);

    }
}
