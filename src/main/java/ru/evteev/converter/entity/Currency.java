package ru.evteev.converter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Lombok
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String parsedId;
    private String numCode;
    private String charCode;
    private Integer nominal;
    private String name;

    public Currency(String parsedId, String numCode, String charCode, Integer nominal, String name) {
        this.parsedId = parsedId;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
    }
}
