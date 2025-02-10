package ar.com.dontar.demo.persistence.entity;

import ar.com.dontar.demo.model.User;
import ar.com.dontar.demo.model.UserType;
import jakarta.persistence.*;


@Entity
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    private final long dni;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    @Enumerated(EnumType.STRING)
    private final UserType userType;


    public UserEntity(User user) {
        this.dni = user.getDni();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.password = user.getPassword();
    }

    public User userToModel(){
        User user = new User();
        user.setIdUser(this.idUser);
        user.setDni(this.dni);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setUserType(this.userType);

        return user;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getDni() {
        return dni;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public UserType getUserType() {
        return userType;
    }

}
