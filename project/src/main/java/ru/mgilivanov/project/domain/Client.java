package ru.mgilivanov.project.domain;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.mgilivanov.project.model.client.ClientAddRequest;
import ru.mgilivanov.project.model.client.ClientEditRequest;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "passportId")
    private String passportId;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "client")
    private List<Credit> credits;

    public Client(String lastName) {
        this.lastName = lastName;
    }

    public Client(ClientAddRequest clientAddRequest){
        this.lastName = clientAddRequest.getLastName();
        this.middleName = clientAddRequest.getMiddleName();
        this.firstName = clientAddRequest.getFirstName();
        this.email = clientAddRequest.getEmail();
        this.passportId = clientAddRequest.getPassportId();
    }

    public Client(ClientEditRequest clientEditRequest){
        this.id = clientEditRequest.getId();
        this.lastName = clientEditRequest.getLastName();
        this.middleName = clientEditRequest.getMiddleName();
        this.firstName = clientEditRequest.getFirstName();
        this.email = clientEditRequest.getEmail();
        this.passportId = clientEditRequest.getPassportId();
    }

}
