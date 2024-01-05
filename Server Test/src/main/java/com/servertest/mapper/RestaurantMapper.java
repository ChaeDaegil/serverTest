package com.servertest.mapper;

import com.servertest.dto.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestaurantMapper {
    public RestaurantDTO check_Restaurant(RestaurantDTO restaurant);
    public void insert_Restaurant(RestaurantDTO restaurant);
    public void update_Restaurant(RestaurantDTO restaurant);
}
