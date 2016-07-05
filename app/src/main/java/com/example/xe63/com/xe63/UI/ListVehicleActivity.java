package com.example.xe63.com.xe63.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xe63.com.xe63.Controller.FilterData;
import com.example.xe63.com.xe63.Controller.NavDrawerListAdapter;
import com.example.xe63.com.xe63.Controller.VehicleAdapter;
import com.example.xe63.com.xe63.Models.BusInfor;
import com.example.xe63.com.xe63.Models.FilterObject;
import com.example.xe63.com.xe63.R;
import com.example.xe63.com.xe63.Utilities.Defines;
import com.example.xe63.com.xe63.Utilities.Utilites;

import java.util.ArrayList;

public class ListVehicleActivity extends AppCompatActivity {
    private RecyclerView vehicleView;
    private ArrayList<BusInfor> vehicles;
    private ArrayList<BusInfor> vehiclesFilter = new ArrayList<>();
    private ArrayList<FilterObject> navDrawerItems;
    private NavDrawerListAdapter adapterDrawer;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Context mContext;
    private boolean isFilter = false;
    private String provinceFrom , provinceTo;
    private TextView txtProvinceFrom, txtProvinceTo, txtNoResult;
    private MenuItem itemFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vehicle);
        mContext = this;
        moveDrawerToTop();
        initComponents();

    }
    private void moveDrawerToTop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor, null); // "null" is important.

        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        LinearLayout container = (LinearLayout) drawer.findViewById(R.id.drawer_content); // This is the container we defined just now.
        container.addView(child, 0);
  //      drawer.findViewById(R.id.drawer).setPadding((int) Utilites.convertDpToPixel(10, this), 0, (int) Utilites.convertDpToPixel(10, this), Utilites.getStatusBarHeight(this));

        // Make the drawer replace the first child
        decor.addView(drawer);
    }
    private void prepareDataSliding() {
        prepareFilterData();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        adapterDrawer = new NavDrawerListAdapter(mContext, navDrawerItems);
        mDrawerList.setAdapter(adapterDrawer);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (!isFilter)
                    Defines.FilterInfor = new BusInfor();
                Defines.clickFilter = false;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (!Defines.clickFilter)
                {
                    itemFilter.setIcon(R.drawable.clear_filter);
                    adapterDrawer = new NavDrawerListAdapter(mContext, navDrawerItems);
                    mDrawerList.setAdapter(adapterDrawer);
                    if (isFilter) {
                        isFilter = false;
                        VehicleAdapter adapter = new VehicleAdapter(mContext, vehicles);
                        vehicleView.setAdapter(adapter);
                    }
                }else {
                    if (Utilites.checkFilterNull()) {
                        itemFilter.setIcon(R.drawable.clear_filter);
                        isFilter = false;
                    } else
                        itemFilter.setIcon(R.drawable.filter);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void prepareFilterData() {
        navDrawerItems = new ArrayList<>();

        FilterData data = new FilterData(vehicles);
        FilterObject filter = new FilterObject("Hãng xe",data.filterVehicleName());
        FilterObject filterFromPlace = new FilterObject("Nơi đi ",data.filterFromPlace());
        FilterObject filterToPlace = new FilterObject("Nơi đến ",data.filterToPlace());
        FilterObject filterStartTime = new FilterObject("Thời gian ",data.filterStartTime());
        FilterObject filterRecepType = new FilterObject("Đón trả ",data.filterRecepType());
        FilterObject filterVehicleType = new FilterObject("Loại xe ",data.filterVehicleType());
        FilterObject filterPrice = new FilterObject("Giá vé ",data.filterPrice());

        navDrawerItems.add(new FilterObject("Bộ lọc",null));
        navDrawerItems.add(filter);
        navDrawerItems.add(filterFromPlace);
        navDrawerItems.add(filterToPlace);
        navDrawerItems.add(filterStartTime);
        navDrawerItems.add(filterRecepType);
        navDrawerItems.add(filterVehicleType);
        navDrawerItems.add(filterPrice);
        navDrawerItems.add(new FilterObject("TÌM",null));
    }

    private void initComponents() {
        vehicleView                 =   (RecyclerView)      findViewById(R.id.vehicle_view);
        Toolbar toolbar             =   (Toolbar)           findViewById(R.id.toolbar);
        mDrawerLayout               =   (DrawerLayout)      findViewById(R.id.drawer_layout);
        mDrawerList                 =   (ListView)          findViewById(R.id.drawer);
        txtProvinceFrom             =   (TextView)          findViewById(R.id.txt_province_from);
        txtProvinceTo               =   (TextView)          findViewById(R.id.txt_province_to);
        txtNoResult                 =   (TextView)          findViewById(R.id.txt_no_result);

        // set cardview
        vehicleView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        vehicleView.setLayoutManager(llm);
        dummyData();
        VehicleAdapter adapter = new VehicleAdapter(mContext,vehicles);
        vehicleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new VehicleAdapter.onClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, BookTicketActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Defines.VEHICLE_PASS_ACTION, vehicles.get(position));
                intent.putExtra(Defines.PROVINCE_FROM_ACTION, provinceFrom);
                intent.putExtra(Defines.PROVINCE_TO_ACTION, provinceTo);
                intent.putExtras(mBundle);

                mContext.startActivity(intent);
            }
        });
        prepareDataSliding();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            provinceFrom    = extras.getString(Defines.PROVINCE_FROM_ACTION);
            provinceTo      = extras.getString(Defines.PROVINCE_TO_ACTION);
        }
        txtProvinceFrom.setText(provinceFrom);
        txtProvinceTo.setText(provinceTo);
        // set Actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xe hoạt động");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void dummyData() {
        vehicles = new ArrayList<>();
        BusInfor infor1 = new BusInfor("Mai Linh", "wifi, điều hòa, nước uống","Bến Xe Nước Ngầm","Bến Xe Miền Đông","sáng, chiều","11:00 17:00","Khách đến bến lên xe","Xe giường nằm 40 chỗ",800000);
        vehicles.add(infor1);
        BusInfor infor2 = new BusInfor("Hoàng Long", "wifi, điều hòa, nước uống","Bến Xe Lương Yên","Bến Xe Miền Đông","sáng, chiều, tối","Từ 05:00 đến 20:30, cách 30 phút có 1 chuyến","Khách đến bến lên xe","Xe giường nằm 40 chỗ",830000);
        vehicles.add(infor2);
        BusInfor infor3 = new BusInfor("Hiền Phước", "wifi, điều hòa, nước uống","Bến Xe Giáp Bát","Bến Xe Miền Đông","sáng, chiều","10:00 15:00 17:00","Khách đến bến lên xe","Xe giường nằm 40 chỗ",850000);
        vehicles.add(infor3);
        BusInfor infor4 = new BusInfor("Mai Linh", "wifi, điều hòa, nước uống, không bắt khách dọc đường","Bến Xe Nước Ngầm","Bến Xe Miền Đông","sáng"," 11:00 ","Khách đến bến lên xe","Xe giường nằm 40 chỗ",750000);
        vehicles.add(infor4);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        itemFilter = menu.findItem(R.id.action_filter);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            Defines.FilterInfor = new BusInfor();
            return true;
        }
        if (item.getItemId() == R.id.action_filter) {
            if (!isFilter) {
                isFilter = true;
                mDrawerLayout.openDrawer(Gravity.LEFT);
                item.setIcon(R.drawable.filter);
                Defines.FilterInfor = new BusInfor();
            }
            else {
                isFilter = false;
                item.setIcon(R.drawable.clear_filter);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                VehicleAdapter adapter = new VehicleAdapter(mContext,vehicles);
                vehicleView.setAdapter(adapter);
                adapter.setOnItemClickListener(new VehicleAdapter.onClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(mContext, BookTicketActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable(Defines.VEHICLE_PASS_ACTION, vehicles.get(position));
                        intent.putExtra(Defines.PROVINCE_FROM_ACTION, provinceFrom);
                        intent.putExtra(Defines.PROVINCE_TO_ACTION, provinceTo);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }
                });
                adapterDrawer = new NavDrawerListAdapter(mContext, navDrawerItems);
                mDrawerList.setAdapter(adapterDrawer);
                txtNoResult.setVisibility(View.GONE);
                Defines.FilterInfor = new BusInfor();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class  SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position >= navDrawerItems.size()-1){
                isFilter = !(Utilites.checkFilterNull());
                vehiclesFilter = Utilites.checkFilterBusNull(vehicles);

                VehicleAdapter adapter = new VehicleAdapter(mContext, vehiclesFilter);
                vehicleView.setAdapter(adapter);
                adapter.setOnItemClickListener(new VehicleAdapter.onClickListener() {
                    @Override
                    public void onItemClick(int position) {


                        Intent intent = new Intent(mContext, BookTicketActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable(Defines.VEHICLE_PASS_ACTION, vehiclesFilter.get(position));
                        intent.putExtra(Defines.PROVINCE_FROM_ACTION, provinceFrom);
                        intent.putExtra(Defines.PROVINCE_TO_ACTION, provinceTo);
                        intent.putExtras(mBundle);
                        startActivity(intent);

                        isFilter = false;
                        itemFilter.setIcon(R.drawable.clear_filter);
                        VehicleAdapter adapter = new VehicleAdapter(mContext,vehicles);
                        vehicleView.setAdapter(adapter);
                        adapterDrawer = new NavDrawerListAdapter(mContext, navDrawerItems);
                        mDrawerList.setAdapter(adapterDrawer);
                        Defines.FilterInfor = new BusInfor();
                    }
                });
                Defines.clickFilter = true;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                if(vehiclesFilter.size() == 0 )
                    txtNoResult.setVisibility(View.VISIBLE);
                else
                    txtNoResult.setVisibility(View.GONE);
            }

        }
    }

}
