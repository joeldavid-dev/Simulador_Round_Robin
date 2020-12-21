package planificadorroundrobin;

/*
 * Esta clase se encarga de agregar los procesos a la cola de procesos listos
 * conforme a su tiempo de llegada.
 */
public class AdminProcesosListos extends Thread {

    //Atributos
    private final Cola colaProcesosCargados;
    private final Cola colaProcesosListos;
    private Proceso procesoTemp;
    private Proceso procesoTempCerrojo;
    private int tiempoTranscurrido;
    private boolean disponible;
    boolean subieronTodos;
    private int memoriaRAM;

    //Constructores
    public AdminProcesosListos(Cola procesosCargados, Cola procesosListos, int RAM) {
        this.setName("HiloAdminProcesosListos");
        this.colaProcesosCargados = procesosCargados;
        this.colaProcesosListos = procesosListos;
        this.tiempoTranscurrido = 0;
        this.disponible = true;
        this.subieronTodos = false;
        this.memoriaRAM = RAM;
    }

    //Metodos
    @Override
    public void run() {
        int cantidadInicial = colaProcesosCargados.getCantidad(); //cantidad fija
        for (int i = 0; i < cantidadInicial; i++) {
            procesoTemp = colaProcesosCargados.desencolar();

            dormir(procesoTemp.tiempoLlegada - tiempoTranscurrido); // duerme lo necesario para que el proceso se insete en su tiempo de llegada           
            tiempoTranscurrido = tiempoTranscurrido + (procesoTemp.tiempoLlegada - tiempoTranscurrido); // Actualiza el tiempo que ha transcurrido en total

            imp("Llega el proceso " + procesoTemp.nombre + " en el tiempo " + procesoTemp.tiempoLlegada + " [ms]");

            encolarProcesoListo(procesoTemp);
        }
        subieronTodos = true;
    }

    private void dormir(int tiempo) {
        try {
            sleep(tiempo);
        } catch (InterruptedException ex) {
            imp("Error al dormir");
        }
    }

    public synchronized void encolarProcesoListo(Proceso procesoListo) {
        while (disponible == false) {
            //Se mantiene en este while cuando otro hilo está ocupando este metodo.
            try {
                wait(); //se pone a dormir y cede el monitor
            } catch (InterruptedException e) {
            }
        }
        //Entra aqui cuando otro hilo ha dejado de ocupar este metodo
        disponible = false;//Cierra el cerrojo para que otro hilo no ocupe el metodo
        colaProcesosListos.insertar(procesoListo);
        colaProcesosListos.imprimirColaCompleta();
        disponible = true;//Abre el cerrojo cuando termina
        notifyAll();
    }

    public synchronized Proceso desencolarProcesoListo() {
        while (disponible == false) {
            //Se mantiene en este while cuando otro hilo está ocupando este metodo.
            try {
                wait(); //se pone a dormir y cede el monitor
            } catch (InterruptedException e) {
            }
        }
        //Entra aqui cuando otro hilo ha dejado de ocupar este metodo
        disponible = false;//Cierra el cerrojo para que otro hilo no ocupe el metodo
        procesoTempCerrojo = colaProcesosListos.desencolar();
        disponible = true;//Abre el cerrojo cuando termina
        notifyAll();
        return procesoTempCerrojo;
    }

    private void imp(String mensaje) {
        System.out.println("\nPROCESOS LISTOS: " + mensaje);
    }
}
