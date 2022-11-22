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

    @Query(value = "SELECT c.name as name,c.code as code,c.description as description,c.id as id, c.parentId as parentId, p.name as parentName " +
            " FROM Category c " +
            " left join Category p on p.id = c.parentId " +
            " WHERE 1=1 " +
            " AND (:keyword IS NULL OR c.code LIKE CONCAT('%',:keyword,'%') " +
            " OR c.name LIKE CONCAT('%',:keyword,'%')" +
            " OR p.name LIKE CONCAT('%',:keyword,'%'))",
            countQuery = "SELECT count(c.id) FROM Category c left join Category p on p.id = c.parentId WHERE 1=1 " +
                    " AND (:keyword IS NULL OR c.code LIKE CONCAT('%',:keyword,'%') " +
                    " OR c.name LIKE CONCAT('%',:keyword,'%') " +
                    " OR p.name LIKE CONCAT('%',:keyword,'%'))")
    Page<CategoryResponse> findPage(TypedParameterValue keyword,Pageable pageable);

    @Query(value = "SELECT name as name, id as id FROM Category  where parentId is null")
    List<CategoryResponse> findAllParent();
}
