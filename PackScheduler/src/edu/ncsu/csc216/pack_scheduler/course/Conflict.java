package edu.ncsu.csc216.pack_scheduler.course;

 /**
  * Interface for checking conflicts between two Activity objects.
  * 
  * @author Udita Pericharla
  */
public interface Conflict {

    /**
     * Checks if this Activity conflicts with the given Activity.
     * 
     * @param possibleConflictingActivity the Activity to compare to
     * @throws ConflictException if there is an overlapping meeting time and day
     */
    void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
