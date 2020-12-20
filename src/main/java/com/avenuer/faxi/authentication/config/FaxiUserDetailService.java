package com.avenuer.faxi.authentication.config;

import com.avenuer.faxi.authentication.doa.AuthCredentialDoa;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FaxiUserDetailService implements UserDetailsService {

    @Autowired
    private AuthCredentialDoa authDoa;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        val credential = authDoa.findOneByEmail(username);

        if (credential.isPresent()) {
            return new FaxiUserDetails(credential.get());
        }

        throw new UsernameNotFoundException("Auth Credential Not Found, please register credential before Login");
    }
}
