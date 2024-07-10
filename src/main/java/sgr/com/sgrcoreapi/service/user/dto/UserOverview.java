package sgr.com.sgrcoreapi.service.user.dto;

import java.time.LocalDate;
import sgr.com.sgrcoreapi.domain.user.UserRoleEnum;

public record UserOverview(String id, String name, String cpf, UserRoleEnum role, String email, String phone, LocalDate birthDate) {
}
