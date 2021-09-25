import java.util.ArrayList;

public class Student {
    private String studentID;
    private String studentGender;
    private int studentMaxLoad;
    private String studentName;
    private ArrayList<Course> enrolledCourse = new ArrayList<>();

    public Student(String studentID, String studentGender, int studentMaxLoad, String studentName){
        this.studentID = studentID;
        this.studentGender = studentGender;
        this.studentMaxLoad = studentMaxLoad;
        this.studentName = studentName;
    }

    public boolean hasTimeConflict(Course otherCourse){
        for(Course course: enrolledCourse){
            if(course.conflictWith(otherCourse))
                return true;
        }return false;
    }
    public boolean enroll(Course course)
    {
        if ( studentMaxLoad <= enrolledCourse.size())
        {
            System.out.println("Enroll: failed to assign: " + course.getCode() + "exceed maximum workload");
            return false;
        }
        if( hasTimeConflict(course)) {
            System.out.printf("Enroll: Failed to enroll %s (%s) to %s. \n", studentName, studentID, course.getCode());
            return false;
        }
        enrolledCourse.add(course);
        return true;
    }

    public boolean unenroll(Course course){
        if(!enrolledCourse.contains(course)) {
            return false;
        }enrolledCourse.remove(course);
        return true;
    }

    public ArrayList<Course> getEnrolledCourse(){
        return enrolledCourse;
    }

    public String getStudentID(){
        return studentID;
    }
    public String getStudentGender(){
        return studentGender;
    }
    public int getStudentMaxLoad(){
        return studentMaxLoad;
    }
    public String getStudentName(){
        return studentName;
    }

    public String toString(){
        String timetable = TimeTable.createTimeTable(createCourseSessionList());
        //return studentName + " (" + studentID +")";
        return String.format("UID: %s\nName: %s\nGender: %s\nMax Course Load: %s\nEnrolled Courses:\n    %s",
                studentID,
                studentName,
                studentGender,
                studentMaxLoad,
                enrolledCourse.isEmpty() ? "NIL" : createCourseString() + "\n TimeTable: \n" + timetable
        );
    }

    public String createCourseString()
    {
        String result = "";
        for(Course course : enrolledCourse)
        {
            result += course.getBasicInfo() + "\n    ";
        }
        return result;
    }

    private ArrayList<String> createCourseSessionList()
    {
        ArrayList<String> courseSessions = new ArrayList<>();
        for(Course course : enrolledCourse)
        {
            for(String session : course.getTimeSlot())
            {
                courseSessions.add(String.format("%s:%s", session, course.getCode()));
            }
        }
        return courseSessions;
    }

    public String getBasicInfo()
    {
        return String.format("%s (%s)", studentName, studentID);
    }

    public String toFileString()
    {
        return String.format("%s %d %s (%s)", studentGender, studentMaxLoad, studentName, studentID);
    }
}
