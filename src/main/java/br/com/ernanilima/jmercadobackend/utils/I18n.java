package br.com.ernanilima.jmercadobackend.utils;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {

    public static final String ROLE_SUPPORT = "role.support";
    public static final String ROLE_USER = "role.user";
    public static final String ROLE_MENU_REGISTRATION = "role.menu.registration";
    public static final String ROLE_MENU_REGISTRATION_FOR_USER = "role.menu.registration.for.user";
    public static final String ROLE_MENU_REGISTRATION_USER = "role.menu.registration.user";
    public static final String ROLE_USER_FIND = "role.user.find";
    public static final String ROLE_USER_INSERT = "role.user.insert";
    public static final String ROLE_USER_UPDATE = "role.user.update";
    public static final String ROLE_USER_DELETE = "role.user.delete";
    public static final String ROLE_MENU_REGISTRATION_USER_GROUP = "role.menu.registration.user.group";
    public static final String ROLE_USER_GROUP_FIND = "role.user.group.find";
    public static final String ROLE_USER_GROUP_INSERT = "role.user.group.insert";
    public static final String ROLE_USER_GROUP_UPDATE = "role.user.group.update";
    public static final String ROLE_USER_GROUP_DELETE = "role.user.group.delete";
    public static final String ROLE_MENU_REGISTRATION_COMPANY = "role.menu.registration.company";
    public static final String ROLE_COMPANY_FIND = "role.company.find";
    public static final String ROLE_COMPANY_INSERT = "role.company.insert";
    public static final String ROLE_COMPANY_UPDATE = "role.company.update";
    public static final String ROLE_COMPANY_DELETE = "role.company.delete";

    public static final String NOT_FOUND_COMPANY = "not.found.company";
    public static final String NOT_FOUND_EIN = "not.found.ein";
    public static final String NOT_FOUND_USER = "not.found.user";
    public static final String NOT_FOUND_EMAIL = "not.found.email";

    public static final String INTEGRITY_IN_UP = "integrity.insert.updade";
    public static final String INTEGRITY_DELETE = "integrity.delete";

    public static final String INVALID_LOGIN = "invalid.login";
    public static final String BAD_CREDENTIALS = "bad.credentials";

    // EXCEPTIONS
    public static final String QUANTITY_OF_ERRORS = "exc.quantity.errors";
    public static final String METHOD_NOT_SUPPORTED = "exc.method.not.supported";
    public static final String ENDPOINT_NOT_FOUND = "exc.endpoint.not.found";
    public static final String INVALID_VALUE_TYPE = "exc.invalid.value.type";

    // VALIDATION
    public static final String LENGTH_MIN_FIELD = "length.min.field";
    public static final String INVALID_EMAIL = "invalid.email";
    public static final String INVALID_EIN = "invalid.ein";
    public static final String LENGTH_FIELD= "length.field";

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
