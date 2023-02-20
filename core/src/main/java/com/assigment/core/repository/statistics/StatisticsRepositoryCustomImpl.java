package com.assigment.core.repository.statistics;

import com.assigment.core.model.entity.QStatisticsEntity;
import com.assigment.core.model.entity.QUserEntity;
import com.assigment.core.model.entity.StatisticsEntity;
import com.assigment.core.model.entity.UserEntity;
import com.assigment.core.repository.user.UserPredicate;
import com.assigment.core.repository.user.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Predicate;


import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.assigment.core.utils.QueryUtils.where;

@Repository
public class StatisticsRepositoryCustomImpl implements StatisticsRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    private static final QStatisticsEntity qStatisticsEntity = QStatisticsEntity.statisticsEntity;
    private static final QUserEntity qUserEntity = QUserEntity.userEntity;

    @Override
    public void updateStatisticsByUsername(String videoLink, String commentLink, String userName) {
        Predicate userPredicate = where(UserPredicate.byUserName(userName));
        Optional<UserEntity> userOptional = Optional.ofNullable(queryFactory.select(qUserEntity)
                                                                            .from(qUserEntity)
                                                                            .where(userPredicate)
                                                                            .fetchOne());

        Predicate statisticsPredicate = where(StatisticsPredicate.byUserName(userName));
        Optional<StatisticsEntity> statisticsOptional = Optional.ofNullable(queryFactory.select(qStatisticsEntity)
                                                                                        .from(qStatisticsEntity)
                                                                                        .where(statisticsPredicate)
                                                                                        .fetchOne());

        UserEntity user = userOptional.orElseThrow();
        user.setLastStatisticsUpdate(new Date());

        StatisticsEntity statistics = statisticsOptional.orElseGet(() -> StatisticsEntity.builder().user(user).build());
        statistics.setCommentLink(commentLink);
        statistics.setVideoLink(videoLink);

        em.merge(user);
        em.merge(statistics);
    }

    @Override
    public StatisticsEntity getByUsername(String userName) {
        Predicate where = where(StatisticsPredicate.byUserName(userName));
        return queryFactory.select(qStatisticsEntity)
                            .from(qStatisticsEntity)
                            .where(where)
                            .fetchOne();
    }
}
