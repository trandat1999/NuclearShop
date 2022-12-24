package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.entity.shopping.Product;
import com.tranhuudat.nuclearshop.response.shopping.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("Select distinct entity from Product entity where " +
            " (:voided is null or entity.voided = :voided) " +
            " and (:voided is null and (entity.voided is null or entity.voided = false )) " +
            " and (:keyword is null or entity.name like concat('%',:keyword,'%') or entity.code like concat('%',:keyword,'%'))")
    Page<ProductResponse> getPage(String keyword,Boolean voided, Pageable pageable);
}
