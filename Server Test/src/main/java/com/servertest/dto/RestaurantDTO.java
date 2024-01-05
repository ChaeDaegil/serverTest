package com.servertest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RestaurantDTO {
    @Id
    private int id;
    private String name;
    private int viewCount;
    private String si;
    private String gnn;
    private String gu;
}
