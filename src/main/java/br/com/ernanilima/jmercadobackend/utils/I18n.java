package br.com.ernanilima.jmercadobackend.utils;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {

    public static final String ROLE_SUPPORT = "role.support";
    public static final String ROLE_FIND = "role.find";
    public static final String ROLE_INSERT = "role.insert";
    public static final String ROLE_UPDATE = "role.update";
    public static final String ROLE_DELETE = "role.delete";

    public static final String NOT_FOUND_EIN = "not.found.ein";

//    public static final String INTEGRITY_INSERT = "integrity.insert";
//    public static final String INTEGRITY_UPDATE = "integrity.updade";
//    public static final String INTEGRITY_DELETE = "integrity.delete";
//    public static final String MESSAGE_ERROR_COUNT = "message.error.count";
//    public static final String MESSAGE_ERROR_CEP = "message.error.cep";


    /**
     * @param e DataIntegrityViolationException
     * @return String - field name that had the error, translated field name
     */
    public static String getErrorFieldNameI18n(DataIntegrityViolationException e) {
        String exception = e.getMostSpecificCause().getMessage();
        return ResourceBundle.getBundle("messages", Locale.getDefault())
                .getString(exception.substring(exception.indexOf("(") + 1, exception.indexOf(")")).toLowerCase());
    }

    /**
     * @param s String
     * @return String - field name that had the error, translated field name
     */
    public static String getErrorFieldNameI18n(String s) {
        return getSimpleMessage(s);
    }

    /**
     * @param s String
     * @return String - translated class name
     */
    public static String getClassSimpleNameI18n(String s) {
        return getSimpleMessage(s);
    }

    /**
     * @param s String
     * @return String - translated message
     */
    public static String getSimpleMessage(String s) {
        return ResourceBundle.getBundle("messages", Locale.getDefault())
                .getString(s.toLowerCase());
    }
}
