package kz.doss.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findByUserId(Long id) {
        return userRepository.getById(id);
    }


    public Optional<User> findUserByUsername(String login) {
        return userRepository.findUserByUsername(login);
    }

}
