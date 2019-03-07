package metodosSeleccion;

public class FactoriaSeleccion {
	public static AlgoritmoSeleccion getAlgoritmoDeSeleccion(String algoritmo, int participantes) {
		switch(algoritmo) {
			case "Ruleta": return new SeleccionRuleta();
			case "Estocastico": return new SeleccionUniversalEstocastica();
			case "Torneo": return new SeleccionTorneo(participantes);
			case "Restos": return new SeleccionRestos();
			// case "TorneoProbabilistico": return new SeleccionTorneoProb(participantes);
			// case "Ranking": return new SeleccionRanking();
			default: return new SeleccionRuleta();
		}
	}
}
