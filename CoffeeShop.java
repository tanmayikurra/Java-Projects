import java.util.Scanner;

/**
 * Program reports the cost of order and estimated delivery time of an inputted order
 * for the Wolfpack Coffee Shop
 * 
 * @author Tanmayi Kurra
 * @version 1.0
 */
public class CoffeeShop {
    
    /**
     * Program will ask for int hour and int min. If hour and min are invalid,
     * program will stop.
     * 
     * Program will then ask if on campus. If not on campus, program will ask for int zipcode.
     * If zipcode is invalid, program will stop.
     * 
     * Program will then ask to input the number of each item for the order.
     * If inputted number of an item is invalid, program will stop.
     * 
     * Program will then output total cost of order, and delivery time if cost of order
     * is not equal to 0.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("                   Welcome to the Wolfpack Coffee Shop!");
        System.out.print("We deliver on-campus and to nearby locations. ");
        System.out.print("There is a $2 delivery fee, ");
        System.out.print("but orders of $25 or more receive free delivery! ");
        System.out.print("Orders must be placed between 7:00 AM and 5:59 PM. ");
        System.out.print("When prompted, please enter the time and your location. ");
        System.out.print("You will then be asked to enter the number ");
        System.out.print("of each item you would like to purchase - "); 
        System.out.print("Small Coffee, Medium Coffee, Large Coffee, ");
        System.out.print("Strawberry Mango Smoothie, Mocha Shake, Chai Tea. ");
        System.out.print("The cost of your order and the estimated ");
        System.out.println("delivery time will then be output. ");
        System.out.println(" ");

        int numHour;
        int numMin;
        System.out.print("Please enter time (hr mn, eg. 8 59): ");
        numHour = scnr.nextInt();
        numMin = scnr.nextInt();

        if (!isValidTime(numHour, numMin)) {
            System.out.println("Invalid time");
            System.exit(1);
        }


        String isOnCampus;
        System.out.print("Are you on-campus? (y/n):");
        isOnCampus = scnr.next();
        
        boolean onCampus;

        if (Character.toLowerCase(isOnCampus.charAt(0)) == 'y') {
            onCampus = true;
        }
        else {
            onCampus = false;
        }

        int zipCode = 0;
        
        if (!onCampus) {
            System.out.print("Please enter your zipcode: ");
            zipCode = scnr.nextInt();

            if (!isValidZipcode(zipCode)) {
                System.out.println("Sorry, we do not deliver to that location.");
                System.exit(1);
            } 
        }

        System.out.println(" ");
        System.out.print("Please enter the number of each drink ");
        System.out.println("that you would like to purchase: ");
        System.out.println(" ");

        int numSCoffee;
        int numMCoffee;
        int numLCoffee;
        int numSmoothie;
        int numShake;
        int numChai;
        double costOfOrder;

        System.out.print("Small coffee: ");
        numSCoffee = scnr.nextInt();

        if (numSCoffee < 0) {
            System.out.println("Invalid amount");
            System.exit (1);
        }

        System.out.print("Medium coffee: ");
        numMCoffee = scnr.nextInt();

        if (numMCoffee < 0) {
            System.out.println("Invalid amount");
            System.exit (1);
        }

        System.out.print("Large coffee: ");
        numLCoffee = scnr.nextInt();

        if (numLCoffee < 0) {
            System.out.println("Invalid amount");
            System.exit (1);
        }

        System.out.print("Strawberry Mango Smoothie: ");
        numSmoothie = scnr.nextInt();

        if (numSmoothie < 0) {
            System.out.println("Invalid amount");
            System.exit (1);
        }

        System.out.print("Mocha Shake: ");
        numShake = scnr.nextInt();

        if (numShake < 0) {
            System.out.println("Invalid amount");
            System.exit (1);
        }

        System.out.print("Chai Tea: ");
        numChai = scnr.nextInt();

        if (numChai < 0) {
            System.out.println("Invalid amount");
            System.exit (1);
        }


        costOfOrder = getOrderCost(numSCoffee, numMCoffee, numLCoffee, 
        numSmoothie, numShake, numChai) / 100.0;

        System.out.println(" ");
        System.out.printf("Cost of Order: $%.2f\n", costOfOrder);

        if (getOrderCost(numSCoffee, numMCoffee, numLCoffee, numSmoothie, numShake, numChai) != 0) {
            System.out.print("Estimated Delivery Time: ");
            System.out.println(getDeliveryTime(onCampus, zipCode, numHour, numMin));
        }

    }



    /**
     * Returns whether the time (hour min) user inputted is valid
     * 
     * @param hour the hour integer value inputted
     * @param min the minute integer value inputted
     * @return true if inputted time is valid, false if inputted time is invalid
     */
    public static boolean isValidTime(int hour, int min) {
        if (min >= 0 && min <= 59) {
            if (hour >= 7 && hour <= 12) {
                return true;
            }
            else if (hour >= 1 && hour <= 5) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }


        
    /**
     * Returns whether the time (hour min) user inputted is during busy time
     * 
     * @param hour the hour integer value inputted
     * @param min the minute integer value inputted
     * @return true if inputted time is during busy time, 
     * false if inputted time is not during busy time
     */
    public static boolean isBusyTime(int hour, int min) {
        if (min >= 0 && min <= 59) {
            if (hour == 7 || hour == 8) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }


    public static boolean isValidZipcode(int zipcode) {
        if (zipcode == 27605 || zipcode == 27606) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns order cost with delivery fee in cents given the number of inputted items in order
     * Adds $2.00 in delivery fee if order is less than $25.00.
     * If all parameters equal to 0, the order cost will be returned as 0
     * 
     * @param smallCoffee number of small coffee in order
     * @param mediumCoffee number of medium coffee in order
     * @param largeCoffee number of large coffee in order
     * @param smoothie number of smoothie in order
     * @param shake number of mocha shake in order
     * @param tea number of chai tea in order
     * @return total cost of order in cents as an integer
     * @throws IllegalArgumentException if invalid amount of parameter inputted 
     * (parameter value is negative)
     */
    public static int getOrderCost
    (int smallCoffee, int mediumCoffee, int largeCoffee, int smoothie, int shake, int tea) {
        int totalPrice;
        int priceBeforeDelivery;
        int deliveryFee = 200;

        if (smallCoffee < 0 || mediumCoffee < 0 || largeCoffee < 0 || 
            smoothie < 0 || shake < 0 || tea < 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        priceBeforeDelivery = (smallCoffee * 149) + (mediumCoffee * 199) + (largeCoffee * 249) + 
            (smoothie * 450) + (shake * 450) + (tea * 450);

        if (priceBeforeDelivery == 0) {
            totalPrice = 0;
        }
        else if (priceBeforeDelivery < 2500) {
            totalPrice = priceBeforeDelivery + deliveryFee;
        }
        else {
            totalPrice = priceBeforeDelivery;
        }

        return totalPrice;

    }

    /**
     * Returns the estimated delivery time of order as a string given where the order is
     * and whether the order was placed during the busy time
     * 
     * @param onCampus whether order is on campus or not
     * @param zipcode inputted integer value of zipcode of order if off campus
     * @param hour integer value of hour from inputted time
     * @param min integer value of minute from inputted time
     * @return estimated delivery time of order as a string
     * @throws IllegalArgumentException if invalid time is inputted
     * @throws IllegalArgumentException if invalid zipcode is inputted
     */
    public static String getDeliveryTime
    (boolean onCampus, int zipcode, int hour, int min) {
        String deliveryTime;

        if (onCampus) {
            if (isValidTime(hour, min)) {
                if (isBusyTime(hour, min)) {
                    min = min + 30;
                }
                else {
                    min = min + 15;
                }
            }
            else {
                throw new IllegalArgumentException("Invalid time");
            }
        }
        else if (isValidZipcode(zipcode)) {
            if (isValidTime(hour, min)) {
                if (zipcode == 27605) {
                    if (isBusyTime(hour, min)) {
                        min = min + 40;
                    }
                    else {
                        min = min + 20;
                    }
                }
                else if (zipcode == 27606) {
                    if (isBusyTime(hour, min)) {
                        min = min + 50;
                    }
                    else {
                        min = min + 25;
                    }
                }
            }
            else {
                throw new IllegalArgumentException("Invalid time");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid zipcode");
        }


        if (min >= 60) {
            hour = hour + 1;
            min = min - 60;
        }

        if (hour == 13) {
            hour = hour - 12;
        }

        if (min < 10) {
            deliveryTime = hour + ":0" + min;
        }
        else {
            deliveryTime = hour + ":" + min;
        }

        return deliveryTime;


    }

                             
}