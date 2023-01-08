import java.util.Scanner;

/**
 * Practice exam som skrevs under 6h 
 * Parkeringssystem som hanterar infart, utfart och kostnad
 * @author Andreas Linder
 */
public class PracticeExam {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) 
    {
        String[][] carInfo = new String[10][6]; // License plate, inDate, Outdate, charge,  Cost, isParked
        int parkedCars = 0;
        
        do {  
            menu();
            
            switch (input()) {
                case 1:
                    carInfo = driveIn(carInfo, parkedCars);
                    parkedCars++;
                    break;
                case 2:       
                    carInfo = driveOut(carInfo, parkedCars); 
                    break;
                case 3:
                    checkParking(carInfo, parkedCars);                    
                    break;
                case 4:
                    printSortedDateInHistory(carInfo, parkedCars);
                    break;
                case 5:
                    printSortedLicensePlateHistory(carInfo,parkedCars);
                    break;
                case -1:
                    System.exit(0);            
                default:
                    System.out.println("\nPlease type a number between 1-5");
                    break;
            }
            
        } while (true);
    }

    /**
     * Metod för att kopiera array
     */
    public static String[][] copyArray(String[][] carInfo, int parkedCars)
    {
        String[][] sortedCarInfo = new String[parkedCars][];

        for(int i = 0; i < parkedCars; i++)
        {
            sortedCarInfo[i] = new String[carInfo[i].length];
            System.arraycopy(carInfo[i], 0, sortedCarInfo[i], 0, sortedCarInfo[i].length);
        }

        return sortedCarInfo;
    }

    /**
     * Metod för att printa ut parkeringshistorik sorterat i datum
     */
    public static void printSortedDateInHistory(String[][] carInfo, int parkedCars)
    {
        int sort_index = 1;

        String [][] newCarInfo = copyArray(carInfo, parkedCars);

        String[][] sortedDateArray = sortCarInfo(newCarInfo, parkedCars, sort_index);

        printParkingHistory(sortedDateArray, parkedCars);
    }

    /**
     * Metod för att printa ut parkeringshistorik sorterat i registreringsskylt
     */
    public static void printSortedLicensePlateHistory(String[][] carInfo, int parkedCars)
    {
        int sort_index = 0;

        String [][] newCarInfo = copyArray(carInfo, parkedCars);

        String[][] sortedLicenseArray = sortCarInfo(newCarInfo, parkedCars, sort_index);

        printParkingHistory(sortedLicenseArray, parkedCars);
    }

    /**
     * Metod för att printa ut parkeringshistorik 
     */
    public static void printParkingHistory(String[][] carInfo, int parkedCars)
    {
        System.out.println("Registration  Entered         Exited       Charging    Parking cost");
        for (int i = 0; i < parkedCars; i++) 
        {
            for (int j = 0; j < 5; j++) 
            {
                if (carInfo[i][j] != null)
                System.out.print(carInfo[i][j]+ "       ");  
                else
                System.out.print("                 ");  
            }
            System.out.println("");
        }
    }

    /**
     * Metod för att sortera array
     */
    public static String[][] sortCarInfo(String[][] carInfo, int parkedCars, int sort_index)
    {
        boolean sorted;

        do{

            sorted = true;

            for (int i = 0; i < parkedCars-1; i++) 
            {
                if(carInfo[i][sort_index].compareTo(carInfo[i+1][sort_index]) > 0)
                {
                    String[] temp = carInfo[i];
                    carInfo[i] = carInfo[i+1];
                    carInfo[i+1] = temp;
                    sorted = false;
                }
            }
        } while (!sorted);

        return carInfo;
    }

    /**
     * Metod för att kontrollera om bil är parkerad
     */
    public static void checkParking(String[][] carInfo, int parkedCars)
    {
        String registrationNr;
        boolean carExist = false;
        String inDate = "";

        do{
            System.out.print("\nEnter registration number: ");
            registrationNr = sc.nextLine();
            if (checkLicensePlate(registrationNr))
                {
                    break;
                }
        }while(true);

        for (int i = 0; i < parkedCars; i++) 
        {
            if(registrationNr.equals(carInfo[i][0]) && carInfo[i][5].equals("Y"))
            {
                carExist = true;
                inDate = carInfo[i][1];
                break;
            }
        }

        if(carExist)
        {
            System.out.println("Car " + registrationNr + " is currently parked since " + inDate);
        }
        else
            System.out.println("Car " + registrationNr + " is not parked at the moment");
    }

    /**
     * Metod för att köra ut från parkeringen
     */
    public static String[][] driveOut(String[][] carInfo, int parkedCars)
    {

        String registrationNr;
        String inDate;
        String outDate;
        String chargeVehicle;
        int parkingDays;
        int cost = -1;


        do{
            System.out.print("\nEnter registration number: ");
            registrationNr = sc.nextLine();
            if (checkLicensePlate(registrationNr))
                {
                    break;
                }
                
        }while(true);

        do 
        {
            System.out.print("Current date (YYYY-MM-DD): ");
            outDate = sc.nextLine();
            if(checkDate(outDate))
            {
                break;
            }
            
        } while (true);

        for (int i = 0; i < parkedCars; i++) 
        {
            if (registrationNr.equals(carInfo[i][0]))
            {
                inDate = carInfo[i][1];
                chargeVehicle = carInfo[i][3];
                parkingDays = calculateParkingDays(inDate, outDate);

                if (parkingDays > 0)
                {
                    cost = calculateParkingCost(parkingDays, chargeVehicle);
                    carInfo[i][2] = outDate;
                    carInfo[i][4] = String.valueOf(cost);
                    carInfo[i][5] = "N";
                    printRecipe(registrationNr, inDate, outDate, parkingDays, chargeVehicle, cost);
                }
                else
                    System.out.println("\nYou can't leave the parking lot before you arrive!");

                break;
            }
        }      

        return carInfo;
    }

    /**
     * Metod för att printa ut kvitto på parkeringen
     */
    public static void printRecipe(String regNr, String inDate, String outDate, int parkingDays, String chargeVehicle, int parkingCost)
    {
        System.out.println("###################################");
        System.out.println("# RECEIPT PARKING #");
        System.out.println("###################################");
        System.out.println("# Reg IN OUT #");
        System.out.println("#" + regNr + " " + inDate + " " + outDate + " #");
        System.out.println("# #");
        System.out.println("# Number of days: " + parkingDays + " days #");
        System.out.println("# Charge: " + chargeVehicle +"#");
        System.out.println("# Cost: " + parkingCost + " kr #");
        System.out.println("###################################");
    }

    /**
     * Metod för att beräkna dagar som bil varit parkerad
     */
    public static int calculateParkingDays(String inDate, String outDate)
    {
        final int MONTH = 12;
        final int DAY = 30;

        String[] dateInArray = inDate.split("-");
        String[] dateOutArray = outDate.split("-");

        int inYear = Integer.parseInt(dateInArray[0]);
        int outYear = Integer.parseInt(dateOutArray[0]);
        int inMonth = Integer.parseInt(dateInArray[1]);
        int outMonth = Integer.parseInt(dateOutArray[1]);
        int inDay = Integer.parseInt(dateInArray[2]);
        int outDay = Integer.parseInt(dateOutArray[2]);

        int parkingDays = ((outYear - inYear) * MONTH * DAY) + ((outMonth-inMonth) * DAY) + (outDay-inDay + 1);

        return parkingDays;

    }

    /**
     * Metod för att beräkna parkeringskostnad
     */
    public static int calculateParkingCost(int parkingDays, String chargeVehicle)
    {
        
        int cost = 0;

        if (parkingDays > 30)
        {
            System.out.println("You have parked for too long - your fee is going to be 10 000 kr");
            cost = 10000;
        }

        else if (parkingDays > 10 && parkingDays < 31)
            cost =  10 * 120 + (parkingDays - 10) * 50;

        else if (parkingDays < 11 && parkingDays > 0)
            cost = parkingDays * 120;
        
        if (chargeVehicle.equals("Y"))
            cost += 250;

        return cost;


    }

    /**
     * Metod för att registrera parkering
     */
    public static String[][] driveIn(String[][] carInfo, int parkedCars)
    {
        do{
            System.out.print("\nEnter registration number: ");
            String registrationNr = sc.nextLine();
            if (checkLicensePlate(registrationNr))
                {
                    carInfo[parkedCars][0] = registrationNr;
                    break;
                }
                
        }while(true);


        do 
        {
            System.out.print("Current date (YYYY-MM-DD): ");
            String currentDate = sc.nextLine();
            if(checkDate(currentDate))
            {
                carInfo[parkedCars][1] = currentDate;
                break;
            }
            
        } while (true);
        

        do
        {
            System.out.print("Charge electric vehicle (Y/N): ");
            String chargVehicle = sc.nextLine();
                if(checkCharge(chargVehicle))
                {
                    carInfo[parkedCars][3] = chargVehicle;
                    break;
                }
        } while(true);

        carInfo[parkedCars][5] = "Y";
        parkedCars++;

        return carInfo;

    }

    /**
     * Metod för att se om bil ska laddas
     */
    public static boolean checkCharge(String chargeVehicle)
    {
        if(chargeVehicle.equals("Y"))
            return true;

        else if(chargeVehicle.equals("N"))
            return true;
        else 
            return false;
    }

    /**
     * Metod för att se att registreringsskyld är i rätt format
     */
    public static boolean checkLicensePlate(String licensePlate)
    {
        if (licensePlate.matches("[a-zA-Z]{3}.*[0-9]{3}"))
            return true;
        else
            System.out.print("Wrong format, try again: ");
            return false;
    }

    /**
     * Metod för att kontrollera att datum är i rätt format
     */
    public static boolean checkDate(String date)
    {
        boolean correctDateFormat = true;

        String[] dateArray = date.split("-");

        int year;
        int month;
        int day;

        try {

            year = checkInt(dateArray[0]);
            month = checkInt(dateArray[1]);
            day = checkInt(dateArray[2]);
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong format try again: ");
            return false;
        }



        if (year <1000 || year > 9999)
        {
            System.out.println("Please enter a year between 1000 and 9999!");
            correctDateFormat = false;
        }

        if (month < 1 || month > 12)
        {
            System.out.println("Please enter a month between 1 and 12.");
            correctDateFormat = false;
        }

        if (day < 1 || day > 30)
        {
            System.out.println("Please enter a day between 1 and 30.");
            correctDateFormat = false;
        }

        return correctDateFormat;
    }

    public static int checkInt(String strNr)
    {
        int number = -1;

        try
        {
            number = Integer.parseInt(strNr);
        }
        catch (NumberFormatException ex)
        {

        }

        return number;
    }

    /**
     * Metod för att printa ut huvudmenyn
     */
    public static void menu()
    {
        System.out.println("\n----- LULEA AIRPORT PARKING LOT -----");
        System.out.println("1. Drive in");
        System.out.println("2. Drive out");
        System.out.println("3. Check parking");
        System.out.println("4. Print parking history (by arrival date)");
        System.out.println("5. Print parking history (by registration number)");
        System.out.println("q. End program");
        System.out.print("Enter your option: ");
    }

    /**
     * Metod för att kontrollera input
     */
    public static int input()
    {
        int userInput = 0;       

        while(true)
        {
            if (sc.hasNextInt())
            {
                userInput = sc.nextInt(); 
                sc.nextLine();   
                break;
            }

            else if (sc.next().equals("q"))
	        {
	            userInput = -1;
	            break;
	        }

            else
            {
                sc.nextLine();
            }
        }
        
        return userInput;
    }
    
}
