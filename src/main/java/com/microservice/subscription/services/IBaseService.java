package com.microservice.subscription.services;

import com.microservice.subscription.entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IBaseService <T extends BaseEntity> {

    T save(T entity);

    T update(T entity);

    void delete(UUID id);

    Page<T> findAll(Pageable pageable);

    T findById(UUID id);

}
