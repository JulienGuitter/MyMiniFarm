package fr.eseo.e3a.myminifarm.utils;

/**
 * The GameTime class represents a time in the game, including day, hour, and minute.
 * It provides methods to manipulate and compare game time, such as adding minutes,
 * calculating differences, and converting to integer representation.
 */
public class GameTime implements Cloneable {
    /** Number of minutes in an hour. */
    private static final int MINUTE_IN_HOUR = 60;
    /** Number of hours in a day. */
    private static final int HOUR_IN_DAY = 24;
    /** Number of days in a month (game logic). */
    public static final int DAY_IN_MONTH = 30;

    /** The current day. */
    private int day;
    /** The current hour. */
    private int hour;
    /** The current minute. */
    private int minute;

    /**
     * Default constructor. Initializes the time to day 1, hour 0, minute 0.
     */
    public GameTime(){
        this.day = 1;
        this.hour = 0;
        this.minute = 0;
    }

    /**
     * Constructs a GameTime with the specified day, hour, and minute.
     * @param day the day
     * @param hour the hour
     * @param minute the minute
     */
    public GameTime(int day, int hour, int minute){
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Constructs a GameTime by copying another GameTime and adding minutes.
     * @param gameTime the GameTime to copy
     * @param minuteToAdd the number of minutes to add
     */
    public GameTime(GameTime gameTime, int minuteToAdd){
        this.day = gameTime.day;
        this.hour = gameTime.hour;
        this.minute = gameTime.minute;
        addminute(minuteToAdd);
    }

    /**
     * Constructs a GameTime from a total number of minutes, starting from day 1.
     * @param minute the total number of minutes
     */
    public GameTime(int minute){
        this.day = 1;
        this.hour = 0;
        this.minute = minute;
        addminute(0);
    }

    /**
     * Adds the specified number of minutes to this GameTime, updating hour and day as needed.
     * @param min the number of minutes to add
     */
    public void addminute(int min){
        this.minute += min;
        if(this.minute >= MINUTE_IN_HOUR){
            this.hour += this.minute / MINUTE_IN_HOUR;
            this.minute = this.minute % MINUTE_IN_HOUR;

            if(this.hour >= 24){
                this.day += this.hour / HOUR_IN_DAY;
                this.hour = this.hour % HOUR_IN_DAY;
            }
        }
    }

    /**
     * Returns the difference between two GameTime objects as a new GameTime.
     * If the result is negative, wraps around to the next month.
     * @param time1 the first GameTime
     * @param time2 the second GameTime
     * @return the difference as a GameTime
     */
    public GameTime subtract(GameTime time1, GameTime time2){
        int thisTime = time1.toInt();
        int otherTime = time2.toInt();
        int diff = thisTime - otherTime;
        if(diff < 0){
            diff += DAY_IN_MONTH * HOUR_IN_DAY * MINUTE_IN_HOUR;
        }
        return new GameTime(diff);
    }

    /**
     * Checks if this GameTime is after the specified GameTime.
     * @param otherTime the GameTime to compare to
     * @return true if this GameTime is after otherTime, false otherwise
     */
    public boolean isTimePassed(GameTime otherTime){
        int thisTime = this.toInt();
        int otherTimeInt = otherTime.toInt();
        return thisTime > otherTimeInt;
    }

    /**
     * Returns the difference between this GameTime and another as a new GameTime.
     * @param otherTime the GameTime to compare to
     * @return the difference as a GameTime
     */
    public GameTime getDiffTime(GameTime otherTime){
        return subtract(this, otherTime);
    }

    /**
     * Converts this GameTime to an integer representing the total number of minutes since day 0.
     * @return the total minutes
     */
    public int toInt(){
        int time = 0;
        time += this.day * HOUR_IN_DAY * MINUTE_IN_HOUR;
        time += this.hour * MINUTE_IN_HOUR;
        time += this.minute;
        return time;
    }

    /**
     * Gets the current day.
     * @return the day
     */
    public int getday() {
        return day;
    }
    /**
     * Gets the current hour.
     * @return the hour
     */
    public int gethour() {
        return hour;
    }
    /**
     * Gets the current minute.
     * @return the minute
     */
    public int getminute() {
        return minute;
    }

    /**
     * Sets the current day.
     * @param day the day to set
     */
    public void setday(int day) {
        this.day = day;
    }
    /**
     * Sets the current hour.
     * @param hour the hour to set
     */
    public void sethour(int hour) {
        this.hour = hour;
    }
    /**
     * Sets the current minute.
     * @param minute the minute to set
     */
    public void setminute(int minute) {
        this.minute = minute;
    }

    /**
     * Returns a string representation of this GameTime in the format "Jour X, XXhXX".
     * @return the string representation
     */
    @Override
    public String toString() {
        return String.format("Jour %d, %02dh%02d", day, hour, minute);
    }

    /**
     * Creates and returns a copy of this GameTime.
     * @return a clone of this GameTime
     */
    @Override
    public GameTime clone() {
        try {
            return (GameTime) super.clone(); // shallow copy
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
