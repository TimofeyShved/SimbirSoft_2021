package com.example.SimbirSoft_2021.mappers;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDto toDto(ProjectEntity projectEntity);
    ProjectEntity toEntity(ProjectDto projectDto);

}
