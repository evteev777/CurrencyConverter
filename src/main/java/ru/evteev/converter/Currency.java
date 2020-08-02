package ru.evteev.converter;

public class Currency {

    private final String id;
    private final String numCode;
    private final String charCode;
    private final int nominal;
    private final String name;

    public Currency(String id, String numCode, String charCode, int nominal, String name) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Currency{id=%7s,\tnumCode=%s,\tcharCode=%s," +
                        "\tnominal=%5d,\tname=%-40s}",
                id, numCode, charCode, nominal, name);
    }
}
