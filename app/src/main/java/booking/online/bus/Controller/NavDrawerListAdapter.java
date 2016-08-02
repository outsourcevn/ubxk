package booking.online.bus.Controller;

import android.app.Activity;
import android.content.Context;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import booking.online.bus.Models.FilterObject;
import booking.online.bus.R;
import booking.online.bus.Utilities.Defines;
import booking.online.bus.Utilities.Utilites;

import java.util.ArrayList;


public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<FilterObject> navDrawerItems;

	public NavDrawerListAdapter(Context context, ArrayList<FilterObject> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        final TextView txtKeyWord 	= (TextView) 				convertView.findViewById(R.id.txt_keyword);
		final Spinner spinner 			= (Spinner) 				convertView.findViewById(R.id.spinner);
		LinearLayout bottomLine 	= (LinearLayout)			convertView.findViewById(R.id.title_bottom_line);
		LinearLayout layoutSpinner	= (LinearLayout)			convertView.findViewById(R.id.layout_spinner);
		txtKeyWord.setText(navDrawerItems.get(position).getKeyWord());

		if (position == 0)
		{
			layoutSpinner.setVisibility(View.GONE);
			txtKeyWord.setTextSize(18);
			txtKeyWord.setTypeface(txtKeyWord.getTypeface(), Typeface.BOLD);
			LinearLayout item = (LinearLayout) convertView.findViewById(R.id.layout_item);
			bottomLine.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2);
			param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			bottomLine.setLayoutParams(param);
			bottomLine.setVisibility(View.VISIBLE);
			item.setBackgroundColor(Color.parseColor("#eeeeee"));

		}else
			if (position < navDrawerItems.size()-1) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.custom_spiner, navDrawerItems.get(position).getArrayCandidate());
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);
				spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
						switch (position){
							case 1:
								if (Defines.FilterInfor != null)
									Defines.FilterInfor.setCarOwner(navDrawerItems.get(position).getArrayCandidate().get(index));
								break;
							case 2:
								if (Defines.FilterInfor != null)
									Defines.FilterInfor.setFromPlace(navDrawerItems.get(position).getArrayCandidate().get(index));
								break;
							case 3:
								if (Defines.FilterInfor != null)
									Defines.FilterInfor.setToPlace(navDrawerItems.get(position).getArrayCandidate().get(index));
								break;
							case 4:
								if (Defines.FilterInfor != null)
									Defines.FilterInfor.setStartTimeofDay(navDrawerItems.get(position).getArrayCandidate().get(index));
								break;
							case 5:
								if (Defines.FilterInfor != null)
									Defines.FilterInfor.setRecepType(navDrawerItems.get(position).getArrayCandidate().get(index));
								break;
							case 6:
								if (Defines.FilterInfor != null)
									Defines.FilterInfor.setVehicleType(navDrawerItems.get(position).getArrayCandidate().get(index));
								break;
							case 7:
								if (Defines.FilterInfor != null)
									Defines.FilterInfor.setPrice(index);
								break;
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
				layoutSpinner.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						spinner.performClick();
					}
				});
			}else{
				layoutSpinner.setVisibility(View.GONE);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, (int) Utilites.convertDpToPixel(56,context));
				param.gravity = Gravity.CENTER;
				param.weight = 0.9f;
				txtKeyWord.setLayoutParams(param);
				txtKeyWord.setTypeface(txtKeyWord.getTypeface(), Typeface.BOLD);
				txtKeyWord.setTextSize(18);

				ImageView imgSearch = (ImageView) convertView.findViewById(R.id.img_search);
				imgSearch.setVisibility(View.VISIBLE);
				LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(0, (int) Utilites.convertDpToPixel(56,context));
				param1.weight = 0.1f;
				param1.rightMargin = (int) Utilites.convertDpToPixel(5,context);
				param1.gravity = Gravity.RIGHT;
				imgSearch.setLayoutParams(param1);



			}

		return convertView;
	}
}
