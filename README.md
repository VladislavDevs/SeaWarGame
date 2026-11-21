# Морской бой


## Скомпилировать
javac -encoding UTF-8 -d out $(find . -name "*.java")

## Запустить демо-режим
java -cp out SeaWarProject.Main Тест --demo

## Демо с фиксированным seed (для повторяемости)
java -cp out SeaWarProject.Main Тест --demo --seed 12345