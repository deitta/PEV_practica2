package metodosCruce;

import base.Cromosoma;

public class CrucePMX implements AlgoritmoCruce {
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		int nGenes = padre1.getnGenes();
		int puntDC1 = (int) (Math.random()*nGenes), puntDC2 = (int) (Math.random()*nGenes), puntDCAux = puntDC1;
		if (puntDC1 == puntDC2) puntDC2 = (puntDC2+1) % nGenes;
		if (puntDC1 > puntDC2) {
			puntDC1 = puntDC2;
			puntDC2 = puntDCAux;
		}

		for (int i = puntDC1; i < puntDC2; i++){
			hijo1.genes[i].setCiudad(padre2.genes[i].getCiudad());
			hijo2.genes[i].setCiudad(padre1.genes[i].getCiudad());
		}

		boolean conflicto = false;
		int indP1, indP2, j;
		for (int i = puntDC2; i >= puntDC2 || i < puntDC1; i = (i+1) % nGenes){
			// hijo 1
			indP1 = i;
			do {
				j = puntDC1;
				do { // comprobamos si padre1 -> ciudad[i] ya esta en el hijo
					conflicto = hijo1.genes[j].getCiudad() == padre1.genes[indP1].getCiudad();
					j = (j+1) % nGenes;
				} while (!conflicto && j != i);
				// si hay conflicto comprobamos que la nueva ciudad a incluir no presenta conflicto
				if (conflicto) indP1 = (nGenes + (j-1))%nGenes;
			} while (conflicto);
			// si hay conflicto sustituimos por la ciudad del padre1 que corresponde a  la posicion de la ciudad i-esima del padre1, dentro del padre2
			hijo1.genes[i].setCiudad(padre1.genes[indP1].getCiudad());

			// hijo 2
			indP2 = i;
			do {
				j = puntDC1;
				do {
					conflicto = hijo2.genes[j].getCiudad() == padre2.genes[indP2].getCiudad();
					j = (j+1) % nGenes;
				} while (!conflicto && j != i);
				if (conflicto) indP2 = (nGenes + (j-1))%nGenes;
			} while (conflicto);
			hijo2.genes[i].setCiudad(padre2.genes[indP2].getCiudad());
		}
	}
}
