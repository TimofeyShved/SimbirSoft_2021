package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.UserMapper;
import com.example.SimbirSoft_2021.model.UserModel;
import com.example.SimbirSoft_2021.repository.UserCrud;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1> Пользовательский сервис - (UserService) </h1>
 * Данный класс реализует запросы, которые
 * приходят в контроллер пользователей (UserController),
 * результат он возвращаяет обратно.
 * <p>
 * <b>Примечание:</b>
 * В данном классе можно конструтор, организовать 3
 * разными способами.
 * А так же он использует свой интерфейс.
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
@Slf4j
public class UserService implements StandartServiceInterface<UserDto>, UserServiceInterface {

    // 2 способ
    //@Autowired
    //private ProjectCrud projectCRUD;

    private final UserCrud userCrud; // создаём интерфейс для взаимодействия с бд
    private final RoleService roleService;

    // 3 способ
    public UserService(UserCrud userCrud, RoleService roleService) {
        this.userCrud = userCrud;
        this.roleService = roleService;
    }

    /**
     * Это основной метод регистрации, из стандартного интерфейса
     * использующий метод registration.
     * Основная задача которой сохранить нового пользователя в бд.
     * @param userDto Это первый и единственный параметр метода registration, который обозначает данные пользователя.
     * @return UserDto Вернёт пользователя.
     * @exception UserExistsException При ошибке если такая реализация существует.
     */
    @Transactional
    @Override
    public UserDto registration(UserDto userDto) throws UserExistsException {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDto);

        //  проверка
        if ((userCrud.findByEmail(userEntity.getEmail())!=null)){ // проверить, что есть такая реализация существует
            throw new UserExistsException();
        }

        // сохраняем
        userCrud.save(userEntity);
        return UserMapper.INSTANCE.toDto(userEntity);
    }

    /**
     * Это основной метод вытащить всё, из стандартного интерфейса
     * использующий метод getAll.
     * Основная задача которой вытащить всех людей из бд.
     * @param () Не используется.
     * @return List<UserDto> Вернёт список всех людей.
     * @exception UserNotFoundException При ошибке если люди вообще не существуют.
     */
    @Transactional
    @Override
    public List<UserModel> getAll() throws UserNotFoundException {
        List<UserEntity> userEntityList = userCrud.findAll();

        //  проверка на то что люди вообще существуют
        if (userEntityList==null){
            throw new UserNotFoundException();
        }

        // перевод коллекции из одного вида в другой
        List<UserDto> userDtoList = userEntityList.stream().map(x-> UserMapper.INSTANCE.toDto(x)).collect(Collectors.toList());
        List<UserModel> userModelList = userDtoList.stream().map(x->new UserModel(x)).collect(Collectors.toList());
        return userModelList;
    }

    /**
     * Это основной метод вытащить одного человека, из стандартного интерфейса
     * использующий метод getOne.
     * Основная задача которой вытащить одного человека из бд.
     * @param id Это первый и единственный параметр метода getOne, который обозначает номер пользователя в бд.
     * @return UserDto Вернёт пользователя.
     * @exception UserNotFoundException При ошибке если люди вообще не существуют.
     */
    @Transactional
    @Override
    public UserModel getOne(Long id) throws UserNotFoundException {
        UserEntity userEntity = userCrud.findByUserId(id);

        //  проверка на то что человек вообще существуют
        if (userEntity==null){
            throw new UserNotFoundException();
        }

        UserModel userModel = new UserModel(UserMapper.INSTANCE.toDto(userEntity));
        return userModel;
    }

    @Override
    public UserEntity findByEmail(String email) {
        UserEntity userEntity = userCrud.findByEmail(email);
        return userEntity;
    }

    /**
     * Это основной метод удалить одного человека, из стандартного интерфейса
     * использующий метод deleteOne.
     * Основная задача которой удалить одного человека из бд.
     * @param id Это первый и единственный параметр метода deleteOne, который обозначает номер пользователя в бд.
     * @return Long Вернёт номер пользователя в бд.
     * @exception UserNotFoundException При ошибке если люди вообще не существуют.
     * @exception RoleNotFoundException При ошибке если роли вообще не существуют.
     */
    @Transactional
    @Override
    public Long deleteOne(Long id) throws UserNotFoundException, RoleNotFoundException {

        //  проверка на то что человек вообще существуют
        if (userCrud.findByUserId(id)==null){
            throw new UserNotFoundException();
        }

        roleService.deleteByUserId(id);
        userCrud.deleteById(id);
        return id;
    }

    /**
     * Это основной метод обновить одного человека, из стандартного интерфейса
     * использующий метод updateOne.
     * Основная задача которой обновить одного человека в бд.
     * @param id Это первый параметр метода updateOne, который обозначает номер пользователя в бд.
     * @param userDto Это второй параметр метода updateOne, который обозначает данные пользователя.
     * @return UserDto Вернёт пользователя.
     * @exception UserNotFoundException При ошибке если люди вообще не существуют.
     * @exception RoleNotFoundException При ошибке если такая реализация уже существует.
     */
    @Transactional
    @Override
    public UserDto updateOne(Long id, UserDto userDto) throws UserNotFoundException, UserExistsException {

        //  проверка на то что человек вообще существуют
        if (userCrud.findByUserId(id)==null){
            throw new UserNotFoundException();
        }
        UserEntity userEntityNew = UserMapper.INSTANCE.toEntity(userDto);
        UserEntity userEntity = userCrud.findByUserId(id);

        //  проверка
        UserEntity userEntityTest = userCrud.findByEmail(userEntityNew.getEmail());
        if ((userEntityTest!=null)&&(userEntityTest != userEntity)){ // проверить, что есть такая реализация существует
            throw new UserExistsException();
        }

        // присваивание новых значений
        userEntity.setFirstName(userEntityNew.getFirstName());
        userEntity.setLastName(userEntityNew.getLastName());
        userEntity.setPatronymic(userEntityNew.getPatronymic());
        userEntity.setEmail(userEntityNew.getEmail());
        userEntity.setPassword(userEntityNew.getPassword());

        // сохранение
        userCrud.save(userEntity);
        return UserMapper.INSTANCE.toDto(userEntity);
    }
}
