package com.example.SimbirSoft_2021.thread;

import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.enumertion.StatusEnum;
import com.example.SimbirSoft_2021.mappers.ReleaseMapper;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

// ----------------------------------------------------------------------------------------------------  TimeLife
@Component
@Scope("prototype")
public class ReleaseThread{

    private List<ReleaseDto> arrayListReleaseDto = new ArrayList<>();

    //@Autowired
    //private final ProjectCrud projectCrud;

    private final ProjectCrud projectCrud;
    private final TaskCrud taskCrud;
    private final ReleaseCrud releaseCrud;

    public ReleaseThread(ProjectCrud projectCrud, TaskCrud taskCrud, ReleaseCrud releaseCrud) {
        List<ReleaseEntity> releaseEntityList = releaseCrud.findAll();

        //  проверка на то что реализации вообще существуют
        if (releaseEntityList!=null){

            // перевод коллекции из одного вида в другой
            List<ReleaseDto> releaseDtoList = releaseEntityList.stream().map(x-> ReleaseMapper.INSTANCE.toDto(x)).collect(Collectors.toList());

            this.arrayListReleaseDto = releaseDtoList;
        }

        this.projectCrud = projectCrud;
        this.taskCrud = taskCrud;
        this.releaseCrud = releaseCrud;
    }

    public void set(ReleaseDto releaseDto) {
        this.arrayListReleaseDto.add(releaseDto);
    }

    //@Scheduled(fixedRate = 1000)
    public void run() throws Exception {
        if (arrayListReleaseDto!=null) {
            List<ReleaseDto> newArrayReleaseDto = new ArrayList<>();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH); // формат времени
            Calendar currentCalendar = Calendar.getInstance(); // вытащить дату из системы
            String currentTime = dateFormat.format(currentCalendar.getTime());
            System.out.println(currentTime);
            //dateFormat.format(currentTime.getTime())); // отправить дату в переменую
            for(ReleaseDto m:arrayListReleaseDto){

                ProjectEntity projectEntity = projectCrud.findByReleaseId(m.getReleaseId());
                TaskEntity taskEntity = taskCrud.findByReleaseId(m.getReleaseId());

                // Если дата реализации больше текущего времени, то он её блокирует
                if((dateFormat.parse(m.getDataStart())).after(dateFormat.parse(currentTime))){
                    if(projectEntity!=null){
                        projectEntity.setProjectStatus(StatusEnum.BACKLOG);
                    }
                    if(taskEntity!=null){
                        taskEntity.setTaskStatus(StatusEnum.BACKLOG);
                    }
                } else { // иначе она в процессе
                    if(projectEntity!=null){
                        if (projectEntity.getProjectStatus()!=StatusEnum.DONE){
                            projectEntity.setProjectStatus(StatusEnum.IN_PROGRESS);
                        }
                    }
                    if(taskEntity!=null){
                        if (taskEntity.getTaskStatus()!=StatusEnum.DONE){
                            taskEntity.setTaskStatus(StatusEnum.IN_PROGRESS);
                        }
                    }
                }

                // Если дата реализации больше текущего времени, то он её закрывает
                if((dateFormat.parse(m.getDataEnd())).before(dateFormat.parse(currentTime))) {
                    if(projectEntity!=null){
                        if (projectEntity.getProjectStatus()!=StatusEnum.DONE){
                            projectEntity.setProjectStatus(StatusEnum.FAILURE);
                        }
                    }
                    if(taskEntity!=null){
                        if (taskEntity.getTaskStatus()!=StatusEnum.DONE){
                            taskEntity.setTaskStatus(StatusEnum.FAILURE);
                        }
                    }
                    m=null;
                }

                if(projectEntity!=null){
                    projectCrud.save(projectEntity);
                }
                if(taskEntity!=null){
                    taskCrud.save(taskEntity);
                }

                if (m!=null) {
                    newArrayReleaseDto.add(m);
                }
            }
            arrayListReleaseDto = newArrayReleaseDto;
        }
    }
}
