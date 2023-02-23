package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.dto.shopping.ProductDto;
import com.tranhuudat.nuclearshop.entity.shopping.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select count(entity.id) from Product entity where (:id is null or entity.id <> :id) " +
            "and (:code is null or entity.code = :code)")
    long countExitsCode(String code, Long id);
    @Query(value = "Select new com.tranhuudat.nuclearshop.dto.shopping.ProductDto(entity)" +
            " from Product entity where " +
            " (:voided is null or entity.voided = :voided) " +
            " and (:voided is null and (entity.voided is null or entity.voided = false )) " +
            " and (:keyword is null or entity.name like concat('%',:keyword,'%') or entity.code like concat('%',:keyword,'%'))",
            countQuery = "Select count(entity.id)" +
            " from Product entity where" +
            " (:voided is null or entity.voided = :voided) " +
            " and (:voided is null and (entity.voided is null or entity.voided = false )) " +
            " and (:keyword is null or entity.name like concat('%',:keyword,'%') or entity.code like concat('%',:keyword,'%'))")
    Page<ProductDto> getPage(String keyword, Boolean voided, Pageable pageable);

    @Query(value = "Select new com.tranhuudat.nuclearshop.dto.shopping.ProductDto(entity)" +
            " from Product entity where (entity.voided is null or entity.voided = false) ")
    List<ProductDto> getAll();
}
