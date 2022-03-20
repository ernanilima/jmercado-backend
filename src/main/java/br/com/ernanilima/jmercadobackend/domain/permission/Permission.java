package br.com.ernanilima.jmercadobackend.domain.permission;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Integer id;
    private String role;
    private String description;
}
