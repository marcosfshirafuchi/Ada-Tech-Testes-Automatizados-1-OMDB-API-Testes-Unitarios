package com.adatech.IMDB.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class FilmeDTO {
    @JsonProperty("Title")
    @NotBlank(message = "O título não pode ser nulo ou em branco.")
    private String title;

    // Construtor vazio
    public FilmeDTO() {
    }

    // Construtor
    public FilmeDTO(String title) {
        this.title = title;
    }

    // Getters e Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}

