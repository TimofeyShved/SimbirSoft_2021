package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.entity.BoardEntity;

public interface BoardServiceInterface {
    public Object getOne(Long id) throws Exception;
    public Long deleteOne(Long id) throws Exception;
    public Object registration(Object o) throws Exception;
    public Object updateOne(Long id, Object o) throws Exception;
}
