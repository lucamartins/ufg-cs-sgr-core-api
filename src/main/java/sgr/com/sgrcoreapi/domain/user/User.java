package sgr.com.sgrcoreapi.domain.user;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sgr.com.sgrcoreapi.domain.tableservice.TableService;
import sgr.com.sgrcoreapi.service.user.dto.AddUserRequest;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String phone;

    private LocalDate birthDate;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "waiter", fetch = FetchType.LAZY)
    private List<TableService> tableServices;

    public User(AddUserRequest addUserRequest) {
        this.name = addUserRequest.name();
        this.cpf = addUserRequest.cpf();
        this.email = addUserRequest.email();
        this.phone = addUserRequest.phone();
        this.birthDate = addUserRequest.birthDate();
        this.password = addUserRequest.password();
        this.role = addUserRequest.role();
    }
}
