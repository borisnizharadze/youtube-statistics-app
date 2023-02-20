package com.assigment.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPropsRequest {

    @NotBlank
    private String countryCode;

    @Min(1)
    @Max(60)
    private Integer intervalMins;

}
