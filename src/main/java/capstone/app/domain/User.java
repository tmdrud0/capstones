package capstone.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @JsonIgnore
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String callNumber;
    @Column
    @Embedded
    private Company company;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private List<Authority> authorities = new ArrayList<>();

    public User(String userName, String name, String callNumber, Company company) {
        this.username = userName;
        this.name = name;
        this.callNumber = callNumber;
        this.company = company;
    }
}
