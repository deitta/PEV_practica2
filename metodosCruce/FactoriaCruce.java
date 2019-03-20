package metodosCruce;

public class FactoriaCruce {
	public static AlgoritmoCruce getAlgoritmoDeCruce(String cruce) {
		switch(cruce) {
			case "PMX": return new CrucePMX();
			case "OX": return new CruceOX();
			case "Variante de OX": return new CruceVarianteOX();
			case "CX": return new CruceCX();
			case "ERX": return new CruceERX();
			case "Ordinal": return new CruceOrdinal();
			// añadir metodo propio
			default: return new CrucePMX();
		}
	}
}
