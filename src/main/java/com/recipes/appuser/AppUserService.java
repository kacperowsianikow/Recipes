package com.recipes.appuser;

import com.recipes.security.PasswordConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE =
            "User with email %s was not found";
    private final AppUserRepository appUserRepository;
    private final PasswordConfig passwordConfig;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MESSAGE, email)
                ));
    }

    public void registerUser(AppUser appUser) {
        Optional<AppUser> appUserByEmail =
                appUserRepository.findByEmail(appUser.getEmail());

        if (appUserByEmail.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }

        if (appUser.getPassword().isBlank() ||
                appUser.getPassword().length() < 8) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }

        String encodedPassword =
                passwordConfig.bCryptPasswordEncoder().encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);
    }

    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }
}
