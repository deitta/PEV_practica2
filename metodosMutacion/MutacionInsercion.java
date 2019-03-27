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
				prob = Math.random();// se genera un numero aleatorio entre [0 1]
				if (prob < probMutacion){// mutan los genes con prob<probMutacion
					genParaMutar = pob[i].genes[j].getCiudad();
					pos = (int) (Math.random() * (j)); //entre 0 incluido y j no
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
