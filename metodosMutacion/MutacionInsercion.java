package metodosMutacion;

import base.Cromosoma;

// los genes seleccionados aleatoriamente son movidos a otra poscion a la izq seleccionada aletoriamente 
public class MutacionInsercion implements AlgoritmoMutacion {

	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int genAux, pos;
		int nGenes = pob[0].getnGenes();

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			for (int j = 1; j < nGenes; j++) {
				prob = Math.random();// se genera un numero aleatorio entre [0 1]
				if (prob < probMutacion){// mutan los genes con prob<probMutacion
					genAux = pob[i].genes[j].getCiudad();
					pos = (int) (Math.random() * (j)); // [0, j)
					for(int k = j; k > pos; k--)
						pob[i].genes[k].setCiudad(pob[i].genes[k-1].getCiudad());
					pob[i].genes[pos].setCiudad(genAux);
					mutado = true;
				}
			}
			if (mutado) pob[i].setFitness(pob[i].evaluaCromosoma());
		}
	}

}
