package com.teamapt.transfers.Repos;

import com.teamapt.transfers.Entities.Trxs;
import com.teamapt.transfers.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TrxsRepository extends PagingAndSortingRepository<Trxs, Long>, QuerydslPredicateExecutor<Trxs> {

    @Query("select t from Trxs t where t.user = user order by timestamp desc")
    List<Trxs> findTrxsByUser(User user);

}

