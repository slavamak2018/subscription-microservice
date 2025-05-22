package com.microservice.subscription.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;


    @Digits(integer = 10, fraction = 0)
    @Column(nullable = false)
    private String phone;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    @ToString.Exclude
    private Set<Subscription> subscriptions = new HashSet<>();

    @Override
    public boolean equals(Object subj) {
        if (this == subj) return true;
        if (subj == null || getClass() != subj.getClass()) return false;
        User user = (User) subj;
        return Objects.equals(this.getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
