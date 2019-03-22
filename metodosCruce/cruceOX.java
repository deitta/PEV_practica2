package metodosCruce;

import base.Cromosoma;

public class CruceOX implements AlgoritmoCruce {
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
			hijo1 = new Cromosoma(idFuncion, tolerancia, nGenes);
			hijo2 = new Cromosoma(idFuncion, tolerancia, nGenes);
			
			cruceOX(pob[selCruce[i]], pob[selCruce[i+1]], hijo1, hijo2);
			// los nuevos individuos sustituyen a sus progenitores
			pob[selCruce[i]] = hijo1;
			pob[selCruce[i+1]] = hijo2;
		}
	}
	

	private void cruceOX(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		int nGenes = pob[0].getnGenes();
		int puntDC1 = Math.random*nGenes, puntDC2 = Math.random*nGenes, puntDCAux = puntDC1;
		if (puntDC1 > puntDC2){
			puntDC1 = puntDC2;
			puntDC2 = puntDCAux;
		}

		for (int i = puntDC1; i < puntDC2; i++){
			hijo1.genes[i].ciudad = padre2.genes[i].ciudad;
			hijo2.genes[i].ciudad = padre1.genes[i].ciudad;
		}

		boolean conflicto;
		int ciudad;
		for (int i = puntDC2; i < nGenes; i++){
			conflicto = false;
			ciudad = padre2.genes[i].ciudad;
			do {
				conflicto = false;
				for (int j = puntDC1; j < i && !conflicto; j++){
					if (hijo1.genes[j].ciudad == ciudad)
						conflicto = true;
					if (conflicto) ciudad = padre2.genes[i].ciudad;
					else hijo1.genes[j].ciudad = ciudad;
				}
			} while (conflicto);

			conflicto = false;
			ciudad = padre1.genes[i].ciudad;
			do {
				conflicto = false;
				for (int j = puntDC1; j < i && !conflicto; j++){
					if (hijo2.genes[j].ciudad == ciudad)
						conflicto = true;
					if (conflicto) ciudad = padre1.genes[i].ciudad;
					else hijo2.genes[j].ciudad = ciudad;
				}
			} while (conflicto);
		}

		for (int i = 0; i < puntDC1; i++){
			conflicto = false;
			ciudad = padre2.genes[i].ciudad;
			do {
				conflicto = false;
				for (int j = puntDC1; j < nGenes && !conflicto; j++){
					if (hijo1.genes[j].ciudad == ciudad)
						conflicto = true;
					if (conflicto) ciudad = padre2.genes[i].ciudad;
					else hijo1.genes[j].ciudad = ciudad;
				}
				for (int j = 0; j < i && !conflicto; j++){
					if (hijo1.genes[j].ciudad == ciudad)
						conflicto = true;
					if (conflicto) ciudad = padre2.genes[i].ciudad;
					else hijo1.genes[j].ciudad = ciudad;
				}
			} while (conflicto);

			conflicto = false;
			ciudad = padre1.genes[i].ciudad;
			do {
				conflicto = false;
				for (int j = puntDC1; j < nGenes && !conflicto; j++){
					if (hijo2.genes[j].ciudad == ciudad)
						conflicto = true;
					if (conflicto) ciudad = padre1.genes[i].ciudad;
					else hijo2.genes[j].ciudad = ciudad;
				}
				for (int j = 0; j < i && !conflicto; j++){
					if (hijo1.genes[j].ciudad == ciudad)
						conflicto = true;
					if (conflicto) ciudad = padre2.genes[i].ciudad;
					else hijo1.genes[j].ciudad = ciudad;
				}
			} while (conflicto);
		}
	}
}
