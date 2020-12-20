package planificadorroundrobin;

public class Cola {

    //ATRIBUTOS
    int memoriaRAM;     //Indicará el tamaño de la memoria RAM
    private int cantidad;       //Cantidad de nodos en la cola
    static int idSegundo = 0;
    private Proceso nodoTemporal;      //Comodín para métodos utilizados en esta clase
    private Proceso nodoInicial;
    private Proceso nodoImpresion;
    private String miNombre;

    //CONSTRUCTORES
    //Cola de procesos listos (Planificador a mediano plazo)
    public Cola(String nombre) {
        this.miNombre = nombre;
        this.memoriaRAM = 0;
        this.nodoTemporal = new Proceso();      //Se inicializa con nodo siguiente, head y tail en null (constructor en clase Proceso)
        this.nodoInicial = this.nodoTemporal;
        this.nodoImpresion = new Proceso();
        this.cantidad = 0;
    }

    //MÉTODOS
    /*
        Método para insertar un nodo nuevo a la cola.
     */
    public void insertar(Proceso nodo) {

        if (cantidad == 0) {
            //Cola vacía
            nodoInicial = nodo;
        } else {
            nodoTemporal.setSiguiente(nodo);
        }
        nodoTemporal = nodo;
        cantidad++;
    }

    //Retorna el primer valor de la cola y lo quita
    public Proceso desencolar() {

        if (cantidad == 0) {
            return null;
        } else {

            Proceso headAntiguo = nodoInicial;
            try {
                nodoInicial = nodoInicial.getSiguiente();
            } catch (NullPointerException e) {
            }

            cantidad--;
            return headAntiguo;
        }
    }

    //Método que imprime una cola con todos los datos
    public void imprimirColaCompleta() {
        try {
            nodoImpresion = nodoInicial;

            imp("\t#### Datos en la cola '" + miNombre + "' ####");
            imp("\t===========================================Cola===========================================");
            imp("\t| Nombre | ID | Prioridad | Tamanio (k) | Tiempo ejecucion (mseg) | Tiempo llegada (mseg)|");

            for (int i = 0; i < cantidad; i++) {
                if (i == 0) {                                          //Si el nodo es head de la cola
                    imp("\t|   " + nodoImpresion.nombre + "   | " + nodoImpresion.id + "  |     " + nodoImpresion.prioridad
                            + "     |      " + nodoImpresion.tam + "     |          " + nodoImpresion.tiempoServicio + "           |         "
                            + nodoImpresion.tiempoLlegada + "         |\t<--- Head");
                } else if (i == cantidad - 1) {                                                //Si el nodo es tail de la cola
                    imp("\t|   " + nodoImpresion.nombre + "   | " + nodoImpresion.id + "  |     " + nodoImpresion.prioridad
                            + "     |      " + nodoImpresion.tam + "     |          " + nodoImpresion.tiempoServicio + "           |         "
                            + nodoImpresion.tiempoLlegada + "         |\t<--- Tail");
                    break; //Si no corta la ejecucion, el nodo temporal sera null y causará errores
                } else {
                    imp("\t|   " + nodoImpresion.nombre + "   | " + nodoImpresion.id + "  |     " + nodoImpresion.prioridad
                            + "     |      " + nodoImpresion.tam + "     |          " + nodoImpresion.tiempoServicio + "           |         "
                            + nodoImpresion.tiempoLlegada + "         |");              //Nodos entre head y tail
                }

                nodoImpresion = nodoImpresion.getSiguiente();
            }

            imp("\t=========================================Fin Cola=========================================");

        } catch (NullPointerException e) {
        }
    }

    //Método que imprime los datos finales y promedios
    public void imprimirDatosFinales() {
        try {
            nodoImpresion = nodoInicial;

            int resp, esp, ejec;
            int respProm = 0;
            int espProm = 0;
            int ejecProm = 0;

            imp("\t#### Datos finales de los procesos ####");
            imp("\t===============================================================================");
            imp("\t| Nombre | T. Llegada | T. Servicio | T. Ejecucion | T. Espera | T. Respuesta |");

            for (int i = 0; i < cantidad; i++) {

                ejec = nodoImpresion.tiempoTotal - nodoImpresion.tiempoLlegada;
                esp = ejec - nodoImpresion.tiempoServicio;
                resp = nodoImpresion.tiempoEntrada - nodoImpresion.tiempoLlegada;

                imp("\t|   " + nodoImpresion.nombre + "   |    " + nodoImpresion.tiempoLlegada + "    |    " + nodoImpresion.tiempoServicio
                        + "     |     " + ejec + "     |     " + esp + "     |      " + resp + "       |\t");

                ejecProm += ejec;
                espProm += esp;
                respProm += resp;

                nodoImpresion = nodoImpresion.getSiguiente();
            }

            imp("\t===============================================================================");

            imp("\nEjecucion promedio: " + ejecProm / cantidad + "\nEspera promedio: "
                    + espProm / cantidad + "\nRespuesta promedio: " + respProm / cantidad);

        } catch (NullPointerException e) {
        }
    }

    public int getCantidad() {
        return this.cantidad;
    }

    private void imp(String mensaje) {
        System.out.println(mensaje);
    }
}
