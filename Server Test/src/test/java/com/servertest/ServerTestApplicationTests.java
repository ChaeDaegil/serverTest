package com.servertest;

import com.servertest.dto.RestaurantDTO;
import com.servertest.dto.SearchDataDTO;
import com.servertest.service.MainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
@SpringBootTest
class ServerTestApplicationTests {


    @Test
    void contextLoads() {
    }
    @Autowired
    MainService mainService;
    @Test
    void apiTest() throws Exception {
        SearchDataDTO searchDataDTO = SearchDataDTO.builder()
                .si("")
                .gnn("달서구")
                .gu("")
                .page(1)
                .perPage(10)
                .build();
        List<RestaurantDTO> restaurantDTOList = mainService.get_api_info(searchDataDTO);
        for (RestaurantDTO restaurantDTO : restaurantDTOList) {
            System.out.println(restaurantDTO.toString());
        }
    }
}
