# UniversityCLI
Console application for university management written in Java.
# University Management System

Консольна система управління університетом, написана на Java.

## Можливості

- CRUD для студентів;
- CRUD для викладачів;
- CRUD для курсів;
- зарахування студентів на курси;
- виставлення оцінок;
- облік оплати;
- обчислення GPA;
- академічний транскрипт;
- пошук і фільтрація;
- Top-N студентів;
- bubble sort за ПІБ;
- обробка некоректного вводу.

## Структура

```text
src/
├── Main.java
├── entities/
├── enums/
├── interfaces/
├── services/
└── util/
# University Management System

Консольна система управління університетом, створена на Java.

## Функціональність

- CRUD-операції для студентів;
- CRUD-операції для викладачів;
- CRUD-операції для курсів;
- зарахування студентів на курси;
- виставлення оцінок;
- облік оплати;
- розрахунок GPA;
- академічний транскрипт;
- пошук студентів за ПІБ або email;
- фільтрація студентів і курсів;
- список неоплачених курсів;
- Top-N студентів за GPA;
- bubble sort студентів за ПІБ;
- обробка некоректного введення.

## Структура проєкту

```text
src/
├── Main.java
├── entities/
│   ├── Person.java
│   ├── Student.java
│   ├── Teacher.java
│   ├── Course.java
│   └── Enrollment.java
├── enums/
│   ├── Grade.java
│   ├── StudentStatus.java
│   └── TeacherPosition.java
├── interfaces/
│   └── Payable.java
├── services/
│   ├── StudentService.java
│   ├── TeacherService.java
│   ├── CourseService.java
│   └── EnrollmentService.java
└── util/
    └── GPAUtils.java
```

## Використані технології

- Java 17
- об’єктно-орієнтоване програмування;
- наслідування та інкапсуляція;
- інтерфейси та enum;
- масиви;
- Scanner;
- винятки;
- Git і GitHub.


## Автор

Артем
