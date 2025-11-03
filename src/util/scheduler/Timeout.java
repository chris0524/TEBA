package util.scheduler;

import java.util.Calendar;
import java.util.Date;

public class Timeout
{

    static final int EVERYDAY = 1;
    
    static final int NEXTTIME = 2;
    
    static final int ABSOULETIME = 0;
    
    
    Calendar calendar;
    
    int type;
    
    public Timeout(int type, Calendar calendar){
        this.type = type;
        this.calendar = calendar;
    }



    public Calendar getCalendar() {
        return calendar;
    }



    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
    

}
