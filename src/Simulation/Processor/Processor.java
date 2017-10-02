package Simulation.Processor;

import Simulation.Simulation;

import java.util.ArrayList;

public class Processor {

    public Core[] cores;
    public Simulation simulation;

    public Processor(int cores, Simulation simulation) {
        this.cores = new Core[cores];
        this.simulation = simulation;
        for(int i = 0; i < cores; i++) {
            this.cores[i] = new Core(i, this);
        }
    }

    public void tick() {
        for (Core core : this.cores ) {
            core.tick();
        }
    }

    public boolean idle() {
        for ( Core core: this.cores ) {
            if(core.currentJob != null) {
                return false;
            }
        }
        return true;
    }
}
