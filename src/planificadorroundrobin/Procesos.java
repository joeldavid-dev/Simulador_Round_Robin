package planificadorroundrobin;

import java.util.Random;
import java.util.Scanner;

public class Procesos {

    //ATRIBUTOS
    int id;                             //Id del proceso (numérico)
    String nombre;                      //Nombre del proceso (alfanumérico)
    int tam;                            //Tamaño del proceso
    int tiempoE;                        //Tiempo que requiere el proceso para su ejecución
    int prioridad;                      //Prioridad del proceso
    int tiempoL;                        //Tiempo de llegada del proceso
    int numProcesos;                    //Total de procesos
    int maxTamProceso;                  //Tamaño máximo de memoria que puede tener un proceso: Entero en kBytes
    int tiempo1;                        //Tiempos de llegada
    int memoriaRAM;                     //Tamaño de la memoria RAM
    int quantumDefault;
    int quantum;
    int contadorQuantum;
    int tiempoRecorrido;
    Cola colaDes;                       //Cola desordenada, se utiliza para hacer los procesos
    Cola colaOrd;                       //Cola ordenada, a partir de la cola desordenada se realiza un ordenamiento y se crea la cola ordenada por tiempoL
    Cola colaMemoriaRAM;                 //Cola para procesos en memoria RAM
    Proceso nodo;                          //Nodo para colaDes
    Proceso nodoAux;                       //Nodo para colaOrd
    Proceso nodoMemoriaRAM;                   //Nodo para colaMemoriaRAM
    Proceso nodoEnEjecucion;               //Nodo en ejecución
    Scanner sc = new Scanner(System.in);
    Random numRandom = new Random();
    int arreglo[][];

    //CONSTRUTOR
    Procesos(int numProcesos, int maxTamProceso, int memoriaRAM, int quantumDefault) {
        this.numProcesos = numProcesos;
        this.maxTamProceso = maxTamProceso;
        this.memoriaRAM = memoriaRAM;
        this.quantumDefault = quantumDefault;
        quantum = quantumDefault;
        contadorQuantum = 0;
        tiempoRecorrido = 0;
        arreglo = new int[2][numProcesos];
    }

    //MÉTODOS
    //Método que hará todos los procesos
    public Cola hacerProcesos() {
        colaDes = new Cola();
        nodo = colaDes.inicializar();

        for (int i = 1; i <= numProcesos; i++) {
            hacerProceso(i);
        }
        // La cola ordenada será la cola definitiva de procesos listos
        ordenar();
        return colaOrd;
    }

    //Método que hará un proceso y lo insertará en la cola desordenada colaDes
    public void hacerProceso(int id) {   //Se le pasa como argumento el id del proceso, con el cual se definirá el nombre como la cadena "P" + (String)id
        this.id = id;
        nombre = "P" + id;
        tam = numRandom.nextInt(maxTamProceso - 99) + 100; //Se genera un número random para tam (del 100 al 500), lo mínimo que puede medir un proceso es 100k
        tiempoE = ((numRandom.nextInt(15)) + 1) * 1000;            //Mínimo 1 segundo y máximo 15 segundos de ejecución
        prioridad = numRandom.nextInt(10) + 1;             //Mínimo 1 y máximo 10
        tiempoL = (numRandom.nextInt(16)) * 1000;          //Mínimo 0 y máximo 15000 -> Ejemplo: El proceso P1 llegó en el milisegundo 6000
        nodo = colaDes.insertar(nodo, id, nombre, tam, tiempoE, prioridad, tiempoL);
    }

    //Método que ordenará los procesos por tiempo de llegada tiempoL y los insertará en colaOrd
    public void ordenar() {
        colaOrd = new Cola();
        nodoAux = colaOrd.inicializar();

        colaMemoriaRAM = new Cola(memoriaRAM);
        nodoMemoriaRAM = colaMemoriaRAM.inicializar();

        nodo = nodo.head;       //Nodo cabeza

        int i;  //Contador

        //Llena id y tiempoL de cada nodo en arreglo
        for (i = 0; i < numProcesos; i++) {
            arreglo[0][i] = nodo.id;        //Renglón 0: id de los nodos
            arreglo[1][i] = nodo.tiempoL;   //Renglón 1: tiempoL de los nodos
            try {
                if (i == numProcesos - 1) {
                    break;
                }
                nodo = nodo.siguiente;
            } catch (NullPointerException e) {
                break;
            }
        }

        //Ordenar arreglo por tiempos de llegada
        arreglo = insertionSort(arreglo);

        tiempo1 = 0;        //Tiempo de llegada menor
        //int tiempo2 = 0;

        //Llenar colaOrd utilizando el arreglo ordenado
        nodo = nodo.head;
        i = 0;
        while (i < numProcesos) {
            if (arreglo[0][i] == nodo.id) {
                //Este if sólo se ejecuta la primera vez que se va a insertar un nodo en la cola de procesos listos
                if (i == 0) {
                    tiempo1 = arreglo[1][i] - tiempo1;
                    colaOrd.dormirV1(arreglo[1][i], tiempo1);
                } //Este else se ejecutará las demás veces que se inserte un nodo en la cola de procesos listos
                else {
                    colaOrd.dormirV2(arreglo[1][i - 1], nodo.tiempoL); //Tiempo anterior, Tiempo de llegada del nuevo nodo
                }
                System.out.println("Llega proceso nuevo");
                nodoAux = colaOrd.insertar(nodoAux, nodo.id, nodo.nombre, nodo.tam, nodo.tiempoE, nodo.prioridad, nodo.tiempoL);
                colaOrd.imprimirColaNombreTL(nodoAux); //Cada que se INSERTA nodo se imprime la cola ordenada

                //Insertar en la cola de procesos listos para ejecución
                if (memoriaRAM - nodoAux.tam > 0 && colaOrd.cantidad < 2) {
                    //Se actualizan valores de la memoria RAM
                    memoriaRAM = memoriaRAM - nodoAux.tam;
                    colaMemoriaRAM.memoriaRAM = memoriaRAM;
                    System.out.println("Subio proceso " + nodo.nombre + " y restan " + memoriaRAM + "[k] de memoria");
                    //Se elimina nodo de la cola de procesos listos
                    colaOrd.borrar(nodoAux);
                    colaOrd.imprimirColaNombreTL(nodoAux); //Cada que se ELIMINA nodo se imprime la cola ordenada
                    //Se inserta en la cola de procesos listos para ejecución
                    nodoMemoriaRAM = colaMemoriaRAM.insertar(nodoMemoriaRAM, nodo.id, nodo.nombre, nodo.tam, nodo.tiempoE, nodo.prioridad, nodo.tiempoL);
                    colaMemoriaRAM.imprimirColaNombreTL(nodoMemoriaRAM);
                }
                nodo = nodo.head;
                i++;
            } else {
                try {
                    nodo = nodo.siguiente;
                } catch (NullPointerException e) {
                    break;
                }
            }
        }
    }

    //Método para ordenar el arreglo que contiene los tiempos de llegada de cada proceso, para identificarlos el arreglo tiene dos renglones,
    //cada columna del primero contiene los id de cada proceso y las columnas del segundo renglón los tiempo de llegada
    public int[][] insertionSort(int arreglo[][]) {
        int n = numProcesos;
        for (int j = 1; j < n; j++) {
            int key1 = arreglo[0][j]; //key1 = Id de un nodo
            int key2 = arreglo[1][j]; //key2 = Tiempo ejecución de un nodo
            int i = j - 1;
            while ((i > -1) && (arreglo[1][i] > key2)) {
                arreglo[0][i + 1] = arreglo[0][i];
                arreglo[1][i + 1] = arreglo[1][i];
                i--;
            }
            arreglo[0][i + 1] = key1;
            arreglo[1][i + 1] = key2;           //Aquí termina cada iteración
        }
        return arreglo;
    }

    public void rr() {

    }
}
