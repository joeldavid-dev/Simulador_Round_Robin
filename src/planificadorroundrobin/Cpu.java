package planificadorroundrobin;

import static java.lang.Thread.sleep;

/*
 * Esta clase se encarga de ejecutar los procesos que le llegan en la cola
 * de procesos listos, los encola de nuevo si necesesita mas tiempo del quantum.
 */
public class Cpu extends Thread {

    //Atributos
    private final int quantum;
    private final AdminProcesosListos procesosListos;
    private Proceso procesoTemp;
    private int tiempoTranscurrido;
    private boolean primeraIteracion;
    private Cola colaProcesosTerminados;

    //Constructores
    public Cpu(int quantum, AdminProcesosListos procesosListos, Cola colaProcesosTerminados) {
        this.setName("CPU");
        this.quantum = quantum;
        this.procesosListos = procesosListos;
        this.tiempoTranscurrido = 0;
        this.primeraIteracion = true;
        this.colaProcesosTerminados = colaProcesosTerminados;
    }

    //Metodos
    @Override
    public void run() {
        while (true) {// Ciclo infinito, se corta hasta que la simulacion finaliza
            procesoTemp = procesosListos.desencolarProcesoListo();

            if (procesosListos.subieronTodos && procesoTemp == null) {
                //Acciones cuando se han subido todos los procesos y ya no quedan mas en la lista de procesos listos
                imp("Terminaron de ejecutarse TODOS los procesos");
                break;
            } else {
                //Acciones cuando aun faltan procesos a ejecutar   
                if (procesoTemp != null) {

                    if (primeraIteracion) {
                        //Acciones cuando se itera por primera vez
                        tiempoTranscurrido = procesoTemp.tiempoLlegada;
                        primeraIteracion = false;
                    }

                    if (procesoTemp.primeraIteracion) {
                        //Acciones cuando un proceso entra a CPU por primera vez
                        procesoTemp.tiempoEntrada = tiempoTranscurrido;
                        procesoTemp.primeraIteracion = false;
                        procesosListos.memoriaRAM = procesosListos.memoriaRAM - procesoTemp.tam;
                        imp("Se cargó el proceso " + procesoTemp.nombre + " en memoria RAM. Memoria RAM disponible " + procesosListos.memoriaRAM + "[k]");
                    }

                    imp("Proceso " + procesoTemp.nombre + " subio a CPU en el tiempo " + tiempoTranscurrido + " [ms], tiempo faltante de ejecucion "
                            + procesoTemp.tiempoFaltante + " [ms]");

                    if (procesoTemp.tiempoFaltante > quantum) {
                        //Caso en el que el proceso necesita mas tiempo en CPU que el quantum, por lo que tiene que repetir
                        dormir(quantum);

                        procesoTemp.tiempoFaltante -= quantum;// Se le resta el tiempo que ya se ejecutó
                        imp("Proceso " + procesoTemp.nombre + " entra de nuevo a la cola de procesos listos en el tiempo " + tiempoTranscurrido + " [ms]");
                        procesosListos.encolarProcesoListo(procesoTemp);

                    } else {
                        //Caso en el que el proceso necesita menos tiempo que el quantum, ya no necesita regresar
                        dormir(procesoTemp.tiempoFaltante);

                        procesoTemp.tiempoFaltante = 0;
                        procesoTemp.tiempoTotal = tiempoTranscurrido;
                        colaProcesosTerminados.insertar(procesoTemp);

                        imp("Proceso " + procesoTemp.nombre + " termino su ejecucion en el tiempo " + tiempoTranscurrido + " [ms]");
                        procesosListos.memoriaRAM = procesosListos.memoriaRAM + procesoTemp.tam;
                        imp("Se liberó memoria RAM. Memoria RAM disponible " + procesosListos.memoriaRAM + "[k]");

                        if (procesosListos.hayProcesoEsperandoRAM) {
                            procesosListos.ramLista = true;
                            procesosListos.esperarRAM();
                        }
                    }
                }
            }
        }
    }

    private void dormir(int tiempo) {
        try {
            sleep(tiempo);
            tiempoTranscurrido += tiempo;
        } catch (InterruptedException ex) {
        }
    }

    private void imp(String mensaje) {
        System.out.println("\nCPU: " + mensaje);
    }
}
