package ee.valiit.rssfeedback.controller;


import ee.valiit.rssfeedback.persitence.user.User;
import ee.valiit.rssfeedback.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        User user = loginService.login(username, password);
        return user;
    }

}
