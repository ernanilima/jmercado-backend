package br.com.ernanilima.jmercadobackend.domain.addressBr;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CountryRegion {

    NORTH("Norte", "1058"),
    NORTHEAST("Nordeste", "1058"),
    MIDWEST("Centro-Oeste", "1058"),
    SOUTHEAST("Sudeste", "1058"),
    SOUTH("Sul", "1058");

    private String description;
    private String country;

    public static List<ComboBox> getComboBox(String codeCountry) {
        List<ComboBox> comboBox = new ArrayList<>();
        Arrays.stream(CountryRegion.values()).forEach(region -> {
            if (region.country.equals(codeCountry))
                comboBox.add(new ComboBox(region.description, region.country));
        });

        return comboBox;
    }

    @Getter
    @AllArgsConstructor
    public static class ComboBox {
        private String description;
        private String country;
    }
}
