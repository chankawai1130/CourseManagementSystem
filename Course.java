import java.util.ArrayList;
import java.util.Arrays;

public class Course {
    private String code ;
    private String[] timeSlot;
    private String name;
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Instructor> instructors = new ArrayList<>();

    public Course(String code, String[] timeSlot, String name){
        this.code=code;
        this.timeSlot = timeSlot;
        this.name = name;
    }

    public String getCode(){
        return code;
    }
    public String[] getTimeSlot(){
        return timeSlot;
    }
    public String getName(){
        return name;
    }

    public boolean conflictWith(Course otherCourse){
        for(String time1 : timeSlot) {
            for(String time2 : otherCourse.timeSlot)
            {
                if (time1.equals(time2))
                {
                    return true;
                }
            }
        }return false;
    }
    public void addStudent(Student student){
        students.add(student);
    }
    public void addInstructor(Instructor instructor){
        instructors.add(instructor);
    }
    public void removeStudent(Student student){
        students.remove(student);
    }
    public void removeInstructor(Instructor instructor){
        instructors.remove(instructor);
    }
    public String toString(){
        //return code + "" + timeSlot + "" + name;
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        String f = "";
         a = "Code: " + getCode() + "\n";
         b = "Name: " + name + "\n";
         c = "Timeslots: " + "[" + timeSlot + "]" + "\n";
         return a + b + c;
    }

    public String getBasicInfo() {
        return String.format("%s [%s] %s", code, Arrays.toString(timeSlot), name);
    }

    public String toFileString()
    {
        return String.format("%s %s %s", code, Arrays.toString(timeSlot), name);
    }
}
