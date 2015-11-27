package edu.csu.basetools;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class C {
    public static Map<String, Bitmap> BITMAPCATCH = new HashMap<String, Bitmap>();
    public static String IP = "http://192.168.1.253/";
    public static String Login = IP + "dpc/mobile/login";
    public static String Logout = IP + "dpc/mobile/logout";
    public static String Movie = IP + "dpc/mobile/movie/get";
    public static String MovieDetial = IP + "dpc/mobile/movie/detail";
    public static String Music = IP + "dpc/mobile/music/get";
}
