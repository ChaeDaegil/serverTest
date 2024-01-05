package com.servertest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.servertest.dto.RestaurantDTO;
import com.servertest.dto.SearchDataDTO;
import com.servertest.mapper.RestaurantMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class MainService {
    String url = "https://api.odcloud.kr/api/15109950/v1/uddi:1f78fe49-78b4-4784-a5f0-e2c8a60515b4?page={page}&perPage={perPage}&serviceKey={serviceKey}";
    private final String SERVICE_KEY = "0UsHXY0Vbg6cfMm2kSmAIt75FLtRSbYU7gLKMi2WEs17WjFGS24JqTuEOonxnkg2UK2m9025roun1Jd0sRwe4A==";

    @Autowired
    RestaurantMapper restaurantMapper;
    public List<RestaurantDTO> get_api_info(SearchDataDTO searchDataDTO) throws Exception {
        String str = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(searchDataDTO.getPage(),searchDataDTO.getPerPage(),SERVICE_KEY).encode().toUri().toString();
        RestOperations restOperations = new RestTemplate();
        RequestEntity<Void> requestEntity = RequestEntity
                .get(
                        UriComponentsBuilder.fromUriString(url)
                                .buildAndExpand(searchDataDTO.getPage(),searchDataDTO.getPerPage(),SERVICE_KEY).encode().toUri()
                ).build();
        ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
        JsonNode dataNode = rootNode.get("data");

        List<RestaurantDTO> dtoList = new ArrayList<>();

        if (dataNode != null && dataNode.isArray()) {
            for (JsonNode element : dataNode) {
                String gnn = element.get("시군구명").asText();

                if(Objects.equals(gnn, searchDataDTO.getGnn())){
                    RestaurantDTO myDTO = RestaurantDTO.builder()
                            .id(Integer.parseInt(element.get("가맹점ID").toString()))
                            .name(element.get("가맹점명").toString())
                            .si(element.get("시도명").toString())
                            .gnn(element.get("시군구명").toString())
                            .gu(element.get("소재지지번주소").toString())
                            .viewCount(0)
                            .build();

                    dtoList.add(myDTO);
                }
            }
        }
        return update_table(dtoList);
    }

    public List<RestaurantDTO> update_table(List<RestaurantDTO> list){
        List<RestaurantDTO> dtoList = new ArrayList<>();
        list.forEach(restaurant -> {
            RestaurantDTO restaurantDTO = restaurantMapper.check_Restaurant(restaurant);
            if(Objects.isNull(restaurantDTO)){
                restaurantMapper.insert_Restaurant(restaurant);
                dtoList.add(restaurant);
            }
            else{
                restaurantDTO.setViewCount(restaurantDTO.getViewCount()+1);
                restaurantMapper.update_Restaurant(restaurantDTO);
                dtoList.add(restaurantDTO);
            }

        });
        return dtoList;
    }
}
