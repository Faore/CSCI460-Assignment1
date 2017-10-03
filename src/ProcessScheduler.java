import Scheduling.CircularMethod;
import Scheduling.FCFSFirstAvailableCore;
import Simulation.Simulation;
import Simulation.Job;
import Simulation.SimulationResults;

import java.util.ArrayList;
import java.util.Random;

public class ProcessScheduler {
    public static void main(String[] args) {

        /*
         * Jobs must be ZERO indexed.
         */

        int numberOfCores = 8092%3 + 2;

        Random random = new Random();

        //b.1

        SimulationResults[] simulationResults = new SimulationResults[100];

        for(int x = 0; x < 100; x++) {
            ArrayList<Job> jobs = new ArrayList<Job>();
            for(int i = 0; i < 1000; i++) {
                jobs.add(new Job(i, i, random.nextInt(501-1) + 1));
            }
            Simulation simulation = new Simulation(numberOfCores, new CircularMethod(), jobs);
            simulationResults[x] = simulation.runSimulation();
        }

        System.out.println("Part b.1 (Statistics from 100 trials of overall turnaround time with 1000 jobs between 1ms and 500ms):");

        int summedOverallTurnaroundTimes = 0;
        int maxOverallTurnaroundTime = 0;
        int minOverallTurnaroundTime = Integer.MAX_VALUE;
        for (SimulationResults result: simulationResults) {
            summedOverallTurnaroundTimes += result.overallTurnaroundTime;
            if(minOverallTurnaroundTime > result.overallTurnaroundTime) {
                minOverallTurnaroundTime = result.overallTurnaroundTime;
            }
            if(maxOverallTurnaroundTime < result.overallTurnaroundTime) {
                maxOverallTurnaroundTime = result.overallTurnaroundTime;
            }
        }

        double averageOverallTurnaroundTime = ((double)summedOverallTurnaroundTimes)/((double)simulationResults.length);

        //Calculate Standard Deviation.
        double variance = 0;
        for(SimulationResults result: simulationResults) {
            variance += Math.pow(averageOverallTurnaroundTime - result.overallTurnaroundTime, 2);
        }
        variance = variance/(double) simulationResults.length;

        double stdDevTurnaroundTime = Math.sqrt(variance);

        System.out.println("Average of Overall Turnaround Times: " + averageOverallTurnaroundTime);
        System.out.println("Min of Overall Turnaround Times: " + minOverallTurnaroundTime);
        System.out.println("Max of Overall Turnaround Times: " + maxOverallTurnaroundTime);
        System.out.println("StdDev of Overall Turnaround Times: " + stdDevTurnaroundTime + "\n");

        //b.2

        System.out.println("Part b.2 (Circular Method on the 12 job table):");
        System.out.println("Note that the job table has been shifted to be Zero-Indexed.");

        Simulation circular12 = new Simulation(numberOfCores, new CircularMethod(), predefinedTable());
        circular12.printResults = true;
        circular12.printTable = true;
        SimulationResults circular12results = circular12.runSimulation();

        System.out.println("Part c: Custom method to beat circular method:");
        System.out.println("Test on 12 job table:");

        Simulation custom12 = new Simulation(numberOfCores, new FCFSFirstAvailableCore(), predefinedTable());
        custom12.printResults = true;
        custom12.printTable = true;
        SimulationResults custom12results = custom12.runSimulation();

        //Custom;

        SimulationResults[] customSimulationResults = new SimulationResults[100];

        for(int x = 0; x < 100; x++) {
            ArrayList<Job> customJobs = new ArrayList<Job>();
            for(int i = 0; i < 1000; i++) {
                customJobs.add(new Job(i, i, random.nextInt(501-1) + 1));
            }
            Simulation customSimulation = new Simulation(numberOfCores, new FCFSFirstAvailableCore(), customJobs);
            customSimulationResults[x] = customSimulation.runSimulation();
        }

        System.out.println("Statistics from 100 trials of overall turnaround time with 1000 jobs between 1ms and 500ms on custom method:");

        int customSummedOverallTurnaroundTimes = 0;
        int customMaxOverallTurnaroundTime = 0;
        int customMinOverallTurnaroundTime = Integer.MAX_VALUE;
        for (SimulationResults result: customSimulationResults) {
            customSummedOverallTurnaroundTimes += result.overallTurnaroundTime;
            if(customMinOverallTurnaroundTime > result.overallTurnaroundTime) {
                customMinOverallTurnaroundTime = result.overallTurnaroundTime;
            }
            if(customMaxOverallTurnaroundTime < result.overallTurnaroundTime) {
                customMaxOverallTurnaroundTime = result.overallTurnaroundTime;
            }
        }

        double customAverageOverallTurnaroundTime = ((double)customSummedOverallTurnaroundTimes)/((double)customSimulationResults.length);

        //Calculate Standard Deviation.
        double customVariance = 0;
        for(SimulationResults result: customSimulationResults) {
            customVariance += Math.pow(customAverageOverallTurnaroundTime - result.overallTurnaroundTime, 2);
        }
        customVariance = customVariance/(double) customSimulationResults.length;

        double customStdDevTurnaroundTime = Math.sqrt(customVariance);

        System.out.println("Average of Overall Turnaround Times: " + customAverageOverallTurnaroundTime);
        System.out.println("Min of Overall Turnaround Times: " + customMinOverallTurnaroundTime);
        System.out.println("Max of Overall Turnaround Times: " + customMaxOverallTurnaroundTime);
        System.out.println("StdDev of Overall Turnaround Times: " + customStdDevTurnaroundTime + "\n");

    }

    public static ArrayList<Job> predefinedTable() {
        ArrayList<Job> jobTable = new ArrayList<Job>();
        jobTable.add(new Job(0, 4, 9));
        jobTable.add(new Job(1, 15, 2));
        jobTable.add(new Job(2, 18, 16));
        jobTable.add(new Job(3, 20, 3));
        jobTable.add(new Job(4, 26, 29));
        jobTable.add(new Job(5, 29, 198));
        jobTable.add(new Job(6, 35, 7));
        jobTable.add(new Job(7, 45, 170));
        jobTable.add(new Job(8, 57, 180));
        jobTable.add(new Job(9, 83, 178));
        jobTable.add(new Job(10, 88, 73));
        jobTable.add(new Job(11, 95, 8));

        return jobTable;
    }
}
