package metodosMutacion;

import base.Cromosoma;

public class MutacionInsercion implements AlgoritmoMutacion {

	@Override
	public void mutacion(Cromosoma ind) {


	}

	public void mutacionInsercion(Cromosoma ind, int tamPob) {
		boolean mutado;
		double prob;
		int genParaMutar;

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			for (int j = 1; j < pob[0].getnGenes; j++) {
				// se genera un numero aleatorio entre [0 1]
				prob = Math.random();
				// mutan los genes con prob<probMutacion
				if (prob < probMutacion){
					genParaMutar = pob[j];
					pos = (int) (Math.random() * (j-1)); //entre 0 incluido y la pos-1
					for(int i = j; j > pos; j--)
						ind[i] = ind[i-1];
					ind[pos] = genParaMutar;
					mutado = true;
				}
			}
			if (mutado)
				pob[i].fitness = pob[i].evaluaCromosoma();
		}
	}

}
