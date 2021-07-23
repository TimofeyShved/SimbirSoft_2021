package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskService {

    @Autowired
    private TaskCrud taskCRUD; // создаём интерфейс для взаимодействия с бд

    public TaskEntity registration(TaskEntity taskEntity) throws Exception {
        if ((taskCRUD.findTaskEntityByTaskName(taskEntity.getTaskName())!=null)){
            throw new Exception("code: TASK_EXISTS");
        }
        return taskCRUD.save(taskEntity);
    }

    public TaskEntity getOne(Long id) throws UserNotFoundException {
        TaskEntity taskEntity = taskCRUD.findById(id).get();
        if (taskEntity==null){
            throw new UserNotFoundException("code: TASK_NOT_FOUND");
        }
        return taskEntity;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (taskCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: TASK_NOT_FOUND");
        }
        taskCRUD.deleteById(id);
        return id;
    }

    public TaskEntity updateOne(Long id, TaskEntity taskEntityNew) throws Exception {
        TaskEntity taskEntity = taskCRUD.findById(id).get();
        if (taskCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: TASK_NOT_FOUND");
        }
        if ((taskCRUD.findTaskEntityByTaskName(taskEntityNew.getTaskName())!=null)){
            throw new Exception("code: TASK_EXISTS");
        }
        taskEntity.setTaskName(taskEntityNew.getTaskName());
        taskEntity.setTaskStatus(taskEntityNew.getTaskStatus());
        taskEntity.setReleaseId(taskEntityNew.getReleaseId());
        return taskCRUD.save(taskEntity);
    }
}
