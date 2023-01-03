package com.tranhuudat.nuclearshop.repository.shopping;

import com.tranhuudat.nuclearshop.entity.shopping.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
