package metodosCruce;

public class FactoriaCruce {
	public static AlgoritmoCruce getAlgoritmoDeCruce(String cruce) {
		switch(cruce) {
			case "Monopunto": return new CruceMonopunto();
			case "Uniforme": return new CruceUniforme();
			case "Aritmetico": return new CruceAritmetico();
			case "SBX": return new CruceSBX();
			default: return new CruceMonopunto();
		}
	}
}
