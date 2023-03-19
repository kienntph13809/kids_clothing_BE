package com.kids_clothing.dao;

import com.kids_clothing.entity.Mamipay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MamiPayDao extends JpaRepository<Mamipay, Long> {
    @Query("SELECT m FROM Mamipay m WHERE m.customer.id =?1")
    Mamipay BillByCustomer(Long id);

    Optional<Mamipay> findByIdcustomer(Long idcustomer);

    @Query("SELECT m FROM Mamipay m WHERE m.idcustomer =?1")
    Mamipay MamipayIdCt(long id);

    List<Mamipay> findAllByIsDeleteFalse();
}
