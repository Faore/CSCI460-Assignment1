package Simulation;

public class Job {

    public final int id, arrivalTime, processingTime;

    public int remainingTime, startTime, finishTime, turnaroundTime;

    public Job(int id, int arrivalTime, int processingTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.remainingTime = processingTime;
    }

    public void calculateTurnaroundTime() {
        this.turnaroundTime = finishTime - arrivalTime;
    }
}
