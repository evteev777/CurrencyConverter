package ru.evteev.converter.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

//Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Currency {

    @Id
    private String id;
    private String numCode;
    private String charCode;
    private Integer nominal;
    private String name;

    @Override
    public String toString() {
        return String.format("Currency{id=%7s,\tnumCode=%s,\tcharCode=%s," +
                        "\tnominal=%5d,\tname=%-40s}",
                id, numCode, charCode, nominal, name);
    }
}
