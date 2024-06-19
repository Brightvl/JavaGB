package ru.gb.junior.lesson2_reflection_annotation.hw;

import ru.gb.junior.lesson2_reflection_annotation.s1.anno.AnnotationsMain;
import ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib.ObjectCreator;
import ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib.Random;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Homework {
    /**
     * В существующий класс ObjectCreator добавить поддержку аннотации RandomDate (по аналогии с Random):
     * 1. Аннотация должна обрабатываться только над полями типа java.util.Date
     * 2. Проверить, что min < max
     * 3. В поле, помеченной аннотацией, нужно вставлять рандомную дату,
     * UNIX-время которой находится в диапазоне [min, max)
     *
     * 4. *** Добавить поддержку для типов Instant, ...
     * 5. *** Добавить атрибут Zone и поддержку для типов LocalDate, LocalDateTime
     */

    /**
     * Примечание: Unix-время - количество миллисекунд, прошедших с 1 января 1970 года по UTC-0.
     */
    public static void main(String[] args) {

        Person rndPerson = ObjectCreator.createObjHW(Person.class);
        System.out.println(rndPerson.birthDate);
        System.out.println(rndPerson.birthInstant);
        System.out.println(rndPerson.birthLocalDate);
        System.out.println(rndPerson.birthLocalDateTime);


    }


    public static class Person {
        @Random
        private int age1;

        @Random(min = 50, max = 51)
        private int age2;

        @RandomDate(min = 1735689600000L)
        private Date birthDate;

        @RandomDate (min = 1735689600000L)
        private Instant birthInstant;

        @RandomDate(min = 1735689600000L,zone = "Europe/Moscow")
        private LocalDate birthLocalDate;

        @RandomDate(min = 1735689600000L,zone = "Europe/Moscow")
        private LocalDateTime birthLocalDateTime;
    }
}

