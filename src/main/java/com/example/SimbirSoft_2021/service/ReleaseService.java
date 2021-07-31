package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.ReleaseMapper;
import com.example.SimbirSoft_2021.mappers.UserMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public ReleaseDto registration(Object o) throws ReleaseExistsException, ReleaseDateFormatException {
        ReleaseDto releaseDto = (ReleaseDto) o;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        //Calendar cal = Calendar.getInstance(); // вытащить дату из системы
        try {
            releaseDto.setDataStart(dateFormat.format(dateFormat.parse(releaseDto.getDataStart())));
            releaseDto.setDataEnd(dateFormat.format(dateFormat.parse(releaseDto.getDataEnd())));
        }
        catch (Exception e){
            throw new ReleaseDateFormatException();
        }

        ReleaseEntity releaseEntity = ReleaseMapper.INSTANCE.toEntity(releaseDto);
        /* // ReleaseExistsException
        if (releaseCrud.findByDataStartAndDataEnd(releaseEntity.getDataStart(), releaseEntity.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }
         */
        releaseCrud.save(releaseEntity);
        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }

    @Transactional
    @Override
    public List<ReleaseDto> getAll() throws ReleaseNotFoundException {
        List<ReleaseEntity> releaseEntityList = releaseCrud.findAll();
        if (releaseEntityList==null){
            throw new ReleaseNotFoundException();
        }
        List<ReleaseDto> releaseDtoList = new ArrayList<>();
        for (ReleaseEntity e:releaseEntityList){
            releaseDtoList.add(ReleaseMapper.INSTANCE.toDto(e));
        }
        return releaseDtoList;
    }

    @Transactional
    @Override
    public ReleaseDto getOne(Long id) throws ReleaseNotFoundException {
        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(id);
        if (releaseEntity==null){
            throw new ReleaseNotFoundException();
        }
        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
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
    public ReleaseDto updateOne(Long id, Object o) throws ReleaseNotFoundException, ReleaseExistsException, ReleaseDateFormatException {
        if (releaseCrud.findByReleaseId(id)==null){
            throw new ReleaseNotFoundException();
        }
        ReleaseDto releaseDto = (ReleaseDto) o;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        //Calendar cal = Calendar.getInstance(); // вытащить дату из системы
        try {
            releaseDto.setDataStart(dateFormat.format(dateFormat.parse(releaseDto.getDataStart())));
            releaseDto.setDataEnd(dateFormat.format(dateFormat.parse(releaseDto.getDataEnd())));
        }
        catch (Exception e){
            throw new ReleaseDateFormatException();
        }

        ReleaseEntity releaseEntityNew = ReleaseMapper.INSTANCE.toEntity(releaseDto);
        /* // ReleaseExistsException
        if (releaseCrud.findByDataStartAndDataEnd(releaseEntity.getDataStart(), releaseEntity.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }
         */

        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(id);

        if (releaseCrud.findByDataStartAndDataEnd(releaseEntityNew.getDataStart(), releaseEntityNew.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }
        releaseEntity.setDataStart(releaseEntityNew.getDataStart());
        releaseEntity.setDataEnd(releaseEntityNew.getDataEnd());
        releaseCrud.save(releaseEntity);
        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }
}
