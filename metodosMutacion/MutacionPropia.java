package metodosMutacion;

import base.Cromosoma;

// desplaza un bloque de genes una posicion a la derecha
public class MutacionPropia implements AlgoritmoMutacion {

	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int genAux, nGenes = pob[0].getnGenes();
		int puntDC1, puntDC2, puntDCAux;

		for (int i = 0; i < tamPob; i++) {
			mutado = false;

			prob = Math.random();// se genera un numero aleatorio entre [0 1]
			if (prob < probMutacion){// mutan los genes con prob<probMutacion
				puntDC1 = (int) (Math.random()*nGenes);
				puntDC2 = (int) (Math.random()*nGenes);
				puntDCAux = puntDC1;
				if (puntDC1 == puntDC2) puntDC2 = (puntDC2+1) % nGenes;
				if (puntDC1 > puntDC2){
					puntDC1 = puntDC2;
					puntDC2 = puntDCAux;
				}
				
				genAux = pob[i].genes[puntDC2].getCiudad();
				for (int j = puntDC2; j > puntDC1; j--)
					pob[i].genes[j].setCiudad(pob[i].genes[j-1].getCiudad());
				pob[i].genes[puntDC1].setCiudad(genAux);
				
				mutado = true;
			}
			
			if (mutado) pob[i].setFitness(pob[i].evaluaCromosoma());
		}
	}

}
