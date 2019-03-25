package metodosMutacion;

import base.Cromosoma;

public class MutacionIntercambio implements AlgoritmoMutacion {

	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int pos1, pos2, ciudadAux;
		int nGenes = pob[0].getnGenes();

		for (int i = 0; i < tamPob; i++) {
			mutado = false;

			prob = Math.random();
			// mutan los genes con prob<probMutacion
			if (prob < probMutacion){
				pos1 = (int) (Math.random()*nGenes);
				pos2 = (int) (Math.random()*nGenes);

				if (pos1 == pos2) pos2 = (pos2+1) % nGenes;

				ciudadAux = pob[i].genes[pos1].getCiudad();
				pob[i].genes[pos1].setCiudad(pob[i].genes[pos2].getCiudad());
				pob[i].genes[pos2].setCiudad(ciudadAux);

				mutado = true;
			}

			if (mutado) pob[i].setFitness(pob[i].evaluaCromosoma());
		}		
	}

}
