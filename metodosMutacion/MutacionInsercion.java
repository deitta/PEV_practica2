package metodosMutacion;

import base.Cromosoma;

public class MutacionInsercion implements AlgoritmoMutacion {

	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int genParaMutar, pos;

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			for (int j = 1; j < pob[0].getnGenes(); j++) {
				// se genera un numero aleatorio entre [0 1]
				prob = Math.random();
				// mutan los genes con prob<probMutacion
				if (prob < probMutacion){
					genParaMutar = pob[i].genes[i].getCiudad();
					pos = (int) (Math.random() * (j-1)); //entre 0 incluido y la j-1
					for(int k = j; k > pos; k--)
						pob[i].genes[k].setCiudad(pob[i].genes[k-1].getCiudad());
					pob[i].genes[pos].setCiudad(genParaMutar);
					mutado = true;
				}
			}
			if (mutado)
				pob[i].setFitness(pob[i].evaluaCromosoma());
		}
	}
}
