package sgr.com.sgrcoreapi.service.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public record UserUpdateRequest(
        @NotBlank
        @Length(min = 3, max = 255)
        String name,
        @Email
        String email,
        @NotBlank
        String phone,
        @NotNull
        LocalDate birthDate) {
}
