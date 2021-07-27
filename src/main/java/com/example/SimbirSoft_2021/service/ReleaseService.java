package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class ReleaseService implements StandartServiceInterface, ProjectServiceInterface {

    // 2 способ
    //@Autowired
    //private ReleaseCrud releaseCRUD;

    private ReleaseCrud releaseCrud; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public ReleaseService(ReleaseCrud releaseCrud) {
        this.releaseCrud = releaseCrud;
    }

    @Transactional
    @Override
    public ReleaseEntity registration(Object o) throws ReleaseExistsException {
        ReleaseEntity releaseEntity = (ReleaseEntity)o;
        if (releaseCrud.findByDataStartAndDataEnd(releaseEntity.getDataStart(), releaseEntity.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }
        return releaseCrud.save(releaseEntity);
    }

    @Transactional
    @Override
    public List<ReleaseEntity> getAll() throws ReleaseNotFoundException {
        List<ReleaseEntity> list = releaseCrud.findAll();
        if (list==null){
            throw new ReleaseNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public ReleaseEntity getOne(Long id) throws ReleaseNotFoundException {
        ReleaseEntity projectEntity = releaseCrud.findByReleaseId(id);
        if (projectEntity==null){
            throw new ReleaseNotFoundException();
        }
        return projectEntity;
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws ReleaseNotFoundException {
        if (releaseCrud.findByReleaseId(id)==null){
            throw new ReleaseNotFoundException();
        }
        releaseCrud.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public ReleaseEntity updateOne(Long id, Object o) throws ReleaseNotFoundException, ReleaseExistsException {
        ReleaseEntity releaseEntityNew = (ReleaseEntity)o;

        if (releaseCrud.findByReleaseId(id)==null){
            throw new ReleaseNotFoundException();
        }
        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(id);

        if (releaseCrud.findByDataStartAndDataEnd(releaseEntityNew.getDataStart(), releaseEntityNew.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }
        releaseEntity.setDataStart(releaseEntityNew.getDataStart());
        releaseEntity.setDataEnd(releaseEntityNew.getDataEnd());
        return releaseCrud.save(releaseEntity);
    }
}
