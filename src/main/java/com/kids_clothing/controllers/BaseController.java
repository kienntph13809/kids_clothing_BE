package com.kids_clothing.controllers;

import com.kids_clothing.userdetails.service.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class BaseController {
    public Long getAuthUID() {
        return getAuthCredentials().map(UserDetailsImpl::getId).orElse(null);
    }

    public String getAuthUsername() {
        return getAuthCredentials().map(UserDetailsImpl::getUsername).orElse(null);
    }

    public Optional<UserDetailsImpl> getAuthCredentials() {
        return Optional.ofNullable((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
