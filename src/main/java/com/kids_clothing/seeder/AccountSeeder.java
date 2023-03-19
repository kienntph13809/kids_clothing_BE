package com.kids_clothing.seeder;

import com.kids_clothing.common.ERole;
import com.kids_clothing.dao.AccountDao;
import com.kids_clothing.dao.AuthorityDao;
import com.kids_clothing.dao.CustomerDao;
import com.kids_clothing.dao.RoleDao;
import com.kids_clothing.entity.Account;
import com.kids_clothing.entity.Authority;
import com.kids_clothing.entity.Customer;
import com.kids_clothing.entity.Role;

public class AccountSeeder implements Seeder {

    @Override
    public void seed() {
        RoleDao roleDao = BeanUtil.getBean(RoleDao.class);
        AccountDao accountDao = BeanUtil.getBean(AccountDao.class);
        CustomerDao customerDao = BeanUtil.getBean(CustomerDao.class);
        AuthorityDao authorityDao = BeanUtil.getBean(AuthorityDao.class);

        if (accountDao.findByUsername("admin").isEmpty()) {
            Account account = new Account();
            account.setPassword("$2a$12$xV/HkPLgQhhC3HTA5jcgse6Ruu2OPaBcq.A.ckpbfdBGz89troxx.");
            account.setUsername("admin");

            Role role = roleDao.findByName(ERole.ROLE_ADMIN.toString()).get();
            Authority authority = new Authority();
            authority.setRole(role);
            authority.setAccount(account);

            accountDao.save(account);
            authorityDao.save(authority);

        }
        if (accountDao.findByUsername("staff").isEmpty()) {
            Account account = new Account();
            account.setPassword("$2a$12$xV/HkPLgQhhC3HTA5jcgse6Ruu2OPaBcq.A.ckpbfdBGz89troxx.");
            account.setUsername("staff");

            Role role = roleDao.findByName(ERole.ROLE_STAFF.toString()).get();
            Authority authority = new Authority();
            authority.setRole(role);
            authority.setAccount(account);

            accountDao.save(account);
            authorityDao.save(authority);

        }
        if (accountDao.findByUsername("customer").isEmpty()) {
            Account account = new Account();
            account.setPassword("$2a$12$xV/HkPLgQhhC3HTA5jcgse6Ruu2OPaBcq.A.ckpbfdBGz89troxx.");
            account.setUsername("customer");

            Role role = roleDao.findByName(ERole.ROLE_CUSTOMER.toString()).get();
            Authority authority = new Authority();
            authority.setRole(role);
            authority.setAccount(account);

            accountDao.save(account);
            authorityDao.save(authority);

            Customer customer = new Customer();
            customer.setIdaccount(account.getId());
            customer.setFullname("Khách hàng");

            customerDao.save(customer);

        }
    }
}
