package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.dto.shopping.OrderImportDto;
import com.tranhuudat.nuclearshop.entity.shopping.OrderImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Repository
public interface OrderImportRepository extends JpaRepository<OrderImport,Long> {
    @Query(value = "select new com.tranhuudat.nuclearshop.dto.shopping.OrderImportDto(entity) from OrderImport entity " +
            " where (entity.voided = false or entity.voided is null) " +
            " and (:fromDate is null or entity.orderDate >=:fromDate) " +
            " and (:toDate is null or entity.orderDate >=:toDate) " +
            " and (:keyword is null or :keyword = '' or entity.staffOrder like concat('%',:keyword,'%') " +
            "   or entity.staffFinished like concat('%',:keyword,'%')) " +
            " and (:publisherId is null or entity.publisher.id = :publisherId) " +
            " and (:warehouseId is null or entity.warehouse.id = :warehouseId) " ,
    countQuery = "select count(entity.id) from OrderImport entity " +
            " where (entity.voided = false or entity.voided is null) " +
            " and (:fromDate is null or entity.orderDate >=:fromDate) " +
            " and (:toDate is null or entity.orderDate >=:toDate) " +
            " and (:keyword is null or :keyword = '' or entity.staffOrder like concat('%',:keyword,'%') " +
            "   or entity.staffFinished like concat('%',:keyword,'%')) " +
            " and (:publisherId is null or entity.publisher.id = :publisherId) " +
            " and (:warehouseId is null or entity.warehouse.id = :warehouseId) ")
    Page<OrderImportDto> getPage(String keyword, Date fromDate, Date toDate, Long publisherId, Long warehouseId, Pageable pageable);
}
