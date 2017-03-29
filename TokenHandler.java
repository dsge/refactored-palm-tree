package eu.clapp.magunkert;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import eu.clapp.magunkert.activities.Splash;

/**
 * Created by benjaminszilagyi on 2017. 03. 14..
 */

public class TokenHandler {
    
    String token = null;
    Date currentDate = null;
    
    public void setToken(String t){
        token = t;
    }
    public String getToken(){
        return token;
    }
    public void setCurrentDate(Date d){
        currentDate = d;   
    }
    public Date getCurrentDate(){
        if (currentDate == null){
            return getDefaultCurrentDate();   
        }
        return currentDate;
    }
    protected Date getDefaultCurrentDate(){
        return Calendar.getInstance();
    }
    /**
     * do we have a valid token?
     * a token is valid if it exists and it's not expired
     */ 
    public boolean isAuthenticated(){
        return getToken() !== null && !expired();
    }

    public boolean expired() {

        Timestamp jwtTs = new Timestamp(Long.parseLong(UserService.getInstance(context).getExpTime())*1000);
        Timestamp todayTs = new Timestamp(System.currentTimeMillis());
        Date date = new Date(todayTs.getTime());
        Date jwtDate = new Date(jwtTs.getTime());
        Log.e("Todaydate and ts: "," " + date+" "+ todayTs);
        Log.e("jwtdate and ts: "," " + jwtDate+" "+ jwtTs);
        long diff = jwtDate.getTime() - date.getTime();


            if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <=2  || jwtDate.before(date)) {
                return true;
            }

            Log.e("Before: "," " + jwtDate.before(date));
            Log.e("After: "," " + jwtDate.after(date));
            Log.e("Days "," " + TimeUnit.DAYS.convert(diff+1, TimeUnit.MILLISECONDS));

        return false;

    }

    public boolean expiredTest(Context context,String token) {

        UserService.getInstance(context).setToken(token);

        Timestamp jwtTs = new Timestamp(Long.parseLong(UserService.getInstance(context).getExpTime())*1000);
        Timestamp todayTs = new Timestamp(System.currentTimeMillis());
        Date date = new Date(todayTs.getTime());
        Date jwtDate = new Date(jwtTs.getTime());
        Log.e("Todaydate and ts: "," " + date+" "+ todayTs);
        Log.e("jwtdate and ts: "," " + jwtDate+" "+ jwtTs);
        long diff = jwtDate.getTime() - date.getTime();


            if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) <=2) {
                return true;
            }

            Log.e("Before: "," " + jwtDate.before(date));
            Log.e("After: "," " + jwtDate.after(date));
            Log.e("Days: "," " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        return false;

    }


    public boolean validToken(Context context,String token){

        if (UserService.getInstance(context).getPayLoad(token) == ""){
            return true;
        }
        return false;
    }
}
