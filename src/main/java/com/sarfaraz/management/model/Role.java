package com.sarfaraz.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(name = "role_name", columnNames = {"name"}))
public class Role {
    @Id
    @GeneratedValue(generator = "id_sec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_sec", sequenceName = "jpa_sequence", allocationSize = 5)
    @Column(nullable = false, precision = 5)
    private long id;

    @NotEmpty(message = "Role name must not be Empty")
    private String name;
    private String detail;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

}
