package com.kids_clothing.seeder;

import com.kids_clothing.common.ERole;
import com.kids_clothing.entity.Role;
import com.kids_clothing.repository.RoleDao;

public class RoleSeeder implements Seeder {

    @Override
    public void seed() {

        RoleDao roleDao = BeanUtil.getBean(RoleDao.class);

        if (!roleDao.findByName(ERole.ROLE_ADMIN.toString()).isPresent()) {
            Role role = new Role();
            role.setName(ERole.ROLE_ADMIN.toString());
            role.setId("admin");
            roleDao.save(role);
        }
        if (!roleDao.findByName(ERole.ROLE_STAFF.toString()).isPresent()) {
            Role role = new Role();
            role.setName(ERole.ROLE_STAFF.toString());
            role.setId("staff");
            roleDao.save(role);
        }
        if (!roleDao.findByName(ERole.ROLE_CUSTOMER.toString()).isPresent()) {
            Role role = new Role();
            role.setName(ERole.ROLE_CUSTOMER.toString());
            role.setId("customer");
            roleDao.save(role);
        }
    }
}
