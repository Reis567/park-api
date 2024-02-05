package com.reis.demo.park.api.web.dto;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableDTO {
    private List content = new ArrayList<>();

    private boolean first ;

    private boolean last ;

    @JsonProperty("page")
    private int number;

    private int size;

    @JsonProperty("pageElements")
    private int numberOfElements;

    private int totalPages;

    private int totalElements;
}
