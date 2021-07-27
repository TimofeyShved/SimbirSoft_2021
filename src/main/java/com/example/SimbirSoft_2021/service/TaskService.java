package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.TaskServiceInterface;
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

    private TaskCrud taskCrud; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public TaskService(TaskCrud taskCrud) {
        this.taskCrud = taskCrud;
    }

    @Transactional
    @Override
    public TaskEntity registration(Object o) throws ReleaseExistsException {
        TaskEntity taskEntity = (TaskEntity)o;
        if (taskCrud.findByReleaseId(taskEntity.getReleaseId())!=null){
            throw new ReleaseExistsException();
        }
        return taskCrud.save(taskEntity);
    }

    @Transactional
    @Override
    public List<TaskEntity> getAll() throws TaskNotFoundException {
        List<TaskEntity> list = taskCrud.findAll();
        if (list==null){
            throw new TaskNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public TaskEntity getOne(Long id) throws TaskNotFoundException {
        TaskEntity taskEntity = taskCrud.findByTaskId(id);
        if (taskEntity==null){
            throw new TaskNotFoundException();
        }
        return taskEntity;
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws TaskNotFoundException {
        if (taskCrud.findByTaskId(id)==null){
            throw new TaskNotFoundException();
        }
        taskCrud.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public TaskEntity updateOne(Long id, Object o) throws TaskNotFoundException, ReleaseExistsException {
        TaskEntity taskEntityNew = (TaskEntity)o;

        if (taskCrud.findByTaskId(id)==null){
            throw new TaskNotFoundException();
        }
        TaskEntity taskEntity = taskCrud.findByTaskId(id);

        if (taskCrud.findByReleaseId(taskEntityNew.getReleaseId())!=null){
            if (taskEntity.getTaskId()!= taskCrud.findByReleaseId(taskEntityNew.getReleaseId()).getTaskId()){
                throw new ReleaseExistsException();
            }
        }

        taskEntity.setTaskName(taskEntityNew.getTaskName());
        taskEntity.setTaskStatus(taskEntityNew.getTaskStatus());
        taskEntity.setReleaseId(taskEntityNew.getReleaseId());
        return taskCrud.save(taskEntity);
    }
}
