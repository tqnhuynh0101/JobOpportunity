package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.entity.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, Long> {
    @Query(value = "SELECT pr FROM Provinces pr")
    public List<Provinces> getAllProvinces();

    @Query(value = "SELECT s.fullName FROM Provinces s WHERE s.code = :code")
    public String getName(@Param("code") String code);
}
