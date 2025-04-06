# Calorie tracking project
Данный проект предназначен для контроля ежедневного потребления калорий пользователя

## Функциональность 
1. Пользователи. Добавление пользователей с параметрами:

- ID

- Имя

- Email

- Возраст

- Вес

- Рост

- Цель (Похудение, Поддержание, Набор массы)

 

На основе данных автоматически рассчитывает дневную норму калорий по формуле Харриса-Бенедикта

2. Блюда. Добавление блюд с параметрами:

- ID

- Название

- Количество калорий на порцию

- Белки/Жиры/Углеводы

3. Прием пищи. Пользователь может добавлять прием пищи со списком блюд 

4. Отчеты (эндпоинты, без формирования документа):

- отчет за день с суммой всех калорий и приемов пищи;

- проверка, уложился ли пользователь в свою дневную норму калорий;

- история питания по дням.

## Стек
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Swagger
- PostgreSQL
- Lombok

## API Endpoints
Все эндпоинты описаны с помощью Swagger.
Для этого необходимо запустить проект и перейти по [ссылке](http://localhost:8080/swagger-ui/index.html#/)

### Пользователи 
&nbsp;&nbsp;&nbsp;GET
- /users/ &nbsp;&nbsp;&nbsp; получение списка всех пользователей 
- /users/{userId} &nbsp;&nbsp;&nbsp; получение пользователя по id
- /users/{userId}/daily-calorie-norm &nbsp;&nbsp;&nbsp;получение ежедневной нормы калорий пользователя
- /users/{userId}/{date}/calories-compliance&nbsp;&nbsp;&nbsp; отчет, убрался ли пользователь в ежедневную норму + получение съеденных блюд
  
&nbsp;&nbsp;&nbsp;POST
- /users/ &nbsp;&nbsp;&nbsp;создание пользователя

&nbsp;&nbsp;&nbsp;DELETE
- /users/{userId} &nbsp;&nbsp;&nbsp;удаление пользователя

&nbsp;&nbsp;&nbsp;PATCH
- /users/{userId} &nbsp;&nbsp;&nbsp; изменение параметров пользователя

### Блюда
&nbsp;&nbsp;&nbsp;POST
- /foods/&nbsp;&nbsp;&nbsp;создание блюда


&nbsp;&nbsp;&nbsp;GET
- /foods&nbsp;&nbsp;&nbsp;получение списка всех блюд


- /foods/{id}&nbsp;&nbsp;&nbsp;получение блюда по id


&nbsp;&nbsp;&nbsp;DELETE
- /foods/{id}&nbsp;&nbsp;&nbsp;удаление блюда

### Приемы пищи
&nbsp;&nbsp;&nbsp;POST
- /foodintakes/{userId}/{meal}&nbsp;&nbsp;&nbsp; добавление приема пищи

&nbsp;&nbsp;&nbsp;GET
- /foodintakes/{user_id}/{date}&nbsp;&nbsp;&nbsp; получение калорий употребленных пользователем за день

- /foodintakes/daily/{userId}/{date}&nbsp;&nbsp;&nbsp; получение приемов пищи пользователя за день

&nbsp;&nbsp;&nbsp;DELETE
- /foodintakes/{user_id}/{date}/{meal}&nbsp;&nbsp;&nbsp; удаление приема пищи пользователя за день

### Важно
- При добавление пользователя для почты могут использоваться только следующие домены
            "mail.ru",
            "yandex.ru",
            "gmail.com",
            "hotmail.com"
- Для каждого пользователя почта уникальна и не может повторяться
- Допустимые Target (цель пользователя) - **LOSE_WEIGHT** (похудение), **KEEPING_FIT** (поддерживание формы), **WEIGHT_GAIN** (набор массы)
- Допустимые Meal (приемы пищи) - __BREAKFAST__ (завтрак), **LUNCH** (обед), **DINNER** (ужин), **SNACK** (перекус) 
