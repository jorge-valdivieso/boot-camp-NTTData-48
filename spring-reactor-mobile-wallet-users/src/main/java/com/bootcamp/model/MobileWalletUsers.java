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
@Document(collection = "mobile-wallet-users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobileWalletUsers {
    @Id
    @EqualsAndHashCode.Include
    @Field(name="yankUserId")
    private String yankUserId;

    @Field
    private String identificationDocument;

    @Field
    private String mobileNumber;

    @Field
    private String imei;

    @Field
    private String emailAddress;

    @Field
    private String status;

    @Field
    private LocalDate creationDate;
}
