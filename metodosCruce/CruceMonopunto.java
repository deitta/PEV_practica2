package metodosCruce;

import cromosoma.FactoriaCromosoma;
import base.Cromosoma;

public class CruceMonopunto implements AlgoritmoCruce{

	public void cruce(Cromosoma[] pob, int tamPob, double probCruce) {
		Cromosoma hijo1, hijo2;
		int nGenes = pob[0].getnGenes();
		double tolerancia = pob[0].getTolerancia();
		int idFuncion = pob[0].getIdFuncion();
		int selCruce[] = new int[tamPob]; //seleccionados para reproducir
		int nSelCruce = 0; //contador seleccionados
		int puntoCruce;
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

		// se cruzan los individuos elegidos en un punto al azar. Todos por el mismo punto
		puntoCruce = (int) (Math.random()*(pob[0].getLcrom() + 1));
		for (int i = 0; i < nSelCruce; i+=2) {
			hijo1 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			hijo2 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			crucePorPunto(pob[selCruce[i]], pob[selCruce[i+1]], hijo1, hijo2, puntoCruce);
			// los nuevos individuos sustituyen a sus progenitores
			// if (pob[selCruce[i]].getFitness() < hijo1.getFitness()) 
			pob[selCruce[i]] = hijo1;
			// if (pob[selCruce[i+1]].getFitness() < hijo2.getFitness()) 
			pob[selCruce[i+1]] = hijo2;
		}
	}

	private void crucePorPunto(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2, int puntoCruce) {
		int i;
		// primera parte del intercambio: 1 a 1 y 2 a 2
		for (i = 0; i < puntoCruce; i++) {
			hijo1.cruzar(padre1, i);
			hijo2.cruzar(padre2, i);
		}

		// segunda parte: 1 a 2 y 2 a 1
		for (i = puntoCruce; i < padre1.getLcrom(); i++) {
			hijo1.cruzar(padre2, i);
			hijo2.cruzar(padre1, i);
		}

		// se evalÃºan
		hijo1.setFitness(hijo1.evaluaCromosoma());
		hijo2.setFitness(hijo2.evaluaCromosoma());
	}
}
