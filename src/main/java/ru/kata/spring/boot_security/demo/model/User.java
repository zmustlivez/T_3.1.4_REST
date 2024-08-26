package ru.kata.spring.boot_security.demo.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "nameowner", nullable = false)
    private String nameowner;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)

    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String nameowner, Role role) {
        this.username = username;
        this.password = password;
        this.nameowner = nameowner;
        this.roles.add(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getNameowner() {
        return nameowner;
    }

    public void setNameowner(String nameowner) {
        this.nameowner = nameowner;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
