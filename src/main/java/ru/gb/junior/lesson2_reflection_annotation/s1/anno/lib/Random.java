package ru.gb.junior.lesson2_reflection_annotation.s1.anno.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
Собственная аннотация в Java

  @Retention типа область видимости права доступа
  SOURCE - доступна только в момент компиляции (после стирается) через Reflection и байт-код ее никак не достать
(Нужна например когда свой компилятор, допустим свой lombok пишем) @Override например такая скомпилировали и забыли
  CLASS - после компиляции не стирается, но доступна только в байт-коде в runtime reflection не читает ее
(нужна к примеру когда пишем свою VM)
  RUNTIME - reflection читает полный доступ (90% программ)

  @Target - над чем эту аннотацию можно ставить в данном случае над полями ElementType.FIELD
  пример: аннотация @Override @Target(ElementType.METHOD) ставится только над методами
  с 18 java если @Target не ставим аннотацию можно поставить везде
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Random {

    /*
    Нужно, для того чтобы задавать параметры аннотации @Random(min = 50, max = 51)
    если default 0 то можно не указывать параметры

    если бы у нас было написано например String value();
    то при объявлении его тип можно явно не писать @Random("S")
    в остальных же случаях @Random(arg = "S")
     */
    int min() default 0;

    int max() default 100;

     /*
     Ограничения на параметры аннотаций
     все примитивы int, long, ...
     String
     любой enum
     Class
     массив всего, что выше

     Нельзя создавать объекты при объявлении @Random(cl = new Person) потому что должен быть static
     но можно @Random(cl = Person.class)
      */


//    String s();
//
//    Gender gender();
//
//    Class<?> cl();
//
//    String[] ss();

    /*
    массивы
    String [] arr
    @Random(arr = {"1","sdf","1g4"})
    @Random(arr = "1")

    String [] value
    @Random({"1","sdf","1g4"})
    @Random("S")
     */

}