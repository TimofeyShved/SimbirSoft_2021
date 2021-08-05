package com.example.SimbirSoft_2021.mappers;

import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReleaseMapper {

    ReleaseMapper INSTANCE = Mappers.getMapper(ReleaseMapper.class);

    ReleaseDto toDto(ReleaseEntity releaseEntity);
    ReleaseEntity toEntity(ReleaseDto releaseDto);
}
