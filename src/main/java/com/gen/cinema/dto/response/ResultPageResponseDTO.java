package com.gen.cinema.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultPageResponseDTO<T> implements Serializable {
    @JsonProperty
    private List<T> results;
    @JsonProperty
    private Integer pages;
    @JsonProperty
    private Long elements;

    public List<T> getResults() {
        return results;
    }
    public void setResults(List<T> result) {
        this.results = result;
    }
    public Integer getPages() {
        return pages;
    }
    public void setPages(Integer pages) {
        this.pages = pages;
    }
    public Long getElements() {
        return elements;
    }
    public void setElements(Long elements) {
        this.elements = elements;
    }
} 