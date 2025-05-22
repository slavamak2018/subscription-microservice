package com.microservice.subscription.controllers;

import com.microservice.subscription.dtos.SubscriptionDto;
import com.microservice.subscription.mapper.SubscriptionMapper;
import com.microservice.subscription.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription", description = "Subscription API")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper mapper;

    @Operation(summary = "Получить все подписки пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найденые подписки пользователя",
                    content = {
                            @Content(mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<Page<SubscriptionDto>> getSubscriptions(final Pageable pageable) {
        return ResponseEntity.ok(
                mapper.toSubscriptionDtoPage(subscriptionService.findAll(pageable)));
    }

    @Operation(summary = "Получить подписку по идентификатору")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найденые подписки пользователя",
                    content = {
                            @Content(mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDto> getSubscriptionById(@PathVariable final UUID id) {
        return ResponseEntity.ok(mapper.toSubscriptionDto(subscriptionService.findById(id)));
    }

    @Operation(summary = "Получить подписку по идентификатору пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Найденые подписки пользователя",
                    content = {
                            @Content(mediaType = "application/json")})
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<Set<SubscriptionDto>> getSubscriptionsByUserId(@PathVariable final UUID id) {
        return ResponseEntity.ok(mapper.toSubscriptionDtoSet(subscriptionService.findByUserId(id)));
    }

    @Operation(summary = "Создание нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Создание пользователя",
                    content = {
                            @Content(mediaType = "application/json")})
    })
    @PostMapping("/users/{id}")
    public ResponseEntity<SubscriptionDto> createUser(@RequestBody @Validated final SubscriptionDto subscriptionDto,
                                                      @PathVariable final UUID id) {
        var subscription = subscriptionService.save(mapper.toSubscription(subscriptionDto), id);
        return ResponseEntity.ok(mapper.toSubscriptionDto(subscription));
    }

    @Operation(summary = "Изменение атрибута пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изменение пользователя",
                    content = {
                            @Content(mediaType = "application/json")})
    })
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable final UUID id,
                                                              @RequestBody @Validated final SubscriptionDto subscriptionDto) {
        return ResponseEntity.ok(
                mapper.toSubscriptionDto(subscriptionService.update(mapper.toSubscription(subscriptionDto))));
    }

    @Operation(summary = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаление пользователя",
                    content = {
                            @Content(mediaType = "application/json")})
    })
    @DeleteMapping("/{sub_id}/users/{id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable final UUID sub_id, @PathVariable final UUID id) {
        subscriptionService.deleteSubscriptionForUser(sub_id, id);
        return ResponseEntity.ok("Subscription  with id " + sub_id + " for user with id " + id +" was deleted");
    }

    @Operation(summary = "Получение топ 3 популярных подписок пользователей")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Топ подписок",
                    content = {
                            @Content(mediaType = "application/json")})
    })
    @GetMapping("/top")
    public ResponseEntity<Page<SubscriptionDto>> getTopSubscriptions(final Pageable pageable) {
        return ResponseEntity.ok(mapper.toSubscriptionDtoPage(subscriptionService.getTopSubscriptions(pageable)));
    }



}
