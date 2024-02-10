public class TriangleArea {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter side A of triangle (as int): ");
        int sideA = in.nextInt();
        System.out.print("Enter side B of triangle (as int): ");
        int sideB = in.nextInt();
        System.out.print("Enter side C of triangle (as int): ");
        int sideC = in.nextInt();
        double s = (sideA+sideB+sideC) / 2;
        double area = sqrt(s*s-sideA*s-sideB*s-sideC);
        System.out.println("The area of the triangle is: " + "area");
    }

}
