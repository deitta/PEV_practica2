package metodosCruce;

import base.Cromosoma;

public class CruceOX implements AlgoritmoCruce {
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
			
			cruceOX(pob[selCruce[i]], pob[selCruce[i+1]], hijo1, hijo2);

			// se evaluan
			hijo1.setFitness(hijo1.evaluaCromosoma());
			hijo2.setFitness(hijo2.evaluaCromosoma());
			
			// los nuevos individuos sustituyen a sus progenitores
			pob[selCruce[i]] = hijo1;
			pob[selCruce[i+1]] = hijo2;
		}
	}
	

	private void cruceOX(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		int nGenes = padre1.getnGenes();
		int puntDC1 = (int) (Math.random()*nGenes), puntDC2 = (int) (Math.random()*nGenes), puntDCAux = puntDC1;
		if (puntDC1 > puntDC2){
			puntDC1 = puntDC2;
			puntDC2 = puntDCAux;
		}

		for (int i = puntDC1; i < puntDC2; i++){
			hijo1.genes[i].setCiudad(padre2.genes[i].getCiudad());
			hijo2.genes[i].setCiudad(padre1.genes[i].getCiudad());
		}

		boolean conflicto;
		// indices de las posiciones donde no hay conflicto para hijo1 e hijo2 repectivamente
		int indH1 = puntDC2, indH2 = puntDC2, j;

		// rellena las posiciones del hijo en orden puntDC2 -> nGenes y de 0 -> puntDC1
		for (int i = puntDC2; i >= puntDC2 || i < puntDC1; i = (i+1) % nGenes) {
			do {
				j = puntDC1;
				// comprobamos si padre1 -> ciudad[indH1] ya esta en el hijo
				do {
					conflicto = hijo1.genes[j].getCiudad() == padre1.genes[indH1].getCiudad();
					j = (j+1) % nGenes;
				} while (!conflicto && j != i);
				if (conflicto) indH1 = (indH1+1) % nGenes;
			} while (conflicto);
			hijo1.genes[i].setCiudad(padre1.genes[indH1].getCiudad());

			do {
				j = puntDC1;
				do {
					conflicto = hijo2.genes[j].getCiudad() == padre2.genes[indH2].getCiudad();
					j = (j+1) % nGenes;					
				} while (!conflicto && j != i);
				if (conflicto) indH2 = (indH2+1) % nGenes;
			} while (conflicto);
			hijo2.genes[j].setCiudad(padre2.genes[indH2].getCiudad());
		}
	}
}
