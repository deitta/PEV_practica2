package cromosoma;

public class CromosomaFuncion4Binario extends CromosomaFuncion4{
	final static int idFuncion = 4;

	public CromosomaFuncion4Binario(double tolerancia, int nGenes) {
		super(tolerancia, idFuncion, nGenes);
		for(int i = 0; i < nGenes; i++)
			inicializaGenes(i, maxX, minX);
		super.lcrom = getLongitud();
	}

}
