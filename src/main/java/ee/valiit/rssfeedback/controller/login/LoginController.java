package ee.valiit.rssfeedback.controller.login;


import ee.valiit.rssfeedback.controller.login.dto.LoginResponse;
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
    public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        LoginResponse loginResponse = loginService.login(username, password);
        return loginResponse;
    }

}
