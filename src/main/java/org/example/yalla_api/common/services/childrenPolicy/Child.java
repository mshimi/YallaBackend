package org.example.yalla_api.common.services.childrenPolicy;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;


@Data


public class Child {

    public Child(Integer age){
        this.age = age;
        this.birthDay = calculateBirthDay(age);
    }

    private LocalDate calculateBirthDay(Integer age) {
        return LocalDate.now().minusYears(age);
    }

    public Child(LocalDate birthDay){
        this.birthDay = birthDay;
        this.age = calculateAgeFromBirthDay(birthDay);
    }

    private Integer calculateAgeFromBirthDay(LocalDate birthDay) { return Period.between(birthDay, LocalDate.now()).getYears(); }

    private final  Integer age;
   private final LocalDate birthDay;




}
