package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.dto.shopping.PublisherDto;
import com.tranhuudat.nuclearshop.dto.shopping.WarehouseDto;
import com.tranhuudat.nuclearshop.entity.shopping.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    @Query(value = "select count(entity.id) from Warehouse entity where (:id is null or entity.id <> :id) " +
            "and (:code is null or entity.code = :code)")
    long countExitsCode(String code, Long id);

    @Query(value="select new com.tranhuudat.nuclearshop.dto.shopping.WarehouseDto(entity) from Warehouse entity " +
            "where entity.voided is null or entity.voided = false")
    List<WarehouseDto> getAll();

    @Query(value = "select new com.tranhuudat.nuclearshop.dto.shopping.WarehouseDto(entity) from Warehouse entity " +
            "where (:keyword is null or :keyword = '' or entity.name like concat('%',:keyword,'%') or entity.code like concat('%',:keyword,'%')) " +
            "and ((entity.voided is null or entity.voided = false)) " +
            "and (:provinceId is null or :provinceId = entity.administrativeUnit.parent.parent.id) " +
            "and (:districtId is null or :districtId = entity.administrativeUnit.parent.id) " +
            "and (:communeId is null or :communeId = entity.administrativeUnit.id)",
            countQuery = "select count(entity.id) from Warehouse entity " +
                    "where (:keyword is null or :keyword = '' or entity.name like concat('%',:keyword,'%') or entity.code like concat('%',:keyword,'%')) " +
                    "and ((entity.voided is null or entity.voided = false)) " +
                    "and (:provinceId is null or :provinceId = entity.administrativeUnit.parent.parent.id) " +
                    "and (:districtId is null or :districtId = entity.administrativeUnit.parent.id) " +
                    "and (:communeId is null or :communeId = entity.administrativeUnit.id)")
    Page<WarehouseDto> getPage(String keyword, Long provinceId, Long districtId, Long communeId, Pageable pageable);
}
