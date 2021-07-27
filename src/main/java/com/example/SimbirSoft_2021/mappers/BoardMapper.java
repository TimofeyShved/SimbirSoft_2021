package com.example.SimbirSoft_2021.mappers;

import com.example.SimbirSoft_2021.Dto.BoardDto;
import com.example.SimbirSoft_2021.entity.BoardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    BoardDto toDto(BoardEntity boardEntity);
    BoardEntity toEntity(BoardDto boardDto);

}
