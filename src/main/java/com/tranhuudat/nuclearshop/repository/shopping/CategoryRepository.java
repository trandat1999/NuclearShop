package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.entity.shopping.Category;
import com.tranhuudat.nuclearshop.response.shopping.CategoryResponse;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT count(id) FROM Category WHERE code = :code AND (:id IS NULL OR id <> :id)")
    long checkCode(TypedParameterValue code, TypedParameterValue id);

    @Query(value = "SELECT name as name,code as code,description as description,id as id, parentId as parentId FROM Category WHERE 1=1 " +
            " AND (:keyword IS NULL OR code LIKE CONCAT('%',:keyword,'%') OR name LIKE CONCAT('%',:keyword,'%'))",
            countQuery = "SELECT count(id) FROM Category WHERE 1=1 " +
                    " AND (:keyword IS NULL OR code LIKE CONCAT('%',:keyword,'%') OR name LIKE CONCAT('%',:keyword,'%'))")
    Page<CategoryResponse> findPage(TypedParameterValue keyword,Pageable pageable);

    @Query(value = "SELECT name as name, id as id FROM Category  where parentId is null")
    List<CategoryResponse> findAllParent();
}
