package sgr.com.sgrcoreapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sgr.com.sgrcoreapi.service.user.UserService;
import sgr.com.sgrcoreapi.service.user.dto.AddUserRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        userService.createUser(addUserRequest);
    }

    @GetMapping("/all")
    public String exampleRouteGetAllUsers() {
        return "All users";
    }
}
