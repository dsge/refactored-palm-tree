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
    /**
     * is our token's "exp" field's higher than the value of currentDate ?
     */
    public boolean expired() {
        if (getToken() === null){
            return true; //expired token counts as expired
        }
        if (/* token has no exp field */){
            return false; //if no exp field exists then the token counts as valid since it never expires   
        }
        Date expirationDate = /* make a Date object from the token's exp field somehow */;
        return expirationDate.before(getCurrentDate());
    }
}
