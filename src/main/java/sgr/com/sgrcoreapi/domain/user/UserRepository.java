package sgr.com.sgrcoreapi.domain.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByIsDeletedFalse(Pageable pageable);
    Page<User> findByIsDeletedFalseAndRole(UserRoleEnum role, Pageable pageable);
    Optional<User> findByIdAndIsDeletedFalse(UUID id);
    Optional<User> findByEmailAndIsDeletedFalse(String email);

}
