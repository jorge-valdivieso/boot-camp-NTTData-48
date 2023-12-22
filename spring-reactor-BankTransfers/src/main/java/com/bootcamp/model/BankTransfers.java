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
@Document(collection = "bankTransfers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankTransfers {

    @Id
    @EqualsAndHashCode.Include
    @Field(name="transferId")
    private String transferId ;

    @Field
    private String sourceAccountId ;

    @Field
    private String destinationAccountId ;

    @Field
    private String amount;

    @Field
    private LocalDate transferDate;

    @Field
    private String commission ;
}
