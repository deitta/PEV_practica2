package metodosCruce;

import java.util.ArrayList;

import base.Cromosoma;

// copia uno si uno no los genes del padreX en el hijoX y el resto lo rellena con los genes del otro padre si no estan ya
public class CrucePropio implements AlgoritmoCruce {

	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
//		ArrayList<Integer> genesP1 = new ArrayList<Integer>();
//		ArrayList<Integer> genesP2 = new ArrayList<Integer>();
		ArrayList<Integer> genesH1 = new ArrayList<Integer>();
		ArrayList<Integer> genesH2 = new ArrayList<Integer>();
		ArrayList<Integer> vaciosH1 = new ArrayList<Integer>();
		ArrayList<Integer> vaciosH2 = new ArrayList<Integer>();
		int nGenes = padre1.genes.length;

		
//		// inicializamos los ArrayList de los padres
//		for(int i = 0; i < padre1.genes.length; i++) {
//			genesP1.add(padre1.genes[i].getCiudad());
//			genesP2.add(padre2.genes[i].getCiudad());
//		}
		
		// copiamos genes alternos de padre1 en hijo1 y los de padre2 en hijo2
		for (int i = 0; i < nGenes; i+=2) {
			hijo1.genes[i].setCiudad(padre1.genes[i].getCiudad());
			genesH1.add(padre1.genes[i].getCiudad());
			hijo2.genes[i].setCiudad(padre2.genes[i].getCiudad());
			genesH2.add(padre2.genes[i].getCiudad());
		}
		
		// copiamos genes alternos (empezando por el segundo) de padre2 en hijo1 y los de padre1 en hijo2(si es posible)
		for (int i = 1; i < nGenes; i+=2) {
			if (!genesH1.contains(padre2.genes[i].getCiudad())) {
				hijo1.genes[i].setCiudad(padre2.genes[i].getCiudad());
				genesH1.add(padre2.genes[i].getCiudad());
			} else vaciosH1.add(i);
			if (!genesH2.contains(padre1.genes[i].getCiudad())) {
				hijo2.genes[i].setCiudad(padre1.genes[i].getCiudad());
				genesH2.add(padre1.genes[i].getCiudad());
			} else vaciosH2.add(i);
		}
		
		// Incluimos las ciudades que no hemos podido heredar del segundo padre
		for (int indNoIncluido = 0; indNoIncluido < nGenes+1 && !vaciosH1.isEmpty(); indNoIncluido++)
			if (!genesH1.contains(indNoIncluido) && indNoIncluido != 25) {
				hijo1.genes[vaciosH1.get(0)].setCiudad(indNoIncluido);
				vaciosH1.remove(0);
				genesH1.add(indNoIncluido);
			}
		
		for (int indNoIncluido = 0; indNoIncluido < nGenes+1 && !vaciosH2.isEmpty(); indNoIncluido++)
			if (!genesH2.contains(indNoIncluido) && indNoIncluido != 25) {
				hijo2.genes[vaciosH2.get(0)].setCiudad(indNoIncluido);
				vaciosH2.remove(0);
				genesH2.add(indNoIncluido);
			}
		
	}

}
