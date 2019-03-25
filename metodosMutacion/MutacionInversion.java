package metodosMutacion;

import base.Cromosoma;

public class MutacionInversion implements AlgoritmoMutacion {

	@Override
	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int puntDC1, puntDC2, puntDCAux, aux;
		int nGenes = pob[0].getnGenes();

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			prob = Math.random();	// se genera un numero aleatorio entre [0 1]
			if (prob < probMutacion){	// mutan los genes con prob<probMutacion
				puntDC1 = (int) (Math.random()*nGenes), puntDC2 = (int) (Math.random()*nGenes), puntDCAux = puntDC1;
				if (puntDC1 > puntDC2){
					puntDC1 = puntDC2;
					puntDC2 = puntDCAux;
				}
				for (int j = puntDC1; j <= puntDC2/2; j++) {
					aux = pob[i].genes[puntDC2];
					pob[i].genes[puntDC2] = pob[i].genes[j];
					pob[i].genes[j] = aux;
					puntDC2--;
				}
			mutado = true;
			}
			if (mutado)
				pob[i].setFitness(pob[i].evaluaCromosoma());
		}
	}

}
