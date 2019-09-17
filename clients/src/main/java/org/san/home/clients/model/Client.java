package org.san.home.clients.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Slf4j
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "client", uniqueConstraints = @UniqueConstraint(columnNames = {"lastname", "firstname", "secondname", "birthday"}))
public class Client {

    @Id
    @Getter
    @Setter
    @GeneratedValue
    private Long  id;

    @NotNull
    @Getter
    @Setter
    @Length(max = 255)
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Getter
    @Setter
    @Length(max = 255)
    @Column(name = "lastname")
    private String lastName;

    @Getter
    @Setter
    @Length(max = 255)
    @Column(name = "secondname")
    private String secondName;

    @NotNull
    @Getter
    @Setter
    @Column(name = "birthday")
    private LocalDate birthDay;

}
