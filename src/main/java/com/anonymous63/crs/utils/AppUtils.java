package com.anonymous63.crs.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
