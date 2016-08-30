package booking.online.bus.Utilities;

import java.util.ArrayList;

import booking.online.bus.Models.BusInfor;

/**
 * Created by DatNT on 6/29/2016.
 */
public class Defines {
    public static  final String     HOSTNAME                    = "http://xe63.vn/";
    public static  final String     URL_LIST_VEHICLE            = HOSTNAME + "Appi/getlist";
    public static  final String     URL_LIST_ONL_VEHICLE        = HOSTNAME + "Appi/getlistonline";
    public static  final String     URL_LIST_OWNER              = HOSTNAME + "Appi/getlistnhaxe";
    public static  final String     URL_BOOK_TICKET             = HOSTNAME + "Appi/ticket";
    public static  final String     URL_REGISTER                = HOSTNAME + "Appi/register";
    public static  final String     URL_LOGIN                   = HOSTNAME + "Appi/login";
    public static  final String     URL_LOCATE                  = HOSTNAME + "Appi/locate";
    public static  final String     URL_ACCEPT                  = HOSTNAME + "Appi/accept";
    public static  final String     URL_PROVINCE                = HOSTNAME + "Appi/getfromto";
    public static  final String     URL_NEW_VEHICLE             = HOSTNAME + "Appi/getauto";
    public static  final String     URL_REGISTER_VEHICLE        = HOSTNAME + "Appi/newnhaxe";


    public static  final String     VEHICLE_PASS_ACTION         = "1";
    public static  final String     PROVINCE_FROM_ACTION        = "2";
    public static  final String     PROVINCE_TO_ACTION          = "3";
    public static  final String     VEHICLE_TYPE_ACTION         = "4";
    public static  final String     VEHICLE_ID_ACTION           = "5";
    public static  final String     OWNER_ID_ACTION             = "6";
    public static BusInfor FilterInfor                 = null;
    public static        boolean    clickFilter                 = false;
    public static String token                 = "";


    public static  final int        LOOP_TIME                   = 5*60*1000;

    public static ArrayList<String> provinceTo;
    public static ArrayList<String> provinceFrom;

}
