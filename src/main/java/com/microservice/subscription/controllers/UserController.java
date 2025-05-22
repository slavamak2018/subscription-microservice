package com.microservice.subscription.controllers;

import com.microservice.subscription.dtos.UserDto;
import com.microservice.subscription.mapper.UserMapper;
import com.microservice.subscription.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Validated final UserDto userDto) {
        var user = userService.save(mapper.toUser(userDto));
        return ResponseEntity.ok(mapper.toUserDto(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(mapper.toUserDtoPage(userService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final UUID id) {
        return ResponseEntity.ok(mapper.toUserDto(userService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable final UUID id, @RequestBody @Validated final UserDto userDto) {
        return ResponseEntity.ok(mapper.toUserDto(userService.update(mapper.toUser(userDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable final UUID id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted with id: " + id);
    }

}
