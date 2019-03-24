package metodosCruce;

import base.Cromosoma;

public class CrucePMX implements AlgoritmoCruce {
	public void cruce(Cromosoma[] pob, int tamPob, double probCruce) {
		Cromosoma hijo1, hijo2;
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
			hijo1 = new Cromosoma();
			hijo2 = new Cromosoma();

			crucePMX(pob[selCruce[i]], pob[selCruce[i+1]], hijo1, hijo2);

			// se evaluan
			hijo1.setFitness(hijo1.evaluaCromosoma());
			hijo2.setFitness(hijo2.evaluaCromosoma());

			// los nuevos individuos sustituyen a sus progenitores
			pob[selCruce[i]] = hijo1;
			pob[selCruce[i+1]] = hijo2;
		}
	}


	private void crucePMX(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
			int nGenes = padre1.getnGenes();
			int pos1 = (int) (Math.random() * (nGenes - 1)); //0 incluido y ngenes-1 no(ultima pos no incluida)
			int pos2 = (int) (Math.random() * (nGenes) + (pos1 + 1)); //pos1+1 incluido y ngenes no (ultima pos incluida)

	}
}
