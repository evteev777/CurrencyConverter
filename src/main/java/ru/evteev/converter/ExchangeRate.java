package ru.evteev.converter;

import java.time.LocalDate;

public class ExchangeRate {

    private final Currency currency;
    private final double value;
    private final LocalDate date;

    public ExchangeRate(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
        this.date = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format("ExchangeRate{%s,\tvalue=%8.4f,\tdate=%s}", currency, value, date);
    }
}
