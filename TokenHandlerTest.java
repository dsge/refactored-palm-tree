package eu.clapp.magunkert.token;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import eu.clapp.magunkert.TokenHandler;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class TokenHandlerTest {


    @Test
    public void testExpiredToken() {
        TokenHandler th = new TokenHandler();
        String token = Jwts.builder()
                .setSubject("Joe")
                .setExpiration(getDate(2030, 2, 3))
                .signWith(SignatureAlgorithm.HS512, MacProvider.generateKey())
                .compact();
        
        th.setToken(token);
        th.setCurrentDate(getDate(2030, 2, 2)); //set the current time to 1 day before then the expiration date
        
        assertTrue(th.expired());
    }
    
    @Test
    public void testMissingTokenShouldBeExpired() {
        TokenHandler th = new TokenHandler();
        assertTrue(th.expired());
    }
    @Test
    public void testMissingTokenWithNoExpFieldShouldNotBeExpired() {
        TokenHandler th = new TokenHandler();
        String token = Jwts.builder()
                .setSubject("Joe")
                .signWith(SignatureAlgorithm.HS512, MacProvider.generateKey())
                .compact();
        
        th.setToken(token);
        
        assertFalse(th.expired());
    }

    public Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
