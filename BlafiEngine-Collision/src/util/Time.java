package util;

public class Time {


     public static double timeStarted = System.nanoTime();


     /**
      * <p>
      * Used to determine the time since the program started running
      *</p>
      * @return      util.Time in seconds
      */
     public static double getTime(){return (System.nanoTime() - timeStarted) * 1E-9;}
}
