package com.example.photogram.mapper;

import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserDTOMapper extends EntityMapper<UserDTO, User> {
    default User fromId (String username) {
        if(username == null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        return user;
    }

}
