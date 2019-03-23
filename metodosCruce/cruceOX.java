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
		int ciudad, ind;
		for (int i = puntDC2; i < nGenes; i++){
			conflicto = false;
			ciudad = padre1.genes[i].getCiudad();
			ind = i;
			do {
				conflicto = false;
				for (int j = puntDC1; j < i && !conflicto; j++){
					if (hijo1.genes[j].getCiudad() == ciudad){
						conflicto = true;
						ind++;
					}
					if (conflicto) ciudad = padre1.genes[ind].getCiudad();
					else hijo1.genes[j].setCiudad(ciudad);
				}
			} while (conflicto);

			conflicto = false;
			ciudad = padre2.genes[i].getCiudad();
			ind = i;
			do {
				conflicto = false;
				for (int j = puntDC1; j < i && !conflicto; j++){
					if (hijo2.genes[j].getCiudad() == ciudad){
						conflicto = true;
						ind++;
					}
					if (conflicto) ciudad = padre2.genes[ind].getCiudad();
					else hijo2.genes[j].setCiudad(ciudad);
				}
			} while (conflicto);
		}

		for (int i = 0; i < puntDC1; i++){
			conflicto = false;
			ciudad = padre1.genes[i].getCiudad();
			ind = i;
			do {
				conflicto = false;
				for (int j = puntDC1; j < nGenes && !conflicto; j++){
					if (hijo1.genes[j].getCiudad() == ciudad){
						conflicto = true;
						ind++;
					}
					if (conflicto) ciudad = padre1.genes[ind].getCiudad();
					else hijo1.genes[j].setCiudad(ciudad);
				}
				for (int j = 0; j < i && !conflicto; j++){
					if (hijo1.genes[j].getCiudad() == ciudad){
						conflicto = true;
						ind = (ind+1)%nGenes;
					}
					if (conflicto) ciudad = padre1.genes[ind].getCiudad();
					else hijo1.genes[j].setCiudad(ciudad);
				}
			} while (conflicto);

			conflicto = false;
			ciudad = padre2.genes[i].getCiudad();
			ind = i;
			do {
				conflicto = false;
				for (int j = puntDC1; j < nGenes && !conflicto; j++){
					if (hijo2.genes[j].getCiudad() == ciudad){
						conflicto = true;
						ind++;
					}
					if (conflicto) ciudad = padre2.genes[ind].getCiudad();
					else hijo2.genes[j].setCiudad(ciudad);
				}
				for (int j = 0; j < i && !conflicto; j++){
					if (hijo1.genes[j].getCiudad() == ciudad){
						conflicto = true;
						ind = (ind+1)%nGenes;
					}
					if (conflicto) ciudad = padre2.genes[ind].getCiudad();
					else hijo1.genes[j].setCiudad(ciudad);
				}
			} while (conflicto);
		}
	}
}
