package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.dto.shopping.OrderImportDto;
import com.tranhuudat.nuclearshop.entity.shopping.OrderImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Repository
public interface OrderImportRepository extends JpaRepository<OrderImport,Long> {
//    Page<OrderImportDto> getPage(String keyword, Date fromDate, Date toDate, Long publisherId, Long warehouseId, Pageable pageable);
}
