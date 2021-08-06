package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.RoleDto;
import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.RoleMapper;
import com.example.SimbirSoft_2021.repository.*;
import com.example.SimbirSoft_2021.service.interfaceService.RoleServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1> Ролевой сервис - (RoleService) </h1>
 * Данный класс реализует запросы, которые
 * приходят в контроллер ролей (RoleController),
 * результат он возвращаяет обратно.
 * <p>
 * <b>Примечание:</b>
 * В данном классе можно конструтор, организовать 3
 * разными способами.
 * А так же он использует свой интерфейс:
 * удалить все роли связанные с задачами (deleteByTaskId),
 * удалить все роли связанные с человеком (deleteByUserId).
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
public class RoleService implements StandartServiceInterface<RoleDto>, RoleServiceInterface {

    // 2 способ
    //@Autowired
    //private RoleCrud roleCRUD;

    private final RoleCrud roleCrud; // создаём интерфейс для взаимодействия с бд
    private final UserCrud userCrud;
    private final TaskCrud taskCrud;

    // 3 способ
    public RoleService(RoleCrud roleCrud, UserCrud userCrud, TaskCrud taskCrud) {
        this.roleCrud = roleCrud;
        this.userCrud = userCrud;
        this.taskCrud = taskCrud;
    }

    /**
     * Это основной метод регистрации, из стандартного интерфейса
     * использующий метод registration.
     * Основная задача которой сохранить новоую роль в бд.
     * @param roleDto Это первый и единственный параметр метода registration, который обозначает данные роли.
     * @return RoleDto Вернёт роль.
     * @throws RoleExistsException При ошибке если такая реализация существует.
     * @throws TaskNotFoundException При ошибке если такая задача вообще не существует.
     * @throws UserNotFoundException При ошибке если такой пользователь вообще не существует.
     */
    @Transactional
    @Override
    public RoleDto registration(RoleDto roleDto) throws RoleExistsException, TaskNotFoundException, UserNotFoundException {

        //  проверка
        if ((taskCrud.findByTaskId(roleDto.getTaskId())==null)){
            throw new TaskNotFoundException();
        }
        if (userCrud.findByUserId(roleDto.getUserId())==null){
            throw new UserNotFoundException();
        }
        RoleEntity roleEntity = RoleMapper.INSTANCE.toEntity(roleDto);
        if (roleCrud.findByRoleNameAndTaskIdAndUserId(roleEntity.getRoleName(), roleEntity.getTaskId(), roleEntity.getUserId())!=null){
            throw new RoleExistsException();
        }

        // сохраняем
        roleCrud.save(roleEntity);
        return RoleMapper.INSTANCE.toDto(roleEntity);
    }

    /**
     * Это основной метод вытащить всё, из стандартного интерфейса
     * использующий метод getAll.
     * Основная задача которой вытащить все роли из бд.
     * @param () Не используется.
     * @return List<RoleDto> Вернёт коллекцию ролей.
     * @throws RoleNotFoundException При ошибке если такая реализация вообще не существует.
     */
    @Transactional
    @Override
    public List<RoleDto> getAll() throws RoleNotFoundException {
        List<RoleEntity> roleEntityList = roleCrud.findAll();

        //  проверка на то что роли вообще существуют
        if (roleEntityList==null){
            throw new RoleNotFoundException();
        }

        // перевод коллекции из одного вида в другой
        List<RoleDto> roleDtoList = roleEntityList.stream().map(x-> RoleMapper.INSTANCE.toDto(x)).collect(Collectors.toList());

        return roleDtoList;
    }

    /**
     * Это основной метод вытащить одну роль, из стандартного интерфейса
     * использующий метод getOne.
     * Основная задача которой вытащить одну роль из бд.
     * @param id Это первый и единственный параметр метода getOne, который обозначает номер роли в бд.
     * @return RoleDto Вернёт роль.
     * @throws RoleNotFoundException При ошибке если такая реализация вообще не существует.
     */
    @Transactional
    @Override
    public RoleDto getOne(Long id) throws RoleNotFoundException {
        RoleEntity roleEntity = roleCrud.findByRoleId(id);

        //  проверка на то что роль вообще существуют
        if (roleEntity==null){
            throw new RoleNotFoundException();
        }

        return RoleMapper.INSTANCE.toDto(roleEntity);
    }

    /**
     * Это основной метод удаления одной роли, из стандартного интерфейса
     * использующий метод deleteOne.
     * Основная задача которой удалить одну роль из бд.
     * @param id Это первый и единственный параметр метода getOne, который обозначает номер роли в бд.
     * @return Long Номер роли.
     * @throws RoleNotFoundException При ошибке если такая реализация вообще не существует.
     */
    @Transactional
    @Override
    public Long deleteOne(Long id) throws RoleNotFoundException {

        //  проверка на то что роль вообще существуют
        if (roleCrud.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }

        roleCrud.deleteById(id);
        return id;
    }

    /**
     * Это дополнительный метод удаления роли связанную с задачами, из личного интерфейса
     * использующий метод deleteByTaskId.
     * Основная задача которой удалить все роли связанные с задачами из бд.
     * @param taskId Это первый и единственный параметр метода getOne, который обозначает номер задачи в роли в бд.
     * @return List<RoleDto> Вернёт коллекцию ролей.
     * @throws RoleNotFoundException При ошибке если такая реализация вообще не существует.
     */
    @Transactional
    @Override
    public List<RoleDto> deleteByTaskId(Long taskId) throws RoleNotFoundException {
        List<RoleEntity> roleEntityList = roleCrud.findAll();

        //  проверка на то что роли вообще существуют
        if (roleEntityList==null){
            throw new RoleNotFoundException();
        }

        List<RoleDto> roleDtoList = new ArrayList<>();

        //  вытаскиваем и удаляем по одной роли, и сохраняем в коллекцию
        for (RoleEntity e:roleEntityList){
            if (e.getTaskId() == taskId){ //  проверка
                roleDtoList.add(RoleMapper.INSTANCE.toDto(e));
                deleteOne(e.getRoleId());
            }
        }

        return roleDtoList;
    }

    /**
     * Это дополнительный метод удаления роли связанную с человеком, из личного интерфейса
     * использующий метод deleteByTaskId.
     * Основная задача которой удалить все роли связанные с человеком из бд.
     * @param userId Это первый и единственный параметр метода deleteByUserId, который обозначает номер человека в роли в бд.
     * @return List<RoleDto> Вернёт коллекцию ролей.
     * @throws RoleNotFoundException При ошибке если такая реализация вообще не существует.
     */
    @Transactional
    @Override
    public List<RoleDto> deleteByUserId(Long userId) throws RoleNotFoundException {
        List<RoleEntity> roleEntityList = roleCrud.findAll();

        //  проверка на то что роли вообще существуют
        if (roleEntityList==null){
            throw new RoleNotFoundException();
        }

        List<RoleDto> roleDtoList = new ArrayList<>();

        //  вытаскиваем и удаляем по одной роли, и сохраняем в коллекцию
        for (RoleEntity e:roleEntityList){
            if (e.getUserId() == userId){ //  проверка
                roleDtoList.add(RoleMapper.INSTANCE.toDto(e));
                deleteOne(e.getRoleId());
            }
        }

        return roleDtoList;
    }

    /**
     * Это основной метод обновить одну роль, из стандартного интерфейса
     * использующий метод updateOne.
     * Основная задача которой обновить одну роль в бд.
     * @param id Это первый параметр метода updateOne, который обозначает номер роли в бд.
     * @param roleDto Это второй параметр метода updateOne, который обозначает данные роли.
     * @return RoleDto Вернёт роль.
     * @throws RoleNotFoundException При ошибке если такая реализация вообще не существует.
     * @throws RoleExistsException При ошибке если такая реализация существует.
     * @throws TaskNotFoundException При ошибке если такая задача вообще не существует.
     * @throws UserNotFoundException При ошибке если такой пользователь вообще не существует.
     */
    @Transactional
    @Override
    public RoleDto updateOne(Long id, RoleDto roleDto) throws RoleNotFoundException, RoleExistsException, TaskNotFoundException, UserNotFoundException {

        //  проверка на то что роль вообще существуют
        if (roleCrud.findByRoleId(id)==null){
            throw new RoleNotFoundException();
        }
        RoleEntity roleEntityNew = RoleMapper.INSTANCE.toEntity(roleDto);
        RoleEntity roleEntity = roleCrud.findByRoleId(id);

        if ((taskCrud.findByTaskId(roleEntityNew.getTaskId())==null)){
            throw new TaskNotFoundException();
        }
        if (userCrud.findByUserId(roleEntityNew.getUserId())==null){
            throw new UserNotFoundException();
        }
        if (roleCrud.findByRoleNameAndTaskIdAndUserId(roleEntityNew.getRoleName(), roleEntityNew.getTaskId(), roleEntityNew.getUserId())!=null){
            throw new RoleExistsException();
        }

        // присваивание новых значений
        roleEntity.setRoleName(roleEntityNew.getRoleName());
        roleEntity.setTaskId(roleEntityNew.getTaskId());
        roleEntity.setUserId(roleEntityNew.getUserId());

        // сохранение
        roleCrud.save(roleEntity);
        return RoleMapper.INSTANCE.toDto(roleEntity);
    }

}
