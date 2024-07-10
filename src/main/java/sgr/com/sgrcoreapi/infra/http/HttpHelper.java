package sgr.com.sgrcoreapi.infra.http;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public class HttpHelper {

    public static URI getCreatedResourceURI(UUID resourceId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(("/{id}"))
                .buildAndExpand(resourceId)
                .toUri();
    }
}
