package com.assigment.core.repository.user;

import com.assigment.core.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<UserEntity, Long>,
                                        QuerydslPredicateExecutor<UserEntity>,
                                        UserRepositoryCustom {

    UserEntity findByUsername(String username);
}
