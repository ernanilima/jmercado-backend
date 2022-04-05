package br.com.ernanilima.jmercadobackend.domain.addressBr;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum Country {

    BRAZIL("Brasil", "BR", "1058");

    private String description;
    private String acronym;
    private String code;

    public static List<ComboBox> getComboBox() {
        List<ComboBox> comboBox = new ArrayList<>();
        Arrays.stream(Country.values()).forEach(country -> {
            comboBox.add(new ComboBox(country.description, country.acronym, country.code));
        });

        return comboBox;
    }

    @Getter
    @AllArgsConstructor
    public static class ComboBox {
        private String description;
        private String acronym;
        private String code;
    }
}
