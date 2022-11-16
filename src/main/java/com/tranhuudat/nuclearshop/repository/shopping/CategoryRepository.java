package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.entity.shopping.Category;
import com.tranhuudat.nuclearshop.response.shopping.CategoryResponse;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT count(id) FROM Category WHERE code = :code AND (:id IS NULL OR id <> :id)")
    long checkCode(TypedParameterValue code, TypedParameterValue id);

    @Query("SELECT name,code,description,children FROM Category WHERE 1=1 " +
            " AND (:code IS NULL OR code LIKE CONCAT('%',:code,'%')) " +
            " AND (:name IS NULL OR name LIKE CONCAT('%',:name,'%')) ")
    Page<CategoryResponse> findPage(TypedParameterValue code, TypedParameterValue name,Pageable pageable);
}
