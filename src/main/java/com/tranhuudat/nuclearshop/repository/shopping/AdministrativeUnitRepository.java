package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.entity.shopping.AdministrativeUnit;
import com.tranhuudat.nuclearshop.response.shopping.AdministrativeUnitResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrativeUnitRepository extends JpaRepository<AdministrativeUnit,Long> {
    AdministrativeUnit findByCode(String code);

    @Query(value = "SELECT name as name, code as code, id as id FROM AdministrativeUnit WHERE parent is null")
    List<AdministrativeUnitResponse> findAllParent();

    @Query(value = "SELECT name as name, code as code, id as id FROM AdministrativeUnit WHERE parent.id = :id")
    List<AdministrativeUnitResponse> findAllByParent(long id);

    @Query(value = "SELECT name as name, code as code, id as id FROM AdministrativeUnit " +
            "WHERE parent is null " +
            "AND (:keyword is null OR name LIKE concat('%',:keyword,'%') OR code LIKE concat('%',:keyword,'%'))",
    countQuery = "SELECT count(id) FROM AdministrativeUnit " +
            "WHERE parent is null " +
            "AND (:keyword is null OR name LIKE concat('%',:keyword,'%') OR code LIKE concat('%',:keyword,'%'))")
    Page<AdministrativeUnitResponse> getPageParent(String keyword,Pageable pageable);
}
