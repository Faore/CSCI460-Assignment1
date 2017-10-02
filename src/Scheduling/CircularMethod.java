package Scheduling;

import Simulation.Job;
import Simulation.Processor.Core;
import Simulation.Processor.Processor;
import Simulation.Scheduler;

import java.util.ArrayList;

public class CircularMethod implements SchedulingMethod {

    Processor processor;
    Scheduler scheduler;
    boolean[] freeCores;
    ArrayList<Integer> prescheduledJobCores;

    @Override
    public void setupScheduler(Processor processor, Scheduler scheduler) {
        this.processor = processor;
        this.scheduler = scheduler;
        this.freeCores = new boolean[processor.cores.length];
        for(int i = 0; i < freeCores.length; i++) {
            freeCores[i] = true;
        }
        prescheduledJobCores = new ArrayList<>();
    }

    @Override
    public void coreFreed(Core core, Job job) {
        freeCores[core.id] = true;
        //System.out.println("Job " + job.id + " finished on core " + core.id + ".");
    }

    @Override
    public void prepareJobs() {
        //Find each core with no jobs and see if one can be found on the queue for it.
        for(int i = 0; i < freeCores.length; i++) {
            if(freeCores[i]) {
                //This core is free, check the queue for a job.
                for(int j = 0; j < this.scheduler.jobQueue.size(); j++) {
                    //If this job is preschecduled for this core, queue it up and remove it from the queue.
                    if(this.prescheduledJobCores.get(this.scheduler.jobQueue.get(j).id) == i) {
                        this.processor.cores[i].prepareJob(this.scheduler.jobQueue.remove(j));
                        //System.out.println("Job " + this.processor.cores[i].preparingJob.id + " is being scheduled on core " + i + ".");
                        freeCores[i] = false;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void prescheduleJob(Job job) {
        //This is the first job, its special.
        if(job.id == 0) {
            prescheduledJobCores.add(job.id, 0);
        } else {
            prescheduledJobCores.add(job.id, (prescheduledJobCores.get(job.id - 1) + 1)%processor.cores.length);
        }
    }
}
