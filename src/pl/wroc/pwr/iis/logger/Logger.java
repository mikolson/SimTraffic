/*
 * Copyright 2005 Michal Stanek. All rights reserved.
 * Project name: 	Magisterska
 * File in project: Logger.java
 * Creation date: 	2005-12-03
 */
package pl.wroc.pwr.iis.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * <code>Logger</code> is ment to help to organize logs in a project. It can
 * show your logs with current system time, with your program's execution's time
 * and/or with a name of the class from which the <code>Logger</code> method
 * was invocated. Logs could be printed to <code>System.out</code>,
 * <code>System.err</code> or into the file(log.txt). <br />
 * <br />
 * Suggested using: <br>
 * <code>
 * if (Logger.DEBUG) Loger.log("your log");
 * </code>
 * @author M.Stanek
 */
public final class Logger
{
    /**
     * Indicates if any logs should be printed. It is not used by
     * <code>Logger</code>, application which is using <code>Logger</code>
     * should check this flag.
     */
    private static final boolean DEBUG = true;

    /**
     * Indicates if usual message should be printed. It is not used by
     * <code>Logger</code>, application which is using <code>Logger</code>
     * should check this flag.
     */
    private static final boolean LOGS = true;

    /**
     * Indicates if warning message should be printed. It is not used by
     * <code>Logger</code>, application which is using <code>Logger</code>
     * should check this flag.
     */
    private static final boolean WARNING = true;

    /**
     * Indicates if error message should be printed. It is not used by
     * <code>Logger</code>, application which is using <code>Logger</code>
     * should check this flag.
     */
    private static final boolean ERROR = true;

    /**
     * Indicates if logs should be printed into the file.
     */
    public static final boolean TO_FILE = false;

    /**
     * Indicates if logs should be printed to <code>System.err</code>. If
     * <code>TO_ERR</code>== false then logs are printd to
     * <code>System.out</code>. This flag is ignored if TO_FILE == true.
     */
    public static final boolean TO_ERR = false;

    /**
     * Indicates if logs file is open.
     */
    private static boolean fileOpen = false;

    /**
     *
     */
    private static BufferedWriter out = null;

    /**
     * Used in displaying date and time.
     */
    private static Calendar calendar = new GregorianCalendar(TimeZone
            .getDefault());

    /**
     * Used in counting the application execution time.
     */
    private static long time = System.currentTimeMillis();

    /**
     * Used in counting the time of execution of given part of the code.
     */
    private static long timerTime = time;

    /**
     * Used in creating String representation of date and time.
     */
    private static StringBuffer buffer = new StringBuffer();

    /**
     * Opens file "log.txt" and writes a headline. It doesn't clear the file.
     */
    private static void openFile()
    {
        try
        {
            out = new BufferedWriter(new FileWriter("log.txt", true));
            out.write("\n\n");
            out.write("*******************************************");
            out.write("\r\n");
            out.write("log started at: NON SPECYFY");
            out.write("\r\n");
            out.write("*******************************************");
            out.write("\r\n\n");
            fileOpen = true;
        }
        catch (IOException e)
        {
            System.err.println("Problems with log file.");
        }
    }

    /**
     * Writes text to a stream (stream is chosen according to flags).
     * @param text
     *            text of log
     */
    private static void write(String text)
    {
        if (DEBUG)
        {
            if (TO_FILE)
            {
                if (!fileOpen)
                {
                    openFile();
                }
                try
                {
                    out.write(text);
                    out.write("\r\n");
                    out.flush();
                }
                catch (IOException e)
                {
                    System.err.println("Problems with log file.");
                }
            }
            else
            {
                if (TO_ERR)
                {
                    System.err.println(text);
                }
                else
                {
                    System.out.println(text);
                }
            }
        }
    }

    /**
     * Prints a warn log.
     * @param text
     *            text of warning
     */
    public static void warn(String text)
    {
        if (WARNING)
        {
            write("! WARNING: " + text + "!");
        }
        Thread.dumpStack();
    }

    /**
     * Prints a warn log.
     * @param text
     *            text of warning
     * @param object
     *            Determine in which class warning occures
     */
    public static void warn(String text, Object object)
    {
        if (WARNING)
        {
            write("! WARNING in "+ object.getClass() +": " + text + "!");
            Thread.dumpStack();
        }
    }

    /**
     * Prints a error log.
     * @param text
     *            text of error
     */
    public static void error(String text)
    {
        if (ERROR)
        {
            write("! ERROR: " + text + "!");
        }
    }

    /**
     * Prints a error log.
     * @param text
     *            text of error
     * @param object
     *            Determine in which class warning occures
     */
    public static void error(String text, Object object)
    {
        if (ERROR)
        {
            write("! ERROR in " + object.getClass() + ": " + text + "!");
            write("Error in:");
            Thread.dumpStack();
        }
    }

    /**
     * Prints a log.
     * @param text
     *            text of log
     */
    public static void log(String text)
    {
        if (LOGS)
        {
            write(text);
        }
    }

    /**
     * Prints a log preceded by the name of the class of function's caller.
     * <br/> ex. <code>Logger.log("lala",this);</code>
     * @param text
     *            text of log
     * @param whoCalls
     *            object who called this function
     */
    public static void log(String text, Object whoCalls)
    {
        if (LOGS)
        {
            write("Logged by " + whoCalls.getClass() + ": " + text);
        }
    }

//    /**
//     * Prints a log preceded by system time. <br />
//     * @param text
//     *            text of the log
//     */
//    public static void logSystemTime(String text)
//    {
//        write(getCurrentDateString() + "  " + text);
//    }

//    /**
//     * Prints a log preceded by system time and the name of the class of
//     * function's caller. <br />
//     * @param text
//     *            text of the log
//     * @param whoCalls
//     *            object who called this function
//     */
//    public static void logSystemTime(String text, Object whoCalls)
//    {
//        write(getCurrentDateString() + "  " + "Logged by "
//                + whoCalls.getClass() + ": " + text);
//    }

//    /**
//     * Prints a log preceded by application execution time.
//     * @param text
//     */
//    public static void logTime(String text)
//    {
//        write(getLogTimeString() + "  " + text);
//    }

//    /**
//     * Prints a log preceded by application execution time and the name of the
//     * class of function's caller.
//     * @param text
//     * @param whoCalls
//     */
//    public static void logTime(String text, Object whoCalls)
//    {
//        write(getLogTimeString() + "  " + "Logged by " + whoCalls.getClass()
//                + ": " + text);
//    }

    /**
     * Sets the timerTime to current system time.
     */
    public static void clearTimer()
    {
        timerTime = System.currentTimeMillis();
    }

    /**
     * Prints a log preceded by the time between last call of
     * <code>clearTimer()</code> and now.
     * @param text Text to write
     */
    public static void logTimerTime(String text)
    {
        if (LOGS)
        {
            write((System.currentTimeMillis() - timerTime) + "  " + text);
        }
    }

    /**
     * Prints a log preceded by the time between last call of
     * <code>clearTimer()</code> and now and the name of the class of
     * function's caller.
     * @param text
     *            Message
     * @param whoCalls
     *            Object which invoke this method
     */
    public static void logTimerTime(String text, Object whoCalls)
    {
        if (LOGS)
        {
            write((System.currentTimeMillis() - timerTime) + "  " + "Logged by "
                + whoCalls.getClass() + ": " + text);
        }
    }

//    /**
//     * Prints system time to the log's destination.
//     */
//    public static void printSystemTime()
//    {
//        write(getCurrentDateString());
//    }

//    /**
//     * Prints application running time to the log's destination.
//     */
//    public static void printLogTime()
//    {
//        write(getLogTimeString());
//    }

    /**
     * Prints the time between last call of <code>clearTimer()</code> and now.
     */
    public static void printTimerTime()
    {
        write(String.valueOf(System.currentTimeMillis() - timerTime));
    }

    /**
     * It appends the date from <code>calendar</code> to the
     * <code>buffer</code>.
     */
    private static void appendDate()
    {
        buffer.append(calendar.get(Calendar.YEAR));
        buffer.append("-");
        buffer.append(calendar.get(Calendar.MONTH) + 1);
        buffer.append("-");
        buffer.append(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * It appends the time from <code>calendar</code> to the
     * <code>buffer</code>.
     */
    private static void appendTime()
    {
        buffer.append(calendar.get(Calendar.HOUR_OF_DAY));
        buffer.append(":");
        buffer.append(calendar.get(Calendar.MINUTE));
        buffer.append(":");
        buffer.append(calendar.get(Calendar.SECOND));
        buffer.append(":");
        buffer.append(calendar.get(Calendar.MILLISECOND));
    }

    // /**
    // * for testing
    // * @param args
    // */
    // public static void main(String[] args)
    // {
    // System.out.println(TimeZone.getDefault().getDisplayName());
    // Logger.log("Logger.log");
    // Logger.log("Logger.log", new String());
    // Logger.logSystemTime("Logger.logSystemTime()");
    // Logger.logSystemTime("Logger.logSystemTime()",new String());
    // Logger.logTime("Logger.logTime()");
    // Logger.logTime("Logger.logTime()",new String());
    // Logger.logTimerTime("Logger.logTimerTime()");
    // Logger.logTimerTime("Logger.logTimerTime()",new String());
    // Logger.printLogTime();
    // Logger.printSystemTime();
    // Logger.printTimerTime();
    // Logger.TO_ERR = true;
    // Logger.log("Logger.log");
    // Logger.log("Logger.log", new String());
    // Logger.logSystemTime("Logger.logSystemTime()");
    // Logger.logSystemTime("Logger.logSystemTime()",new String());
    // Logger.logTime("Logger.logTime()");
    // Logger.logTime("Logger.logTime()",new String());
    // Logger.logTimerTime("Logger.logTimerTime()");
    // Logger.logTimerTime("Logger.logTimerTime()",new String());
    // Logger.printLogTime();
    // Logger.printSystemTime();
    // Logger.printTimerTime();
    //
    // Logger.TO_FILE = true;
    // Logger.log("Logger.log");
    // Logger.log("Logger.log", new String());
    // Logger.logSystemTime("Logger.logSystemTime()");
    // Logger.logSystemTime("Logger.logSystemTime()",new String());
    // Logger.logTime("Logger.logTime()");
    // Logger.logTime("Logger.logTime()",new String());
    // Logger.logTimerTime("Logger.logTimerTime()");
    // Logger.logTimerTime("Logger.logTimerTime()",new String());
    // Logger.printLogTime();
    // Logger.printSystemTime();
    // Logger.printTimerTime();
    //
    // }

}