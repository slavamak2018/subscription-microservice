package com.microservice.subscription.mapper;

import com.microservice.subscription.dtos.SubscriptionDto;
import com.microservice.subscription.entities.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDto toSubscriptionDto(final Subscription SubscriptionEntity);
    Subscription toSubscription(final SubscriptionDto SubscriptionDto);

    Set<Subscription> toSubscriptionSet(final Set<SubscriptionDto> SubscriptionDtos);
    Set<SubscriptionDto> toSubscriptionDtoSet(final Set<Subscription> subscriptions);
    default Page<SubscriptionDto> toSubscriptionDtoPage(final Page<Subscription> subscriptions) {
        return subscriptions.map(this::toSubscriptionDto);
    }

    void updateSubscriptionFromDto(final SubscriptionDto dto, @MappingTarget final Subscription subscriptionEntity);
}
