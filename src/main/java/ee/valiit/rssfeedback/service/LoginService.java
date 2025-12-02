package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.infrastructure.exception.ForbiddenException;
import ee.valiit.rssfeedback.persitence.user.User;
import ee.valiit.rssfeedback.persitence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final UserRepository userRepository;

    public User login(String username, String password) {

        User user = userRepository.findActiveUserBy(username, password).orElseThrow(() -> new ForbiddenException("", 0));

        boolean exists = userRepository.existsById(1);

        return user;
    }





}
