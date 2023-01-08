import java.util.Scanner;

/**
 * Koden skriven i Visual Studio Code 
 * Användaren skriver in radie och höjd med ett separerat mellanslag och avslutar med q
 * Användaren kan skriva in hur många r och h som helst innan den väljer att avsluta med q
 * while loop används för att skriva ut all area, mantelarea och volym
 * Användaren får sedan skriva in täljare och nämnare med ett separerat mellanslag och avsluta med q
 * Användaren kan skriva in hur många täljare och nämnare som helst innan den väljer att avsluta med q
 * while loop används för att skriva ut talet så förkortat som möjligt
 * @author Andreas Linder, andliz-1
 */

public class Assignment5 {

    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) 
	{
        int radius = 0;
        int height = 0;
        int nominator = 0;
        int denominator = 0;

        System.out.println("---------------------------------");
        System.out.println("# Test av area och volymmetoderna");
        System.out.println("---------------------------------");

        while (true)  
        {      
            radius = input();
            if (radius == -1)
                break;
            height = input();
            if (height == -1)
                break;

            double baseArea = area(radius);
            double mantelArea = area(radius, height);
            double volume = volume(radius,height);

            System.out.println("r = " + radius + " h = " + height);
            System.out.printf("Basytans area:\t  %.2f ", baseArea);
            System.out.printf("\nMantelytans area: %.2f", mantelArea);
            System.out.printf("\nVolym:\t\t\t  %.2f\n\n", volume);
        }
        

        System.out.println("---------------------------------");
        System.out.println("# Test av bråktalsmetoderna");
        System.out.println("---------------------------------");

        while (true)  
        {      
            nominator = input();
            if (nominator == -1)
                break;
            denominator = input();
            if (denominator == -1)
                break;

            int[] fractions = fraction(nominator, denominator);

            System.out.print(nominator + "/" + denominator + " = ");
            printFraction(fractions); 
            System.out.println();
        }



    }


    /**
     * Metod för att kontrollera input av användaren
     * @return användarens input eller -1
     */
    public static int input()
    {
        int input = 0;
        
        while(true)
        {

	        if (userInput.hasNextInt())
	        {
	            input = userInput.nextInt();
	            input = Math.abs(input);      
	            break;
	        }
	
	        else if (userInput.next().equals("q"))
	        {
	            input = -1;
	            break;
	        }
        }

        return input;
    }


    /**
     * Metod för att beräkna basytans area
     * @return basytans area
     */
    public static double area(int radius)
    {
        return Math.PI * Math.pow(radius, 2);
    }
    
    /**
     * Metod för att beräkna pythagoras sats
     * @return hypotenusans längd i en rätvinklig triangel
     */
    public static double pythagoras(int sideA, int sideB)
    {
        return Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));
    }


    /**
     * Metod för att beräkna mantelarea
     * @return mantelarea
     */
    public static double area(int radius, int height)
    {
        return pythagoras(radius, height) * radius * Math.PI;
    } 

    /**
     * Metod för att beräkna volym
     * @return volym
     */
    public static double volume(int radius, int height)
    {
        return Math.PI * Math.pow(radius, 2) * height / 3;
    }  

    /**
     * Metod för att beräkna bråk
     * @return Array fraction som innehåller heltal och bråktal
     */
    public static int[] fraction(int nominator, int denominator)
    {
        if (denominator != 0)
        {
            int gcd = gcd (nominator, denominator);
        
            nominator = nominator / gcd;
            denominator = denominator / gcd;
            
            int[] fractions = {nominator/denominator, nominator % denominator, denominator};
            
            return fractions;
        }
        else
            return null;
    }


    /**
     * Metod för att hitta minsta gemensama nämnare
     * @return minsta gemensama nämnare
     */
    public static int gcd(int a, int b)
    {
        int temp;
        int c=0;  

        if (b > a);
        {
            temp = a;
            a = b;
            b = temp;
        }
                
        while(b != 0)  
        {  
            c = a % b;
            a = b;  
            b = c;  
        }  
            return a;  
    }

    /**
     * Metod för att skriva ut svaren i heltal och bråkform
     */
    public static void printFraction(int[] parts)
    {
        if (parts == null)
            System.out.print("\"Error\"");
            
        else
        {
            if (parts[0] != 0)
            System.out.print(parts[0]);

            if (parts[1] !=0)
            System.out.print(" " + parts[1] + "/" + parts[2]);

            if (parts[0] == 0 && parts[1] == 0)
            System.out.print("0");
        }
    } 
}
