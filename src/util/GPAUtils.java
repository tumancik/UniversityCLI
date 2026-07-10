package util;

import entities.Enrollment;
import enums.Grade;

public class GPAUtils {

    public static double calculateGPA(Enrollment[] enrollments) {

        if (enrollments == null || enrollments.length == 0) {
            return 0.0;
        }

        double total = 0;
        int count = 0;

        for (Enrollment enrollment : enrollments) {

            if (enrollment == null) {
                continue;
            }

            Grade grade = enrollment.getGrade();

            switch (grade) {
                case A:
                    total += 4.0;
                    count++;
                    break;
                case B:
                    total += 3.0;
                    count++;
                    break;
                case C:
                    total += 2.0;
                    count++;
                    break;
                case D:
                    total += 1.0;
                    count++;
                    break;
                case F:
                    total += 0.0;
                    count++;
                    break;
                case NA:
                    break;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return total / count;
    }

}
