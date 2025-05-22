package com.microservice.subscription.services;

import com.microservice.subscription.entities.User;
import com.microservice.subscription.mapper.UserMapper;
import com.microservice.subscription.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService  implements IBaseService<User> {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public User save(User entity) {
        log.debug("User was saved for id {}", entity.getId());
        return repository.save(entity);
    }

    @Override
    public User update(User entity) {
        var user = repository.findById(entity.getId());
        if (user.isEmpty()) {
            log.warn("User not found for id {}", entity.getId());
            return null;
        }
        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        log.debug("User was deleted for id {}", id);
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(UUID id) {
        var user = repository.findById(id);
        if (user.isEmpty()) {
            log.warn("User not found for id {}", id);
            return null;
        }
        log.debug("User was found for id {}", id);
        return repository.findById(id).orElse(null);
    }
}
