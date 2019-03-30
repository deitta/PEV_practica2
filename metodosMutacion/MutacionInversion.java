package metodosMutacion;

import base.Cromosoma;

// Invierte el orden de un intervalo aleatorio
public class MutacionInversion implements AlgoritmoMutacion {

	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int puntDC1, puntDC2, puntDCAux, genAux;
		int nGenes = pob[0].getnGenes();

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			prob = Math.random();	// se genera un numero aleatorio entre [0 1]
			if (prob < probMutacion){	// mutan los genes con prob<probMutacion
				puntDC1 = (int) (Math.random()*nGenes); puntDC2 = (int) (Math.random()*nGenes); puntDCAux = puntDC1;
				if (puntDC1 > puntDC2){
					puntDC1 = puntDC2;
					puntDC2 = puntDCAux;
				}
				
				int medio = (puntDC2+puntDC1)/2;
				for (int j = puntDC1; j <= medio; j++){
					genAux = pob[i].genes[puntDC2].getCiudad();
					pob[i].genes[puntDC2].setCiudad(pob[i].genes[j].getCiudad());
					pob[i].genes[j].setCiudad(genAux);
					puntDC2--;
				}
				mutado = true;
			}
			if (mutado) pob[i].setFitness(pob[i].evaluaCromosoma());
		}
		
	}
	
}
