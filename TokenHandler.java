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
    int renewTreshold = 60 * 60 * 48; //seconds | if the expiration date is closed than this then the token should be renewed
    
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
    /**
     * should our token be renewed?
     */
    public boolean shouldBeRenewed(){
        if (expired()){
            return false; //if our token is expired then we cannot renew it   
        }
        if (/* token has no exp field */){
            return false; //if no exp field exists then we don't want to renew it since it cannot even expire
        }
        Date expirationDate = /* make a Date object from the token's exp field somehow */;
        Date renewDate = getCurrentDate() - renewTreshold; //make a new Date object that tells when the token should be renewed
        return expirationDate.after(renewDate);
    }
}
