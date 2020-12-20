package planificadorroundrobin;

public class SimuladorPlanificadorProcesos {

    public static void main(String[] args) {
        Datos datos = new Datos();
        RoundRobin roundRobin = new RoundRobin(datos);
    }
}
