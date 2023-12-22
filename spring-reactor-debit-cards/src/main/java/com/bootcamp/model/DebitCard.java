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
@Document(collection = "debit-card")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebitCard {
    @Id
    @EqualsAndHashCode.Include
    @Field(name="debitCardId")
    private String debitCardId;

    @Field
    private String clientId;

    @Field
    private String principalAccountId;

    @Field
    private LocalDate issueDate;

    @Field
    private LocalDate expiryDate;

    @Field
    private String status;

    @Field
    private String currentBalance;
}
