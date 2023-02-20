package com.assigment.consumer.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateStatisticsMessage implements Serializable {
    private String username;
    private String countryCode;
}
