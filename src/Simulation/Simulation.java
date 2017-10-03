package Simulation;

import Scheduling.SchedulingMethod;
import Simulation.Processor.Core;
import Simulation.Processor.Processor;

import java.util.ArrayList;

public class Simulation {
    public final int numberOfCores;
    public Processor processor;
    public SchedulingMethod schedulingMethod;
    public ArrayList<Job> jobs;
    public int lastJobAdded = -1;
    public int currentTick = 0;
    public boolean printResults = false;
    public boolean printTable = false;

    public Simulation(int numberOfCores, SchedulingMethod method, ArrayList<Job> jobs) {
        this.numberOfCores = numberOfCores;
        this.processor = new Processor(numberOfCores, this);
        this.schedulingMethod = method;
        this.jobs = jobs;
        //System.out.println(simulationPrefix() + " Preparing simulation for " + numberOfCores + " cores, " + jobs.size() + " jobs using " + method.getClass().getSimpleName() + ".");
    }

    public String simulationPrefix() {
        return "[" + numberOfCores + "," + jobs.size() + "," + schedulingMethod.getClass().getSimpleName() + "]";
    }

    public SimulationResults runSimulation() {
        //System.out.println(simulationPrefix() + " Starting simulation.");
        Scheduler scheduler = new Scheduler(processor, schedulingMethod);
        //Each iteration of this loop represents one "tick" of the processor
        while (scheduler.jobQueue.size() > 0 || !processor.idle() || lastJobAdded + 1 != jobs.size()) {
            //Add new jobs to the scheduler
            while(true) {
                if(lastJobAdded + 1 == jobs.size()) {
                    break;
                }
                if(jobs.get(lastJobAdded + 1).arrivalTime == currentTick) {
                    scheduler.addJobToQueue(jobs.get(++lastJobAdded));
                } else {
                    break;
                }
            }
            //Check if any cores have finished a job.
            for(Core core: processor.cores) {
                Job finishedJob = core.removeJobIfFinished();
                if(finishedJob != null) {
                    finishedJob.finishTime = currentTick;
                    scheduler.coreFreed(core, finishedJob);
                }
            }
            //Perform any scheduling
            scheduler.schedulingMethod.prepareJobs();
            //Have processor perform next tick.
            processor.tick();
            currentTick++;
        }
        //System.out.println(simulationPrefix() + " Simulation complete.");
        //Prepare results.

        SimulationResults results = new SimulationResults();
        int summedTurnaroundTime = 0;
        int maxTurnaroundTime = 0;
        int minTurnaroundTime = Integer.MAX_VALUE;
        for (Job job : jobs) {
            job.calculateTurnaroundTime();
            summedTurnaroundTime += job.turnaroundTime;
            if(minTurnaroundTime > job.turnaroundTime) {
                minTurnaroundTime = job.turnaroundTime;
            }
            if(maxTurnaroundTime < job.turnaroundTime) {
                maxTurnaroundTime = job.turnaroundTime;
            }
        }

        results.averageTurnaroundTime = ((double)summedTurnaroundTime)/((double)jobs.size());
        results.maxTurnaroundTime = maxTurnaroundTime;
        results.minTurnaroundTime = minTurnaroundTime;
        results.overallTurnaroundTime = currentTick;

        //Calculate Standard Deviation.
        double variance = 0;
        for(Job job : jobs) {
            variance += Math.pow(results.averageTurnaroundTime - job.turnaroundTime, 2);
        }
        variance = variance/(double) jobs.size();

        results.stdDevTurnaroundTime = Math.sqrt(variance);

        if(printTable) {
            System.out.println(simulationPrefix() + " JobID:TurnaroundTime");
            for ( Job job : jobs ) {
                System.out.println(simulationPrefix() + " " + job.id + ":" + job.turnaroundTime);
            }
            System.out.println();
        }

        if(printResults) {
            System.out.println(simulationPrefix() + " Average Turnaround Time: " + results.averageTurnaroundTime);
            System.out.println(simulationPrefix() + " Min Turnaround Time: " + results.minTurnaroundTime);
            System.out.println(simulationPrefix() + " Max Turnaround Time: " + results.maxTurnaroundTime);
            System.out.println(simulationPrefix() + " Overall Turnaround Time: " + results.overallTurnaroundTime);
            System.out.println(simulationPrefix() + " StdDev Turnaround Time: " + results.stdDevTurnaroundTime + "\n");
        }



        return results;
    }

}
