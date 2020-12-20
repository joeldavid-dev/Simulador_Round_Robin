package planificadorroundrobin;

public class Proceso {
    //ATRIBUTOS
    int id;         //Id del proceso (numérico)
    String nombre;  //Nombre del proceso (alfanumérico)
    int tam;        //Tamaño del proceso
    int tiempoE;    //Tiempo que requiere el proceso para su ejecución
    int prioridad;  //Prioridad del proceso
    int tiempoL;    //Tiempo de llegada del proceso
    Proceso siguiente; //Apuntador hacia el siguiente Proceso
    Proceso head;      //Cabeza de la cola
    Proceso tail;      //Cola de la cola
    int tiempoFaltante; //tiempo que le falta para que termine su ejecución
    
    //CONSTRUCTORES
    Proceso(){
        //NODOS INVOLUCRADOS
        siguiente = null;
        head = null;
        tail = null;
    }
    
    Proceso(int id, String nombre, int tam, int tiempoE, int prioridad, int tiempoL){
        //NODOS INVOLUCRADOS
        siguiente = null;
        head = null;
        tail = null;
        //Inicializacion de atributos
        this.id = id;
        this.nombre = nombre;
        this.tam = tam;
        this.tiempoE = tiempoE;
        this.prioridad = prioridad;
        this.tiempoL = tiempoL;
        this.tiempoFaltante = tiempoE;
    }
    
    //MÉTODOS
    public void asignacion(Proceso siguiente, Proceso head, Proceso tail){
        this.siguiente = siguiente;
        this.head = head;
        this.tail = tail;
    }
    
}
