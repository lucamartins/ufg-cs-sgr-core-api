package sgr.com.sgrcoreapi.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.user.User;
import sgr.com.sgrcoreapi.domain.user.UserRepository;
import sgr.com.sgrcoreapi.service.user.dto.AddUserRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(AddUserRequest addUserRequest) {
        User newUser = new User(addUserRequest);

        userRepository.save(newUser);
    }
}
