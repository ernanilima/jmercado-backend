package br.com.ernanilima.jmercadobackend.domain.permission;

import br.com.ernanilima.jmercadobackend.utils.I18n;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions {

    SUPPORT(9999, "ROLE_SUPPORT", I18n.getSimpleMessage(I18n.ROLE_SUPPORT)),
    FIND(1, "ROLE_FIND", I18n.getSimpleMessage(I18n.ROLE_FIND)),
    INSERT(2, "ROLE_INSERT", I18n.getSimpleMessage(I18n.ROLE_INSERT)),
    UPDATE(3, "ROLE_UPDATE", I18n.getSimpleMessage(I18n.ROLE_UPDATE)),
    DELETE(4, "ROLE_DELETE", I18n.getSimpleMessage(I18n.ROLE_DELETE));

    private int id;
    private String role;
    private String description;

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * @param id Integer - id to permission
     * @return Permission
     */
    public static Permission toEnum(Integer id) {
        if (id == null) { return null; }
        for (Permissions permission : Permissions.values()) {
            if (id.equals(permission.id)) {
                return new Permission(permission.getId(), permission.getRole(), permission.getDescription());
            }
        } throw new IllegalArgumentException("Invalid");
    }
}
