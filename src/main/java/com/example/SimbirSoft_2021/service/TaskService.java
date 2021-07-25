package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class TaskService implements StandartServiceInterface, TaskServiceInterface {

    // 2 способ
    //@Autowired
    //private TaskCrud projectCRUD;

    private TaskCrud taskCRUD; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public TaskService(TaskCrud taskCRUD) {
        this.taskCRUD = taskCRUD;
    }

    @Transactional
    @Override
    public TaskEntity registration(Object o) throws ReleaseExistsException {
        TaskEntity taskEntity = (TaskEntity)o;
        if (taskCRUD.findByReleaseId(taskEntity.getReleaseId())!=null){
            throw new ReleaseExistsException();
        }
        return taskCRUD.save(taskEntity);
    }

    @Transactional
    @Override
    public List<TaskEntity> getAll() throws TaskNotFoundException {
        List<TaskEntity> list = taskCRUD.findAll();
        if (list==null){
            throw new TaskNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public TaskEntity getOne(Long id) throws TaskNotFoundException {
        TaskEntity taskEntity = taskCRUD.findByTaskId(id);
        if (taskEntity==null){
            throw new TaskNotFoundException();
        }
        return taskEntity;
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws TaskNotFoundException {
        if (taskCRUD.findByTaskId(id)==null){
            throw new TaskNotFoundException();
        }
        taskCRUD.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public TaskEntity updateOne(Long id, Object o) throws TaskNotFoundException, ReleaseExistsException {
        TaskEntity taskEntityNew = (TaskEntity)o;

        if (taskCRUD.findByTaskId(id)==null){
            throw new TaskNotFoundException();
        }
        TaskEntity taskEntity = taskCRUD.findByTaskId(id);

        if (taskCRUD.findByReleaseId(taskEntityNew.getReleaseId())!=null){
            if (taskEntity.getTaskId()!=taskCRUD.findByReleaseId(taskEntityNew.getReleaseId()).getTaskId()){
                throw new ReleaseExistsException();
            }
        }

        taskEntity.setTaskName(taskEntityNew.getTaskName());
        taskEntity.setTaskStatus(taskEntityNew.getTaskStatus());
        taskEntity.setReleaseId(taskEntityNew.getReleaseId());
        return taskCRUD.save(taskEntity);
    }
}
