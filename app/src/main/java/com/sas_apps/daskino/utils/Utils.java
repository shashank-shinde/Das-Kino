package com.sas_apps.daskino.utils;
/*
 * Created by Shashank Shinde.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class Utils {

    public final static String API_KEY = "5cbefd2315d88e62a174ad57bf0e0ba0";
    public final static String BASE_URL = "https://api.themoviedb.org/3/";
    public final static String THUMBNAIL_URL = "http://image.tmdb.org/t/p/w185";
    public final static String POSTER_URL = "http://image.tmdb.org/t/p/w500";
    public final static String BACKGROUND_IMAGE_URL = "http://image.tmdb.org/t/p/w780";



    public static String getLanguage(String id) {
        String result = "";
        switch (id) {
            case "de":
                result = "German";
                break;
            case "en":
                result = "English US";
                break;
            case "hi":
                result = "Hindi";
                break;
            case "mr":
                result = "Marathi";
                break;
            case "fr":
                result = "French";
                break;
            case "it":
                result = "Italian";
                break;
            default:
                result = id;
        }
        return result;
    }

    public static String getHours(String time) {
        int t = Integer.parseInt(time);
        int hours = t / 60;
        int minutes = t % 60;
        return hours + " hr " + minutes + " min";
    }


    public static String getDate(String date) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd", Locale.US);
        Date date1 = dt.parse(String.valueOf(date));
        SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy",Locale.US);
        return dt1.format(date1);
    }


    public static String KMBconversion(String i) {
        long count=Long.parseLong(i);
        if (count < 1000) return "Unavailable";
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp-1))+" USD";
    }

}
