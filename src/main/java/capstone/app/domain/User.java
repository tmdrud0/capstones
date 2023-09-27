package capstone.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;


@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String callNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Profile> profiles = new ArrayList<>();
}
