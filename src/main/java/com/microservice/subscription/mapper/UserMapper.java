package com.microservice.subscription.mapper;

import com.microservice.subscription.dtos.UserDto;
import com.microservice.subscription.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(final User userEntity);
    User toUser(final UserDto userDto);

    Set<User> toUserSet(final Set<UserDto> userDtos);
    Set<UserDto> toUserDtoSet(final Set<User> users);
    default Page<UserDto> toUserDtoPage(final Page<User> users) {
        return users.map(this::toUserDto);
    }

    void updateUserFromDto(final UserDto dto, @MappingTarget final User userEntity);
}
