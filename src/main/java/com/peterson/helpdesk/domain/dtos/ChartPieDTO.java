package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class ChartPieDTO {
    List<String> labels;
    List<Integer> series;
}
