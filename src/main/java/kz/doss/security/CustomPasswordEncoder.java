package kz.doss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class CustomPasswordEncoder {

    /**
     * Applies bcrypt strong hashing encryption
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * In-memory user details service
     *
     * @param encoder PasswordEncoder
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(new User(
                "assan", encoder.encode("password"),
                Collections.singletonList(new SimpleGrantedAuthority("USER"))));
        usersList.add(new User(
                "zaure", encoder.encode("password"),
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))));
        return new InMemoryUserDetailsManager(usersList);
    }
}
