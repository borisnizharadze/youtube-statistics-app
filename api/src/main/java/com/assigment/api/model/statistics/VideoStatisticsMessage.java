package com.assigment.api.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoStatisticsMessage implements Serializable {
    private String username;
    private String videoLink;
    private String commentLink;
    private String countryCode;
}
