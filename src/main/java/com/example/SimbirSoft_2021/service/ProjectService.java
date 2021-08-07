package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.ProjectMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ProjectServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <h1> Сервис проектов - (ProjectService) </h1>
 * Данный класс реализует запросы, которые
 * приходят в контроллер пользователей (ProjectController),
 * результат он возвращаяет обратно.
 * <p>
 * <b>Примечание:</b>
 * В данном классе можно конструтор, организовать 3
 * разными способами.
 * А так же он использует свой интерфейс:
 * вытащить все проекты по статусу (getAllByStatus),
 * вытащить количество проектов по статусу (getCountByStatus),
 * поискать проект по реализации (findByReleaseId),
 * удалить реализацию связанную с проектом (deleteReleaseInProject).
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
public class ProjectService implements StandartServiceInterface<ProjectDto>, ProjectServiceInterface {

    // 2 способ
    //@Autowired
    //private ProjectCrud projectCRUD;

    private final ProjectCrud projectCrud; // создаём интерфейс для взаимодействия с бд
    private final ReleaseCrud releaseCrud;
    private final TaskCrud taskCrud;
    private final TaskService taskService;

    // 3 способ
    public ProjectService(ProjectCrud projectCrud, ReleaseCrud releaseCrud, TaskCrud taskCrud, TaskService taskService) {
        this.projectCrud = projectCrud;
        this.releaseCrud = releaseCrud;
        this.taskCrud = taskCrud;
        this.taskService = taskService;
    }

    /**
     * Это основной метод регистрации, из стандартного интерфейса
     * использующий метод registration.
     * Основная задача которой сохранить новый проект в бд.
     * @param projectDto Это первый и единственный параметр метода registration, который обозначает данные проекта.
     * @return ProjectDto Вернёт проект.
     * @throws ProjectExistsException При ошибке если такая реализация проекта уже существует.
     * @throws ReleaseNotFoundException При ошибке если такая реализация даты/времени ещё не существует.
     * @throws ProjectAndDateTimeExistsException При ошибке если такая реализация даты/времени среди проектов уже существует.
     * @throws TaskAndDateTimeExistsException При ошибке если такая реализация даты/времени среди задач уже существует.
     * @throws StatusEnumException При ошибке если такой статус проекта не существует.
     */
    @Transactional
    @Override
    public ProjectDto registration(ProjectDto projectDto) throws ProjectExistsException, ReleaseNotFoundException, ProjectAndDateTimeExistsException,
            TaskAndDateTimeExistsException, StatusEnumException {
        ProjectEntity projectEntity = ProjectMapper.INSTANCE.toEntity(projectDto);

        //  проверка
        if (releaseCrud.findByReleaseId(projectEntity.getReleaseId())==null){ // проверить, что есть такая реализация существует
            throw new ReleaseNotFoundException();
        }
        if (projectCrud.findByReleaseId(projectEntity.getReleaseId())!=null){ // что реализации нет среди проектов
            throw new ProjectAndDateTimeExistsException();
        }
        if (taskCrud.findByReleaseId(projectEntity.getReleaseId())!=null){ // что реализации нет среди задач
            throw new TaskAndDateTimeExistsException();
        }
        if (projectCrud.findByProjectName(projectEntity.getProjectName())!=null){ // что имя проекта не повторяется
            throw new ProjectExistsException();
        }

        //  сохранение
        projectCrud.save(projectEntity);
        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }

    /**
     * Это основной метод вытащить всё, из стандартного интерфейса
     * использующий метод getAll.
     * Основная задача которой вытащить все проекты из бд.
     * @param () Не используется.
     * @return List<ProjectDto> Вернёт список проектов.
     * @throws ProjectNotFoundException При ошибке если проектов ещё не существует.
     */
    @Transactional
    @Override
    public List<ProjectDto> getAll() throws ProjectNotFoundException {
        List<ProjectEntity> projectEntityList = projectCrud.findAll();

        //  проверка на то что проекты вообще существуют
        if (projectEntityList==null){
            throw new ProjectNotFoundException();
        }

        // перевод коллекции из одного вида в другой
        List<ProjectDto> projectDtoList = projectEntityList.stream().map(x->ProjectMapper.INSTANCE.toDto(x)).collect(Collectors.toList());

        return projectDtoList;
    }

    /**
     * Это основной метод вытащить один проект, из стандартного интерфейса
     * использующий метод getOne.
     * Основная задача которой вытащить один проект из бд.
     * @param id Это первый и единственный параметр метода getOne, который обозначает номер проекта в бд.
     * @return ProjectDto Вернёт проект.
     * @throws ProjectNotFoundException При ошибке если проектов ещё не существует.
     */
    @Transactional
    @Override
    public ProjectDto getOne(Long id) throws ProjectNotFoundException {
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        //  проверка на то что проект вообще существуют
        if (projectEntity==null){
            throw new ProjectNotFoundException();
        }

        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }

    /**
     * Это дополнительный метод, что-бы
     * вытащить все проекты по статусу, из личного интерфейса
     * использующий метод getAllByStatus.
     * Основная задача которой вытащить все проекты по статусу из бд.
     * @param status Это первый и единственный параметр метода getAllByStatus, который обозначает статус проекта в бд.
     * @return List<ProjectDto> Вернёт список проектов.
     * @throws ProjectNotFoundException При ошибке если проектов ещё не существует.
     */
    @Transactional
    @Override
    public List<ProjectDto> getAllByStatus(String status) throws ProjectNotFoundException {
        List<ProjectEntity> projectEntityList = projectCrud.findAll();

        //  проверка на то что проекты вообще существуют
        if (projectEntityList==null){
            throw new ProjectNotFoundException();
        }

        List<ProjectDto> projectDtoList = new ArrayList<>();

        //  вытаскиваем один проект == статусу, и сохраняем в коллекцию
        for (ProjectEntity e:projectEntityList){
            if (e.getProjectStatus().toString().equals(status)){ //  проверка
                projectDtoList.add(ProjectMapper.INSTANCE.toDto(e));
            }
        }

        return projectDtoList;
    }

    /**
     * Это дополнительный метод, что-бы
     * вытащить количество проектов по статусу, из личного интерфейса
     * использующий метод getCountByStatus.
     * Основная задача которой вытащить все проекты по статусу из бд.
     * @param status Это первый и единственный параметр метода getAllByStatus, который обозначает статус проекта в бд.
     * @return Long Вернёт количество проектов.
     * @throws ProjectNotFoundException При ошибке если проектов ещё не существует.
     */
    @Transactional
    @Override
    public Long getCountByStatus(String status) throws ProjectNotFoundException {
        List<ProjectEntity> projectEntityList = projectCrud.findAll();

        //  проверка на то что проекты вообще существуют
        if (projectEntityList==null){
            throw new ProjectNotFoundException();
        }

        Long projectCount = 0L;

        //  вытаскиваем один проект == статусу, и сохраняем в коллекцию
        for (ProjectEntity e:projectEntityList){
            if (e.getProjectStatus().toString().equals(status)){ //  проверка
                projectCount++;
            }
        }

        return projectCount;
    }

    /**
     * Это дополнительный метод, что-бы
     * поискать проект по реализации, из личного интерфейса
     * использующий метод findByReleaseId.
     * Основная задача которой поиск по реализации из бд.
     * @param releaseId Это первый и единственный параметр метода findByReleaseId, который обозначает номер проекта в бд.
     * @return ProjectDto Вернёт проект.
     */
    @Transactional
    @Override
    public ProjectDto findByReleaseId(Long releaseId) {
        ProjectEntity projectEntity = projectCrud.findByReleaseId(releaseId);

        //  проверка на то что проект вообще существуют
        if (projectEntity==null){
            return null;
        }

        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }

    /**
     * Это основной метод удалить одного проекта, из стандартного интерфейса
     * использующий метод deleteOne.
     * Основная задача которой удалить один проект из бд.
     * @param id Это первый и единственный параметр метода deleteOne, который обозначает номер проекта в бд.
     * @return Long Вернёт номер проекта.
     * @throws ProjectNotFoundException При ошибке если проектов ещё не существует.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     * @throws ReleaseNotFoundException При ошибке если реализации даты/времени ещё не существует.
     * @throws RoleNotFoundException При ошибке если ролей ещё не существует.
     */
    @Transactional
    @Override
    public Long deleteOne(Long id) throws ProjectNotFoundException, TaskNotFoundException, ReleaseNotFoundException, RoleNotFoundException {
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        //  проверка на то что проект вообще существуют
        if (projectEntity==null){
            throw new ProjectNotFoundException();
        }

        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(projectEntity.getReleaseId());
        if (taskService.deleteTaskByProjectId(id)){
            projectCrud.delete(projectEntity);
        }
        if (releaseEntity!=null){
            releaseCrud.delete(releaseEntity);
        }
        return id;
    }

    /**
     * Это дополнительный метод, что-бы
     * удалить реализацию связанную с проектом, из личного интерфейса
     * использующий метод getCountByStatus.
     * Основная задача которой удалить реализацию связанную с проектом из бд.
     * @param id Это первый и единственный параметр метода deleteOne, который обозначает номер проекта в бд.
     * @return boolean Вернёт значение успеха выполения данного действия (логический).
     */
    @Transactional
    @Override
    public boolean deleteReleaseInProject(Long id) {
        ProjectEntity projectEntity = projectCrud.findByReleaseId(id);
        if(projectEntity!=null){
            projectEntity.setReleaseId(null);
            projectCrud.save(projectEntity);
        }
        return true;
    }

    /**
     * Это основной метод обновить один проект, из стандартного интерфейса
     * использующий метод updateOne.
     * Основная задача которой обновить один проект в бд.
     * @param id Это первый параметр метода updateOne, который обозначает номер проекта в бд.
     * @param projectDto Это второй параметр метода updateOne, который обозначает данные проекта.
     * @return ProjectDto Вернёт проект.
     * @throws ProjectNotFoundException При ошибке если проектов ещё не существует.
     * @throws ProjectExistsException При ошибке если такая реализация проекта уже существует.
     * @throws ReleaseNotFoundException При ошибке если реализации даты/времени ещё не существует.
     * @throws ProjectAndDateTimeExistsException При ошибке если такая реализация даты/времени среди проектов уже существует.
     * @throws TaskAndDateTimeExistsException При ошибке если такая реализация даты/времени среди задач уже существует.
     * @throws TaskNotFoundException При ошибке если задач ещё не существует.
     * @throws StatusEnumException При ошибке если такой статус проекта не существует.
     */
    @Transactional
    @Override
    public ProjectDto updateOne(Long id, ProjectDto projectDto) throws ProjectNotFoundException, ProjectExistsException, ReleaseNotFoundException,
            ProjectAndDateTimeExistsException, TaskAndDateTimeExistsException, TaskNotFoundException, StatusEnumException {

        //  проверка на то что проект вообще существуют
        if (projectCrud.findByProjectId(id)==null){
            throw new ProjectNotFoundException();
        }

        // получение сущности нового проекта
        ProjectEntity projectEntityNew = ProjectMapper.INSTANCE.toEntity(projectDto);

        // получение сущности старого проекта
        ProjectEntity projectEntity = projectCrud.findByProjectId(id);

        // проверка
        if (releaseCrud.findByReleaseId(projectEntityNew.getReleaseId())==null){ // проверить, что есть такая реализация существует
            throw new ReleaseNotFoundException();
        }
        if ((projectCrud.findByReleaseId(projectEntityNew.getReleaseId())!=null)
                &&(projectEntity.getReleaseId()!=projectEntityNew.getReleaseId())){ // что реализации нет среди проектов
            throw new ProjectAndDateTimeExistsException();
        }
        if (taskCrud.findByReleaseId(projectEntityNew.getReleaseId())!=null){ // что реализации нет среди задач
            throw new TaskAndDateTimeExistsException();
        }
        if ((projectCrud.findByProjectName(projectEntityNew.getProjectName())!=null)
                &&(!Objects.equals(projectEntity.getProjectName(), projectEntityNew.getProjectName()))){ // что имя проекта не повторяется
            throw new ProjectExistsException();
        }

        // присваивание новых значений
        projectEntity.setProjectName(projectEntityNew.getProjectName());
        projectEntity.setProjectStatus(projectEntityNew.getProjectStatus());
        projectEntity.setReleaseId(projectEntityNew.getReleaseId());

        // сохранение
        projectCrud.save(projectEntity);
        return ProjectMapper.INSTANCE.toDto(projectEntity);
    }
}
