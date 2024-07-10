package sgr.com.sgrcoreapi.service.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import sgr.com.sgrcoreapi.domain.user.UserRoleEnum;

import java.time.LocalDate;

public record AddUserRequest(
        @NotBlank
        @Length(min = 3, max = 255)
        String name,
        @NotBlank
        @Length(min = 11, max = 11)
        String cpf,
        @Email
        String email,
        @NotBlank
        String phone,
        @NotNull
        LocalDate birthDate,
        @NotBlank
        String password,
        @NotNull
        UserRoleEnum role
) {
}
