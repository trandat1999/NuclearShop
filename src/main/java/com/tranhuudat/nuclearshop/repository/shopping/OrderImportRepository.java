package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.entity.shopping.OrderImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Repository
public interface OrderImportRepository extends JpaRepository<OrderImport,Long> {
}
