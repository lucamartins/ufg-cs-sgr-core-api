package sgr.com.sgrcoreapi.service.user;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.user.User;
import sgr.com.sgrcoreapi.domain.user.UserRepository;
import sgr.com.sgrcoreapi.domain.user.UserRoleEnum;
import sgr.com.sgrcoreapi.service.user.dto.AddUserRequest;
import sgr.com.sgrcoreapi.service.user.dto.UserUpdateRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(AddUserRequest addUserRequest) {
        User newUser = new User(addUserRequest);

        userRepository.save(newUser);
    }

    public Page<User> getActiveUsers(int page, int pageSize, UserRoleEnum userRole) {
        Pageable paging = PageRequest.of(page, pageSize);
        if (userRole == null) {
            return userRepository.findByIsDeletedFalse(paging);
        } else {
            return userRepository.findByIsDeletedFalseAndRole(userRole, paging);
        }
    }

    public Page<User> getAllUser(int page, int pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        return userRepository.findAll(paging);
    }

    public User getUserDetails(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public void updateUser(UUID userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        user.updateUserInfo(userUpdateRequest);
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {

        // TODO: Verify if Waiter have table service in progress
        User user = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        user.setDeleted(true);
        userRepository.save(user);
    }
}
