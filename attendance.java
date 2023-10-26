import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

class Student {
    private String name;
    private boolean[][] attendanceRecords;  // true for present, false for absent

    public Student(String name, int numDaysToKeep) {
        this.name = name;
        this.attendanceRecords = new boolean[numDaysToKeep][2]; // 2 records per day (morning and afternoon)
    }

    public String getName() {
        return name;
    }

    public void markAttendance(int day, int recordIndex) {
        if (day >= 0 && day < attendanceRecords.length && recordIndex >= 0 && recordIndex < 2) {
            attendanceRecords[day][recordIndex] = true;  // Mark attendance as present
        }
    }

    public void markAbsent(int day, int recordIndex) {
        if (day >= 0 && day < attendanceRecords.length && recordIndex >= 0 && recordIndex < 2) {
            attendanceRecords[day][recordIndex] = false;  // Mark attendance as absent
        }
    }

    public double getAttendancePercentage() {
        if (attendanceRecords.length == 0) {
            return 0.0;
        }

        int totalPresent = 0;
        int totalPossible = attendanceRecords.length * 2;

        for (boolean[] dayAttendance : attendanceRecords) {
            for (boolean record : dayAttendance) {
                if (record) {
                    totalPresent++;
                }
            }
        }

        return ((double) totalPresent / totalPossible) * 100;
    }

    public boolean[][] getAttendanceRecords() {
        return attendanceRecords;
    }
}

public class attendance {
    private Map<Integer, Student> students;

    public attendance() {
        students = new HashMap<>();
        populateStudentData();
    }

    private void populateStudentData() {
        students.put(101, new Student("John Doe", 3)); // Store attendance for 3 days
        students.put(102, new Student("Alice Smith", 3)); // Store attendance for 3 days
        // Add more students as needed
    }

    public static void main(String[] args) {
        attendance attendanceSystem = new attendance();
        showOptions(attendanceSystem);
    }

    private static void showOptions(attendance attendanceSystem) {
        String[] options = {"Mark Attendance", "Show Attendance", "Exit"};
        int choice;
        while (true) {
            choice = JOptionPane.showOptionDialog(null, "Select an option", "Attendance Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    markAttendance(attendanceSystem);
                    break;
                case 1:
                    showAttendanceDetails(attendanceSystem);
                    break;
                default:
                    return;
            }
        }
    }

    private static void markAttendance(attendance attendanceSystem) {
        String rollNoText = JOptionPane.showInputDialog("Enter student's roll number:");
        if (rollNoText == null || rollNoText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid roll number.");
            return;
        }

        try {
            int rollNo = Integer.parseInt(rollNoText);
            attendanceSystem.markAttendance(rollNo, 0, 0); // Assuming morning attendance for simplicity
            JOptionPane.showMessageDialog(null, "Attendance marked as Present for student with Roll No " + rollNo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid roll number. Please enter a valid integer.");
        }
    }

    private static void showAttendanceDetails(attendance attendanceSystem) {
        String rollNoText = JOptionPane.showInputDialog("Enter student's roll number:");
        if (rollNoText == null || rollNoText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid roll number.");
            return;
        }

        try {
            int rollNo = Integer.parseInt(rollNoText);
            Student student = attendanceSystem.getStudent(rollNo);
            if (student != null) {
                boolean[][] attendanceRecord = student.getAttendanceRecords();
                String attendanceStatus = attendanceRecord[0][0] ? "Present" : "Absent"; // Assuming morning attendance for simplicity
                JOptionPane.showMessageDialog(null, "Attendance for " + student.getName() + " (Roll No " + rollNo + "): " + attendanceStatus);
            } else {
                JOptionPane.showMessageDialog(null, "Student with Roll No " + rollNo + " not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid roll number. Please enter a valid integer.");
        }
    }
    
    public Student getStudent(int rollNo) {
        return students.get(rollNo);
    }

    public void markAttendance(int rollNo, int day, int recordIndex) {
        Student student = students.get(rollNo);
        if (student != null) {
            student.markAttendance(day, recordIndex);
        }
    }
}
