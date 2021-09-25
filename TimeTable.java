import java.util.ArrayList;

public class TimeTable {
    private static final String[] DAYS = {"M", "T", "W", "R", "F"};
    private static final String[] TIMESLOT = {"08", "09", "10", "11", "12", "13", "14", "15", "16", "17"};
    private static final String BLANKROW = "   |         |         |         |         |         |\n";
    private static final String SEPERATOR = "   +---------+---------+---------+---------+---------+\n";
    private static final String DAYSSTR = "        M         T         W         R         F\n";


    public static String createTimeTable(ArrayList<String> courseSession)
    {
        String result = DAYSSTR + SEPERATOR;
        for(String time : TIMESLOT) {
            result += BLANKROW + (time.startsWith("0") ? " " + time.substring(1) : time) + " |";
            for (String day : DAYS) {
                String courseCode = getCourseCodeBySession(courseSession, day + time);
                if (courseCode != null) {
                    result += " " + courseCode+ " |";
                } else {
                    result += "         |";
                }
            }
            result += (time.startsWith("0") ? " " + time.substring(1) : time) + "\n" + BLANKROW + SEPERATOR;
        }
        result += DAYSSTR;
        return result;
    }

    public static String getCourseCodeBySession(ArrayList<String> courseSession, String session)
    {
        for(String sessionStr : courseSession)
        {
            if (sessionStr.startsWith(session + ":")) {
                return sessionStr.replaceFirst(session + ":", "");
            }
        }
        return null;
    }
}
