package br.com.ernanilima.jmercadobackend.domain.addressBr;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CountryRegion {

    NORTE("Norte", Country.BRASIL.getCode()),
    NORDESTE("Nordeste", Country.BRASIL.getCode()),
    CENTRO_OESTE("Centro-Oeste", Country.BRASIL.getCode()),
    SUDESTE("Sudeste", Country.BRASIL.getCode()),
    SUL("Sul", Country.BRASIL.getCode());

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
