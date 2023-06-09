package com.kids_clothing.userdetails.service;

import com.kids_clothing.entity.Account;
import com.kids_clothing.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountDao accountDao;

    //    /**
//     * Locates the user based on the username. In the actual implementation, the search
//     * may possibly be case sensitive, or case insensitive depending on how the
//     * implementation instance is configured. In this case, the <code>UserDetails</code>
//     * object that comes back may have a username that is of a different case than what
//     * was actually requested..
//     *
//     * @param username the username identifying the user whose data is required.
//     * @return a fully populated user record (never <code>null</code>)
//     * @throws UsernameNotFoundException if the user could not be found or the user has no
//     *                                   GrantedAuthority
//     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found With Username: " + username));

        return UserDetailsImpl.build(account);
    }
}
