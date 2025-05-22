package com.microservice.subscription.services;

import com.microservice.subscription.entities.Subscription;
import com.microservice.subscription.mapper.SubscriptionMapper;
import com.microservice.subscription.repositories.SubscriptionRepository;
import com.microservice.subscription.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionService implements IBaseService<Subscription> {

    private final SubscriptionRepository repository;

    private final UserRepository userRepository;

    private final SubscriptionMapper mapper;

    @Override
    public Subscription save(final Subscription entity) {
        var subscription = repository.save(entity);
        log.debug("Subscription was saved for id {}", entity.getId());
        return subscription;
    }

    public Subscription save(final Subscription entity, final UUID id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("User not found for id {}", id);
            return null;
        }
        var subscription = repository.save(entity);
        user.get().getSubscriptions().add(subscription);
        entity.getUsers().add(user.get());

        log.debug("Subscription was saved for user with id {}", id);
        return subscription;
    }

    @Override
    public Subscription update(final Subscription entity) {
        var subscription = repository.findById(entity.getId());
        if (subscription.isEmpty()) {
            log.warn("Subscription not found for id {}", entity.getId());
            return null;
        }
        return repository.save(entity);
    }

    @Override
    public void delete(final UUID id) {
        log.debug("Subscription was deleted for id {}", id);
        repository.deleteById(id);
    }

    public void deleteSubscriptionForUser(final UUID sub_id, final UUID id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("User not found for id {}", id);
            return;
        }
        user.get().getSubscriptions().removeIf(subscription -> subscription.getId().equals(sub_id));
        log.debug("Subscription was deleted for id {}", id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Subscription findById(final UUID id) {
        var subscription = repository.findById(id);
        if (subscription.isEmpty()) {
            log.warn("Subscription not found for id {}", id);
            return null;
        }
        log.debug("Subscription was found for id {}", id);
        return subscription.orElse(null);
    }

    @Transactional(readOnly = true)
    public Set<Subscription> findByUserId(final UUID id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("User not found for id {}", id);
            return null;
        }

        var subscriptions = user.get().getSubscriptions();
        if (subscriptions.isEmpty()) {
            log.warn("Subscriptions not found for user with id {}", id);
            return null;
        }

        log.debug("Subscription was found for id {}", id);
        return subscriptions;
    }

    public Page<Subscription> getTopSubscriptions(final Pageable pageable) {
        return repository.findTop3Subscriptions(pageable);
    }

}
