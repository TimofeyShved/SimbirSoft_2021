package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.ReleaseMapper;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.interfaceService.ReleaseServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import com.example.SimbirSoft_2021.thread.ReleaseThread;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1> Сервис реализации даты/времени - (ReleaseService) </h1>
 * Данный класс реализует запросы, которые
 * приходят в контроллер пользователей (ReleaseController),
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
public class ReleaseService implements StandartServiceInterface<ReleaseDto>, ReleaseServiceInterface {

    // 2 способ
    //@Autowired
    //private ReleaseCrud releaseCRUD;

    private final ReleaseCrud releaseCrud; // создаём интерфейс для взаимодействия с бд
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ReleaseThread releaseThread;

    // 3 способ
    public ReleaseService(ReleaseCrud releaseCrud, ProjectService projectService, TaskService taskService, ReleaseThread releaseThread) {
        this.releaseCrud = releaseCrud;
        this.projectService = projectService;
        this.taskService = taskService;
        this.releaseThread = releaseThread;
    }

    /**
     * Это основной метод регистрации, из стандартного интерфейса
     * использующий метод registration.
     * Основная задача которой сохранить новую реализацию времени в бд.
     * @param releaseDto Это первый и единственный параметр метода registration, который обозначает данные реализации времени.
     * @return ReleaseDto Вернёт реализацию времени.
     * @throws ReleaseExistsException При ошибке если такая реализация существует.
     * @throws ReleaseDateFormatException При ошибке если неверный формат даты/времени реализации.
     */
    @Transactional
    @Override
    public ReleaseDto registration(ReleaseDto releaseDto) throws ReleaseExistsException, ReleaseDateFormatException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        //Calendar cal = Calendar.getInstance(); // вытащить дату из системы

        //  проверка на формат
        try {
            // присваивание новых значений из форматированных под стандарт данных прешедших значений
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
        }*/

        // сохранение
        releaseCrud.save(releaseEntity);

        // отправка в поток
        releaseThread.set(ReleaseMapper.INSTANCE.toDto(releaseEntity));

        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }

    /**
     * Это основной метод вытащить все реализации времени, из стандартного интерфейса
     * использующий метод getAll.
     * Основная задача которой вытащить все реализации времени из бд.
     * @param () Не используется.
     * @return List<ReleaseDto> Вернёт список всех реализации времени.
     * @throws ReleaseNotFoundException При ошибке если такой реализации даты/времени вообще не существуют.
     */
    @Transactional
    @Override
    public List<ReleaseDto> getAll() throws ReleaseNotFoundException {
        List<ReleaseEntity> releaseEntityList = releaseCrud.findAll();

        //  проверка на то что реализации вообще существуют
        if (releaseEntityList==null){
            throw new ReleaseNotFoundException();
        }

        // перевод коллекции из одного вида в другой
        List<ReleaseDto> releaseDtoList = releaseEntityList.stream().map(x-> ReleaseMapper.INSTANCE.toDto(x)).collect(Collectors.toList());

        return releaseDtoList;
    }

    /**
     * Это основной метод вытащить одну реализацию времени, из стандартного интерфейса
     * использующий метод getOne.
     * Основная задача которой вытащить одну реализацию времени из бд.
     * @param id Это первый и единственный параметр метода getOne, который обозначает номер реализации времени.
     * @return ReleaseDto Вернёт реализацию времени.
     * @throws ReleaseNotFoundException При ошибке если такой реализации даты/времени вообще не существуют.
     */
    @Transactional
    @Override
    public ReleaseDto getOne(Long id) throws ReleaseNotFoundException {
        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(id);

        //  проверка на то что реализации вообще существуют
        if (releaseEntity==null){
            throw new ReleaseNotFoundException();
        }

        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }

    /**
     * Это основной метод удалить одну реализацию времени, из стандартного интерфейса
     * использующий метод deleteOne.
     * Основная задача которой удалить одну реализацию времени из бд.
     * @param id Это первый и единственный параметр метода deleteOne, который обозначает номер реализации времени.
     * @return Long Вернёт номер реализации времени.
     * @throws ReleaseNotFoundException При ошибке если такой реализации даты/времени вообще не существуют.
     */
    @Transactional
    @Override
    public Long deleteOne(Long id) throws ReleaseNotFoundException {
        if (id!=null){
            //  проверка на то что реализации вообще существуют
            if (releaseCrud.findByReleaseId(id)==null){
                throw new ReleaseNotFoundException();
            }
            if((projectService.deleteReleaseInProject(id))&&(taskService.deleteReleaseInTask(id))){
                releaseCrud.deleteById(id);
            }
        }
        return id;
    }

    /**
     * Это основной метод обновить одну реализацию времени, из стандартного интерфейса
     * использующий метод updateOne.
     * Основная задача которой обновить одну реализацию времени в бд.
     * @param id Это первый параметр метода updateOne, который обозначает номер реализации времени в бд.
     * @param releaseDto Это второй параметр метода updateOne, который обозначает данные реализации времени.
     * @return ReleaseDto Вернёт реализацию времени.
     * @throws ReleaseNotFoundException При ошибке если такой реализации даты/времени вообще не существуют.
     * @throws ReleaseExistsException При ошибке если такая реализация существует.
     * @throws ReleaseDateFormatException При ошибке если неверный формат даты/времени реализации.
     */
    @Transactional
    @Override // ----------------- обновить один проект
    public ReleaseDto updateOne(Long id, ReleaseDto releaseDto) throws ReleaseNotFoundException, ReleaseExistsException, ReleaseDateFormatException {

        //  проверка на то что реализации вообще существуют
        if (releaseCrud.findByReleaseId(id)==null){
            throw new ReleaseNotFoundException();
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат времени
        //Calendar cal = Calendar.getInstance(); // вытащить дату из системы

        //  проверка на формат
        try { // присваивание новых значений из форматированных под стандарт данных прешедших значений
            releaseDto.setDataStart(dateFormat.format(dateFormat.parse(releaseDto.getDataStart())));
            releaseDto.setDataEnd(dateFormat.format(dateFormat.parse(releaseDto.getDataEnd())));
        }
        catch (Exception e){
            throw new ReleaseDateFormatException();
        }

        ReleaseEntity releaseEntityNew = ReleaseMapper.INSTANCE.toEntity(releaseDto);
        ReleaseEntity releaseEntity = releaseCrud.findByReleaseId(id);

        //  проверка
        if (releaseCrud.findByDataStartAndDataEnd(releaseEntityNew.getDataStart(), releaseEntityNew.getDataEnd())!=null){
            throw new ReleaseExistsException();
        }

        // присваивание новых значений
        releaseEntity.setDataStart(releaseEntityNew.getDataStart());
        releaseEntity.setDataEnd(releaseEntityNew.getDataEnd());

        // сохранение
        releaseCrud.save(releaseEntity);
        return ReleaseMapper.INSTANCE.toDto(releaseEntity);
    }
}
