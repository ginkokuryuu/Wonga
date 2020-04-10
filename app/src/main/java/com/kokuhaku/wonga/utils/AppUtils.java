package com.kokuhaku.wonga.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {
    public static Date getCurrentDateTIme(){
        Date currentDate = Calendar.getInstance().getTime();
        return currentDate;
    }

    public static String getFormattedDateString(Date date){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MM d HH:mm:ss zzz yyyy");
            String dateString = sdf.format(date);

            Date newDate = sdf.parse(dateString);
            sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
            return sdf.format(newDate);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
