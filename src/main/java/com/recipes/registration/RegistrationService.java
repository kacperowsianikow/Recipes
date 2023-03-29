package com.recipes.registration;

import com.recipes.appuser.AppUser;
import com.recipes.appuser.AppUserRole;
import com.recipes.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if (!isEmailValid) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }

        appUserService.registerUser(
                new AppUser(
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );

        appUserService.enableAppUser(request.getEmail());

        //TODO sent email

        return "successful registration";
    }

}
