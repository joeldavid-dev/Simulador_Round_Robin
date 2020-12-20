package planificadorroundrobin;

public class LlegadaProcesos extends Thread{
    //Atributos
    private final Cola colaProcesosCargados;
    private final Cola colaProcesosListos;
    
    //Constructores
    public LlegadaProcesos(Cola procesosCargados, Cola procesosListos){
        this.colaProcesosCargados = procesosCargados;
        this.colaProcesosListos = procesosListos;
        
    }
    
    //Metodos
}
