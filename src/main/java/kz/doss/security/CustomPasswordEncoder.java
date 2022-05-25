package kz.doss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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


//    /**
//     * In-memory user details service
//     *
//     * @param encoder PasswordEncoder
//     * @return UserDetailsService
//     */
/*    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(new User(
                "assan", encoder.encode("password"),
                Collections.singletonList(new SimpleGrantedAuthority("USER"))));
        usersList.add(new User(
                "zaure", encoder.encode("password"),
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN"))));
        return new InMemoryUserDetailsManager(usersList);
    }*/

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
}
