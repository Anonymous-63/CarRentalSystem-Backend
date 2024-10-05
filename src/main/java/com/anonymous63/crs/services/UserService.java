package com.anonymous63.crs.services;

import com.anonymous63.crs.dtos.UserDto;

public interface UserService extends CrudService<UserDto, Long> {
    UserDto register(UserDto userDto);
}
