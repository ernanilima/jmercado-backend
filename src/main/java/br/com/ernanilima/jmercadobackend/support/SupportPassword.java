package br.com.ernanilima.jmercadobackend.support;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;

class SupportPassword {
    static String getPassword() {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1; //month starts with zero
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        String calculation = String.valueOf(year * (month * day));
        System.out.println("Support password is " + calculation);

        return new BCryptPasswordEncoder().encode(calculation);
    }
}