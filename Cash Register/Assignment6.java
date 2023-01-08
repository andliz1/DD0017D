import java.util.Date;
import java.util.Random;
import java.util.Scanner;


/**
 * Program som fungerar som ett enklare kassasystem
 * Användaren får välja via en meny om en ska lägga till artikel, ta bort artikel, sälja artikel eller visa information om sålda artiklar
 * @author Andreas Linder, andliz-1
 */
public class Assignment6 
{

    private static Scanner userInput = new Scanner(System.in);
    /**
     * Mainmetod som kör programmet, tar upp menyn och använder användarens input
     * @param articles Artiklarna som finns i kassasystemet
     * @param sales Information om det som har sålts
     * @param salesDate Datum och tid på en försäljning
     */
    public static void main(String[] args) 
    { 
        int [][] articles = new int [10][3]; 
        int [][] sales = new int [10][3]; 
        Date [] salesDate = new Date[10];
        int articleNumber = 1000;

        do
        {
            switch (menu()) 
            {
                case 1:
                    System.out.printf("Hur många artiklar ska läggas till? ");
                    int noOfArticles = input();
                    if (noOfArticles != 0)
                    {
                        articles = insertArticles (checkFull(articles,noOfArticles), articleNumber, noOfArticles);
                        articleNumber += noOfArticles; // Efter den har lagt till artiklarna ska nästa gång användaren vill lägga till en artikel börja med nästa nummer
                        System.out.printf("Du har lagt till " + noOfArticles + " artiklar!");
                        break;
                    }
                    else
                    {
                        System.out.printf("Du kan inte addera 0 artiklar!");
                        break;
                    }
                case 2:
                    removeArticle (articles);
                    break;
                case 3:
                    printArticles (articles);
                    break;
                case 4:
                    sellArticle(sales, salesDate, articles);
                    break;
                case 5:
                    printSales(sales, salesDate);
                    break;
                case 6:
                    sortedTable(sales, salesDate);
                    break;
                case 7:
                    System.exit(0); 
                default:
                    System.out.printf("%nPlease type a number between 1-7!"); // Om det är en int som inte finns i menyn talar programmet om det
                    break;

            }
        } while (true);
    }

    /**
     * Metod som tar upp menyn
     * @return användarens menyval
     */
    public static int menu()
    {

        System.out.printf("%n1. Lägg in artiklar%n2. Ta bort artikel%n3. Visa artiklar%n4. Försäljning%n5. Orderhistorik%n6. Sortera orderhistoriktabell%n7. Avsluta%n");               

        int userChoice = input();

        return userChoice;


    }

    /**
     * Metod som kontrollerar om användares input är en int
     * @return användarens input
     */
    public static int input()
    {

        int input = 0;       

        while(true)
        {
    
            if (userInput.hasNextInt())
            {
                input = userInput.nextInt();    
                break;
            }

            else if (!userInput.hasNextInt())
            {
                userInput.nextLine();
            }
        }
    
        return input;
    }
    
    /**
     * Metod som adderar artiklar
     * @return Array med artiklar, antal och pris
     */
    public static int[][] insertArticles (int[][]articles, int articleNumber, int noOfArticles)
    {
        Random randomNumber = new Random();
        int lowestPrice = 100;
        int highestPrice = 1000;
        int lowestQuantity = 1;
        int highestQuantity = 10;
        int price = 0;
        int quantity = 0;

        swap(articles); // Sortera så att de tomma rutorna kommer först

        for (int i = 0; i < noOfArticles; i++)
        {
            // priset och kvantiteten slumpas fram inom angivna intervaller
            price = lowestPrice + randomNumber.nextInt(highestPrice - lowestPrice + 1); 
            quantity = lowestQuantity + randomNumber.nextInt(highestQuantity - lowestQuantity + 1);   

            articles [i][0] = articleNumber;
            articles [i][1] = quantity;
            articles [i][2] = price;

            articleNumber += 1; // Plussar på ett så det blir ett högre värde nästa gång
        }

        return articles;
    }

    /**
     * Metod som kontrollerar om tillräckligt med lediga platser finns i artikelmatrisen
     * Om den är full så utökas platserna så det finns tillräckligt för alla önskade artiklar
     * @return ursprungsmatris om tillräckligt platser finns, annars en ny array med tillräckligt tomma platser
     */
     public static int[][] checkFull(int[][]articles, int noOfArticles)
    {
        int counter = 0;

        for (int i = 0; i < articles.length; i++)
        {
            if (articles[i][0] == 0)
            {
            counter += 1;
            } 
        }
            
        if (noOfArticles > counter)
        {
            int extraRows = (noOfArticles - counter); // Extra rader som behövs
            int extendedArray[][] = new int [articles.length + extraRows][3]; 
            
            for (int i = 0; i < articles.length; i++)
            {
                extendedArray[i] = articles[i];
            }
            
            return extendedArray;
        }

        else 
        {
            return articles;
        }
    }
    
    /**
     * Metod som tar bort artiklar
     * Alla värden sätts till 0
     */
    public static void removeArticle (int[][]articles)
    {
        System.out.printf("Vilken artikel vill du ta bort? ");
        int removeArticle = input();
        Boolean soldArticle = false;

        if (removeArticle != 0)
        {
            for (int i = 0; i < articles.length; i++)
            {
                if (articles[i][0] == removeArticle)
                {
                    articles [i][0] = 0;
                    articles [i][1] = 0;
                    articles [i][2] = 0;
                    soldArticle = true;    
                }
            }

            if (soldArticle == true)
            {
                System.out.printf("Du har tagit bort artikel " + removeArticle + "!");
            }
            else
            {
                System.out.println("Du har inte denna artikel i ditt sortiment.");
            }
        }

        else
        {
            System.out.printf("Du kan inte ta bort 0 artiklar!");
        }
    }

    /**
     * Metod som printar tabell med alla artiklar, antal och pris
     */
    public static void printArticles (int[][]articles)
    {
        articles = swap(articles);
        System.out.println("\nArticle Number Quantity Price");
        System.out.print("==============================");
        for (int i = 0; i < articles.length; i++) // Printar ut tabellen
        {   if (articles[i][0] !=0)
            {
                System.out.print("\n");
                for (int j = 0 ; j < articles[i].length; j++)
                    System.out.print(articles[i][j] + " ");
            }

        }
        System.out.print("\n");
    }

    /**
     * Metod för att sälja artiklar
     * Fråga efter artikelnummer
     * Fråga efter antal varor
     * Kolla om antal varor finns för just det artikelnummer
     * Subtrahera antal varor 
     * Registrera försäljningen med artikelnummer, antal och summa i matrisen sales
     */
    public static void sellArticle(int[][]sales, Date[] salesDate, int[][]articles)
    {
        System.out.printf("What article do you want to sell? ");
        int articleNumber = input();
        System.out.printf("How many? ");
        int quantity = input();
        int sumPrice;

        if (articleNumber != 0)
        {
            for (int i = 0; i < articles.length; i++)
            {
                if (articles[i][0] == articleNumber)
                {
                    if (quantity <= articles [i][1])
                    {
                        articles [i][1] -= quantity;
    
                        for (int j = 0; j < sales.length; j++)
                        {
                            if (sales[j][0] == 0)
                            {
                                sumPrice = articles [i][2] * quantity;

                                sales[j][0] = articleNumber;
                                sales[j][1] = quantity;
                                sales[j][2] = (sumPrice);
                                salesDate[j] = new Date();
                                System.out.printf("Du har sålt " + quantity + " st av artikelnummer " + articleNumber +"%n");
                                break;
                            }   
                        }
                    }
                    else
                    {
                        System.out.printf("Du kan inte sälja fler artiklar än du har!");
                    }
                }
            }
        }
    }

    /**
     * Metod för att skriva ut försäljningsarna
     * Skriver ut alla försäljningstransaktioner med datum, artikelnummer, antal och summa.
     */
    public static void printSales(int[][]sales, Date[] salesDate)
    {
        System.out.printf("%nDate Article Number Quantity Amount%n=====================================");

        for (int i = 0; i < sales.length; i++) 
        {
            if (sales[i][0] != 0)
                System.out.printf("%n" + salesDate[i] + " ");

            for (int j = 0; j < sales[i].length; j++) 
            {
                if (sales[i][j] !=0)
                {
                    System.out.print(sales[i][j] + " ");
                }
            } 
        }
        System.out.printf("%n");
    }

    /**
     * Metod för att skriva ut försäljningsarna sorterade efter artikelnummer
     * Skapar två kopior av försäljning och datum matriserna för att kunna sortera
     */
    public static void sortedTable(int[][]sales,  Date[] salesDate)
    {
       Date[] sortedSalesDate = new Date[salesDate.length];
       System.arraycopy(salesDate, 0, sortedSalesDate, 0, salesDate.length);
       int [][] sortedSales = new int[sales.length][];

        for(int i = 0; i < sales.length; i++)
        {
            sortedSales[i] = new int[sales[i].length];
            System.arraycopy(sales[i], 0, sortedSales[i], 0, sortedSales[i].length);
        }

        // sorterar
        int sort_index = 0;
        Boolean sorted;

        do
        {         
             sorted = true;
 
             for(int i = 0;i < (sortedSales.length-1); i++)
             {
                 if(sortedSales[i][sort_index] > sortedSales[i+1][sort_index])
                 {
                     int[] temp = sortedSales[i];
                     sortedSales[i] = sortedSales[i+1];
                     sortedSales[i+1] = temp;
                     sorted = false;
 
                     // sorterar vi sales arrayen så sorterar vi också date array
                     if (!sorted)
                     {
                         Date tempDate = sortedSalesDate[i];
                         sortedSalesDate[i] = sortedSalesDate[i+1];
                         sortedSalesDate[i+1] = tempDate;
                     }
                 }
             }
             } while(!sorted);

       // EFter att vi har sorterat så printar vi tabellen med hjälp av den andra metoden
       printSales(sortedSales, sortedSalesDate);
    }


    /**
     * Metod för att sortera artikelmatrisen
     * @return sorterad artikelmatris i stigande ordning
     */
    public static int[][] swap (int[][] articles)
    {
        int sort_index = 0; 
        Boolean sorted;
       
        do
        {
            sorted = true;

            for(int i = 0;i < (articles.length-1); i++)
            {
                if(articles[i][sort_index] > articles[i+1][sort_index])
                {
                    int[] temp = articles[i];
                    articles[i] = articles[i+1];
                    articles[i+1] = temp;
                    sorted = false;
                }
            }
        } while (!sorted);
        
        return articles;
    }
}

