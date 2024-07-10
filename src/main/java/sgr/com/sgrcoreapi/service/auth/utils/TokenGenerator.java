package sgr.com.sgrcoreapi.service.auth.utils;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenGenerator {
    public static String generateSimulatedToken() {
        return UUID.randomUUID().toString().replace("-", "") +
                "." + UUID.randomUUID().toString().replace("-", "") +
                "." + UUID.randomUUID().toString().replace("-", "");
    }
}
