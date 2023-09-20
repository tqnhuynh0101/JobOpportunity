package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.dto.post.PostAdminItfDto;
import com.jot.JobOpportunity.dto.post.PostAutoSearchItfDto;
import com.jot.JobOpportunity.dto.post.PostDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.dto.post.PostItfDto;
import com.jot.JobOpportunity.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT id as id, title as title, content as content, quantity as quantity,"
            + " age_Max as ageMax, age_Min as ageMin, gender as gender, experience as experience,"
            + " type as type, format as format, position as position, requirement as requirement,"
            + " benafit as benafit, duty as duty, salary as salary, salary_Min as salaryMin,"
            + " salary_Max as salaryMax, unit as unit, company as company, tel as tel, email as email,"
            + " expired_Date as expiredDate, flag as flag, poster_Id as posterId, address as address," +
            " ward_id as wardId, district_id as districtId, province_id as provinceId, image as image FROM POST WHERE is_del = 0 AND flag = 1" +
            " ORDER BY id DESC", nativeQuery = true)
    public Page<PostItfDto> getAllPost(Pageable pageable);

    @Query(value = "SELECT id as id, title as title, content as content, quantity as quantity,"
            + " age_Max as ageMax, age_Min as ageMin, gender as gender, experience as experience,"
            + " type as type, format as format, position as position, requirement as requirement,"
            + " benafit as benafit, duty as duty, salary as salary, salary_Min as salaryMin,"
            + " salary_Max as salaryMax, unit as unit, company as company, tel as tel, email as email,"
            + " expired_Date as expiredDate, flag as flag, poster_Id as posterId," +
            " address as address, ward_id as wardId, district_id as districtId, province_id as provinceId, image as image FROM POST WHERE is_del = 0 AND flag = 1 ORDER BY create_time DESC\n" +
            "LIMIT 20", nativeQuery = true)
    public List<PostItfDto> getNewestPosts();

    @Query(value = "SELECT p.id as id, p.age_Max as ageMax, p.age_Min as ageMin, p.gender as gender,\n" +
            "                        p.position as position, p.salary_Min as salaryMin, p.salary_Max as salaryMax, p.province_id as provinceId,\n" +
            "                        p.title as title, p.image as image, p.content as content, p.unit as unit\n" +
            "                        FROM POST p\n" +
            "                        WHERE p.is_del = 0 AND p.flag = 1 AND p.expired_date > :expired_date and\n" +
            "                        p.id not in (select p.id from post p inner join effect e on p.id = e.post_id where account_id = :accountId)\n" +
            "                        order by create_time DESC", nativeQuery = true)
    public List<PostAutoSearchItfDto> getPostForAutoSearch(@Param("accountId") Long accountId, @Param("expired_date") String expired_date);

    @Query(value = "SELECT distinct p.id as id, p.title as title, p.content as content, p.quantity as quantity,\n" +
            "            p.age_Max as ageMax, p.age_Min as ageMin, p.gender as gender, p.experience as experience,\n" +
            "            p.type as type, p.format as format, p.position as position, p.requirement as requirement,\n" +
            "            p.benafit as benafit, p.duty as duty, p.salary as salary, p.salary_Min as salaryMin,\n" +
            "            p.salary_Max as salaryMax, p.unit as unit, p.company as company, p.tel as tel, p.email as email,\n" +
            "            p.expired_Date as expiredDate, p.flag as flag, p.poster_Id as posterId,\n" +
            "            p.address as address, p.ward_id as wardId, p.district_id as districtId, p.province_id as provinceId, p.image as image, p.create_time as createTime \n" +
            "            FROM (POST p\n" +
            "            inner join skill_post sp\n" +
            "            on p.id = sp.post_id) \n" +
            "            inner join skill_employee se\n" +
            "            on se.employee_id = :id and se.skill = sp.skill\n" +
            "            WHERE p.is_del = 0 AND p.flag = 1 AND (:age between p.age_Min and p.age_Max)\n" +
            "            ORDER BY p.create_time DESC\n" +
            "            LIMIT 20", nativeQuery = true)
    public List<PostItfDto> getSuggest(@Param("id") Long id, @Param("age") Integer age);

    @Query(value = "SELECT distinct p.id as id, p.title as title, p.content as content, p.quantity as quantity," +
            "p.age_Max as ageMax, p.age_Min as ageMin, p.gender as gender, p.experience as experience," +
            "p.type as type, p.format as format, p.position as position, p.requirement as requirement," +
            "p.benafit as benafit, p.duty as duty, p.salary as salary, p.salary_Min as salaryMin," +
            "p.salary_Max as salaryMax, p.unit as unit, p.company as company, p.tel as tel, p.email as email," +
            "p.expired_Date as expiredDate, p.flag as flag, p.poster_Id as posterId, p.address as address," +
            "p.ward_id as wardId, p.district_id as districtId, p.province_id as provinceId, p.image as image " +
            "FROM POST p " +
            "LEFT JOIN skill_post s " +
            "ON p.id = s.post_id "+
            "WHERE (p.ward_Id = :wardId or p.district_id = :districtId or p.province_id = :provinceId) " +
            "AND (p.position like :position or s.skill like :skill or p.content like :content or p.company like :company)", nativeQuery = true)
    public List<PostItfDto> searchPost(@Param("wardId") String wardId, @Param("districtId") String districtId
            ,@Param("provinceId") String provinceId, @Param("position") String position, @Param("skill") String skill, @Param("content") String content,@Param("company") String company);

    @Query(value = "SELECT distinct p.id as id, p.title as title, p.content as content, p.quantity as quantity," +
            "p.age_Max as ageMax, p.age_Min as ageMin, p.gender as gender, p.experience as experience," +
            "p.type as type, p.format as format, p.position as position, p.requirement as requirement," +
            "p.benafit as benafit, p.duty as duty, p.salary as salary, p.salary_Min as salaryMin," +
            "p.salary_Max as salaryMax, p.unit as unit, p.company as company, p.tel as tel, p.email as email," +
            "p.expired_Date as expiredDate, p.flag as flag, p.poster_Id as posterId, p.address as address," +
            "p.ward_id as wardId, p.district_id as districtId, p.province_id as provinceId, p.image as image " +
            "FROM POST p " +
            "LEFT JOIN skill_post s " +
            "ON p.id = s.post_id "+
            "WHERE" +
            "(p.position like :position or s.skill like :skill or p.content like :content or p.company like :company)", nativeQuery = true)
    public List<PostItfDto> searchByText(@Param("position") String position, @Param("skill") String skill, @Param("content") String content, @Param("company") String company);

    @Query(value = "SELECT id as id, title as title, content as content, quantity as quantity,"
            + " age_Max as ageMax, age_Min as ageMin, gender as gender, experience as experience,"
            + " type as type, format as format, position as position, requirement as requirement,"
            + " benafit as benafit, duty as duty, salary as salary, salary_Min as salaryMin,"
            + " salary_Max as salaryMax, unit as unit, company as company, tel as tel, email as email,"
            + " expired_Date as expiredDate, flag as flag," +
            " poster_Id as posterId, address as address, ward_id as wardId, district_id as districtId, " +
            "province_id as provinceId, image as image, is_Del as isDel, create_by as createBy, create_time as createTime" +
            " FROM POST ORDER BY create_time DESC", nativeQuery = true)
    public Page<PostAdminItfDto> adminPost(Pageable pageable);

    @Query(value = "SELECT id as id, title as title, content as content, quantity as quantity,"
            + " age_Max as ageMax, age_Min as ageMin, gender as gender, experience as experience,"
            + " type as type, format as format, position as position, requirement as requirement,"
            + " benafit as benafit, duty as duty, salary as salary, salary_Min as salaryMin,"
            + " salary_Max as salaryMax, unit as unit, company as company, tel as tel, email as email,"
            + " expired_Date as expiredDate, flag as flag," +
            " poster_Id as posterId, address as address, ward_id as wardId, district_id as districtId, " +
            "province_id as provinceId, image as image, is_Del as isDel, create_by as createBy, create_time as createTime" +
            " FROM POST WHERE poster_id = :posterId ORDER BY create_time DESC", nativeQuery = true)
    public Page<PostAdminItfDto> seekerPost(Pageable pageable, @Param("posterId") Long posterId);

    @Modifying
    @Query(value = "UPDATE Post p SET p.isDel = true WHERE p.id = :id")
    public void deletePostById(@Param("id") Long id);

    @Query(value = "SELECT p FROM Post p WHERE p.isDel = false AND p.id = :id")
    public Post getPostById(@Param("id") Long id);

    @Query(value = "SELECT * FROM post WHERE (STR_TO_DATE(expired_date, '%d%m%Y') - CURDATE() =1)", nativeQuery = true)
    public List<Post> getPostNearingDay1();

    @Query(value = "SELECT * FROM post WHERE (STR_TO_DATE(expired_date, '%d%m%Y') - CURDATE() =2)", nativeQuery = true)
    public List<Post> getPostNearingDay2();

    @Query(value = "SELECT * FROM post WHERE (STR_TO_DATE(expired_date, '%d%m%Y') - CURDATE() =3)", nativeQuery = true)
    public List<Post> getPostNearingDay3();

    @Modifying
    @Query(value = "UPDATE post SET flag = true WHERE STR_TO_DATE(expired_date, '%d%m%Y') <= CURDATE()", nativeQuery = true)
    public void autoDestroyPost();

//	@Modifying
//	@Query(value = "UPDATE Post p SET  p.flag = :flag, p.isDel = :isDel, p.expiredDate = :expiredDate	WHERE p.id = :id")
//	public Post updateAdminPost(@Param("id") Long id, @Param("flag") Boolean flag, @Param("isDel") Boolean isDel,  @Param("expiredDate") String expiredDate);
}
