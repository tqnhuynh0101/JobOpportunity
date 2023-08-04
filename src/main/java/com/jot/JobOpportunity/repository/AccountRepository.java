package com.jot.JobOpportunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query(value = "SELECT a FROM Account a WHERE a.username = :username")
	Account getAccountByUsername(@Param("username") String username);

	@Query(value = "SELECT a FROM Account a WHERE a.id = :id")
	Account getAccountById(@Param("id") Long id);
	
	@Query(value = "SELECT COUNT(id) FROM account WHERE username REGEXP :usernameRegix or username = :username", nativeQuery = true)
	Long countByUsername(@Param("usernameRegix") String usernameRegix, @Param("username") String username);
	
	@Modifying
	@Query(value = "UPDATE Account a SET "
			+ "a.age = :age, "
			+ "a.email = :email, "
			+ "a.tel = :tel, "
			+ "a.gender = :gender "
			+ "WHERE a.id = :id")
	void updateInformation(@Param("age") int age, @Param("tel") String tel, @Param("email") String email, @Param("gender") Boolean gender, @Param("id") Long id);
}
