package ee.valiit.rssfeedback.service;

import ee.valiit.rssfeedback.controller.login.dto.LoginResponse;
import ee.valiit.rssfeedback.infrastructure.exception.ForbiddenException;
import ee.valiit.rssfeedback.persitence.user.User;
import ee.valiit.rssfeedback.persitence.user.UserMapper;
import ee.valiit.rssfeedback.persitence.user.UserRepository;
import ee.valiit.rssfeedback.persitence.userfeedselection.UserFeedSelectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ee.valiit.rssfeedback.infrastructure.error.Error.INCORRECT_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserFeedSelectionRepository userFeedSelectionRepository;


    public LoginResponse login(String username, String password) {

        User user = userRepository.findActiveUserBy(username, password)
                .orElseThrow(() -> new ForbiddenException(INCORRECT_CREDENTIALS.getMessage(), INCORRECT_CREDENTIALS.getErrorCode()));

        boolean userHasSelectedFeedSettings = userFeedSelectionRepository.userFeedSelectionExistsBy(user.getId());


        LoginResponse loginResponse = userMapper.toLoginResponse(user);
        loginResponse.setUserHasSelectedFeedSettings(userHasSelectedFeedSettings);

        return loginResponse;
    }





}
