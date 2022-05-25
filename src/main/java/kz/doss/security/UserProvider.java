package kz.doss.security;

import kz.doss.security.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserProvider implements AuthenticationProvider {

    private final CustomPasswordEncoder customPasswordEncoder;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Request to authenticate by email : {}", authentication.getName());
        String email = authentication.getName();
        String password = Optional.ofNullable(authentication.getCredentials()).map(Object::toString)
                .orElseThrow(() -> {
                    log.error("Password can't be null by email : {}", email);
                    return new BadRequestException("Введите пароль");
                });
        return userService.findUserByUsername(email).map(user -> {

            if (customPasswordEncoder.passwordEncoder().matches(password, user.getPassword())) {
                List<GrantedAuthority> grantList = new ArrayList<>();
                String role = user.getRole();
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
                PersonAuthDTO person = new PersonAuthDTO(user.getId(), email, user.getPassword(), grantList);
                return new UsernamePasswordAuthenticationToken(person, user.getPassword(), grantList);
            } else {
                throw new BadCredentialsException("Неверный пароль");
            }
        }).orElseThrow(() -> new BadRequestException("Неверный логин"));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}