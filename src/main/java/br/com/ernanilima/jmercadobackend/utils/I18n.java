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

    public static final String NOT_FOUND_COMPANY = "not.found.company";
    public static final String NOT_FOUND_EIN = "not.found.ein";
    public static final String NOT_FOUND_USER = "not.found.user";
    public static final String NOT_FOUND_EMAIL = "not.found.email";

    public static final String INTEGRITY_IN_UP = "integrity.insert.updade";

//    public static final String INTEGRITY_DELETE = "integrity.delete";
//    public static final String MESSAGE_ERROR_COUNT = "message.error.count";
//    public static final String MESSAGE_ERROR_CEP = "message.error.cep";


    /**
     * @param e DataIntegrityViolationException - erro
     * @return String - nome do campo com o erro
     */
    public static String getFieldName(DataIntegrityViolationException e) {
        String exception = e.getMostSpecificCause().getMessage();
        // exemplo: PUBLIC.COMPANY(EIN NULLS FIRST)
        int startField = exception.indexOf("(") + 1; // primeiro '('
        int endField = exception.indexOf(" ", startField); // primeiro ' ' a partir do index informado
        return ResourceBundle.getBundle("messages", Locale.getDefault())
                .getString(exception.substring(startField, endField).toLowerCase());
    }

    /**
     * @param s String
     * @return String - nome da classe
     */
    public static String getClassName(String s) {
        return getMessage(s);
    }

    /**
     * @param s String - tipo de mensagem
     * @return String - mensagem
     */
    public static String getMessage(String s) {
        return ResourceBundle.getBundle("messages", Locale.getDefault())
                .getString(s.toLowerCase());
    }
}
