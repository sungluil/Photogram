package com.example.photogram.mapper;

import com.example.photogram.domain.Subscribe;
import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SubscribeDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface SubscribeDTOMapper extends EntityMapper<SubscribeDTO, Subscribe> {
    default Subscribe fromId(Long id) {
        if(id == null) {
            return null;
        }
        Subscribe subscribe = new Subscribe();
        subscribe.setId(id);
        return subscribe;
    }

}
