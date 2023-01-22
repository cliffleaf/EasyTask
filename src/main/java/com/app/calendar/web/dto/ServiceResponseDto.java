package com.app.calendar.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServiceResponseDto<T> {
    private String status;
    private T data;
    private List<Integer> isCompleteList;
}