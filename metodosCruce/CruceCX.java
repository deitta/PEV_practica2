package metodosCruce;

import java.util.ArrayList;

import base.Cromosoma;

public class CruceCX implements AlgoritmoCruce {
	
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		ArrayList<Integer> genesP1 = new ArrayList<Integer>();
		ArrayList<Integer> genesP2 = new ArrayList<Integer>();
		ArrayList<Integer> genesH1 = new ArrayList<Integer>();
		ArrayList<Integer> genesH2 = new ArrayList<Integer>();
		int ind = 0, nGenes = padre1.genes.length;
		
		for(int i = 0; i < nGenes; i++) {
			genesP1.add(padre1.genes[i].getCiudad());
			genesP2.add(padre2.genes[i].getCiudad());
		}
		
		// copiamos los genes de padre2 en hijo1 y los de padre1 en hijo2
		for (int i = 1; i < nGenes; i++) {
			hijo1.genes[i].setCiudad(genesP2.get(i));
			hijo2.genes[i].setCiudad(genesP1.get(i));
		}
		
		// añade a hijo1 los genes de p1 que corresponden
		do {
			hijo1.genes[ind].setCiudad(genesP1.get(ind));
			genesH1.add(genesP1.get(ind));
			ind = genesP2.indexOf(genesP1.get(ind)); // coge la poscion de padre2 donde esta la ultima ciudad añadida
		} while (!genesH1.contains(genesP1.get(ind))); // continua mientras no haya completado el ciclo
		
		// lo mismo para hijo2
		ind = 0;
		do {
			hijo2.genes[ind].setCiudad(genesP2.get(ind));
			genesH2.add(genesP2.get(ind));
			ind = genesP2.indexOf(genesP1.get(ind));
		} while (!genesH2.contains(genesP2.get(ind)));
	}

}
