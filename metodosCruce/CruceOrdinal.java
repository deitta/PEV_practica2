package metodosCruce;

import java.util.ArrayList;

import base.Cromosoma;

public class CruceOrdinal implements AlgoritmoCruce {
	
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		int nGenes = padre1.genes.length, puntDC = (int) (Math.random()*nGenes);
		int[] ordinalP1 = new int[nGenes], ordinalP2 = new int[nGenes];
		
		// los arrays auxiliares para calcular las posiciones por orden
		ArrayList<Integer> ordenNaturalP1 = new ArrayList<Integer>(), ordenNaturalP2 = new ArrayList<Integer>();
		for (int i = 0; i < nGenes+1; i++) {
			ordenNaturalP1.add(i);
			ordenNaturalP2.add(i);
		}
		// quitamos madrid
		ordenNaturalP1.remove(25);
		ordenNaturalP2.remove(25);
		
		// Obtenemos los valores por orden de los padres
		for (int i = 0; i< nGenes; i++) {
			ordinalP1[i] = ordenNaturalP1.indexOf(padre1.genes[i].getCiudad());
			ordenNaturalP1.remove(ordinalP1[i]);
			ordinalP2[i] = ordenNaturalP2.indexOf(padre2.genes[i].getCiudad());
			ordenNaturalP2.remove(ordinalP2[i]);
		}
		
		// reestablecemos los arrays auxiliares
		for (int i = 0; i < nGenes+1; i++) {
			ordenNaturalP1.add(i);
			ordenNaturalP2.add(i);
		}
		// quitamos madrid
		ordenNaturalP1.remove(25);
		ordenNaturalP2.remove(25);
		
		// rellenamos los hijos
		for (int i = 0; i < puntDC; i++) {
			hijo1.genes[i].setCiudad(ordenNaturalP1.get(ordinalP1[i]));
			ordenNaturalP1.remove(ordinalP1[i]);
			hijo2.genes[i].setCiudad(ordenNaturalP2.get(ordinalP2[i]));
			ordenNaturalP2.remove(ordinalP2[i]);
		}
		
		for (int i = puntDC; i < nGenes; i++) {
			hijo1.genes[i].setCiudad(ordenNaturalP1.get(ordinalP2[i]));
			ordenNaturalP1.remove(ordinalP2[i]);
			hijo2.genes[i].setCiudad(ordenNaturalP2.get(ordinalP1[i]));
			ordenNaturalP2.remove(ordinalP1[i]);
		}
	}

}
