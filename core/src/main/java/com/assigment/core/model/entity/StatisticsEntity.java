package com.assigment.core.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsEntity {

    @Id
    @GeneratedValue
    private long id;

    private String videoLink;

    private String commentLink;

    @OneToOne
    private UserEntity user;

}
