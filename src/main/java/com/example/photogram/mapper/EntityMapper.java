package com.example.photogram.mapper;

import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
    List<E> toEntity(List<D> dto);
    List<D> toDto(List<E> entity);

}

