package br.com.ernanilima.jmercadobackend.support;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;

public class SupportPassword {

    public static String getHashingPassword() {
        return new BCryptPasswordEncoder().encode(getBasicPassword());
    }

    public static String getBasicPassword() {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1; //month starts with zero
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        String calculation = String.valueOf(year * (month * day));
        System.out.println("Support password is " + calculation);
        return calculation;
    }
}