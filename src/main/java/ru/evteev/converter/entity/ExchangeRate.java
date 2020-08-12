package ru.evteev.converter.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Lombok
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    private Currency currency;

    private double value;

    //    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate date = LocalDate.now();

    public ExchangeRate(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }
}
