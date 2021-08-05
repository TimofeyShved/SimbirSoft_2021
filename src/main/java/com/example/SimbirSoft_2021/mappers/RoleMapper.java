package com.example.SimbirSoft_2021.mappers;

import com.example.SimbirSoft_2021.Dto.RoleDto;
import com.example.SimbirSoft_2021.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto(RoleEntity roleEntity);
    RoleEntity toEntity(RoleDto roleDto);
}
