package com.ooplab.abbank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "addresses")
public class Address {
    @Id
    private String id;
    private String city;
    private String district;
    private String street;
    private String no;

    public Address(String city, String district, String street, String no) {
        this.city = city;
        this.district = district;
        this.street = street;
        this.no = no;
    }
}
