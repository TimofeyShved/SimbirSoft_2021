package com.example.SimbirSoft_2021.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Schema(description = "Реализация (Дата/время)")
public class ReleaseDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id реализации")
    private Long releaseId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(description = "Начало реализации (Дата/время)")
    private String dataStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(description = "Конец реализации (Дата/время)")
    private String dataEnd;

    public ReleaseDto() { // конструктор
    }

    public ReleaseDto(String dataStart, String dataEnd) {
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
    }

    // ----------------------------------------------- гетеры и сетеры


    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(String dataEnd) {
        this.dataEnd = dataEnd;
    }
}