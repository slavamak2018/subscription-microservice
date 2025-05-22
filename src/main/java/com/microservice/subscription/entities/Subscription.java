package com.microservice.subscription.entities;

import com.microservice.subscription.enums.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "subscriptions")
public class Subscription extends BaseEntity {

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String description;

    @Enumerated
    @Column(nullable = false, name = "subscription_type")
    private SubscriptionType subscriptionType;

    @Column(name = "from_date", nullable = false)
    private LocalDateTime fromDate;

    @Column(name = "till_date", nullable = false)
    private LocalDateTime tillDate;

    @ManyToMany(mappedBy = "subscriptions", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object subj) {
        if (this == subj) return true;
        if (subj == null || getClass() != subj.getClass()) return false;
        Subscription subscription = (Subscription) subj;
        return Objects.equals(this.getId(), subscription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
