package br.com.ernanilima.jmercadobackend.dto;

import br.com.ernanilima.jmercadobackend.domain.permission.Permissions;
import com.fasterxml.jackson.databind.JsonNode;

public class PermissionDto {
    public JsonNode getPermissions() {
        return Permissions.getPermissions();
    }
}
