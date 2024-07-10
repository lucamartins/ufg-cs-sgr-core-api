package sgr.com.sgrcoreapi.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.user.User;
import sgr.com.sgrcoreapi.domain.user.UserRepository;
import sgr.com.sgrcoreapi.service.auth.dto.UserLoginRequest;
import sgr.com.sgrcoreapi.service.auth.utils.TokenGenerator;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public String login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmailAndIsDeletedFalse(userLoginRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!user.getPassword().equals(userLoginRequest.password()))
            throw new RuntimeException("Invalid credentials");

        return TokenGenerator.generateSimulatedToken();
    }

    public String logout() {
        return "Usuario deslogado com sucesso";
    }
}
