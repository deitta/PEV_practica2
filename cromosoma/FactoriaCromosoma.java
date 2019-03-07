package cromosoma;

import base.Cromosoma;

public class FactoriaCromosoma {
	public static Cromosoma getFuncionCromosoma(int idFuncion, double tolerancia, int nGenes){
		switch (idFuncion){
			case 1: return new CromosomaFuncion1(tolerancia, 2);
			case 2: return new CromosomaFuncion2(tolerancia, 2);
			case 3: return new CromosomaFuncion3(tolerancia, 2);
			case 4: return new CromosomaFuncion4Binario(tolerancia, nGenes);
			case 5: return new CromosomaFuncion4Real(nGenes);
			default: return null;
		}
	}

}
