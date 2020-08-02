package ru.evteev.converter.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

//Lombok
@Getter
@Setter
@NoArgsConstructor

@Entity
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @OneToOne
    private Currency currency;

    private Double value;
    private LocalDate date = LocalDate.now();

    public ExchangeRate(Currency currency, Double value) {
        this.currency = currency;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("ExchangeRate{%s,\tvalue=%8.4f,\tdate=%s}",
                currency, value, date);
    }
}
