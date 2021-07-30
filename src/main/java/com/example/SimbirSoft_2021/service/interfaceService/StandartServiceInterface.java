package com.example.SimbirSoft_2021.service.interfaceService;

import java.util.List;

public interface StandartServiceInterface {
    public Object registration(Object o) throws Exception;
    public List getAll() throws Exception;
    public Object getOne(Long id) throws Exception;
    public Long deleteOne(Long id) throws Exception;
    public Object updateOne(Long id, Object o) throws Exception;
}
