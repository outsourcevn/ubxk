package booking.online.bus.Utilities;

import booking.online.bus.Models.BusInfor;

/**
 * Created by DatNT on 6/29/2016.
 */
public class Defines {
    public static  final String     HOSTNAME                    = "http://xe63.vn/";
    public static  final String     URL_LIST_VEHICLE            = HOSTNAME + "Appi/getlist";
    public static  final String     URL_BOOK_TICKET             = HOSTNAME + "Appi/ticket";
    public static  final String     URL_REGISTER                = HOSTNAME + "Appi/register";
    public static  final String     VEHICLE_PASS_ACTION         = "1";
    public static  final String     PROVINCE_FROM_ACTION        = "2";
    public static  final String     PROVINCE_TO_ACTION          = "3";
    public static  final String     VEHICLE_TYPE_ACTION         = "4";
    public static  final String     VEHICLE_ID_ACTION           = "5";
    public static BusInfor FilterInfor                 = null;
    public static        boolean    clickFilter                 = false;
    public static String token                 = "";



}
