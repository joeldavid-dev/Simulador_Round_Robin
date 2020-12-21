package planificadorroundrobin;

import java.util.Random;
import java.util.Scanner;

//Clase que recopila todos los datos necesarios y genera la cola de procesos
public class Datos {

    //ATRIBUTOS
    int numProcesos; //Total de procesos
    int maxTamProceso; //Tamaño máximo de memoria que puede tener un proceso: Entero en kBytes
    int memoriaRAM; //Tamaño de la memoria RAM: Entero en kBytes
    int quantum;
    Cola colaProcesos; //Objeto tipo Cola
    Proceso nodo;
    Scanner sc = new Scanner(System.in);
    Random numRandom = new Random();

    //CONSTRUCTOR
    /*
        En este constructor se define el número de procesos a crear (numProcesos),
        el tamaño de memoria máximo para un proceso (maxTamProceso) y tamaño de memoria RAM (memoria).
        Se crea un objeto Cola (cola) y se inicializa creando un objeto Proceso (nodo).
        La cola está vacía hasta que se ejecuta la instrucción procesos.hacerProcesos().
        Al terminar, la cola ya tiene a los procesos listos.
     */
    public Datos() {
        maxTamProceso = 50; //Se define un tamaño de memoria máximo por default para un proceso
        memoriaRAM = 1024; //Se define un tamaño de memoria RAM por default
        quantum = 4000; //Se define un tiempo de quantum por default
        imp("Valor por default del tamanio maximo de memoria que tendra un proceso: " + maxTamProceso + "[k]");
        imp("Valor por default del tamanio de memoria RAM: " + memoriaRAM + "[k]");
        imp("Valor por default del quantum: " + quantum + "[mseg]");
        imp("Redefinir valores s/n: ");
        String respuesta = sc.next();
        if (respuesta.contains("s")) {
            imp("Ingresar el nuevo valor para tamanio maximo de memoria que tendra un proceso: ");
            maxTamProceso = sc.nextInt();
            imp("Ingresar el nuevo valor para tamanio de memoria RAM: ");
            memoriaRAM = sc.nextInt();
            imp("Ingresar el nuevo valor para quantum: ");
            quantum = sc.nextInt();

            imp("Valor nuevo del tamanio maximo de memoria que tendra un proceso: " + maxTamProceso + "[k]");
            imp("Valor nuevo del tamanio de memoria RAM: " + memoriaRAM + "[k]");
            imp("Valor nuevo del quantum: " + quantum + "[mseg]\n");
        }

        //Aqui creará la cola de procesos ingresados
        imp("Ingresar la cantidad de procesos: ");
        numProcesos = sc.nextInt();

        colaProcesos = new Cola("Cola de procesos cargados"); //Instancia e inicializa la colaProcesos
        int horaLlegada = 0;
        int tiempoEjecucion = 0;
        int prioridad = 0;

        Proceso procesosDesordenados[] = new Proceso[numProcesos]; //Array de procesos que se ordenaran

        imp("Por favor, ingresar los procesos en orden de entrada.");
        int indiceNormal = 0;
        for (int i = 0; i < numProcesos; i++) { //ciclo que solicita datos y guarda los procesos en procesosDesordenados
            indiceNormal = i + 1;

            imp("Ingresa el tiempo de llegada del proceso " + indiceNormal + " en [ms]: ");
            horaLlegada = sc.nextInt();
            imp("Ingresa el tiempo de servicio del proceso " + indiceNormal + " en [ms]: ");
            tiempoEjecucion = sc.nextInt();
            imp("Ingresa la prioridad del proceso " + indiceNormal + ": ");
            prioridad = sc.nextInt();

            procesosDesordenados[i] = new Proceso(indiceNormal, "P" + indiceNormal, (int) (Math.random() * maxTamProceso) + 1, horaLlegada, tiempoEjecucion, prioridad);
        }

        for (int i = 0; i < numProcesos; i++) { //ciclo que llena la colaProcesos con los procesos ordenados
            colaProcesos.insertar(procesosDesordenados[i]);
        }

        imp("Procesos que se cargaron en el simulador:");
        colaProcesos.imprimirColaCompleta();
    }

    //METODOS    
    private void imp(String mensaje) {
        System.out.println(mensaje);
    }
}
