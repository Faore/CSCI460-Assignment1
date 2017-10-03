package Scheduling;

import Simulation.Job;
import Simulation.Processor.Core;
import Simulation.Processor.Processor;
import Simulation.Scheduler;

public class FCFSFirstAvailableCore implements SchedulingMethod {

    Processor processor;
    Scheduler scheduler;
    boolean[] freeCores;

    @Override
    public void setupScheduler(Processor processor, Scheduler scheduler) {
        this.processor = processor;
        this.scheduler = scheduler;
        this.freeCores = new boolean[processor.cores.length];
        for(int i = 0; i < freeCores.length; i++) {
            freeCores[i] = true;
        }
    }

    @Override
    public void coreFreed(Core core, Job job) {
        freeCores[core.id] = true;
    }

    @Override
    public void prepareJobs() {
        //Find each core with no jobs and see if one can be found on the queue for it.
        for(int i = 0; i < freeCores.length; i++) {
            if(freeCores[i]) {
                if(!this.scheduler.jobQueue.isEmpty()) {
                    this.processor.cores[i].prepareJob(this.scheduler.jobQueue.remove(0));
                    freeCores[i] = false;
                }
            }
        }
    }

    @Override
    public void prescheduleJob(Job job) {
        //This algorithm doesn't need to preschedule a job as it arrives.
    }
}
