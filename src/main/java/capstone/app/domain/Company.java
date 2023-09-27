package capstone.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Company {

    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String callNumber;

    @Column
    private String faxNumber;
}
