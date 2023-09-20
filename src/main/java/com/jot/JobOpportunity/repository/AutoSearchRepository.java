package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.dto.autosearch.AutoSearchItfDto;
import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunItfDto;
import com.jot.JobOpportunity.entity.AutoSearch;
import com.jot.JobOpportunity.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoSearchRepository extends JpaRepository<AutoSearch, Long> {

    @Query(value = "SELECT a.id as id, a.account_id as accountId, a.age as age,\n" +
            " a.flag as flag, a.gender as gender, p.code as province, a.salary as salary\n" +
            " FROM auto_search a, provinces p WHERE a.province_code = p.code AND a.account_id = :accountId", nativeQuery = true)
    AutoSearchItfDto selectAutoSearchByCurrentUser(@Param("accountId") Long accountId);

    @Query(value = "SELECT a.account_id as accountId, acc.age as age, acc.gender as gender, a.province_code as provinceCode, a.salary as salary,\n" +
            "cv.pos as pos, acc.email as email\n" +
            "FROM (auto_search a INNER JOIN account acc ON a.account_id = acc.id) INNER JOIN CV cv ON a.account_id = cv.employee_id\n" +
            "WHERE flag = 1 ORDER BY cv.create_time DESC LIMIT 1", nativeQuery = true)
    public List<AutoSearchRunItfDto> getAllAutoSearch();

    @Query(value = "SELECT a FROM AutoSearch a WHERE a.accountId = :id")
    AutoSearch selectAutoSearchByIdToUpdate(@Param("id") Long id);
}
