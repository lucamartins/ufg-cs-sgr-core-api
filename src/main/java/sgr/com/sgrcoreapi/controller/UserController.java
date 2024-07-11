package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sgr.com.sgrcoreapi.converters.user.UserConversionUtil;
import sgr.com.sgrcoreapi.infra.http.ApiResponse;
import sgr.com.sgrcoreapi.infra.http.NoDataApiResponse;
import sgr.com.sgrcoreapi.infra.http.PagedApiResponse;
import sgr.com.sgrcoreapi.service.user.dto.UserDetails;
import sgr.com.sgrcoreapi.service.user.dto.UserOverview;
import sgr.com.sgrcoreapi.domain.user.UserRoleEnum;
import sgr.com.sgrcoreapi.service.user.UserService;
import sgr.com.sgrcoreapi.service.user.dto.AddUserRequest;
import sgr.com.sgrcoreapi.service.user.dto.UserUpdateRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<NoDataApiResponse> createUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        userService.createUser(addUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new NoDataApiResponse(HttpStatus.CREATED.value(), "Usuario criado com sucesso"));
    }


    @GetMapping
    public ResponseEntity<PagedApiResponse<UserOverview>> getActiveUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "role", required = false) UserRoleEnum role) {

        var users = userService.getActiveUsers(page, pageSize, role);
        var usersOverview = users.map(UserConversionUtil::toUserOverview).toList();

        Page<UserOverview> usersOverviewPage = new PageImpl<>(usersOverview, PageRequest.of(page, pageSize), users.getTotalElements());

        var response = new PagedApiResponse<>(
                HttpStatus.OK.value(),
                usersOverviewPage
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDetails>> getUserDetails(@PathVariable UUID userId) {
        var user = userService.getUserDetails(userId);
        var userOverview = UserConversionUtil.toUserDetails(user);

        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                userOverview
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<NoDataApiResponse> updateUser(@PathVariable UUID userId, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);

        return ResponseEntity.ok(new NoDataApiResponse(HttpStatus.OK.value(), "Usuario atualizado com sucesso"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<NoDataApiResponse> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);

        var response = new NoDataApiResponse(
                HttpStatus.NO_CONTENT.value()
        );
        return ResponseEntity.ok(response);
    }


}
