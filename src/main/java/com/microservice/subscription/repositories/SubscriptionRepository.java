package com.microservice.subscription.repositories;

import com.microservice.subscription.entities.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository <Subscription, UUID> {

   @Query(value = """
        select s.*, count(us.user_id) users from subscription s
            inner join user_subscriptions us on s.id = us.subscription_id group by s.id order by users LIMIT 3
      """, nativeQuery = true)
   Page<Subscription> findTop3Subscriptions(Pageable pageable);
}
