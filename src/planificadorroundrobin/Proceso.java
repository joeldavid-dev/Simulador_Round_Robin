package planificadorroundrobin;

public class Proceso {

    //ATRIBUTOS
    int id; //Id del proceso (numérico)
    String nombre; //Nombre del proceso (alfanumérico)
    int tam; //Tamaño del proceso
    int tiempoServicio; //Tiempo que requiere el proceso para su ejecución
    int prioridad; //Prioridad del proceso
    int tiempoLlegada; //Tiempo de llegada del proceso
    private Proceso siguiente; //Apuntador hacia el siguiente Proceso

    //Variables que ocupa cpu
    int tiempoFaltante;
    int tiempoTotal;
    boolean primeraIteracion;
    int tiempoEntrada;

    //CONSTRUCTORES
    Proceso() {
        //NODOS INVOLUCRADOS
        siguiente = null;
    }

    Proceso(int id, String nombre, int tam, int tiempoLlegada, int tiempoServicio, int prioridad) {
        //NODOS INVOLUCRADOS
        siguiente = null;
        //Inicializacion de atributos
        this.id = id;
        this.nombre = nombre;
        this.tam = tam;
        this.tiempoServicio = tiempoServicio;
        this.prioridad = prioridad;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoFaltante = tiempoServicio;
        this.tiempoTotal = tiempoLlegada;
        this.primeraIteracion = true;
        this.tiempoEntrada = 0;
    }

    //MÉTODOS
    public void setSiguiente(Proceso siguiente) {
        this.siguiente = siguiente;
    }

    public Proceso getSiguiente() {
        return this.siguiente;
    }
}
