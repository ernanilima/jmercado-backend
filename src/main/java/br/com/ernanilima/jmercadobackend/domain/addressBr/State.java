package br.com.ernanilima.jmercadobackend.domain.addressBr;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum State {

    RONDONIA("Rondônia", "RO", "11", Country.BRASIL.getCode(), CountryRegion.NORTE.name()),
    ACRE("Acre", "AC", "12", Country.BRASIL.getCode(), CountryRegion.NORTE.name()),
    AMAZONAS("Amazonas", "AM", "13", Country.BRASIL.getCode(), CountryRegion.NORTE.name()),
    RORAIMA("Roraima", "RR", "14", Country.BRASIL.getCode(), CountryRegion.NORTE.name()),
    PARA("Pará", "PA", "15", Country.BRASIL.getCode(), CountryRegion.NORTE.name()),
    AMAPA("Amapá", "AP", "16", Country.BRASIL.getCode(), CountryRegion.NORTE.name()),
    TOCANTIS("Tocantins", "TO", "17", Country.BRASIL.getCode(), CountryRegion.NORTE.name()),
    MARANHAO("Maranhão", "MA", "21", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    PIAUI("Piauí", "PI", "22", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    CEARA("Ceará", "CE", "23", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    RIO_GRANDE_DO_NORTE("Rio Grande do Norte", "RN", "24", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    PARAIBA("Paraíba", "PB", "25", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    PERNAMBUCO("Pernambuco", "PE", "26", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    ALAGOAS("Alagoas", "AL", "27", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    SERGIPE("Sergipe", "SE", "28", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    BAHIA("Bahia", "BA", "29", Country.BRASIL.getCode(), CountryRegion.NORDESTE.name()),
    MINAS_GERAIS("Minas Gerais", "MG", "31", Country.BRASIL.getCode(), CountryRegion.SUDESTE.name()),
    ESPIRITO_SANTO("Espírito Santo", "ES", "32", Country.BRASIL.getCode(), CountryRegion.SUDESTE.name()),
    RIO_DE_JANEIRO("Rio de Janeiro", "RJ", "33", Country.BRASIL.getCode(), CountryRegion.SUDESTE.name()),
    SAO_PAULO("São Paulo", "SP", "35", Country.BRASIL.getCode(), CountryRegion.SUDESTE.name()),
    PARANA("Paraná", "PR", "41", Country.BRASIL.getCode(), CountryRegion.SUL.name()),
    SANTA_CATARINA("Santa Catarina", "SC", "42", Country.BRASIL.getCode(), CountryRegion.SUL.name()),
    RIO_GRANDE_DO_SUL("Rio Grande do Sul", "RS", "43", Country.BRASIL.getCode(), CountryRegion.SUL.name()),
    MATO_GROSSO_DO_SUL("Mato Grosso do Sul", "MS", "50", Country.BRASIL.getCode(), CountryRegion.CENTRO_OESTE.name()),
    MATO_GROSSO("Mato Grosso", "MT", "51", Country.BRASIL.getCode(), CountryRegion.CENTRO_OESTE.name()),
    GOIAIS("Goiás", "GO", "52", Country.BRASIL.getCode(), CountryRegion.CENTRO_OESTE.name()),
    DISTRITO_FEDERAL("Distrito Federal", "DF", "53", Country.BRASIL.getCode(), CountryRegion.CENTRO_OESTE.name());

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
