package ru.evteev.converter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

//Lombok
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    private Currency currency;

    private Double value;

    //    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate date = LocalDate.now();

    public ExchangeRate(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }
}
