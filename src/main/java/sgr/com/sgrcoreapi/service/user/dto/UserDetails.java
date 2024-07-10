package sgr.com.sgrcoreapi.service.user.dto;

import java.time.LocalDate;
import sgr.com.sgrcoreapi.domain.user.UserRoleEnum;

public record UserDetails(String name, String cpf, String email, String phone, LocalDate birthDate, UserRoleEnum role, boolean isDeleted) {
}
