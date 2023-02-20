package com.assigment.core.repository.user;

import com.assigment.core.model.entity.UserEntity;

import java.util.Date;
import java.util.List;

public interface UserRepositoryCustom {
    List<UserEntity> getUsersForStatisticsUpdate(Date date);
}
