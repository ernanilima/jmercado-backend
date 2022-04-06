package br.com.ernanilima.jmercadobackend.domain.addressBr;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum State {

    RONDONIA("Rondônia", "RO", "11", Country.BRAZIL.getCode(), CountryRegion.NORTH.name()),
    ACRE("Acre", "AC", "12", Country.BRAZIL.getCode(), CountryRegion.NORTH.name()),
    AMAZONAS("Amazonas", "AM", "13", Country.BRAZIL.getCode(), CountryRegion.NORTH.name()),
    RORAIMA("Roraima", "RR", "14", Country.BRAZIL.getCode(), CountryRegion.NORTH.name()),
    PARA("Pará", "PA", "15", Country.BRAZIL.getCode(), CountryRegion.NORTH.name()),
    AMAPA("Amapá", "AP", "16", Country.BRAZIL.getCode(), CountryRegion.NORTH.name()),
    TOCANTIS("Tocantins", "TO", "17", Country.BRAZIL.getCode(), CountryRegion.NORTH.name()),
    MARANHAO("Maranhão", "MA", "21", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    PIAUI("Piauí", "PI", "22", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    CEARA("Ceará", "CE", "23", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    RIO_GRANDE_DO_NORTE("Rio Grande do Norte", "RN", "24", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    PARAIBA("Paraíba", "PB", "25", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    PERNAMBUCO("Pernambuco", "PE", "26", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    ALAGOAS("Alagoas", "AL", "27", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    SERGIPE("Sergipe", "SE", "28", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    BAHIA("Bahia", "BA", "29", Country.BRAZIL.getCode(), CountryRegion.NORTHEAST.name()),
    MINAS_GERAIS("Minas Gerais", "MG", "31", Country.BRAZIL.getCode(), CountryRegion.SOUTHEAST.name()),
    ESPIRITO_SANTO("Espírito Santo", "ES", "32", Country.BRAZIL.getCode(), CountryRegion.SOUTHEAST.name()),
    RIO_DE_JANEIRO("Rio de Janeiro", "RJ", "33", Country.BRAZIL.getCode(), CountryRegion.SOUTHEAST.name()),
    SAO_PAULO("São Paulo", "SP", "35", Country.BRAZIL.getCode(), CountryRegion.SOUTHEAST.name()),
    PARANA("Paraná", "PR", "41", Country.BRAZIL.getCode(), CountryRegion.SOUTH.name()),
    SANTA_CATARINA("Santa Catarina", "SC", "42", Country.BRAZIL.getCode(), CountryRegion.SOUTH.name()),
    RIO_GRANDE_DO_SUL("Rio Grande do Sul", "RS", "43", Country.BRAZIL.getCode(), CountryRegion.SOUTH.name()),
    MATO_GROSSO_DO_SUL("Mato Grosso do Sul", "MS", "50", Country.BRAZIL.getCode(), CountryRegion.MIDWEST.name()),
    MATO_GROSSO("Mato Grosso", "MT", "51", Country.BRAZIL.getCode(), CountryRegion.MIDWEST.name()),
    GOIAIS("Goiás", "GO", "52", Country.BRAZIL.getCode(), CountryRegion.MIDWEST.name()),
    DISTRITO_FEDERAL("Distrito Federal", "DF", "53", Country.BRAZIL.getCode(), CountryRegion.MIDWEST.name());

    private String description;
    private String acronym;
    private String code;
    private String country;
    private String region;

    public static List<ComboBox> getComboBox(String codeCountry) {
        List<ComboBox> comboBox = new ArrayList<>();
        Arrays.stream(State.values()).forEach(state -> {
            if (state.country.equals(codeCountry))
                comboBox.add(new ComboBox(state.description, state.acronym, state.code, state.country, state.region));
        });

        return comboBox;
    }

    @Getter
    @AllArgsConstructor
    public static class ComboBox {
        private String description;
        private String acronym;
        private String code;
        private String country;
        private String region;
    }
}
