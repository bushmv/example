# Example

Example - android приложение, написанное на Kotlin для изучения английского языка. Основная идея - запоминание слов в контексте, повторяя предложения, где эти слова встречаются. Все слова разбиты по категориям (например, предлоги, слова на тему дома, семьи и т.д.) и сложности(по уровню английского, beginner, elementary и т.д.). Каждое предложение - пример употребления слова, поэтому приложение называется Example.

Скачать приложение можно здесь https://play.google.com/store/apps/details?id=com.bush_example_app.bushv.bush_example_app

Несколько изображений для наглядности

![alt text](https://github.com/bushmv/example/blob/master/images_for_readme/e1.jpg)
![alt text](https://github.com/bushmv/example/blob/master/images_for_readme/e2.jpg)
![alt text](https://github.com/bushmv/example/blob/master/images_for_readme/e3.jpg)
![alt text](https://github.com/bushmv/example/blob/master/images_for_readme/e4.jpg)
![alt text](https://github.com/bushmv/example/blob/master/images_for_readme/e5.jpg)
![alt text](https://github.com/bushmv/example/blob/master/images_for_readme/e6.jpg)

### Если вы хотите изменить язык, но использовать ту же логику, сделайте следующее:

1. Склонируйте этот репозиторий к себе
2. Откройте проект в Android Studio
3. Найдите директорию assets/db. В ней в текстовом формате хранятся все примеры и темы из приложения, которые заносятся в базу данных Room при первой установке. Удалите все примеры и заполните эту директорию любыми своими примерами, сохраняя следующую схему (см. класс DatabaseFiller)

первая строка - названи темы в следующем формате:

{название на другом языке(EN)}/{название на родном языке(RU)}|{описание темы}|{уровень языка}

уровни языка представлены в enum классе EnglishLevel и имеют следующие значения (от 0 до 4 включительно):
- BEGINNER(0)
- ELEMENTARY(1)
- INTERMEDIATE(2)
- UPPER_INTERMEDIATE(3)
- ADVANCED(4)
В уровень языка нужно подставить число от 0 до 4

В следующий строках идут примеры из данной темы по следующей схеме

{слово}|{перевод слова}|{предложение на не родном языке}|{предложение на родном языке}

Предложения должны содержать в себе изучаемое слова, заключенное в кавычки "{" и "}". Это нужно для выделения слова в предложение
