package com.ooplab.abbank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "salaries")
public class Salary {
    @Id
    private String id;
    @Field(targetType = DECIMAL128)
    private BigDecimal value;
    private Binary salaryCertificate;

    public Salary(BigDecimal value, Binary salaryCertificate) {
        this.value = value;
        this.salaryCertificate = salaryCertificate;
    }
}
