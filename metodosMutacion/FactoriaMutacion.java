package metodosMutacion;


public class FactoriaMutacion {
	public static AlgoritmoMutacion getAlgoritmoDeMutacion(String algoritmo) {
		switch(algoritmo) {
			case "Heuristica": return new MutacionHeuristica();
			case "Insercion": return new MutacionInsercion();
			case "Intercambio": return new MutacionIntercambio();
			case "Inversion": return new MutacionInversion();
			// Propio
			default: return new MutacionHeuristica();
		}
	}
}
