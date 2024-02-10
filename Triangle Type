import java.util.Scanner;
/**
 * This algorithm is used to take input from the user to construct a triangle.
 * Given the various lengths of the triangle, it will then output what type of
 * triangle was input. This will be either Equilateral, Isosceles or Scalene
 * 
 */

public class TriangleType {

    /**
     * main method
     * @param args Makes the function work
     */
    public static void main(String[] args) {
        System.out.println("Triangle program provides the type of any triangle."
            + "Please enter the lengths of sides a, b, and c.");

        boolean isValid = true;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter side A of triangle (as int): ");
        int sideA = in.nextInt();
        System.out.print("Enter side B of triangle (as int): ");
        int sideB = in.nextInt();
        System.out.print("Enter side C of triangle (as int): ");
        int sideC = in.nextInt();

        //Make sure no sides are negatives
        if (sideA <= 0 || sideB <= 0 || sideC <= 0){
            isValid = false;
        }

        //Make sure no sides are longer than other sides combined
        if (sideA >= sideB + sideC || sideB >= sideA + sideC || sideC >= sideA + sideB){
            isValid = false;
        }

        if (isValid){
            if (sideA == sideB && sideB == sideC){
                //Check if equilateral
                System.out.println("Equilateral triangle");
                System.out.println("Would you like to see the height (y/n):");
                String response = in.next();

                if (response.equals("y")) {
                    final double MULTIPLE = 0.5;
                    double height = (MULTIPLE * Math.sqrt(3) * sideA);
                    System.out.printf("Height: %.2f", height);
                }
            } else if (sideA == sideB || sideB == sideC || sideC == sideA) {
                //Check if isosceles
                System.out.println("Isosceles triangle");
                System.out.println("Would you like to see the perimeter (y/n):");
                String response = in.next();

                if (response.equals("y")) {
                    double perimeter = (sideA + sideB + sideC);
                    System.out.printf("Perimeter: %.2f", perimeter);
                }
            } else {
                //Check if scalene
                System.out.println("Scalene triangle");
                System.out.println("Would you like to see the area (y/n):");
                String response = in.next();

                if (response.equals("y")) {
                    double s = (sideA + sideB + sideC) / 2;
                    double area = Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
                    System.out.printf("Area: %.2f", area);
                }
            }
        } else {
            System.out.println("Not a valid triangle");
        }
        in.close();
    }
}
