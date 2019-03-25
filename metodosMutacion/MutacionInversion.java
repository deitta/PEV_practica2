package metodosMutacion;

import base.Cromosoma;

public class MutacionInversion implements AlgoritmoMutacion {

	@Override
	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int pos1, pos2, aux;
		int nGenes = pob[0].getnGenes();

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			prob = Math.random();	// se genera un numero aleatorio entre [0 1]
			if (prob < probMutacion){	// mutan los genes con prob<probMutacion
				pos1 = (int) (Math.random() * (nGenes - 1)); //0 incluido y ngenes-1 no(ultima pos no incluida)
				pos2 = (int) (Math.random() * (nGenes) + (pos1 + 1)); //pos1+1 incluido y ngenes no (ultima pos incluida)
				for (int j = pos1; j <= pos2/2; j++) {
					aux = pob[i].genes[pos2];
					pob[i].genes[pos2] = pob[i].genes[j];
					pob[i].genes[j] = aux;
					pos2--;
				}
			mutado = true;
			}
			if (mutado)
				pob[i].setFitness(pob[i].evaluaCromosoma());
		}
	}

}
