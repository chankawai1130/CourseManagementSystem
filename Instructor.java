import java.util.ArrayList;

public class Instructor {
    private String instructorID;
    private String instructorGender;
    private int instructorMaxLoad;
    private String instructorName;
    private ArrayList<Course> assignedCourse = new ArrayList<>();


    public Instructor(String instructorID, String instructorGender, int instructorMaxLoad, String instructorName){
        this.instructorID = instructorID;
        this.instructorGender = instructorGender;
        this.instructorMaxLoad = instructorMaxLoad;
        this.instructorName = instructorName;
    }


    public boolean hasTimeConflict(Course otherCourse){
        for(Course course: assignedCourse){
            if(course.conflictWith(otherCourse))
                return true;
        }return false;
    }
    public boolean assign(Course course)
    {
        if ( instructorMaxLoad <= assignedCourse.size())
        {
            System.out.println("Enroll: failed to assign: " + course.getCode() + "exceed maximum workload");
            return false;
        }
        if( hasTimeConflict(course)) {
            System.out.printf("Assign: Failed to assign %s (%s) to %s. \n", instructorName, instructorID, course.getCode());
            return false;
        }
        assignedCourse.add(course);
        return true;
    }

    public boolean unassign(Course course){
        if(!assignedCourse.contains(course)) {
            return false;
        }assignedCourse.remove(course);
        return true;
    }

    private ArrayList<String> createCourseSessionList()
    {
        ArrayList<String> courseSessions = new ArrayList<>();
        for(Course course : assignedCourse)
        {
            for(String session : course.getTimeSlot())
            {
                courseSessions.add(String.format("%s:%s", session, course.getCode()));
            }
        }
        return courseSessions;
    }


    public String getInstructorID(){
        return instructorID;
    }
    public String getInstructorGender(){
        return instructorGender;
    }
    public int getInstructorMaxLoad(){
        return instructorMaxLoad;
    }
    public String getInstructorName(){
        return instructorName;
    }
    public ArrayList<Course> getAssignedCourse(){
        return assignedCourse;
    }

    public String toString(){
        String timetable = TimeTable.createTimeTable(createCourseSessionList());

        return String.format("SID: %s\nName: %s\nGender: %s\nMax Course Load: %s\nAssigned Courses: %s",
                instructorID,
                instructorName,
                instructorGender,
                instructorMaxLoad,
                assignedCourse.isEmpty() ? "NIL" : createCourseString() + "\n TimeTable: \n" + timetable
        );
    }

    public String createCourseString()
    {
        String result = "";
        for(Course course : assignedCourse)
        {
            result += course.getBasicInfo() + "\n";
        }
        return result;
    }

    public String getBasicInfo() {
        return String.format("%s (%s)", instructorName, instructorID);
    }

    public String toFileString()
    {
        return String.format("%s %d %s (%s)", instructorGender, instructorMaxLoad, instructorName, instructorID);
    }
}


