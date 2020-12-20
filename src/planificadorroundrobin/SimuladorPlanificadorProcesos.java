package planificadorroundrobin;

public class SimuladorPlanificadorProcesos {

    public static void main(String[] args) {
        Datos datos = new Datos();//Solamente recolecta los datos necesarios para el simulador
        Simulador simulador = new Simulador(datos);
        
        simulador.iniciar();// Inicia la ejecución
    }
}
