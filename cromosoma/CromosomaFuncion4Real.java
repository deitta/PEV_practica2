package cromosoma;

import base.Cromosoma;
import base.GenReal;


public class CromosomaFuncion4Real extends CromosomaFuncion4{

	final static int idFuncion = 5;

	public CromosomaFuncion4Real( int nGenes) {
		super(0, idFuncion, nGenes);
		genes = new GenReal[nGenes];
		for (int i = 0; i < nGenes; i++)
			inicializaGenes(i, maxX, minX);
		super.lcrom = getLongitud();
	}
	
	public void cruzar(Cromosoma padre, int pos){ // es el indice del gen
		genes[pos] = padre.genes[pos];
	}

	public boolean isMaximizar() {
		return maximizar;
	}
	
	public void inicializaGenes(int indGen, double max, double min){
		genes[indGen] = new GenReal(max, min, getTolerancia());
	}
	
	public void mutar(int pos) {	
		genes[pos].inicializaGen();
	}

}
