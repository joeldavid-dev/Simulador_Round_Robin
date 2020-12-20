package planificadorroundrobin;

public class RoundRobin {

    //atributos
    private final Datos datos;
    private final Cpu cpu;
    private LlegadaProcesos llegadaP;
    private final Cola colaProcesosListos;
    private Proceso procesoDeLlegada;

    //constructores
    public RoundRobin(Datos datos) {
        this.datos = datos;
        colaProcesosListos = new Cola("Cola de procesos listos"); //Instancia e inicializa la cola de procesos listos

        this.llegadaP = new LlegadaProcesos(datos.colaProcesos, colaProcesosListos);
        this.cpu = new Cpu(datos.quantum, colaProcesosListos);

        if (datos.numProcesos != 0) {
            imp("Iniciando...");
            //Inicia llegadaP
            //Inicia CPU

            datos.colaProcesos.imprimirColaCompleta();
        } else {
            imp("No hay procesos que hacer.");
        }
    }

    //Metodos
    private void imp(String mensaje) {
        System.out.println("SIMULADOR: " + mensaje);
    }
}
