package Scheduling;

import Simulation.Job;
import Simulation.Processor.Core;
import Simulation.Processor.Processor;
import Simulation.Scheduler;

public interface SchedulingMethod {

    public void setupScheduler(Processor processor, Scheduler scheduler);
    public void coreFreed(Core core, Job job);
    public void prepareJobs();
    public void prescheduleJob(Job job);
}
