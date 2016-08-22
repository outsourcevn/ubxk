package booking.online.bus.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharePreference {

    private Context activity;
    private String TOKEN = "token";
    private String NAME  = "name";
    private String PHONE = "phone";
    private String LOGIN = "login";
    private String LICENSE = "license";
    private String DRIVER_PHONE = "driver phone";
    private String PLACE_FROM = "place from";
    private String PLACE_TO = "place to";
    private String PARK_FROM = "park from";
    private String PARK_TO = "park to";
    private String DRIVER_ID = "driver id";
    private String OWNER_NAME = "owner name";
    private String TYPE = "type";
    // constructor
    public SharePreference(Context activity) {
        this.activity = activity;
    }
    public void saveToken(String token) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }
    public String getToken() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(TOKEN, "");
    }

    public void saveName(String name) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(NAME, name);
        editor.apply();
    }
    public String getName() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(NAME, "");
    }

    public void savePhone(String phone) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PHONE, phone);
        editor.apply();
    }
    public String getPhone() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(PHONE, "");
    }
    public void saveLogin() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(LOGIN, true);
        editor.apply();
    }
    public boolean getLogin() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getBoolean(LOGIN,false);
    }

    public void saveLicense(String license) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LICENSE, license);
        editor.apply();
    }
    public String getLicense() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(LICENSE,"");
    }


    public void saveDriverPhone(String phone) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(DRIVER_PHONE, phone);
        editor.apply();
    }
    public String getDriverPhone() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(DRIVER_PHONE,"");
    }
    public void savePlaceFrom(String place) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PLACE_FROM, place);
        editor.apply();
    }
    public String getPlaceFrom() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(PLACE_FROM,"");
    }
    public void savePlaceTo(String place) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PLACE_TO, place);
        editor.apply();
    }
    public String getPlaceTo() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(PLACE_TO,"");
    }
    public void saveOwnerName(String name) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(OWNER_NAME, name);
        editor.apply();
    }
    public String getOwnerName() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(OWNER_NAME,"");
    }

    public void saveType(String type) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TYPE, type);
        editor.apply();
    }
    public String getType() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(TYPE,"");
    }

    public void saveParkStart(String park) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PARK_FROM, park);
        editor.apply();
    }
    public String getParkStart() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(PARK_FROM,"");
    }

    public void saveParkEnd(String park) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PARK_TO, park);
        editor.apply();
    }
    public String getParkEnd() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString(PARK_TO,"");
    }
    public void saveDriverId(int id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(DRIVER_ID, id);
        editor.apply();
    }
    public int getDriverId() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getInt(DRIVER_ID,0);
    }
}
