package com.tranhuudat.nuclearshop.repository;

import com.tranhuudat.nuclearshop.entity.User;
import com.tranhuudat.nuclearshop.response.CurrentUserResponse;
import com.tranhuudat.nuclearshop.response.UserResponse;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u as user from User u where u.id = ?1")
    UserResponse getById(Long id);

    @Query("select u as user from User u")
    List<UserResponse> getAll();

    @Query(value = "select u as user from User u " +
            " where ( :keyword is null or u.username like concat('%',:keyword,'%') or u.person.lastName like concat('%',:keyword,'%') " +
            " or u.person.firstName like concat('%',:keyword,'%') or u.email like concat('%',:keyword,'%') or u.person.email like concat('%',:keyword,'%') )",
            countQuery = "select count(u)  from User u " +
                    "where ( :keyword is null or u.username like concat('%',:keyword,'%') or u.person.lastName like concat('%',:keyword,'%')) " +
                    " or u.person.firstName like concat('%',:keyword,'%') or u.email like concat('%',:keyword,'%') or u.person.email like concat('%',:keyword,'%')")
    Page<UserResponse> getPage(TypedParameterValue keyword, Pageable pageable);

    @Query("select u  as user from User u where u.username = :username")
    CurrentUserResponse getCurrentUser(TypedParameterValue username);

    Optional<User> findByUsernameAndEmail(String username, String email);

    long countUserByEmail(String email);

    long countUserByUsername(String username);

    @Query(value = "select count(u.id) from User u where (:id is null or u.id <> :id) " +
            " and (:username is null or u.username = :username) " +
            " and (:email is null or u.email = :email)")

    long checkExistEmailOrUsername(String username,String email, Long id);
}
