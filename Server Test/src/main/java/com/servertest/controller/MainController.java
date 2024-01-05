package com.servertest.controller;

import com.servertest.dto.RestaurantDTO;
import com.servertest.dto.SearchDataDTO;
import com.servertest.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RestController
public class MainController {
    @Autowired
    private MainService mainService;

    @GetMapping("/main")
    public void get_Main(){
        System.out.println("get_Main");
    }

    @PostMapping("/restaurantApi")
    @ResponseBody
    public List<RestaurantDTO> restaurant_get_api(@RequestBody SearchDataDTO searchDataDTO){
        try {
            return mainService.get_api_info(searchDataDTO);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
