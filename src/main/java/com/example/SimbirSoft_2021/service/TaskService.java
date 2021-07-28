package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.ProjectMapper;
import com.example.SimbirSoft_2021.mappers.TaskMapper;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.TaskServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class TaskService implements StandartServiceInterface, TaskServiceInterface {

    // 2 способ
    //@Autowired
    //private TaskCrud projectCRUD;

    private TaskCrud taskCrud; // создаём интерфейс для взаимодействия с бд
    private ReleaseCrud releaseCrud;

    // 3 способ
    public TaskService(TaskCrud taskCrud, ReleaseCrud releaseCrud) {
        this.taskCrud = taskCrud;
        this.releaseCrud = releaseCrud;
    }

    @Transactional
    @Override
    public TaskDto registration(Object o) throws ReleaseExistsException, ReleaseNotFoundException, TaskAndDateTimeExistsException {
        TaskDto taskDto = (TaskDto)o;
        if (releaseCrud.findByReleaseId(taskDto.getReleaseId())==null){
            throw new ReleaseNotFoundException();
        }
        if (taskCrud.findByReleaseId(taskDto.getReleaseId())!=null){
            throw new TaskAndDateTimeExistsException();
        }
        TaskEntity taskEntity = TaskMapper.INSTANCE.toEntity(taskDto);
        if (taskCrud.findByReleaseId(taskEntity.getReleaseId())!=null){
            throw new ReleaseExistsException();
        }
        taskCrud.save(taskEntity);
        return TaskMapper.INSTANCE.toDto(taskEntity);
    }

    @Transactional
    @Override
    public List<TaskDto> getAll() throws TaskNotFoundException {
        List<TaskEntity> taskEntityList = taskCrud.findAll();
        if (taskEntityList==null){
            throw new TaskNotFoundException();
        }
        List<TaskDto> taskDtoList = new ArrayList<>();
        for (TaskEntity e:taskEntityList){
            taskDtoList.add(TaskMapper.INSTANCE.toDto(e));
        }
        return taskDtoList;
    }

    @Transactional
    @Override
    public TaskDto getOne(Long id) throws TaskNotFoundException {
        TaskEntity taskEntity = taskCrud.findByTaskId(id);
        if (taskEntity==null){
            throw new TaskNotFoundException();
        }
        return TaskMapper.INSTANCE.toDto(taskEntity);
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
    public TaskDto updateOne(Long id, Object o) throws TaskNotFoundException, ReleaseExistsException, ReleaseNotFoundException, TaskAndDateTimeExistsException {
        if (taskCrud.findByTaskId(id)==null){
            throw new TaskNotFoundException();
        }
        TaskEntity taskEntityNew = TaskMapper.INSTANCE.toEntity((TaskDto) o);
        TaskEntity taskEntity = taskCrud.findByTaskId(id);

        if (releaseCrud.findByReleaseId(taskEntityNew.getReleaseId())==null){
            throw new ReleaseNotFoundException();
        }
        if (taskCrud.findByReleaseId(taskEntityNew.getReleaseId())!=null){
            if (taskEntity.getTaskId()!= taskCrud.findByReleaseId(taskEntityNew.getReleaseId()).getTaskId()){
                throw new TaskAndDateTimeExistsException();
            }
        }

        taskEntity.setTaskName(taskEntityNew.getTaskName());
        taskEntity.setTaskStatus(taskEntityNew.getTaskStatus());
        taskEntity.setReleaseId(taskEntityNew.getReleaseId());
        taskCrud.save(taskEntity);
        return TaskMapper.INSTANCE.toDto(taskEntity);
    }
}
