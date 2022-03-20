package br.com.ernanilima.jmercadobackend.domain.permission;

import br.com.ernanilima.jmercadobackend.utils.I18n;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum Permissions {

    SUPPORT(9999, null, "ROLE_SUPPORT", I18n.getMessage(I18n.ROLE_SUPPORT)),
    USER(9998, SUPPORT, "ROLE_USER", I18n.getMessage(I18n.ROLE_USER)),
        MENU_REGISTRATION(1000, USER, "ROLE_MENU_REGISTRATION", I18n.getMessage(I18n.ROLE_MENU_REGISTRATION)),
            MENU_REGISTRATION_FOR_USER(1200, MENU_REGISTRATION, "ROLE_MENU_REGISTRATION_FOR_USER", I18n.getMessage(I18n.ROLE_MENU_REGISTRATION_FOR_USER)),
                MENU_REGISTRATION_USER(1201, MENU_REGISTRATION_FOR_USER, "ROLE_MENU_REGISTRATION_USER", I18n.getMessage(I18n.ROLE_MENU_REGISTRATION_USER)),
                    USER_FIND(1202, MENU_REGISTRATION_USER, "ROLE_USER_FIND", I18n.getMessage(I18n.ROLE_USER_FIND)),
                    USER_INSERT(1203, MENU_REGISTRATION_USER, "ROLE_USER_INSERT", I18n.getMessage(I18n.ROLE_USER_INSERT)),
                    USER_UPDATE(1204, MENU_REGISTRATION_USER, "ROLE_USER_UPDATE", I18n.getMessage(I18n.ROLE_USER_UPDATE)),
                    USER_DELETE(1205, MENU_REGISTRATION_USER, "ROLE_USER_DELETE", I18n.getMessage(I18n.ROLE_USER_DELETE)),
                MENU_REGISTRATION_USER_GROUP(1211, MENU_REGISTRATION_FOR_USER, "ROLE_MENU_REGISTRATION_USER_GROUP", I18n.getMessage(I18n.ROLE_MENU_REGISTRATION_USER_GROUP)),
                    USER_GROUP_FIND(1212, MENU_REGISTRATION_USER_GROUP, "ROLE_USER_GROUP_FIND", I18n.getMessage(I18n.ROLE_USER_GROUP_FIND)),
                    USER_GROUP_INSERT(1213, MENU_REGISTRATION_USER_GROUP, "ROLE_USER_GROUP_INSERT", I18n.getMessage(I18n.ROLE_USER_GROUP_INSERT)),
                    USER_GROUP_UPDATE(1214, MENU_REGISTRATION_USER_GROUP, "ROLE_USER_GROUP_UPDATE", I18n.getMessage(I18n.ROLE_USER_GROUP_UPDATE)),
                    USER_GROUP_DELETE(1215, MENU_REGISTRATION_USER_GROUP, "ROLE_USER_GROUP_DELETE", I18n.getMessage(I18n.ROLE_USER_GROUP_DELETE)),

            MENU_REGISTRATION_COMPANY(1230, MENU_REGISTRATION, "ROLE_MENU_REGISTRATION_COMPANY", I18n.getMessage(I18n.ROLE_MENU_REGISTRATION_COMPANY)),
                COMPANY_FIND(1231, MENU_REGISTRATION_COMPANY, "ROLE_COMPANY_FIND", I18n.getMessage(I18n.ROLE_COMPANY_FIND)),
                COMPANY_INSERT(1232, MENU_REGISTRATION_COMPANY, "ROLE_COMPANY_INSERT", I18n.getMessage(I18n.ROLE_COMPANY_INSERT)),
                COMPANY_UPDATE(1233, MENU_REGISTRATION_COMPANY, "ROLE_COMPANY_UPDATE", I18n.getMessage(I18n.ROLE_COMPANY_UPDATE)),
                COMPANY_DELETE(1234, MENU_REGISTRATION_COMPANY, "ROLE_COMPANY_DELETE", I18n.getMessage(I18n.ROLE_COMPANY_DELETE));

    private int id;
    private Permissions idSuper;
    private String role;
    private String description;

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * @param id Integer - id da permissao
     * @return Permission
     */
    public static Permission toEnum(Integer id) {
        if (id == null) { return null; }
        for (Permissions permission : Permissions.values()) {
            if (id.equals(permission.id))
                return new Permission(permission.getId(), permission.getRole(), permission.getDescription());
        } throw new IllegalArgumentException("Invalid");
    }

    public static JsonNode getPermissions() {
        return getPermissions(new HashSet<>());
    }

    @SneakyThrows
    public static JsonNode getPermissions(Set<Permissions> permissions) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder stringJson = new StringBuilder();
        for (Permissions permission : Permissions.values()) {
            if (permission.getId() < Permissions.USER.getId()) {
                // verifica se existe a permissao e atribui o resultado
                // true se o usuario tiver a permissao
                boolean havePermission = permissions.stream().anyMatch(p -> p.getRole().equals(permission.getRole()));

                StringBuilder children = new StringBuilder();
                children.append("\"").append(permission.getRole())
                        .append("\": [{\"role\": ").append(havePermission)
                        .append(", \"description\": \"").append(permission.getDescription())
                        .append("\", \"id\": ").append(permission.getId()).append("}]");

                if (stringJson.toString().contains((permission.getIdSuper() == null) ? "-1" : permission.getIdSuper().toString())) {
                    stringJson.insert(stringJson.indexOf(permission.getIdSuper().toString()) + 4, "," + children);

                } else {
                    stringJson.append("{").append(children).append("}");
                }
            }
        }

        return objectMapper.readTree("[" + stringJson + "]");
    }
}
