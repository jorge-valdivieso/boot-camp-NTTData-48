package com.bootcamp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "bankAccounts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccounts {

    @Id
    @EqualsAndHashCode.Include
    @Field(name="accountId")
    private String accountId;

    @Field
    private String type;

    @Field
    private String maintenanceFee;

    @Field
    private String monthlyMovementLimit;

    @Field
    private LocalDate specificMovementDay;

    @Field
    private String clientId;
}