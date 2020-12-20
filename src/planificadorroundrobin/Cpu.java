package planificadorroundrobin;

import static java.lang.Thread.sleep;

public class Cpu {

    //Atributos
    private final int quantum;
    private final Cola colaProcesosListos;

    //Constructores
    public Cpu(int quantum, Cola procesosListos) {
        this.quantum = quantum;
        this.colaProcesosListos = procesosListos;
    }

    //Metodos
    public Proceso ejecutar(Proceso proceso) {

        if (proceso.tiempoFaltante >= quantum) {
            System.out.println("Proceso " + proceso.nombre + " entró en ejecucion, quantum: " + quantum);
            try {
                sleep(quantum);
            } catch (InterruptedException ex) {
            }

            proceso.tiempoFaltante = proceso.tiempoFaltante - quantum;

        } else {
            System.out.println("Proceso "+proceso.nombre+" entró en ejecucion, quantum: "+proceso.tiempoFaltante);
            try {
                sleep(proceso.tiempoFaltante);
            } catch (InterruptedException ex) {
            }

            proceso.tiempoFaltante = 0;

        }
        System.out.println("Fin del quantum");
        return proceso;
    }
}
