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
public class TokenTest {


    @Test
    public void testExpiredTokenTest() {
        TokenHandler th = new TokenHandler();
        Date date = getDate(2016, 2, 1);

        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        Log.e("MY_TOKEN", " " + compactJws);
        System.out.println(compactJws);

        assertTrue("Token", th.expiredTest(InstrumentationRegistry.getTargetContext().getApplicationContext(), compactJws));
    }

    @Test
    public void testNowTokenTest() {
        TokenHandler th = new TokenHandler();
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        Log.e("MY_TOKEN", " " + compactJws);
        System.out.println(compactJws);

        assertTrue("Token", th.expiredTest(InstrumentationRegistry.getTargetContext().getApplicationContext(), compactJws));
    }

    @Test
    public void testFutureTokenTest() {
        TokenHandler th = new TokenHandler();
        Date date = getDate(2017,6,1);


        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        Log.e("MY_TOKEN", " " + compactJws);
        System.out.println(compactJws);

        assertFalse("Token", th.expiredTest(InstrumentationRegistry.getTargetContext().getApplicationContext(), compactJws));
    }

    @Test
    public void testBefore2DaysTokenTest() {
        TokenHandler th = new TokenHandler();

        Date date = getDate(2017,2,25);

        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        Log.e("MY_TOKEN", " " + compactJws);
        System.out.println(compactJws);

        assertTrue("Token", th.expiredTest(InstrumentationRegistry.getTargetContext().getApplicationContext(), compactJws));
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
