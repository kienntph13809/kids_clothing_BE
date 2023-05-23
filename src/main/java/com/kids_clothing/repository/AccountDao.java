package com.kids_clothing.repository;

import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Customer;
import com.kids_clothing.entity.Event;
import com.kids_clothing.model.request.CustomerRequestt;
import com.kids_clothing.model.request.requestAccount;
import com.kids_clothing.model.response.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);
    @Query("SELECT ac FROM Account ac WHERE ac.isDelete=false ")
    List<Account> findAllByIsDeleteFalse();
    @Query("SELECT a.phone as phone ,c.fullname as fullName FROM Customer c JOIN c.account a WHERE a.phone LIKE :phone%")
    List<CustomerResponse> findByPhone(@Param("phone") String phone);

    @Query("SELECT ac FROM Account ac WHERE ac.phone LIKE %:name%")
    List<CustomerRequestt> findByNameLike(@Param("name") String name);


    @Query(value = "SELECT account.id,account.email, account.password, account.phone, account.username, role.name AS role " +
            "FROM account " +
            "JOIN authority ON account.id = authority.idaccount " +
            "JOIN role ON authority.idrole = role.id WHERE role.id <> 'customer'",nativeQuery = true)
    List<requestAccount> findAlllAdmin();



    @Query(value = "SELECT  account.id, account.email, account.password, account.phone, account.username,customer.address FROM account JOIN customer on account.id = customer.idaccount",nativeQuery = true)
    List<CustomerRequestt> findAllCustomer();


}
