package com.microservice.subscription.dtos;

import com.microservice.subscription.enums.SubscriptionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto implements Serializable {

    private UUID id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotEmpty
    private SubscriptionType subscriptionType;

    @NotEmpty
    private LocalDateTime from;

    @NotEmpty
    private LocalDateTime till;

    private Set<UserDto> users;

}
