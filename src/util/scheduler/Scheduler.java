package util.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;


public class Scheduler
{

    static Logger logger = Logger.getLogger(Scheduler.class);
    static Timer mainTimer = new Timer();
    static long onedaysecond = 1000*60*60*24;
    
    

    static public synchronized void addTask(TimerTask task,Timeout timeout){
        Calendar cal = GregorianCalendar.getInstance();
        Date time;
        switch(timeout.getType()){
        case Timeout.ABSOULETIME:
            
            time = timeout.getCalendar().getTime();
            
            logger.debug("Schedule Absolute time:"+time);
            mainTimer.schedule(new DEBUGWrapper(task),time);
            break;
        case Timeout.NEXTTIME:
            
            cal.setTime(timeout.getCalendar().getTime());
            
            time = getNextTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
            
            logger.debug("Schedule Next Time:"+time);
            mainTimer.schedule(new DEBUGWrapper(task),time);
            break;
        case Timeout.EVERYDAY:
            
            cal.setTime(timeout.getCalendar().getTime());
            time = getNextTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
            logger.debug("Schedule Every Day:"+time);
            
            mainTimer.scheduleAtFixedRate(new DEBUGWrapper(task),time,onedaysecond);
            break;
        }
        
    }
    
    static class DEBUGWrapper extends TimerTask{

        TimerTask task;
        
        public DEBUGWrapper(TimerTask task){
            this.task = task;
        }
        
        public void run() {
           logger.debug("Start Run Task");
           try{
               task.run();
           }catch(Throwable x){
               logger.error(x);
           }
           logger.debug("End Run Task"); 
        }

        public boolean cancel() {
            return task.cancel();
        }

        public long scheduledExecutionTime() {
            return task.scheduledExecutionTime();
        }

    }
    
    
    
    public static Date getNextTime(int hour,int min,int sec){
        Calendar now = GregorianCalendar.getInstance();
        int nh = now.get(Calendar.HOUR_OF_DAY);
        int nm = now.get(Calendar.MINUTE);
        int ns = now.get(Calendar.SECOND);
        
        long t1 = ns+nm*60 + nh*3600;
        long t2 = sec+min*60+hour*3600;
        
        
        now.set(Calendar.HOUR_OF_DAY,hour);
        now.set(Calendar.MINUTE,min);
        now.set(Calendar.SECOND,sec);
        
        if(t1>=t2){
            //tomorrow
            now.add(Calendar.DATE,1);
        }
        
        return now.getTime();
        
        
    }
    
    
    public static void main(String[] args){
        
        TimerTask t1 = new TimerTask(){
            public void run(){
                System.out.println("Do Task 1");
            }
        };
        
        TimerTask t2 = new TimerTask(){
            public void run(){
                System.out.println("Do Task 2");
            }
        };
        
        TimerTask t3 = new TimerTask(){
            public void run(){
                System.out.println("Do Task 3");
            }
        };
        
        TimerTask t4 = new TimerTask(){
            public void run(){
                System.out.println("Do Task 4");
            }
        };
        
        
        Calendar c1 = GregorianCalendar.getInstance();
        Calendar c2 = (Calendar)c1.clone();
        Calendar c3 = (Calendar)c1.clone();
        Calendar c4 = (Calendar)c1.clone();
        
        c1.add(Calendar.MINUTE,1);
        c2.add(Calendar.MINUTE,-20);
        
        c3.add(Calendar.MINUTE,2);
        c4.add(Calendar.MINUTE,-30);
        
        Scheduler.addTask(t1,new Timeout(Timeout.NEXTTIME,c1));
        Scheduler.addTask(t2,new Timeout(Timeout.NEXTTIME,c2));
        
        Scheduler.addTask(t3,new Timeout(Timeout.EVERYDAY,c3));
        Scheduler.addTask(t4,new Timeout(Timeout.EVERYDAY,c4));
        
        
    }
    
    
    
}
