package com.kids_clothing.repository;

import com.kids_clothing.common.EnumRefund;
import com.kids_clothing.common.EnumStatus;
import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BillDao extends JpaRepository<Bill, String> {

    List<Bill> findAllByIdCustomer(Long id);

    List<Bill> findAllByIdCustomerOrderByCreateAtDesc(Long id);

    @Query("select b from Bill b where " +
            " b.createAt >= ?1 and b.createAt < ?2 order by b.createAt desc ")
    List<Bill> findAllByStarAndEnd(Date star, Date end);

    @Query("select b from Bill b where " +
            " b.createAt >= ?1 and b.createAt < ?2  and b.status = ?3 order by b.createAt desc ")
    List<Bill> findAllByStarAndEnd(Date star, Date end, EnumStatus status);

    @Query("select sum (b.total) from Bill b where " +
            " b.createAt >= ?1 and b.createAt < ?2 order by b.createAt desc ")
    Double sumTotalMonthOfYear(Date star, Date end);

    @Query("select sum (b.total) from Bill b where " +
            " b.createAt >= ?1 and b.createAt < ?2 and b.status = ?3 order by b.createAt desc ")
    Double sumTotalMonthOfYear(Date star, Date end, EnumStatus status);

    @Query("select b from Bill b " +
            " where b.idCustomer = ?1 " +
            " group by b.address " +
            " order by b.id desc ")
    List<Bill> getAddress(Long idCustomer);

    @Query("select count (b.id) from Bill b " +
            " where b.updateAts >= ?1 and b.updateAts < ?2 and b.status = ?3")
    Long countBill(Date start, Date end, EnumStatus status);

    @Query("select count (b.id) from Bill b " +
            " where b.createAt >= ?1 and b.createAt < ?2")
    Long countBillNew(Date start, Date end);

    @Query("select count (b.id) from Bill b " +
            " where b.updateAts >= ?1 and b.updateAts < ?2 " +
            " and b.status = ?3 and b.refund = ?4")
    Long countBillRefund(Date start, Date end, EnumStatus status, EnumRefund refund);


    @Query("SELECT b FROM Bill b ORDER BY b.createAt DESC")
    List<Bill> findByDayNewCreate();
    @Query("SELECT b FROM Bill b WHERE b.statusshipping =:statusshipping")
    List<Bill> findBystatusshipping(@Param("statusshipping") String statusshipping);
    @Query(value = "SELECT * FROM Bill  WHERE status = :status",nativeQuery = true)
    List<Bill> findByStatus(@Param("status") String status);

    @Query(value = "SELECT * FROM Bill  WHERE payment  = :payment",nativeQuery = true)
    List<Bill> findBypayment(@Param("payment") Integer payment);

    @Query(value = "SELECT * FROM bill WHERE create_at BETWEEN :date AND :dateto",nativeQuery = true )
    List<Bill> findBydate(@Param("date") String date,@Param("dateto") String dateto);
      @Query(value = "SELECT * FROM bill WHERE create_at BETWEEN :date AND :dateto  and status = :status",nativeQuery = true )
    List<Bill> findBydateAndStatus(@Param("date") String date,@Param("dateto") String dateto,@Param("status") String status);
     @Query(value = "SELECT * FROM bill WHERE create_at BETWEEN :date AND :dateto  and payment = :payment",nativeQuery = true )
    List<Bill> findBydateAndpayment(@Param("date") String date,@Param("dateto") String dateto,@Param("payment") Integer payment);

    @Query(value = "SELECT * FROM bill WHERE create_at BETWEEN :date AND :dateto and payment = :payment and status = :status",nativeQuery = true)
    List<Bill> findbydateAndpaymentAndstatus(@Param("date") String date,@Param("dateto")String dateto,@Param("payment")  Integer payment,@Param("status") String status);

    @Query(value = "SELECT * FROM bill WHERE (TRIM(:payment) is null or payment = :payment) and (TRIM(:status) is null or status =:status)",nativeQuery = true)
    List<Bill> findbypaymentAndstatus(@Param("payment")  Integer payment,@Param("status") String status);


}
