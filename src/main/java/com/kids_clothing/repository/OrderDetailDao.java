package com.kids_clothing.repository;

import com.kids_clothing.common.EnumStatus;
import com.kids_clothing.entity.Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDetailDao extends JpaRepository<Orderdetail, Long> {
    @Query("SELECT o FROM Orderdetail o WHERE o.bill.customer.id =?1")
    List<Orderdetail> AllByCustomer(Long id);

    @Query("select o from Orderdetail o where " +
            " o.bill.isDelete = false and o.idbill = ?1")
    List<Orderdetail> getListOrderDetail(String id);

    @Query("select o.productDetail.product.name as name, sum(o.quantitydetail) as qty,sum(o.intomoney) as intomoney " +
            " from Orderdetail o " +
            " where o.createAt >= ?1 and o.createAt <= ?2 " +
            " group by name " +
            " order by qty desc ")
    List<Object[]> getSumQtyProduct(Date start, Date end);

    @Query("select o.productDetail.product.name as name, sum(o.quantitydetail) as qty,sum(o.intomoney) as intomoney " +
            " from Orderdetail o " +
            " where o.createAt >= ?1 and o.createAt <= ?2 " +
            " and o.bill.status = ?3" +
            " group by name " +
            " order by qty desc ")
    List<Object[]> getSumQtyProduct(Date start, Date end, EnumStatus status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Orderdetail u set u.quantitydetail = :quantityNew where u.idbill = :idBill and  u.id =:idOrder")
    void updateQuantitydetailByBillId(@Param("quantityNew") Long quantityNew, @Param("idBill") String idBill,@Param("idOrder") Long idOrder);

}
