package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.dto.cv.CvDetailItfDto;
import com.jot.JobOpportunity.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
	
    @Query(value = "SELECT cv.id as id, cv.pos as pos, a.name as name, a.email as email, a.tel as tel, a.gender as gender, "
    		+ " a.age as age, cv.intro as intro, cv.address as address, cv.avatar as avatar, a.id as employeeId, cv.education as education, cv.awards as awards, cv.work_experience as workExperience, cv.certifications as certifications ,cv.interest as interest "
    		+ " FROM Cv cv INNER JOIN Account a ON cv.employee_id = a.id "
    		+ " WHERE cv.employee_id = :employeeId ORDER BY cv.create_time DESC LIMIT 1 ", nativeQuery = true)
    public CvDetailItfDto selectCvByEmployeId(@Param("employeeId") Long employeeId);
    
    
    @Query(value = "SELECT * FROM Cv c WHERE c.employee_id = :employeeId ORDER BY c.create_time DESC LIMIT 1", nativeQuery = true)
    public Cv selectCvByEmployeIdToUpdate(@Param("employeeId") Long employeeId);

	@Query(value = "SELECT * FROM Cv c WHERE c.employee_id = :employeeId ORDER BY c.create_time DESC LIMIT 1", nativeQuery = true)
	public Cv getById(@Param("employeeId") Long employeeId);

	@Query(value = "SELECT cv.id as id, cv.pos as pos, a.name as name, a.email as email, a.tel as tel, a.gender as gender, "
			+ " a.age as age, cv.intro as intro, cv.address as address, cv.avatar as avatar, a.id as employeeId, cv.education as education, cv.awards as awards, cv.work_experience as workExperience, cv.certifications as certifications ,cv.interest as interest "
			+ " FROM Cv cv INNER JOIN Account a ON cv.employee_id = a.id "
			+ " WHERE cv.uuid = :uuid", nativeQuery = true)
	public CvDetailItfDto getByUuid(@Param("uuid") String uuid);

//	@Modifying
//    @Query(value = "UPDATE Cv c SET c.intro = :intro, "
//    		+ " c.education = :education, "
//    		+ " c.address = :address, "
//    		+ " c.workExperience = :workExperience, "
//    		+ " c.certifications = :certifications, "
//    		+ " c.awards = :awards, "
//    		+ " c.interest = :interest, "
//    		+ " c.avatar = :avatar "
//    		+ " WHERE c.employeeId = :employeeId ")
//	public void update(@Param("intro") String intro, @Param("education") String education, @Param("address") String address, @Param("workExperience") String workExperience, @Param("certifications") String certifications,
//			@Param("awards") String awards, @Param("interest") String interest, @Param("avatar") String avatar, @Param("employeeId") Long employeeId);
}
