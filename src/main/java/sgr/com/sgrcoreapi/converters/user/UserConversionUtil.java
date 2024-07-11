package sgr.com.sgrcoreapi.converters.user;

import lombok.experimental.UtilityClass;
import sgr.com.sgrcoreapi.domain.user.User;
import sgr.com.sgrcoreapi.service.user.dto.UserDetails;
import sgr.com.sgrcoreapi.service.user.dto.UserOverview;

@UtilityClass
public class UserConversionUtil {
    public static UserOverview toUserOverview(User user) {
        return new UserOverview(
                user.getId().toString(),
                user.getName(),
                user.getCpf(),
                user.getRole(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthDate()
        );
    }

    public static UserDetails toUserDetails(User user) {
        return new UserDetails(
                user.getName(),
                user.getCpf(),
                user.getEmail(),
                user.getPhone(),
                user.getBirthDate(),
                user.getRole(),
                user.isDeleted()
        );
    }
}
