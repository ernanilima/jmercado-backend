package br.com.ernanilima.jmercadobackend.domain.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Integer id;
    private String role;
    private String description;
}
