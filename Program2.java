import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 */
public class Program2
{

    public static Semaphore even            = new Semaphore(1);
    public static Semaphore odd             = new Semaphore(0);
    public static Semaphore mutualExclusion = new Semaphore(1);
    public static Semaphore evenSelector    = new Semaphore(1);
    public static Semaphore oddSelector     = new Semaphore(0);

    public static Stack<Integer> odds           = new Stack<Integer>();
    public static LinkedList<Integer> evens     = new LinkedList<Integer>();
    public static LinkedList<Integer> priority  = new LinkedList<Integer>();
    public static LinkedList<Integer> oddGroup  = new LinkedList<Integer>();
    public static int selectedInt = -1;
    public static int selectedOdd = -1;
    public static int numberOfWrenches = 0;

    public static void main(String[] args){

        int numberOfValves = 0;
        int magicNumber = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of valves (5 < v <= 100)\n> ");
        if(scanner.hasNextInt()) {
            numberOfValves = scanner.nextInt();
            validate(5, numberOfValves, 100 + 1);
        } else {
            System.out.println("You did not enter a valid number.");
            System.exit(1);
        }

        System.out.print("Enter number of wrenches (3 < w <=25)\n> ");
        if(scanner.hasNextInt()) {
            numberOfWrenches = scanner.nextInt();
            validate(3, numberOfWrenches, 25 + 1);
        } else {
            System.out.println("You did not enter a valid number.");
            System.exit(1);
        }

        System.out.print("Enter a magic number (0 <= x < 12)\n> ");
        if(scanner.hasNext()) {
            magicNumber = scanner.nextInt();
            validate(0 - 1, magicNumber, 12);
        } else {
            System.out.println("You did not enter a valid number.");
            System.exit(1);
        }

        ValveOpener[] valveOpeners = new ValveOpener[numberOfValves];

        for(int i = 1; i < numberOfValves+1; i++) {
            if(i % 2 == 0) {
                if(magicNumber != 0 && i % magicNumber == 0) {
                    priority.add(i);
                } else {
                    evens.add(i);
                }
            } else {
                odds.push(i);
            }
            valveOpeners[i-1] = new ValveOpener(i);
        }

        System.out.println(evens.size());
        new Thread(new RandomSelector()).start();
        new Thread(new OddSelector()).start();
        for(int i = 0; i < valveOpeners.length; i++) {
            new Thread(valveOpeners[i]).start();
        }

    }

    /**
     * This method ensures I have the good values. I am able to do this logic only because
     * integers have no inbetween numbers. So I can do a -1 for the lowest and +1 for the highest.
     *
     * @param lowest
     * @param value
     * @param highest
     */
    public static void validate(int lowest, int value, int highest) {
        if(lowest < value && value < highest) {}
        else {
            System.out.println("The value is not within the range.");
            System.exit(1);
        }
    }
}
