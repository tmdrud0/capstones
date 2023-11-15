package capstone.app.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Getter
@Embeddable
public class Company {

    private String companyName;

    private String companyAddress;

    private String companyCallNumber;

    private String companyFaxNumber;

    private String companyEmail;

    protected Company( ){}
    public Company(String name, String add, String call, String fax, String email){
        companyName = name;
        companyAddress = add;
        companyCallNumber = call;
        companyFaxNumber = fax;
        companyEmail = email;
    }

}
