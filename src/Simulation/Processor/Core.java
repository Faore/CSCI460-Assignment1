package Simulation.Processor;

import Simulation.Job;

public class Core {
    public Job currentJob;
    public Job preparingJob;
    public Processor processor;

    public final int id;

    public Core(int id, Processor processor) {
        this.id = id;
        this.processor = processor;
    }

    public boolean runJob(Job job) {
        if(currentJob == null) {
            currentJob = job;
            return true;
        } else {
            return false;
        }
    }

    public void tick() {
        if(currentJob != null) {
            currentJob.remainingTime--;
        } else {
            if(preparingJob != null) {
                this.currentJob = preparingJob;
                this.preparingJob = null;
                this.currentJob.startTime = this.processor.simulation.currentTick;
            }
        }
    }

    public Job removeJobIfFinished() {
        if(currentJob == null) {
            return null;
        }
        if(currentJob.remainingTime == 0) {
            Job job = currentJob;
            this.currentJob.finishTime = this.processor.simulation.currentTick;
            this.currentJob = null;
            return job;
        }
        return null;
    }

    public void prepareJob(Job job) {
        if(this.currentJob == null) {
            this.preparingJob = job;
        }
        else {
            System.out.println("Tried to prepare a job while another job was running on core " + this.id + ".");
        }
    }
}
