package Simulation;

import Scheduling.SchedulingMethod;
import Simulation.Processor.Core;
import Simulation.Processor.Processor;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler {

    public Processor processor;
    public SchedulingMethod schedulingMethod;
    public ArrayList<Job> jobQueue;

    public Scheduler(Processor processor, SchedulingMethod schedulingMethod) {
        this.processor = processor;
        this.schedulingMethod = schedulingMethod;
        this.jobQueue = new ArrayList<Job>();
        this.schedulingMethod.setupScheduler(processor, this);
    }

    public void coreFreed(Core core, Job job) {
        schedulingMethod.coreFreed(core, job);
    }

    public void addJobToQueue(Job job) {
        this.jobQueue.add(job);
        this.schedulingMethod.prescheduleJob(job);
    }
}
