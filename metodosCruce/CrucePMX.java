package metodosCruce;

import base.Cromosoma;

public class CrucePMX implements AlgoritmoCruce {
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
		
		boolean conflicto = false;
		int j;
		for (int i = puntDC2; i >= puntDC2 || i < puntDC1; i = (i+1) % nGenes){
			// hijo 1
			// comprobamos si padre1 -> ciudad[i] ya esta en el hijo
			j = puntDC1;
			do {
				conflicto = hijo1.genes[j].getCiudad() == padre1.genes[i].getCiudad();
				j++;
			} while (!conflicto && j < puntDC2);
			// si hay conflicto sustituimos por la ciudad del padre1 que corresponde a  la posicion de la ciudad i-esima del padre1, dentro del padre2
			if (conflicto)
				hijo1.genes[i].setCiudad(padre1.genes[j-1].getCiudad());
			else hijo1.genes[i].setCiudad(padre1.genes[i].getCiudad());
			
			// hijo 2
			j = puntDC1;
			do {
				conflicto = hijo2.genes[j].getCiudad() == padre2.genes[i].getCiudad();
				j++;
			} while (!conflicto && j < puntDC2);
			if (conflicto)
				hijo2.genes[i].setCiudad(padre2.genes[j-1].getCiudad());
			else hijo2.genes[i].setCiudad(padre2.genes[i].getCiudad());
		}
	}
}
