package kz.doss.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/registration")
    public String register() {
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepo.save(user);

        return "redirect:/login";
    }
}
