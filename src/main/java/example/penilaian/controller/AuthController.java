package example.penilaian.controller;

import example.penilaian.model.LoginUserRequest;
import example.penilaian.model.UserToken;
import example.penilaian.model.WebResponse;
import example.penilaian.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(
            path = "/api/auth/login",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserToken> login(@RequestBody LoginUserRequest request) {
        UserToken userToken = authService.login(request);


        return WebResponse.<UserToken>builder()
                .data(userToken)
                .build();
    }




}
