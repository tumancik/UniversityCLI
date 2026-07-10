import entities.Course;
import entities.Enrollment;
import entities.Student;
import entities.Teacher;
import enums.Grade;
import enums.StudentStatus;
import enums.TeacherPosition;
import services.CourseService;
import services.EnrollmentService;
import services.StudentService;
import services.TeacherService;
import util.GPAUtils;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService =
            new EnrollmentService();

    public static void main(String[] args) {
        addDemoData();

        boolean running = true;

        while (running) {
            printMainMenu();

            try {
                int choice = readInt("Оберіть пункт: ");

                switch (choice) {
                    case 1:
                        studentMenu();
                        break;
                    case 2:
                        teacherMenu();
                        break;
                    case 3:
                        courseMenu();
                        break;
                    case 4:
                        enrollmentMenu();
                        break;
                    case 5:
                        reportsMenu();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Роботу програми завершено.");
                        break;
                    default:
                        System.out.println("Невідомий пункт меню.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("======================================");
        System.out.println("   UNIVERSITY MANAGEMENT SYSTEM");
        System.out.println("======================================");
        System.out.println("1. Студенти");
        System.out.println("2. Викладачі");
        System.out.println("3. Курси");
        System.out.println("4. Зарахування");
        System.out.println("5. Звіти та пошук");
        System.out.println("0. Вихід");
    }

    // =========================
    // STUDENTS
    // =========================

    private static void studentMenu() {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("----- СТУДЕНТИ -----");
            System.out.println("1. Показати всіх студентів");
            System.out.println("2. Додати студента");
            System.out.println("3. Оновити студента");
            System.out.println("4. Видалити студента");
            System.out.println("5. Фільтр за статусом");
            System.out.println("6. Фільтр за роком навчання");
            System.out.println("7. Сортування за ПІБ");
            System.out.println("0. Назад");

            try {
                int choice = readInt("Оберіть пункт: ");

                switch (choice) {
                    case 1:
                        printStudents(studentService.getAllStudents());
                        break;
                    case 2:
                        addStudent();
                        break;
                    case 3:
                        updateStudent();
                        break;
                    case 4:
                        deleteStudent();
                        break;
                    case 5:
                        filterStudentsByStatus();
                        break;
                    case 6:
                        filterStudentsByYear();
                        break;
                    case 7:
                        studentService.sortByNameBubbleSort();
                        printStudents(studentService.getAllStudents());
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Невідомий пункт меню.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addStudent() {
        String name = readText("ПІБ: ");
        String email = readText("Email: ");
        String phone = readText("Телефон: ");
        int year = readInt("Рік навчання (1-6): ");

        printStudentStatuses();
        StudentStatus status = readStudentStatus();

        Student student = new Student(
                name,
                email,
                phone,
                year,
                status
        );

        studentService.addStudent(student);
        System.out.println("Студента додано. ID: "
                + student.getStudentId());
    }

    private static void updateStudent() {
        String id = readText("ID студента: ");

        Student student = studentService.findById(id);

        if (student == null) {
            throw new IllegalArgumentException("Студента не знайдено.");
        }

        String name = readText("Нове ПІБ: ");
        String email = readText("Новий email: ");
        String phone = readText("Новий телефон: ");
        int year = readInt("Новий рік навчання: ");

        printStudentStatuses();
        StudentStatus status = readStudentStatus();

        studentService.updateStudent(
                id,
                name,
                email,
                phone,
                year,
                status
        );

        System.out.println("Дані студента оновлено.");
    }

    private static void deleteStudent() {
        String id = readText("ID студента: ");

        if (studentService.deleteStudent(id)) {
            System.out.println("Студента видалено.");
        } else {
            System.out.println("Студента не знайдено.");
        }
    }

    private static void filterStudentsByStatus() {
        printStudentStatuses();
        StudentStatus status = readStudentStatus();

        Student[] students =
                studentService.filterByStatus(status);

        printStudents(students);
    }

    private static void filterStudentsByYear() {
        int year = readInt("Рік навчання: ");

        Student[] students =
                studentService.filterByYear(year);

        printStudents(students);
    }

    // =========================
    // TEACHERS
    // =========================

    private static void teacherMenu() {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("----- ВИКЛАДАЧІ -----");
            System.out.println("1. Показати всіх викладачів");
            System.out.println("2. Додати викладача");
            System.out.println("3. Оновити викладача");
            System.out.println("4. Видалити викладача");
            System.out.println("5. Фільтр за посадою");
            System.out.println("0. Назад");

            try {
                int choice = readInt("Оберіть пункт: ");

                switch (choice) {
                    case 1:
                        printTeachers(
                                teacherService.getAllTeachers()
                        );
                        break;
                    case 2:
                        addTeacher();
                        break;
                    case 3:
                        updateTeacher();
                        break;
                    case 4:
                        deleteTeacher();
                        break;
                    case 5:
                        filterTeachersByPosition();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Невідомий пункт меню.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addTeacher() {
        String name = readText("ПІБ: ");
        String email = readText("Email: ");
        String phone = readText("Телефон: ");

        printTeacherPositions();
        TeacherPosition position = readTeacherPosition();

        Teacher teacher = new Teacher(
                name,
                email,
                phone,
                position
        );

        teacherService.addTeacher(teacher);
        System.out.println("Викладача додано. ID: "
                + teacher.getTeacherId());
    }

    private static void updateTeacher() {
        String id = readText("ID викладача: ");

        Teacher teacher = teacherService.findById(id);

        if (teacher == null) {
            throw new IllegalArgumentException(
                    "Викладача не знайдено."
            );
        }

        String name = readText("Нове ПІБ: ");
        String email = readText("Новий email: ");
        String phone = readText("Новий телефон: ");

        printTeacherPositions();
        TeacherPosition position = readTeacherPosition();

        teacherService.updateTeacher(
                id,
                name,
                email,
                phone,
                position
        );

        System.out.println("Дані викладача оновлено.");
    }

    private static void deleteTeacher() {
        String id = readText("ID викладача: ");

        if (teacherService.deleteTeacher(id)) {
            System.out.println("Викладача видалено.");
        } else {
            System.out.println("Викладача не знайдено.");
        }
    }

    private static void filterTeachersByPosition() {
        printTeacherPositions();
        TeacherPosition position = readTeacherPosition();

        Teacher[] teachers =
                teacherService.filterByPosition(position);

        printTeachers(teachers);
    }

    // =========================
    // COURSES
    // =========================

    private static void courseMenu() {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("----- КУРСИ -----");
            System.out.println("1. Показати всі курси");
            System.out.println("2. Додати курс");
            System.out.println("3. Оновити курс");
            System.out.println("4. Видалити курс");
            System.out.println("5. Фільтр за кредитами");
            System.out.println("6. Фільтр за викладачем");
            System.out.println("0. Назад");

            try {
                int choice = readInt("Оберіть пункт: ");

                switch (choice) {
                    case 1:
                        printCourses(courseService.getAllCourses());
                        break;
                    case 2:
                        addCourse();
                        break;
                    case 3:
                        updateCourse();
                        break;
                    case 4:
                        deleteCourse();
                        break;
                    case 5:
                        filterCoursesByCredits();
                        break;
                    case 6:
                        filterCoursesByTeacher();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Невідомий пункт меню.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addCourse() {
        String name = readText("Назва курсу: ");
        int credits = readInt("Кількість кредитів: ");

        printTeachers(teacherService.getAllTeachers());

        String teacherId = readText("ID викладача: ");
        Teacher teacher = teacherService.findById(teacherId);

        if (teacher == null) {
            throw new IllegalArgumentException(
                    "Викладача не знайдено."
            );
        }

        double price = readDouble("Вартість курсу: ");

        Course course = new Course(
                name,
                credits,
                teacher,
                price
        );

        courseService.addCourse(course);
        System.out.println("Курс додано. ID: " + course.getId());
    }

    private static void updateCourse() {
        int id = readInt("ID курсу: ");

        Course course = courseService.findById(id);

        if (course == null) {
            throw new IllegalArgumentException("Курс не знайдено.");
        }

        String name = readText("Нова назва: ");
        int credits = readInt("Нова кількість кредитів: ");

        printTeachers(teacherService.getAllTeachers());

        String teacherId = readText("ID нового викладача: ");
        Teacher teacher = teacherService.findById(teacherId);

        if (teacher == null) {
            throw new IllegalArgumentException(
                    "Викладача не знайдено."
            );
        }

        double price = readDouble("Нова вартість: ");

        courseService.updateCourse(
                id,
                name,
                credits,
                teacher,
                price
        );

        System.out.println("Курс оновлено.");
    }

    private static void deleteCourse() {
        int id = readInt("ID курсу: ");

        if (courseService.deleteCourse(id)) {
            System.out.println("Курс видалено.");
        } else {
            System.out.println("Курс не знайдено.");
        }
    }

    private static void filterCoursesByCredits() {
        int credits = readInt("Кількість кредитів: ");

        Course[] courses =
                courseService.filterByCredits(credits);

        printCourses(courses);
    }

    private static void filterCoursesByTeacher() {
        printTeachers(teacherService.getAllTeachers());

        String id = readText("ID викладача: ");
        Teacher teacher = teacherService.findById(id);

        if (teacher == null) {
            throw new IllegalArgumentException(
                    "Викладача не знайдено."
            );
        }

        Course[] courses =
                courseService.filterByTeacher(teacher);

        printCourses(courses);
    }

    // =========================
    // ENROLLMENTS
    // =========================

    private static void enrollmentMenu() {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("----- ЗАРАХУВАННЯ -----");
            System.out.println("1. Показати всі зарахування");
            System.out.println("2. Зарахувати студента");
            System.out.println("3. Виставити оцінку");
            System.out.println("4. Позначити оплату");
            System.out.println("5. Видалити зарахування");
            System.out.println("6. Транскрипт студента");
            System.out.println("0. Назад");

            try {
                int choice = readInt("Оберіть пункт: ");

                switch (choice) {
                    case 1:
                        printEnrollments(
                                enrollmentService
                                        .getAllEnrollments()
                        );
                        break;
                    case 2:
                        enrollStudent();
                        break;
                    case 3:
                        setEnrollmentGrade();
                        break;
                    case 4:
                        markEnrollmentPaid();
                        break;
                    case 5:
                        deleteEnrollment();
                        break;
                    case 6:
                        printStudentTranscript();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Невідомий пункт меню.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void enrollStudent() {
        printStudents(studentService.getAllStudents());

        String studentId = readText("ID студента: ");
        Student student = studentService.findById(studentId);

        if (student == null) {
            throw new IllegalArgumentException(
                    "Студента не знайдено."
            );
        }

        printCourses(courseService.getAllCourses());

        int courseId = readInt("ID курсу: ");
        Course course = courseService.findById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("Курс не знайдено.");
        }

        String semester = readText("Семестр: ");

        enrollmentService.enrollStudent(
                student,
                course,
                semester
        );

        System.out.println("Студента зараховано на курс.");
    }

    private static void setEnrollmentGrade() {
        printEnrollments(
                enrollmentService.getAllEnrollments()
        );

        int enrollmentId = readInt("ID зарахування: ");

        printGrades();
        Grade grade = readGrade();

        enrollmentService.setGrade(enrollmentId, grade);
        System.out.println("Оцінку встановлено.");
    }

    private static void markEnrollmentPaid() {
        printEnrollments(
                enrollmentService.getAllEnrollments()
        );

        int enrollmentId = readInt("ID зарахування: ");

        enrollmentService.markPaid(enrollmentId);
        System.out.println("Оплату підтверджено.");
    }

    private static void deleteEnrollment() {
        int id = readInt("ID зарахування: ");

        if (enrollmentService.deleteEnrollment(id)) {
            System.out.println("Зарахування видалено.");
        } else {
            System.out.println("Зарахування не знайдено.");
        }
    }

    private static void printStudentTranscript() {
        String studentId = readText("ID студента: ");

        Student student = studentService.findById(studentId);

        if (student == null) {
            throw new IllegalArgumentException(
                    "Студента не знайдено."
            );
        }

        Enrollment[] enrollments =
                enrollmentService.getStudentEnrollments(student);

        System.out.println();
        System.out.println("===== АКАДЕМІЧНИЙ ТРАНСКРИПТ =====");
        System.out.println("Студент: " + student.getName());
        System.out.println("ID: " + student.getStudentId());
        System.out.println("----------------------------------");

        printEnrollments(enrollments);

        double gpa = GPAUtils.calculateGPA(enrollments);

        System.out.printf("GPA: %.2f%n", gpa);
    }

    // =========================
    // REPORTS
    // =========================

    private static void reportsMenu() {
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("----- ЗВІТИ ТА ПОШУК -----");
            System.out.println("1. Пошук студента");
            System.out.println("2. Неоплачені курси");
            System.out.println("3. GPA студента");
            System.out.println("4. Середній GPA курсу");
            System.out.println("5. Середній GPA семестру");
            System.out.println("6. Top-N студентів");
            System.out.println("0. Назад");

            try {
                int choice = readInt("Оберіть пункт: ");

                switch (choice) {
                    case 1:
                        searchStudents();
                        break;
                    case 2:
                        printUnpaidEnrollments();
                        break;
                    case 3:
                        printStudentGPA();
                        break;
                    case 4:
                        printCourseAverageGPA();
                        break;
                    case 5:
                        printSemesterAverageGPA();
                        break;
                    case 6:
                        printTopStudents();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Невідомий пункт меню.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void searchStudents() {
        String query = readText(
                "Введіть частину ПІБ або email: "
        );

        Student[] students =
                studentService.searchByNameOrEmail(query);

        printStudents(students);
    }

    private static void printUnpaidEnrollments() {
        Enrollment[] enrollments =
                enrollmentService.getAllEnrollments();

        boolean found = false;

        for (Enrollment enrollment : enrollments) {
            if (!enrollment.isPaid()) {
                System.out.println(enrollment);
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "Неоплачених зарахувань немає."
            );
        }
    }

    private static void printStudentGPA() {
        String id = readText("ID студента: ");

        Student student = studentService.findById(id);

        if (student == null) {
            throw new IllegalArgumentException(
                    "Студента не знайдено."
            );
        }

        Enrollment[] enrollments =
                enrollmentService.getStudentEnrollments(student);

        double gpa = GPAUtils.calculateGPA(enrollments);

        System.out.printf(
                "GPA студента %s: %.2f%n",
                student.getName(),
                gpa
        );
    }

    private static void printCourseAverageGPA() {
        int courseId = readInt("ID курсу: ");

        Course course = courseService.findById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("Курс не знайдено.");
        }

        Enrollment[] enrollments =
                enrollmentService.getCourseEnrollments(course);

        double gpa = GPAUtils.calculateGPA(enrollments);

        System.out.printf(
                "Середній GPA курсу \"%s\": %.2f%n",
                course.getName(),
                gpa
        );
    }

    private static void printSemesterAverageGPA() {
        String semester = readText("Семестр: ");

        Enrollment[] all =
                enrollmentService.getAllEnrollments();

        Enrollment[] temporary =
                new Enrollment[all.length];

        int count = 0;

        for (Enrollment enrollment : all) {
            if (enrollment.getSemester()
                    .equalsIgnoreCase(semester)) {

                temporary[count++] = enrollment;
            }
        }

        Enrollment[] result = new Enrollment[count];

        for (int i = 0; i < count; i++) {
            result[i] = temporary[i];
        }

        double gpa = GPAUtils.calculateGPA(result);

        System.out.printf(
                "Середній GPA за семестр %s: %.2f%n",
                semester,
                gpa
        );
    }

    private static void printTopStudents() {
        int topCount = readInt("Кількість студентів: ");

        Student[] students =
                studentService.getAllStudents();

        if (topCount < 1) {
            throw new IllegalArgumentException(
                    "Кількість повинна бути більшою за 0."
            );
        }

        if (topCount > students.length) {
            topCount = students.length;
        }

        double[] gpaValues = new double[students.length];

        for (int i = 0; i < students.length; i++) {
            Enrollment[] enrollments =
                    enrollmentService
                            .getStudentEnrollments(students[i]);

            gpaValues[i] =
                    GPAUtils.calculateGPA(enrollments);
        }

        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0;
                 j < students.length - i - 1;
                 j++) {

                if (gpaValues[j] < gpaValues[j + 1]) {
                    double temporaryGpa = gpaValues[j];
                    gpaValues[j] = gpaValues[j + 1];
                    gpaValues[j + 1] = temporaryGpa;

                    Student temporaryStudent = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temporaryStudent;
                }
            }
        }

        System.out.println("===== TOP СТУДЕНТІВ =====");

        for (int i = 0; i < topCount; i++) {
            System.out.printf(
                    "%d. %s — GPA %.2f%n",
                    i + 1,
                    students[i].getName(),
                    gpaValues[i]
            );
        }
    }

    // =========================
    // PRINT METHODS
    // =========================

    private static void printStudents(Student[] students) {
        if (students.length == 0) {
            System.out.println("Студентів немає.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void printTeachers(Teacher[] teachers) {
        if (teachers.length == 0) {
            System.out.println("Викладачів немає.");
            return;
        }

        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    private static void printCourses(Course[] courses) {
        if (courses.length == 0) {
            System.out.println("Курсів немає.");
            return;
        }

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void printEnrollments(
            Enrollment[] enrollments
    ) {
        if (enrollments.length == 0) {
            System.out.println("Зарахувань немає.");
            return;
        }

        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    // =========================
    // ENUM INPUT
    // =========================

    private static void printStudentStatuses() {
        System.out.println(
                "Статуси: ACTIVE, ON_LEAVE, EXPELLED, GRADUATED"
        );
    }

    private static StudentStatus readStudentStatus() {
        String value = readText("Статус: ");

        try {
            return StudentStatus.valueOf(
                    value.toUpperCase()
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Неправильний статус студента."
            );
        }
    }

    private static void printTeacherPositions() {
        System.out.println(
                "Посади: ASSISTANT, LECTURER, PROFESSOR"
        );
    }

    private static TeacherPosition readTeacherPosition() {
        String value = readText("Посада: ");

        try {
            return TeacherPosition.valueOf(
                    value.toUpperCase()
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Неправильна посада викладача."
            );
        }
    }

    private static void printGrades() {
        System.out.println("Оцінки: A, B, C, D, F, NA");
    }

    private static Grade readGrade() {
        String value = readText("Оцінка: ");

        try {
            return Grade.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Неправильна оцінка."
            );
        }
    }

    // =========================
    // INPUT METHODS
    // =========================

    private static String readText(String message) {
        System.out.print(message);

        String value = scanner.nextLine().trim();

        if (value.isEmpty()) {
            throw new IllegalArgumentException(
                    "Поле не може бути порожнім."
            );
        }

        return value;
    }

    private static int readInt(String message) {
        System.out.print(message);

        String value = scanner.nextLine().trim();

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Потрібно ввести ціле число."
            );
        }
    }

    private static double readDouble(String message) {
        System.out.print(message);

        String value = scanner.nextLine()
                .trim()
                .replace(',', '.');

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Потрібно ввести число."
            );
        }
    }

    // =========================
    // DEMO DATA
    // =========================

    private static void addDemoData() {
        Teacher teacher1 = new Teacher(
                "Олександр Бондар",
                "bondar@university.ua",
                "+380501112233",
                TeacherPosition.PROFESSOR
        );

        Teacher teacher2 = new Teacher(
                "Наталія Коваль",
                "koval@university.ua",
                "+380671234567",
                TeacherPosition.LECTURER
        );

        teacherService.addTeacher(teacher1);
        teacherService.addTeacher(teacher2);

        Student student1 = new Student(
                "Андрій Мельник",
                "melnyk@student.ua",
                "+380931112233",
                2,
                StudentStatus.ACTIVE
        );

        Student student2 = new Student(
                "Марія Савчук",
                "savchuk@student.ua",
                "+380961234567",
                1,
                StudentStatus.ACTIVE
        );

        Student student3 = new Student(
                "Богдан Левченко",
                "levchenko@student.ua",
                "+380991234567",
                3,
                StudentStatus.ON_LEAVE
        );

        studentService.addStudent(student1);
        studentService.addStudent(student2);
        studentService.addStudent(student3);

        Course course1 = new Course(
                "Java Programming",
                5,
                teacher1,
                2500
        );

        Course course2 = new Course(
                "Software Engineering",
                4,
                teacher2,
                2100
        );

        courseService.addCourse(course1);
        courseService.addCourse(course2);

        enrollmentService.enrollStudent(
                student1,
                course1,
                "2025-1"
        );

        enrollmentService.enrollStudent(
                student1,
                course2,
                "2025-1"
        );

        enrollmentService.enrollStudent(
                student2,
                course1,
                "2025-1"
        );

        Enrollment[] enrollments =
                enrollmentService.getAllEnrollments();

        enrollmentService.setGrade(
                enrollments[0].getId(),
                Grade.A
        );

        enrollmentService.setGrade(
                enrollments[1].getId(),
                Grade.B
        );

        enrollmentService.setGrade(
                enrollments[2].getId(),
                Grade.C
        );

        enrollmentService.markPaid(
                enrollments[0].getId()
        );
    }
}
