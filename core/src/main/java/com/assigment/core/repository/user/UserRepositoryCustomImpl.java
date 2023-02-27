package com.assigment.core.repository.user;

import com.assigment.core.model.entity.QUserEntity;
import com.assigment.core.model.entity.UserEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.assigment.core.repository.user.UserPredicate.byOverdueUpdate;
import static com.assigment.core.utils.QueryUtils.where;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    private final QUserEntity qUserEntity = QUserEntity.userEntity;

    @Override
    public List<UserEntity> getUsersForStatisticsUpdate() {
        Predicate where = where(byOverdueUpdate());

        return queryFactory.select(qUserEntity).from(qUserEntity).where(where).fetch();
    }
}
