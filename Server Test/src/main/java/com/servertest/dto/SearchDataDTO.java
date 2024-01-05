package com.servertest.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDataDTO {
    private String si;
    private String gnn;
    private String gu;
    private int page =0;
    private int perPage=0;
}
