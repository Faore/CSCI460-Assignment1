import Scheduling.CircularMethod;
import Simulation.Simulation;
import Simulation.Job;
import Simulation.SimulationResults;

import java.util.ArrayList;
import java.util.Random;

public class ProcessScheduler {
    public static void main(String[] args) {
        int numberOfCores = 8092%3 + 2;

        Random random = new Random();

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
    }
}
