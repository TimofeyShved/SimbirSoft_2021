package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.TaskMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.TaskServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1> Сервис задач - (TaskService) </h1>
 * Данный класс реализует запросы, которые
 * приходят в контроллер пользователей (TaskController),
 * результат он возвращаяет обратно.
 * <p>
 * <b>Примечание:</b>
 * В данном классе можно конструтор, организовать 3
 * разными способами.
 * А так же он использует свой интерфейс:
 * вытащить все задачи по статусу (getAllByStatus),
 * вытащить количество задач по статусу (getCountByStatus),
 * поискать задачу по реализации (findByReleaseId),
 * удалить задачу связанную с проектом (deleteTaskByProjectId),
 * удалить реализацию связанную с проектом (deleteReleaseInTask).
 * И стандартный для этого проекта, а это:
 * регистрация (registration),
 * вытащить всё (getAll),
 * вытащить одно (getOne),
 * удалить одно (deleteOne),
 * обновить одно (updateOne).
 *
 * @автор  Швед Т.Ю.
 * @версия 0.4
 * @от   2021-08-13
 */

// 1 способ
//@RequiredArgsConstructor
@Service
public class TaskService implements StandartServiceInterface<TaskDto>, TaskServiceInterface {

    // 2 способ
    //@Autowired
    //private TaskCrud projectCRUD;

    private final TaskCrud taskCrud; // создаём интерфейс для взаимодействия с бд
    private final ReleaseCrud releaseCrud;
    private final ProjectCrud projectCrud;
    private final RoleService roleService;

    // 3 способ
    public TaskService(TaskCrud taskCrud, ReleaseCrud releaseCrud, ProjectCrud projectCrud, RoleService roleService) {
        this.taskCrud = taskCrud;
        this.releaseCrud = releaseCrud;
        this.projectCrud = projectCrud;
        this.roleService = roleService;
    }

    /**
     * Это основной метод регистрации, из стандартного интерфейса
     * использующий метод registration.
     * Основная задача которой сохранить новой задачи в бд.
     * @param taskDto Это первый и единственный параметр метода registration, который обозначает данные задачи.
     * @return TaskDto Вернёт задачу.
     * @throws ReleaseNotFoundException При ошибке если такая реализация даты/времени ещё не существует.
     * @throws TaskAndDateTimeExistsException При ошибке если такая реализация даты/времени среди задач уже существует.
     * @throws ProjectAndDateTimeExistsException При ошибке если такая реализация даты/времени среди проектов уже существует.
     * @throws TaskExistsException При ошибке если такая реализация задачи уже существует.
     * @throws StatusEnumException При ошибке если такой статус задачи не существует.
     */
    @Transactional
    @Override
    public TaskDto registration(TaskDto taskDto) throws ReleaseNotFoundException, TaskAndDateTimeExistsException,
            ProjectAndDateTimeExistsException, TaskExistsException, StatusEnumException {

        //  проверка
        if (releaseCrud.findByReleaseId(taskDto.getReleaseId())==null){ // проверить, что есть такая реализация существует
            throw new ReleaseNotFoundException();
        }
        if (taskCrud.findByReleaseId(taskDto.getReleaseId())!=null){ // что реализации нет среди задач
            throw new TaskAndDateTimeExistsException();
        }
        if (projectCrud.findByReleaseId(taskDto.getReleaseId())!=null){ // что реализации нет среди проектов
            throw new ProjectAndDateTimeExistsException();
        }
        if (taskCrud.findByTaskNameAndProjectIdAndReleaseId(taskDto.getTaskName(), taskDto.getProjectId(), taskDto.getReleaseId())!=null){ // что нет похожей задачи у проекта
            throw new TaskExistsException();
        }

        TaskEntity taskEntity = TaskMapper.INSTANCE.toEntity(taskDto);

        // сохраняем
        taskCrud.save(taskEntity);
        return TaskMapper.INSTANCE.toDto(taskEntity);
    }

    /**
     * Это основной метод вытащить всё, из стандартного интерфейса
     * использующий метод getAll.
     * Основная задача которой вытащить все задачи из бд.
     * @param () Не используется.
     * @return List<TaskDto> Вернёт список задач.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     */
    @Transactional
    @Override
    public List<TaskDto> getAll() throws TaskNotFoundException {
        List<TaskEntity> taskEntityList = taskCrud.findAll();

        //  проверка на то что задачи вообще существуют
        if (taskEntityList==null){
            throw new TaskNotFoundException();
        }

        // перевод коллекции из одного вида в другой
        List<TaskDto> taskDtoList = taskEntityList.stream().map(x-> TaskMapper.INSTANCE.toDto(x)).collect(Collectors.toList());

        return taskDtoList;
    }

    /**
     * Это основной метод вытащить одну задачу, из стандартного интерфейса
     * использующий метод getOne.
     * Основная задача которой вытащить одну задачу из бд.
     * @param id Это первый и единственный параметр метода getOne, который обозначает номер задачи в бд.
     * @return TaskDto Вернёт задачу.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     */
    @Transactional
    @Override
    public TaskDto getOne(Long id) throws TaskNotFoundException {
        TaskEntity taskEntity = taskCrud.findByTaskId(id);

        //  проверка на то что задачи вообще существуют
        if (taskEntity==null){
            throw new TaskNotFoundException();
        }

        return TaskMapper.INSTANCE.toDto(taskEntity);
    }

    /**
     * Это дополнительный метод, что-бы
     * вытащить все задачи по статусу и проекту, из личного интерфейса
     * использующий метод getAllByStatus.
     * Основная задача которой вытащить все задачи по статусу и проекту из бд.
     * @param projectId Это первый параметр метода getAllByStatus, который обозначает номер проекта в бд.
     * @param status Это второй параметр метода getAllByStatus, который обозначает статус проекта в бд.
     * @return List<TaskDto> Вернёт список задач.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     */
    @Transactional
    @Override
    public List<TaskDto> getAllByStatus(Long projectId, String status) throws TaskNotFoundException {
        List<TaskEntity> taskEntityList = taskCrud.findAll();

        //  проверка на то что задачи вообще существуют
        if (taskEntityList==null){
            throw new TaskNotFoundException();
        }

        List<TaskDto> taskDtoList = new ArrayList<>();

        //  вытаскиваем по одной задачи == статусу, и сохраняем в коллекцию
        for (TaskEntity e:taskEntityList){
            if ((e.getProjectId() == projectId)||(projectId==null)){ //  проверка
                if (e.getTaskStatus().toString().equals(status)){ //  проверка
                    taskDtoList.add(TaskMapper.INSTANCE.toDto(e));
                }
            }
        }

        return taskDtoList;
    }

    /**
     * Это дополнительный метод, что-бы
     * вытащить количество задач по статусу и проекту, из личного интерфейса
     * использующий метод getCountByStatus.
     * Основная задача которой вытащить количество задач по статусу и проекту из бд.
     * @param projectId Это первый параметр метода getCountByStatus, который обозначает номер проекта в бд.
     * @param status Это второй параметр метода getCountByStatus, который обозначает статус проекта в бд.
     * @return Long Вернёт количество задач.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     */
    @Transactional
    @Override
    public Long getCountByStatus(Long projectId, String status) throws TaskNotFoundException {
        List<TaskEntity> taskEntityList = taskCrud.findAll();

        //  проверка на то что задачи вообще существуют
        if (taskEntityList==null){
            throw new TaskNotFoundException();
        }

        Long countTask = 0L;

        //  вытаскиваем по одной задачи == статусу, и сохраняем в коллекцию
        for (TaskEntity e:taskEntityList){
            if ((e.getProjectId() == projectId)||(projectId==null)){ //  проверка
                if (e.getTaskStatus().toString().equals(status)){ //  проверка
                    countTask++;
                }
            }
        }

        return countTask;
    }

    /**
     * Это дополнительный метод, что-бы
     * поискать задачу по реализации, из личного интерфейса
     * использующий метод findByReleaseId.
     * Основная задача которой поиск по реализации из бд.
     * @param releaseId Это первый и единственный параметр метода findByReleaseId, который обозначает номер задачи в бд.
     * @return TaskDto Вернёт задачу.
     */
    @Transactional
    @Override
    public TaskDto findByReleaseId(Long releaseId) {
        TaskEntity taskEntity = taskCrud.findByReleaseId(releaseId);

        //  проверка на то что задачи вообще существуют
        if (taskEntity==null){
            return null;
        }

        return TaskMapper.INSTANCE.toDto(taskEntity);
    }

    /**
     * Это основной метод удалить одну задачу, из стандартного интерфейса
     * использующий метод deleteOne.
     * Основная задача которой удалить одну задачу из бд.
     * @param id  Это первый и единственный параметр метода deleteOne, который обозначает номер задачи в бд.
     * @return Long Вернёт номер задачи.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     * @throws ReleaseNotFoundException При ошибке если реализации даты/времени ещё не существует.
     * @throws RoleNotFoundException При ошибке если ролей ещё не существует.
     */
    @Transactional
    @Override
    public Long deleteOne(Long id) throws TaskNotFoundException, ReleaseNotFoundException, RoleNotFoundException {
        TaskEntity taskEntity = taskCrud.findByTaskId(id);

        //  проверка на то что задача вообще существуют
        if (taskEntity==null){
            throw new TaskNotFoundException();
        }

        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(taskEntity.getReleaseId());
        if (releaseEntity!=null){
            releaseCrud.delete(releaseEntity);
        }

        roleService.deleteByTaskId(id);
        taskCrud.delete(taskEntity);
        return id;
    }

    /**
     * Это дополнительный метод, что-бы
     * удалить задачи связанные с проектом, из личного интерфейса
     * использующий метод getCountByStatus.
     * Основная задача которой удалить задачи связанные с проектом из бд.
     * @param projectId Это первый и единственный параметр метода deleteTaskByProjectId, который обозначает номер проекта в бд.
     * @return boolean Вернёт значение успеха выполения данного действия (логический).
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     * @throws ReleaseNotFoundException При ошибке если реализации даты/времени ещё не существует.
     * @throws RoleNotFoundException При ошибке если ролей ещё не существует.
     */
    @Transactional
    @Override
    public boolean deleteTaskByProjectId(Long projectId) throws TaskNotFoundException, ReleaseNotFoundException, RoleNotFoundException {
        List<TaskEntity> taskEntityList = taskCrud.findAll();

        //  проверка на то что задача вообще существуют
        if (taskEntityList==null){
            throw new TaskNotFoundException();
        }

        //  вытаскиваем и удаляем по одной задачи, и сохраняем в коллекцию
        for (TaskEntity e:taskEntityList){
            if (e.getProjectId() == projectId){ //  проверка
                deleteOne(e.getTaskId());
            }
        }

        return true;
    }

    /**
     * Это дополнительный метод, что-бы
     * удалить реализацию связанную с задачей, из личного интерфейса
     * использующий метод deleteReleaseInTask.
     * Основная задача которой удалить реализацию связанную с задачей из бд.
     * @param id Это первый и единственный параметр метода deleteReleaseInTask, который обозначает номер реализация даты/времени в бд.
     * @return boolean Вернёт значение успеха выполения данного действия (логический).
     */
    @Transactional
    @Override
    public boolean deleteReleaseInTask(Long id) {
        TaskEntity taskEntity = taskCrud.findByReleaseId(id);
        if(taskEntity!=null){
            taskEntity.setReleaseId(null);
            taskCrud.save(taskEntity);
        }
        return true;
    }

    /**
     * Это основной метод обновить одну задачу, из стандартного интерфейса
     * использующий метод updateOne.
     * Основная задача которой обновить одну задачу в бд.
     * @param id Это первый параметр метода updateOne, который обозначает номер задачи в бд.
     * @param taskDto Это второй параметр метода updateOne, который обозначает данные задачи.
     * @return TaskDto Вернёт задачу.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     * @throws ReleaseNotFoundException При ошибке если реализации даты/времени ещё не существует.
     * @throws TaskAndDateTimeExistsException При ошибке если такая реализация даты/времени среди задач уже существует.
     * @throws ProjectAndDateTimeExistsException При ошибке если такая реализация даты/времени среди проектов уже существует.
     * @throws TaskExistsException При ошибке если такая реализация задачи уже существует.
     * @throws StatusEnumException При ошибке если такой статус задач не существует.
     */
    @Transactional
    @Override
    public TaskDto updateOne(Long id, TaskDto taskDto) throws TaskNotFoundException, ReleaseNotFoundException,
            TaskAndDateTimeExistsException, ProjectAndDateTimeExistsException, TaskExistsException, StatusEnumException {

        //  проверка на то что задача вообще существуют
        if (taskCrud.findByTaskId(id)==null){
            throw new TaskNotFoundException();
        }

        TaskEntity taskEntityNew = TaskMapper.INSTANCE.toEntity(taskDto);
        TaskEntity taskEntity = taskCrud.findByTaskId(id);

        //  проверка
        if (releaseCrud.findByReleaseId(taskEntityNew.getReleaseId())==null){ // проверить, что есть такая реализация существует
            throw new ReleaseNotFoundException();
        }
        if ((taskCrud.findByReleaseId(taskEntityNew.getReleaseId())!=null)
                &&(taskEntity.getReleaseId()!=taskEntityNew.getReleaseId())){ // что реализации нет среди задач
            throw new TaskAndDateTimeExistsException();
        }
        if (projectCrud.findByReleaseId(taskEntityNew.getReleaseId())!=null){ // что реализации нет среди проектов
            throw new ProjectAndDateTimeExistsException();
        }
        TaskEntity taskEntityTest = taskCrud.findByTaskNameAndProjectIdAndReleaseId(taskEntityNew.getTaskName(), taskEntityNew.getProjectId(), taskEntityNew.getReleaseId());
        if ((taskEntityTest!=null)&&(taskEntityTest!=taskEntity)){ // что нет похожей задачи у проекта
            throw new TaskExistsException();
        }

        // присваивание новых значений
        taskEntity.setTaskName(taskEntityNew.getTaskName());
        taskEntity.setTaskStatus(taskEntityNew.getTaskStatus());
        taskEntity.setProjectId(taskEntityNew.getProjectId());
        taskEntity.setReleaseId(taskEntityNew.getReleaseId());

        // сохранение
        taskCrud.save(taskEntity);
        return TaskMapper.INSTANCE.toDto(taskEntity);
    }
}
