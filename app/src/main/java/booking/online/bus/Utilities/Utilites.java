package booking.online.bus.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import booking.online.bus.Models.BusInfor;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DatNT on 6/13/2016.
 */
public class Utilites {
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public static void assignArray(ArrayList<BusInfor> p1, ArrayList<BusInfor> p2 ){
        for (int i=0; i < p1.size() ; i++)
            p2.add(p1.get(i));
    }
    public static ArrayList<BusInfor> checkFilterBusNull(ArrayList<BusInfor> infor){
        ArrayList<BusInfor> tempName = new ArrayList<>();
        ArrayList<BusInfor> tempToPlace = new ArrayList<>();
        ArrayList<BusInfor> tempFromPlace = new ArrayList<>();
        ArrayList<BusInfor> tempStartTime = new ArrayList<>();
        ArrayList<BusInfor> tempRecep = new ArrayList<>();
        ArrayList<BusInfor> tempBusType = new ArrayList<>();

        // Bus name
        if (Defines.FilterInfor.getCarOwner() ==null || Defines.FilterInfor.getCarOwner().equals("Tất cả"))
            tempName = infor;
        else{
            for(BusInfor info : infor)
                if (info.getCarOwner().equals(Defines.FilterInfor.getCarOwner()))
                    tempName.add(info);
        }
        // Start Place
        if (Defines.FilterInfor.getFromPlace() ==null || Defines.FilterInfor.getFromPlace().equals("Tất cả"))
            assignArray(tempName,tempFromPlace);
        else{
            for(BusInfor info : tempName)
                if (info.getFromPlace().equals(Defines.FilterInfor.getFromPlace()))
                    tempFromPlace.add(info);
        }


        // Destination place
        if (Defines.FilterInfor.getToPlace() ==null || Defines.FilterInfor.getToPlace().equals("Tất cả"))
            assignArray(tempFromPlace,tempToPlace);
        else{
            for(BusInfor info : tempFromPlace)
                if (info.getToPlace().equals(Defines.FilterInfor.getToPlace()))
                    tempToPlace.add(info);
        }

        // start time
        if (Defines.FilterInfor.getStartTimeofDay() ==null || Defines.FilterInfor.getStartTimeofDay().equals("Tất cả"))
            assignArray(tempToPlace,tempStartTime);
        else{
            for(BusInfor info : tempToPlace)
                if (info.getStartTimeofDay().equals(Defines.FilterInfor.getStartTimeofDay()))
                    tempStartTime.add(info);
        }

        // reception type
        if (Defines.FilterInfor.getRecepType() ==null || Defines.FilterInfor.getRecepType().equals("Tất cả"))
            assignArray(tempStartTime,tempRecep);
        else{
            for(BusInfor info : tempStartTime)
                if (info.getRecepType().equals(Defines.FilterInfor.getRecepType()))
                    tempRecep.add(info);
        }


        // vehicle type
        if (Defines.FilterInfor.getVehicleType() ==null || Defines.FilterInfor.getVehicleType().equals("Tất cả"))
            assignArray(tempRecep,tempBusType);
        else{
            for(BusInfor info : tempRecep)
                if (info.getVehicleType().equals(Defines.FilterInfor.getVehicleType()))
                    tempBusType.add(info);
        }
        if (Defines.FilterInfor.getPrice() == 0)
            return tempBusType;
        else if ( Defines.FilterInfor.getPrice() == 1)
            return sortDecrease(tempBusType);
        else if ( Defines.FilterInfor.getPrice() == 2)
            return sortIncrease(tempBusType);

        return null;
    }

    public static ArrayList<BusInfor> sortDecrease(ArrayList<BusInfor> infor){
        boolean swapped = true;
        int j = 0;
        BusInfor tmp ,tmp2;
        ArrayList<BusInfor> temp = new ArrayList<>();
        assignArray(infor, temp);
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < temp.size() - j; i++) {
                if (temp.get(i).getPrice() < temp.get(i+1).getPrice()) {
                    tmp = temp.get(i);
                    tmp2 = temp.get(i+1);
                    temp.set(i,tmp2);
                    temp.set(i+1,tmp);
                    swapped = true;
                }
            }
        }
        return temp;
    }
    public static ArrayList<BusInfor> sortIncrease(ArrayList<BusInfor> infor){
        boolean swapped = true;
        int j = 0;
        BusInfor tmp ,tmp2;
        ArrayList<BusInfor> temp = new ArrayList<>();
        assignArray(infor, temp);
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < temp.size() - j; i++) {
                if (temp.get(i).getPrice() > temp.get(i+1).getPrice()) {
                    tmp = temp.get(i);
                    tmp2 = temp.get(i+1);
                    temp.set(i,tmp2);
                    temp.set(i+1,tmp);
                    swapped = true;
                }
            }
        }
        return temp;
    }
    public static boolean checkFilterNull(){
        if (Defines.FilterInfor.getCarOwner() != null)
            return false;
        if (Defines.FilterInfor.getFromPlace() != null)
            return false;
        if (Defines.FilterInfor.getToPlace() != null)
            return false;
        if (Defines.FilterInfor.getStartTime() != null)
            return false;
        if (Defines.FilterInfor.getRecepType() != null)
            return false;
        if (Defines.FilterInfor.getVehicleType() != null)
            return false;
        return true;
    }
    public static String convertCurrency(int currency){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(currency);
        if (moneyString.endsWith(".00")) {
            int centsIndex = moneyString.lastIndexOf(".00");
            if (centsIndex != -1) {
                moneyString = moneyString.substring(0, centsIndex);
            }
        }
        return moneyString;
    }
    public static String getTimeStamp(){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return timeStamp;
    }
    public static void showDialog(Context mContext,String title, String content)
    {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void systemUiVisibility(Activity activity) {
//         This work only for android 4.4+
        final View decorView = activity.getWindow().getDecorView();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flagsKitkat);

            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flagsKitkat);
                    }
                }
            });
        }
    }

    public static final int flagsKitkat = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

}
