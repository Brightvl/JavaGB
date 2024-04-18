# Устанавливаем базовый образ, содержащий JDK
FROM openjdk:latest

# Компиляция
# WORKDIR - директория, в которой Docker выполняет все действия с файлами и папками в контейнере
# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /usr/src/app
# Копируем исходный код приложения внутрь контейнера
COPY ./src/main/java .
# Компилируем Java исходный код
RUN javac -sourcepath . -d out ./ru/gb/core/lesson1/task1/Program.java
# Теперь рабочая директория - директория с файлами .class

# Запуск
# Снова меняем директорию
WORKDIR /usr/src/app/out
# Последняя команда выполнится после запуска контейнера
# Один из варинатов запуска через CMD
# CMD java -classpath . ru.gb.core.lesson1.task1.Program
# Другой из варинатов запуска через ENTRYPOINT
# ENTRYPOINT - Команда или исполняемый файл, который запускается, когда контейнер создается из образа.
ENTRYPOINT ["java","ru.gb.core.lesson1.task1.Program"]