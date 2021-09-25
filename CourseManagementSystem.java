import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.*;

public class CourseManagementSystem {
    static ArrayList<Course> COURSES = new ArrayList<>();
    static ArrayList<Student> STUDENTS = new ArrayList<>();
    static ArrayList<Instructor> INSTRUCTORS = new ArrayList<>();

    public CourseManagementSystem() throws Exception {  //constructor

    }

    public static void main(String[] args) throws Exception {
        //so that we don't have to type "static" in all method
        CourseManagementSystem courseManagementSystem = new CourseManagementSystem();
        courseManagementSystem.runApp(args);
    }

    void runApp(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        if (args.length > 0) {
            String scriptFile = args[0];
            Scanner sc = new Scanner(new File(scriptFile));
            while (sc.hasNextLine()) {
                String command = sc.nextLine();
                processCommand(command);
            }
        }
        while (true) {
            System.out.print("ready> "); //prompt users to input  command, data type and filename
            String command = in.nextLine(); // load/  save/ list/ assign...
            processCommand(command);
        }

    }

    public void processCommand(String command) {
        if ("quit".equalsIgnoreCase(command)) {
            System.exit(0);
        }
        String[] cmdArgs = command.split(" ", 3);//instructors/ students/ courses...
        switch (cmdArgs[0].toLowerCase()) {
            case "load":
                if (cmdArgs.length < 3) {
                    System.out.println("xxx");
                    return;
                }
                Load(cmdArgs[1], cmdArgs[2]);
                break;
            case "status":
                Status(cmdArgs[1], cmdArgs[2]);
                break;
            case "save":
                Save(cmdArgs[1], cmdArgs[2]);
                break;
            case "list":
                List(cmdArgs[1]);
                break;
            case "enroll":
                Enroll(cmdArgs[1], cmdArgs[2]);
                break;
            case "assign":
                Assign(cmdArgs[1], cmdArgs[2]);
                break;
            case "unenroll":
                Unenroll(cmdArgs[1], cmdArgs[2]);
                break;
            case "unassign":
                Unassign(cmdArgs[1], cmdArgs[2]);
                break;
        }
    }

    public void Load(String dataType, String filename) {
        //reading text file
        File inputFile = new File(filename);   //filename=Courses.db/ Students.db/ Instructors.db
        try {
            Scanner io = new Scanner(inputFile);  //create a scanner object
            switch (dataType.toLowerCase()) {
                case "students": {
                    int n = 0;
                    while (io.hasNextLine()) {
                        //read the files line by line;
                        n++;
                        String input = io.nextLine();  //e.g. input = 16111 M 3 Peter the Rabbit
                        if (!input.matches("^[0-9]{5}\\s+[M|F]{1}\\s+[1-6]{1}\\s+.+$"))
                        {
                            System.out.println("Invalid student record format [" + input + "] at line " + n);
                            continue;
                        }

                        //split the input
                        String[] splitInput = input.split("\\s+", 4);
                        String studentID = splitInput[0];
                        String studentGender = splitInput[1];
                        String studentMaxLoad = splitInput[2];
                        int sml = Integer.parseInt(studentMaxLoad);
                        String studentName = splitInput[3];

                        Student s = new Student(studentID, studentGender, sml, studentName);
                        STUDENTS.add(s);
                    }
                    System.out.println("Load Students: " + STUDENTS.size() + " students records successfully read from " + "\"" + filename + "\".");
                    break;
                }
                case "instructors": {
                    int n = 0;
                    while (io.hasNextLine()) {
                        //read the files line by line;
                        String input = io.nextLine();  //e.g. input = 2321 F 2 Minerva McGonagall
                        n++;
                        if (!input.matches("^[0-9]{4}\\s+[M|F]{1}\\s+[1-4]{1}\\s+.+$"))
                        {
                            System.out.println("Invalid instructor record format [" + input + "] at line " + n);
                            continue;
                        }
                        //split the input
                        String[] splitInput = input.split("\\s+", 4);
                        String instructorID = splitInput[0];
                        String instructorGender = splitInput[1];
                        String instructorMaxLoad = splitInput[2];
                        int iml = Integer.parseInt(instructorMaxLoad);
                        String instructorName = splitInput[3];

                        Instructor i = new Instructor(instructorID, instructorGender, iml, instructorName); //create objects of  "Instructor" class
                        INSTRUCTORS.add(i);
                    }
                    System.out.println("Load Instructors: " + n + " instructors records successfully read from " + "\"" + filename + "\".");
                    break;
                }
                case "courses": {
                    int n = 0;
                    while (io.hasNextLine()) {
                        //read the files line by line;
                        n++;
                        String input = io.nextLine();  //e.g. input = comp226 W12 F12 F13 Object Oriented Programming
                        if(!input.matches("^[A-Za-z]{4}[0-9]{3}\\s+([M|T|W|R|F]{1}(0[8|9]|1[0-7])\\s+){3}.+$"))
                        {
                            System.out.println("Invalid course record format [" + input + "] at line " + n);
                            continue;
                        }

                        //split the input
                        String[] splitInput = input.split("\\s+", 5);
                        String code = splitInput[0];
                        String timeSlot1 = splitInput[1];
                        String timeSlot2 = splitInput[2];
                        String timeSlot3 = splitInput[3];
                        String name = splitInput[4];

                        String[] t = new String[3];
                        t[0] = timeSlot1;
                        t[1] = timeSlot2;
                        t[2] = timeSlot3;
                        Course c = new Course(code, t, name); //create objects of  "Course" class
                        COURSES.add(c);
                    }
                    System.out.println("Load Courses: " + COURSES.size() + " course records successfully read from " + "\"" + filename + "\".");
                    break;
                }
                case "enrollments": {
                    int n = 0;
                    int lineNumber = 0;
                    while (io.hasNextLine()) {
                        //read the files line by line;
                        String input = io.nextLine();  //e.g. input = 16111 comp226
                        lineNumber++;
                        if(!input.matches("^[0-9]{5}\\s+[A-Za-z]{4}[0-9]{3}$")){
                            System.out.println("Invalid course record format [" + input + "] at line " + lineNumber);
                            continue;
                        }
                        //split the input
                        String[] splitInput = input.split("\\s+", 2);
                        String studentID = splitInput[0];
                        String courseCode = splitInput[1];
                        if (Enroll(studentID, courseCode)) {
                            n++;
                        }
                    }
                    System.out.println("Load Courses: " + n + " course records successfully read from " + "\"" + filename + "\".");
                    break;
                }
                case "assignments": {
                    int n = 0;
                    int lineNumber = 0;
                    while (io.hasNextLine()) {
                        //read the files line by line;
                        lineNumber++;
                        String input = io.nextLine();

                        if (!input.matches("^[0-9]{4}\\s+[A-Za-z]{4}[0-9]{3}$"))
                        {
                            System.out.println("Invalid course record format [" + input + "] at line " + lineNumber);
                            continue;
                        }
                        //split the input
                        String[] splitInput = input.split("\\s+", 2);
                        String instructorID = splitInput[0];
                        String courseCode = splitInput[1];
                        if (Assign(instructorID, courseCode)) {
                            n++;
                        }
                    }
                    System.out.println("Load Assignements: " + n + "  records successfully read from " + "\"" + filename + "\".");
                    break;
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println(" Failed opening input file (" + filename + ")");
        } catch (IOException ex)
        {
            System.out.println("Error occurs while I/O operation.");
        }
    }

    public void List(String dataType) {
        switch (dataType.toLowerCase()) {
            case "students": {
                for (Student student : STUDENTS) {
                    System.out.println(student.getBasicInfo());
                }
                break;
            }
            case "instructors": {
                for (Instructor instructor : INSTRUCTORS) {
                    System.out.println(instructor.getBasicInfo());
                }
                break;
            }
            case "courses": {
                for (Course course : COURSES) {
                    System.out.println(course.getBasicInfo());
                }
            }
            break;
        }
    }

    public void Save(String dataType, String filename) {
        try {
            PrintWriter out = new PrintWriter(filename);
            switch (dataType.toLowerCase()) {
                case "students": {
                    for (Student student : STUDENTS) {
                        //read the files line by line;
                        String line = student.toFileString();
                        out.println(line);
                        System.out.println("Saving " + line);
                    }
                    System.out.println("Saved " + STUDENTS.size() + " students records to " + "\"" + filename + "\".");
                    break;
                }
                case "instructors": {
                    for (Instructor instructor : INSTRUCTORS) {
                        //read the files line by line;
                        String line = instructor.toFileString();
                        out.println(line);
                        System.out.println("Saving " + line);
                    }
                    System.out.println("Saved " + INSTRUCTORS.size() + " instructor records to " + "\"" + filename + "\".");
                    break;
                }
                case "courses": {
                    for (Course course : COURSES) {
                        //read the files line by line;
                        String line = course.toFileString();
                        out.println(line);
                        System.out.println("Saving " + line);
                    }
                    System.out.println("Saved " + COURSES.size() + " course records to " + "\"" + filename + "\".");
                    break;
                }
                case "enrollments": {
                    int n=0;
                    for(Student student : STUDENTS)
                    {
                        for(Course course : student.getEnrolledCourse())
                        {
                            String line = String.format("%s %s", student.getStudentID(), course.getCode());
                            out.println(line);
                            System.out.println("Saving " + line);
                            n++;
                        }
                    }System.out.println("Saved " + n + "enrollment records to " + "\"" + filename + "\".");
                    break;
                }case "assignment":{
                    int n=0;
                    for(Instructor instructor : INSTRUCTORS)
                    {
                        for(Course course : instructor.getAssignedCourse())
                        {
                            String line = String.format("%s %s", instructor.getInstructorID(), course.getCode());
                            out.println(line);
                            System.out.println("Saving " + line);
                            n++;
                        }
                    }System.out.println("Saved " + n + "assignment records to " + "\"" + filename + "\".");
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Failed opening output file (" + filename + ")");
        } catch (IOException ex)
        {
            System.out.println("Error occurs while I/O operation.");
        }
    }


    public void studentStatus(String studentID) {
        Student stud = getStudentByID(studentID);
        if (stud == null) {
            System.out.println("No such students!");
        } else {
            System.out.println(stud);
        }
    }

    public void instructorStatus(String instructorID) {
        Instructor instr = getInstructorByID(instructorID);
        if (instr == null) {
            System.out.println("No such instructor!");
        } else {
            System.out.println(instr);
        }
    }

    public void courseStatus(String courseCode) {
        Course c = getCourseByCode(courseCode);
        if (c == null) {
            System.out.println("No such course!");
        } else {
            System.out.println(c);
        }
    }

    public Student getStudentByID(String studID) {
        for (Student student : STUDENTS) {
            if (student.getStudentID().equals(studID)) {
                return student;
            }
        }
        return null;
    }

    public Instructor getInstructorByID(String instrID) {
        for (Instructor instructor : INSTRUCTORS) {
            if (instructor.getInstructorID().equals(instrID)) {
                return instructor;
            }
        }
        return null;
    }

    public Course getCourseByCode(String courseCode) {
        for (Course course : COURSES) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public boolean Assign(String instructorID, String courseCode) {
        Course course = getCourseByCode(courseCode);
        Instructor instructor = getInstructorByID(instructorID);

        if (course == null) {
            System.out.println("Couldn't find course");
            return false;
        }
        if (instructor == null) {
            System.out.println("Couldn't find instructor");
            return false;
        }

        if (instructor.assign(course)) {
            course.addInstructor(instructor);
            System.out.println(String.format("Assign: Assigned %s (%s) to %s", instructor.getInstructorName(), instructor.getInstructorID(), course.getCode()));
            return true;
        }

        return false;
    }

    public void Unassign(String instructorID, String courseCode) {
        Course course = getCourseByCode(courseCode);
        Instructor instructor = getInstructorByID(instructorID);

        if (course == null) {
            System.out.println("Couldn't find course");
            return;
        }
        if (instructor == null) {
            System.out.println("Couldn't find instructor");
            return;
        }
        if (instructor.unassign(course)) {
            course.removeInstructor(instructor);
            System.out.println(String.format("Unassign:  Unassigned %s (%s) from %s", instructor.getInstructorName(), instructor.getInstructorID(), course.getCode()));
        }
    }

    public boolean Enroll(String studentID, String courseCode) {
        Course course = getCourseByCode(courseCode);
        Student student = getStudentByID(studentID);

        if (course == null) {
            System.out.println("Couldn't find course");
            return false;
        }
        if (student == null) {
            System.out.println("Couldn't find student");
            return false;
        }

        if (student.enroll(course)) {
            course.addStudent(student);
            System.out.println(String.format("Enroll: Enrolled %s (%s) to %s", student.getStudentName(), student.getStudentID(), course.getCode()));
            return true;
        }

        return false;
    }

    public void Unenroll(String studentID, String courseCode) {
        Course course = getCourseByCode(courseCode);
        Student student = getStudentByID(studentID);

        if (course == null) {
            System.out.println("Couldn't find course");
            return;
        }
        if (student == null) {
            System.out.println("Couldn't find student");
            return;
        }
        if (student.unenroll(course)) {
            course.removeStudent(student);
            System.out.println(String.format("Unenroll:  Unenrolled %s (%s) from %s", student.getStudentName(), student.getStudentID(), course.getCode()));
        }
    }

    public void Status(String dataType, String id) {
        switch (dataType) {
            case "instructor":
                instructorStatus(id);
                break;
            case "course":
                courseStatus(id);
                break;
            case "student":
                studentStatus(id);
                break;
            default:
                System.out.println("Invalid arguments");
        }
    }
}