import java.util.Scanner;

/** Simulate the check-in process of an airline.
 */
public class AirlineCheckinSim {
    Scanner scan = new Scanner(System.in);
// Data Fields
    /**
     * Queue of frequent flyers.
     */
    private PassengerQueue frequentFlyerQueue =
            new PassengerQueue("Frequent Flyer");
    /**
     * Queue of regular passengers.
     */
    private PassengerQueue regularPassengerQueue =
            new PassengerQueue("Regular Passenger");
    /**
     * Maximum number of frequent flyers to be served
     * before a regular passenger gets served.
     */
    private int frequentFlyerMax = 0;
    /**
     * Maximum time to service a passenger.
     */
    private int maxProcessingTime;
    /**
     * Total simulated time.
     */
    private int totalTime;
    /**
     * If set true, print additional output.
     */
    private boolean showAll;
    /**
     * Simulated clock.
     */
    private int clock = 0;
    /**
     * Time that the agent will be done with the current passenger.
     */
    private int timeDone;
    /**
     * Number of frequent flyers served since the
     * last regular passenger was served.
     */
    private int frequentFlyersSinceRP;

    /**
     * Main method.
     *
     */
    private void runSimulation() {
        for (clock = 0; clock < totalTime; clock++) {
            frequentFlyerQueue.checkNewArrival(clock, showAll);
            regularPassengerQueue.checkNewArrival(clock, showAll);
            if (clock >= timeDone) {
                startServe();
            }
        }
    }
    private void startServe() {
        if ((frequentFlyersSinceRP <= frequentFlyerMax)
                || regularPassengerQueue.isEmpty()) {
            if (!frequentFlyerQueue.isEmpty()) {
// Serve the next frequent flyer.
                frequentFlyersSinceRP++;
                timeDone = frequentFlyerQueue.update(clock, showAll);
            } else if (!regularPassengerQueue.isEmpty()) {
// Serve the next regular passenger.
                frequentFlyersSinceRP = 0;
                timeDone = regularPassengerQueue.update(clock, showAll);
            } else if (showAll) {
                System.out.println("Time is " + clock
                        + " server is idle");
            }
        } else if (!regularPassengerQueue.isEmpty()) {
// Serve the next regular passenger.
            frequentFlyersSinceRP = 0;
            timeDone = regularPassengerQueue.update(clock, showAll);
        } else if (showAll) {
            System.out.println("Time is " + clock
                    + " server is idle");
        }
    }
    /** Method to show the statistics. */
    private void showStats() {
        System.out.println
                ("\nThe number of regular passengers served was "
                        + regularPassengerQueue.getNumServed());
        double averageWaitingTime1 =
                (double) regularPassengerQueue.getTotalWait()
                        / (double) regularPassengerQueue.getNumServed();
        System.out.println(" with an average waiting time of "
                + averageWaitingTime1);
        System.out.println("The number of frequent flyers served was "
                + frequentFlyerQueue.getNumServed());
        double averageWaitingTime2 =
                (double) frequentFlyerQueue.getTotalWait()
                        / (double) frequentFlyerQueue.getNumServed();
        System.out.println(" with an average waiting time of "
                + averageWaitingTime2);
        System.out.println("Passengers in frequent flyer queue: "
                + frequentFlyerQueue.size());
        System.out.println("Passengers in regular passenger queue: "
                + regularPassengerQueue.size());
    }
    public void enterData(){
        System.out.print("Expected number of frequent flyers per hour: ");
         frequentFlyerQueue.arrivalRate =  (scan.nextInt()/60) ;
        System.out.print("Expected number of regular passenger arrivals per hour: ");
        regularPassengerQueue.arrivalRate  = (scan.nextInt()/60);
        System.out.print("Maximum service time in minutes: ");
        maxProcessingTime = scan.nextInt();
        System.out.print("Total simulation time in minutes: ");
        totalTime = scan.nextInt();
        System.out.print("Display minute-by-minute trace of simulation (Y or N): ");
        scan.next("Y");
        if(showAll){
            scan.next("Y");


        }
        else
            showAll = false;
    }
    public void helloMike(){
        System.out.println("Hello Mike this is Dominic Zelinsky, I added this method to your code!");
    }

    public static void main(String[] args) {
        AirlineCheckinSim sim = new AirlineCheckinSim();
        sim.helloMike();
        sim.enterData();
        sim.runSimulation();
        sim.showStats();
        System.exit(0);
    }

}
