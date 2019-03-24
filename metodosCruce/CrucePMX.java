package metodosCruce;

import base.Cromosoma;

public class CrucePMX implements AlgoritmoCruce {
	public void cruce(Cromosoma[] pob, int tamPob, double probCruce) {
		int nGenes = padre1.getnGenes();
		int puntDC1 = (int) (Math.random() * (nGenes - 1)); //0 incluido y ngenes-1 no(ultima pos no incluida)
		int puntDC2 = (int) (Math.random() * (nGenes) + (pos1 + 1)); //pos1+1 incluido y ngenes no (ultima pos incluida)

		for (int i = puntDC1; i <= puntDC2; i++){
			hijo1.genes[i].setCiudad(padre2.genes[i].getCiudad());
			hijo2.genes[i].setCiudad(padre1.genes[i].getCiudad());
		}

		boolean conflicto = false;
		// indices de las posiciones donde no hay conflicto para hijo1 e hijo2 repectivamente
		int indP1 = puntDC2 + 1, indP2 = puntDC2 + 1, j;
		// rellena las posiciones del hijo en orden puntDC2 -> nGenes y de 0 -> puntDC1
		for (int i = puntDC2 + 1; i >= puntDC2 || i < puntDC1; i = (i+1) % nGenes) {
			do {
				j = puntDC1; //para recorrer el array entre los puntDC
				do{
					conflicto = hijo1.genes[j].getCiudad() == padre1.genes[indP1].getCiudad();
					j = (j+1) % nGenes;
				}while(!conflicto && j != i);
				// si hay conflicto pasamos a la siguiente ciudad
				if (conflicto) {
					indP1 = (indP1+1) % nGenes;
					i++;
				}
			} while (conflicto);
			hijo1.genes[i].setCiudad(padre1.genes[indP1].getCiudad());

			// lo mismo de antes pero para el hijo2
			do {
				j = puntDC1;
				do {
					conflicto = hijo2.genes[j].getCiudad() == padre2.genes[indP2].getCiudad();
					j = (j+1) % nGenes;
				} while (!conflicto && j != i);
				if (conflicto) {
					indH2 = (indH2+1) % nGenes;
					i++;
				}
			} while (conflicto);
			hijo2.genes[i].setCiudad(padre2.genes[indP2].getCiudad());
		}

	}
}
