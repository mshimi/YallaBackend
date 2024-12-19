package org.example.yalla_api.common.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    final private ModelMapper modelMapper ;

    public Mapper(){
        this. modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Bean
    public ModelMapper getModelMapper(){

        return modelMapper;
    }

}
