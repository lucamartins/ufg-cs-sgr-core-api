package sgr.com.sgrcoreapi.service.user;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sgr.com.sgrcoreapi.domain.tableservice.TableServiceStatus;
import sgr.com.sgrcoreapi.domain.user.User;
import sgr.com.sgrcoreapi.domain.user.UserRepository;
import sgr.com.sgrcoreapi.domain.user.UserRoleEnum;
import sgr.com.sgrcoreapi.infra.exception.custom.BadRequestException;
import sgr.com.sgrcoreapi.infra.exception.custom.NotFoundException;
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
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
    }

    public void updateUser(UUID userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        patchUser(user, userUpdateRequest);
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        User user = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        boolean hasTableServiceInProgress = user.getTableServices().stream()
                .anyMatch(tableService -> tableService.getStatus().equals(TableServiceStatus.IN_PROGRESS));

        if(hasTableServiceInProgress) {
            throw new BadRequestException("Waiter have table service in progress");
        }

        user.setDeleted(true);
        userRepository.save(user);
    }

    private void patchUser(User user, UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.name() != null) {
            user.setName(userUpdateRequest.name());
        }
        if (userUpdateRequest.email() != null) {
            user.setEmail(userUpdateRequest.email());
        }
        if (userUpdateRequest.phone() != null) {
            user.setPhone(userUpdateRequest.phone());
        }
        if (userUpdateRequest.birthDate() != null) {
            user.setBirthDate(userUpdateRequest.birthDate());
        }
    }
}
