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
@Document(collection = "clients")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @Id
    @EqualsAndHashCode.Include
    @Field(name="clientId")
    private String clientId;

    @Field
    private String firstName;

    @Field
    private String lastName;

    @Field
    private LocalDate birthDate;

    @Field
    private String type;

    @Field
    private String profile;
}
