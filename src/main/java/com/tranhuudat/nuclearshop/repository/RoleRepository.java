package com.tranhuudat.nuclearshop.repository;

import com.tranhuudat.nuclearshop.entity.Role;
import com.tranhuudat.nuclearshop.response.RoleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT entity FROM Role entity ")
    List<RoleResponse> getAllRoles();
}
