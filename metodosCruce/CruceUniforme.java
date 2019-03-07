package metodosCruce;

import cromosoma.FactoriaCromosoma;
import base.Cromosoma;

public class CruceUniforme implements AlgoritmoCruce {

	public void cruce(Cromosoma[] pob, int tamPob, double probCruce) {
		Cromosoma hijo1, hijo2;
		int nGenes = pob[0].getnGenes();
		double tolerancia = pob[0].getTolerancia();
		int idFuncion = pob[0].getIdFuncion();
		int selCruce[] = new int[tamPob]; //seleccionados para reproducir
		int nSelCruce = 0; //contador seleccionados
		double prob;

		//Se eligen los individuos a cruzar
		for (int i = 0; i < tamPob; i++) {
			prob = Math.random(); //se generan tam_pob números aleatorios en [0 1)
			//se eligen los individuos de las posiciones i si prob < probCruce
			if (prob < probCruce){
				selCruce[nSelCruce] = i;
				nSelCruce++;
			}
		}

		// el numero de seleccionados se hace par
		if ((nSelCruce % 2) == 1)
			nSelCruce--;

		for (int i = 0; i < nSelCruce; i+=2) {
			hijo1 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			hijo2 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			for (int j = 0; j < pob[0].getLcrom(); j++){
				prob = (int) Math.random() * 2;
				if (prob == 1) {
					hijo1.cruzar(pob[selCruce[i]], j);
					hijo2.cruzar(pob[selCruce[i+1]], j);
				} else {
					hijo1.cruzar(pob[selCruce[i+1]], j);
					hijo2.cruzar(pob[selCruce[i]], j);
				}
			}

			// los nuevos individuos sustituyen a sus progenitores
			// if (pob[selCruce[i]].getFitness() < hijo1.getFitness()) 
			pob[selCruce[i]] = hijo1;
			// if (pob[selCruce[i+1]].getFitness() < hijo2.getFitness()) 
			pob[selCruce[i+1]] = hijo2;
		}
	}
}
