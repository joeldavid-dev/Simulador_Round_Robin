package planificadorroundrobin;

import static java.lang.Thread.sleep;

public class Cola {

    //ATRIBUTOS
    int memoriaRAM;     //Indicará el tamaño de la memoria RAM
    int cantidad;       //Cantidad de nodos en la cola
    static int idSegundo = 0;
    Proceso nodoTemporal;      //Comodín para métodos utilizados en esta clase
    Proceso nodoInicial;
    private String miNombre;

    //CONSTRUCTORES
    //Cola de procesos listos (Planificador a mediano plazo)
    public Cola(String nombre) {
        this.miNombre = nombre;
        this.memoriaRAM = 0;
        this.nodoTemporal = new Proceso();      //Se inicializa con nodo siguiente, head y tail en null (constructor en clase Proceso)
        this.nodoInicial = this.nodoTemporal;
        cantidad = 0;
    }

    //Cola de procesos listos para ejecución (Planificador a corto plazo)
    public Cola(int memoriaRAM) {
        this.memoriaRAM = memoriaRAM;
        this.nodoTemporal = new Proceso();      //Se inicializa con nodo siguiente, head y tail en null (constructor en clase Proceso)
        this.nodoInicial = this.nodoTemporal;
        cantidad = 0;           //Cantidad de nodos en la cola
    }

    //MÉTODOS
    /*
        Método para insertar un nodo nuevo a la cola.
        Para utilizar el método insertar, primero se tuvo que ejecutar la función inicializar
     */
    //public Proceso insertar(Proceso nodo, int id, String nombre, int tam, int tiempoE, int prioridad, int tiempoL){
    public void insertar(Proceso nodo) {
        Proceso nodoNuevo = nodo;
        if (nodoTemporal.siguiente == null & nodoTemporal.head == null & nodoTemporal.tail == null) {             //Cola vacía
            nodoNuevo.asignacion(null, nodoNuevo, nodoNuevo);                                               //siguiente, head, tail
            nodoInicial = nodo;                                                                             //nodo inicial es igual al primer proceso que entra
        } else if (nodoTemporal.head != null & nodoTemporal.tail != null) {                                  //Cola no vacía
            nodoTemporal.siguiente = nodoNuevo;
            nodoNuevo.siguiente = null;
            nodoNuevo.head = nodoTemporal.head;
            nodoTemporal.tail = nodoNuevo;
            nodoNuevo.tail = nodoNuevo;
        }
        cantidad++;
        nodoTemporal = nodoNuevo;
    }

    public Proceso desencolar() {
        nodoTemporal = nodoInicial;

        Proceso headAntiguo = nodoTemporal;

        try {
            nodoTemporal = nodoTemporal.siguiente;
            nodoTemporal.head = nodoTemporal;
            nodoInicial = nodoTemporal;
        } catch (NullPointerException e) {

        }
        cantidad--;
        return headAntiguo;
    }

    public void borrar(Proceso nodo) {
        nodo = nodo.head;
        if (nodo.siguiente == null) {
            nodo.head = null;
            nodo.tail = null;
        } else {
            nodo.head = null;
            nodo.tail = null;
            Proceso nodoR = nodo;
            nodo.siguiente = null;
            nodo = null;

            nodo = nodoR.siguiente;                              //Nuevo nodo Head
            nodoR = null;

            nodo.head = nodo;
            while (nodo.siguiente != null) {
                nodo.siguiente.head = nodo.head;
                nodo = nodo.siguiente;
            }
        }
        cantidad--;
    }

    //Método que imprime una cola con los datos nombre y tiempo de llegada.
    public void imprimirColaNombreTL(Proceso nodo) {
        int i = cantidad - 1;

        if (memoriaRAM > 0) {
            System.out.println("\t****Procesos listos para ejecucion (procesos en memoria)****");
        } else {
            System.out.println("\t****Procesos listos (procesos en espera para subir a la memoria)****");
        }

        try {
            nodo = nodo.head;
        } catch (NullPointerException e) {
        }

        System.out.println("\t==============Cola==============");
        while (i > -1) {
            if (i == cantidad - 1) {                                          //Si el nodo es head de la cola
                if (cantidad == 1) {
                    System.out.println("\t| " + nodo.nombre + " | " + nodo.tiempoL + " | " + nodo.tiempoE + " |\t<--- Head / Tail");
                } else {
                    System.out.println("\t| " + nodo.nombre + " | " + nodo.tiempoL + " | " + nodo.tiempoE + " |\t<--- Head");
                }
            } else if (i == 0) {                                                //Si el nodo es tail de la cola
                System.out.println("\t| " + nodo.nombre + " | " + nodo.tiempoL + " | " + nodo.tiempoE + " |\t<--- Tail");
            } else {
                System.out.println("\t| " + nodo.nombre + " | " + nodo.tiempoL + " | " + nodo.tiempoE + "|");              //Nodos entre head y tail
            }
            try {
                if (i == 0) {
                    break;
                }
                nodo = nodo.siguiente;
            } catch (NullPointerException e) {
                break;
            }
            i--;
        }
        System.out.println("\t============Fin Cola============\n");
    }

    //Método que imprime una cola con todos los datos, se le pasa como argumento uno de los nodos de la cola que se vaya a imprimir
    public void imprimirColaCompleta() {
        try {
            nodoTemporal = nodoTemporal.head;

            imp("\t#### Datos en la cola '" + miNombre + "' ####");

            imp("\t===========================================Cola===========================================");
            imp("\t| nombre | id | prioridad | tamanio (k) | tiempo ejecucion (mseg) | tiempo llegada (mseg)|");

            for (int i = 0; i < cantidad; i++) {
                if (i == 0) {                                          //Si el nodo es head de la cola
                    imp("\t|   " + nodoTemporal.nombre + "   | " + nodoTemporal.id + "  |     " + nodoTemporal.prioridad
                            + "     |     " + nodoTemporal.tam + "     |          " + nodoTemporal.tiempoE + "           |         "
                            + nodoTemporal.tiempoL + "         |\t<--- Head");
                } else if (i == cantidad - 1) {                                                //Si el nodo es tail de la cola
                    imp("\t|   " + nodoTemporal.nombre + "   | " + nodoTemporal.id + "  |     " + nodoTemporal.prioridad
                            + "     |     " + nodoTemporal.tam + "     |          " + nodoTemporal.tiempoE + "           |         "
                            + nodoTemporal.tiempoL + "         |\t<--- Tail");
                    break; //Si no corta la ejecucion, el nodo temporal sera null y causará errores
                } else {
                    imp("\t|   " + nodoTemporal.nombre + "   | " + nodoTemporal.id + "  n|     " + nodoTemporal.prioridad
                            + "     |     " + nodoTemporal.tam + "     |          " + nodoTemporal.tiempoE + "           |         "
                            + nodoTemporal.tiempoL + "         |");              //Nodos entre head y tail
                }

                nodoTemporal = nodoTemporal.siguiente;
            }

            imp("\t=========================================Fin Cola=========================================");

        } catch (NullPointerException e) {
        }
    }

    //Método para simular que llega un proceso en cierto tiempo
    public int dormirV1(int tiempoL, int tiempo) {
        int segundo = 1000;
        int tFinalizador = 0;

        System.out.println(tFinalizador + " [mseg]");

        while (tFinalizador != tiempo) {
            try {
                sleep(segundo);
                tFinalizador = tFinalizador + segundo;
                idSegundo = tFinalizador;
                System.out.println(tFinalizador + " [mseg]");
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return tFinalizador;
    }

    //Método para simular que trabaja un proceso, teniendo en cuenta el tiempo anterior de ejecución de otro proceso
    public int dormirV2(int tiempoAnterior, int tiempoL) {
        int segundo = 1000;
        int tContadorGeneral = tiempoAnterior;

        while (tContadorGeneral != tiempoL) {     //Se ejecuta hasta que llegue un nuevo proceso
            try {
                sleep(segundo);
                tContadorGeneral = tContadorGeneral + segundo;
                idSegundo = tContadorGeneral;
                System.out.println(tContadorGeneral + " [mseg]");
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return tContadorGeneral;
    }

    private void imp(String mensaje) {
        System.out.println(mensaje);
    }
}
