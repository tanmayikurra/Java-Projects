import java.util.Scanner;

/**
 * This algorithm will take the user input for a race they ran and calculate their race pace.
 * The inputs of this function are what type of race they ran, and the time it took for them
 * to run it.
 */
public class PaceCalculator {
    
    /**
     * Main algorithm that will begin immediately upon the algorithm being started.
     * Contains all code in this algorithm, which will ask the user for the various
     * inputs and then.
     * 
     * @param args necessary to make function work
     */
    public static void main(String[] args) {


        // Declare the lengths of the different types of races as final variables.
        final double MARATHON_DISTANCE = 26.2;
        final double HALF_MARATHON_DISTANCE = 13.1;
        final double TENK_DISTANCE = 6.2;
        final double FIVEK_DISTANCE = 3.1;
        final double MILE_DISTANCE = 1;

        // Display the options for the user to select, along with the corresponding key.
        System.out.println("Race Distance Menu:\n"
            + "- M: Marathon\n"
            + "- H: Half-Marathon\n"
            + "- T: 10K\n"
            + "- F: 5K\n"
            + "- I: Mile\n");
        
        Scanner in = new Scanner(System.in);

        // Get the user from the player and convert it to lowercase so that it
        // will no longer be case sensitive in the conditionals.
        System.out.println("Race Distance: ");
        String rawInput = (in.next());
        String input = rawInput.toLowerCase();

        // Initialize race distance before declaring it.
        double raceDistance = 0;

        switch (input) {
            case "m":
                raceDistance = MARATHON_DISTANCE;
                break;
            case "h": 
                raceDistance = HALF_MARATHON_DISTANCE;
                break;
            case "t" :
                raceDistance = TENK_DISTANCE;
                break;
            case "f":
                raceDistance = FIVEK_DISTANCE;
                break;
            case "i":
                raceDistance = MILE_DISTANCE;
                break;
            default:
                // Exit the program and display the error that the user made in their input.
                System.out.println("Invalid distance");
                in.close();
                return;  
        }

        // Get input from the user for the race time in the desired format.
        System.out.print("\nRace time (HH:MM:SS): ");
        String raceTime = in.next();

        // Check to make sure that there is either one or two numbers before the first ":"
        if (raceTime.indexOf(":") != 1 && raceTime.indexOf(":") != 2){
            System.out.println("Invalid time");
            in.close();
            return;
        }

        final int MIN_LENGTH = 7;
        final int MAX_LENGTH = 8;

        if (raceTime.length() != MIN_LENGTH && raceTime.length() != MAX_LENGTH){
            System.out.println("Invalid time");
            in.close();
            return;
        }
        
        // Allow us to seperate the input by ":" so that we may easily get the hours,
        // minutes and seconds.
        Scanner newInput = new Scanner(raceTime).useDelimiter(":");

        int hours = newInput.nextInt();
        int minutes = newInput.nextInt();
        int sec = newInput.nextInt();
        
        
        // Declare the maximum minutes and seconds to avoid magic number checkstyle error.
        final int MAX_MINUTES = 60;
        final int MAX_SECONDS = 60;

        int totalSeconds = (hours * MAX_MINUTES * MAX_SECONDS) + (minutes * MAX_MINUTES) + (sec);



        // Conditional to make sure number are cool
        if (hours < 0 || minutes > MAX_MINUTES || minutes < 0 || sec > MAX_SECONDS || sec < 0){
            // if something failed the check display the error, close open scanners and then exit
            // the program.
            System.out.println("Invalid time");
            in.close();
            newInput.close();
            return;
        }

        int paceTime = (int)(totalSeconds / raceDistance);

        // Divide the times by distance to get the paces.
        int newHours = (paceTime / (MAX_MINUTES * MAX_SECONDS));
        paceTime = paceTime - newHours * MAX_MINUTES * MAX_SECONDS;
        //minutes = (int)(minutes + (hours % raceDistance) * MAX_MINUTES);

        int newMinutes = (paceTime / (MAX_SECONDS));
        paceTime = paceTime - newMinutes * MAX_SECONDS;
        //sec = (int)(sec + (minutes % raceDistance) * MAX_SECONDS);

        int newSec = (paceTime);

        // Display pace.
        System.out.printf("%01d:%02d:%02d\n",  newHours, newMinutes, newSec); 

        // Close open scanners.
        newInput.close();
        in.close();
    }
}
