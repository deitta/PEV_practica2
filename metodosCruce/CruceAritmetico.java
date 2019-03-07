package metodosCruce;

import base.Cromosoma;
import base.GenReal;
import cromosoma.FactoriaCromosoma;

public class CruceAritmetico implements AlgoritmoCruce {
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

		// se cruzan los individuos elegidos en un punto al azar. Todos por el mismo punto
		for (int i = 0; i < nSelCruce; i+=2) {
			hijo1 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			hijo2 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			
			cruceAritmetico(pob[selCruce[i]], pob[selCruce[i+1]], hijo1, hijo2);
			// los nuevos individuos sustituyen a sus progenitores
			pob[selCruce[i]] = hijo1;
			pob[selCruce[i+1]] = hijo2;
		}
	}
	

	private void cruceAritmetico(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		double alfa;
		// primera parte del intercambio: 1 a 1 y 2 a 2
		for (int i = 0; i < padre1.getLcrom(); i++) {
			alfa = Math.random();
			((GenReal) hijo1.genes[i]).setValor(((GenReal) padre1.genes[i]).getValor()*alfa + ((GenReal) padre2.genes[i]).getValor()*(1 - alfa));
			((GenReal) hijo2.genes[i]).setValor(((GenReal) padre2.genes[i]).getValor()*alfa + ((GenReal) padre1.genes[i]).getValor()*(1 - alfa));
		}

		// se evaluan
		hijo1.setFitness(hijo1.evaluaCromosoma());
		hijo2.setFitness(hijo2.evaluaCromosoma());
	}
}
