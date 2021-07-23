package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.ReleaseCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseCRUD releaseCRUD; // создаём интерфейс для взаимодействия с бд

    public ReleaseEntity registration(ReleaseEntity releaseEntity) throws Exception {
        System.out.println(releaseEntity.getDataStart());
        if ((releaseCRUD.findReleaseEntityByDataStart(releaseEntity.getDataStart())!=null)
                &&((releaseCRUD.findReleaseEntityByDataEnd(releaseEntity.getDataEnd())!=null))){
            throw new Exception("code: RELEASE_EXISTS");
        }
        return releaseCRUD.save(releaseEntity);
    }

    public ReleaseEntity getOne(Long id) throws UserNotFoundException {
        ReleaseEntity releaseEntity = releaseCRUD.findById(id).get();
        if (releaseEntity==null){
            throw new UserNotFoundException("code: RELEASE_NOT_FOUND");
        }
        return releaseEntity;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (releaseCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: RELEASE_NOT_FOUND");
        }
        releaseCRUD.deleteById(id);
        return id;
    }

    public ReleaseEntity updateOne(Long id, ReleaseEntity releaseEntityNew) throws Exception {
        ReleaseEntity releaseEntity = releaseCRUD.findById(id).get();
        if (releaseCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: TASK_NOT_FOUND");
        }
        if ((releaseCRUD.findReleaseEntityByDataStart(releaseEntityNew.getDataStart())!=null)
                &&((releaseCRUD.findReleaseEntityByDataEnd(releaseEntityNew.getDataEnd())!=null))){
            throw new Exception("code: RELEASE_EXISTS");
        }
        releaseEntity.setDataStart(releaseEntityNew.getDataStart());
        releaseEntity.setDataEnd(releaseEntityNew.getDataEnd());
        return releaseCRUD.save(releaseEntity);
    }
}
