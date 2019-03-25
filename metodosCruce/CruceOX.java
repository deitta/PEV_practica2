package metodosCruce;

import base.Cromosoma;

public class CruceOX implements AlgoritmoCruce {
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		int nGenes = padre1.getnGenes();
		int puntDC1 = (int) (Math.random()*nGenes), puntDC2 = (int) (Math.random()*nGenes), puntDCAux = puntDC1;
		if (puntDC1 == puntDC2) puntDC2 = (puntDC2+1) % nGenes;
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
		int indP1 = puntDC2, indP2 = puntDC2, j;

		// rellena las posiciones del hijo en orden puntDC2 -> nGenes y de 0 -> puntDC1
		for (int i = puntDC2; i >= puntDC2 || i < puntDC1; i = (i+1) % nGenes) {
			do {
				j = puntDC1;
				// comprobamos si padre1 -> ciudad[indP1] ya esta en el hijo
				do {
					conflicto = hijo1.genes[j].getCiudad() == padre1.genes[indP1].getCiudad();
					j = (j+1) % nGenes;
				} while (!conflicto && j != i);
				// si hay conflicto pasamos a la siguiente ciudad
				if (conflicto) indP1 = (indP1+1) % nGenes;
			} while (conflicto);
			// añadimos a hijo1 -> ciudad[i] la primera ciudad del padre que no presenta conflicto
			hijo1.genes[i].setCiudad(padre1.genes[indP1].getCiudad());

			// lo mismo de antes pero para el hijo2
			do {
				j = puntDC1;
				do {
					conflicto = hijo2.genes[j].getCiudad() == padre2.genes[indP2].getCiudad();
					j = (j+1) % nGenes;					
				} while (!conflicto && j != i);
				if (conflicto) indP2 = (indP2+1) % nGenes;
			} while (conflicto);
			hijo2.genes[i].setCiudad(padre2.genes[indP2].getCiudad());
		}
	}
}
