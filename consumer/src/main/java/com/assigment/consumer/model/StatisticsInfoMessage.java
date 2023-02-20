package com.assigment.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsInfoMessage implements Serializable {
    private String username;
    private String videoLink;
    private String commentLink;
    private String countryCode;
}
