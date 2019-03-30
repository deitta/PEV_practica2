package metodosCruce;

import java.util.ArrayList;

import base.Cromosoma;

public class CruceVarianteOX implements AlgoritmoCruce {
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		double prob = 0.5;
		ArrayList<Integer> c1 = new ArrayList<Integer>(); //array de valores elegidos para hijo1
		ArrayList<Integer> c2 = new ArrayList<Integer>(); //array de indices para hijo1
		ArrayList<Integer> c3 = new ArrayList<Integer>(); //array de valores elegidos para hijo2
		ArrayList<Integer> c4 = new ArrayList<Integer>(); //array de indices para hijo2

		for(int i = 0; i < padre1.getnGenes(); i++){
			double valor = Math.random();
			if(valor > prob) { //esta prob o le ponemos por defecto un valor en este metodo o se lo pasamos por param.
				c1.add(padre1.genes[i].getCiudad());	
				c3.add(padre2.genes[i].getCiudad());	
			}
		}

		for (int i = 0; i < c1.size(); i++)
			c2.add(funcionaux(c1.get(i), 0, padre2.getnGenes() - 1, padre2));

		int j = 0;
		for (int i = 0; i < padre2.getnGenes(); i++){
			if(!c2.contains(i))
				hijo1.genes[i].setCiudad(padre2.genes[i].getCiudad());
			else {
				hijo1.genes[i].setCiudad(c1.get(j));
				j++;
			}
		}
		//hijo2		
		for (int i = 0; i < c3.size(); i++)
			c4.add(funcionaux(c3.get(i), 0, padre2.getnGenes() - 1, padre1));

		int k = 0;
		for (int i = 0; i < padre2.getnGenes(); i++){
			if(!c4.contains(i))
				hijo2.genes[i].setCiudad(padre1.genes[i].getCiudad());
			else {
				hijo2.genes[i].setCiudad(c3.get(k));
				k++;
			}
		}
	}

	private int funcionaux(int num, int puntDC1, int puntDC2, Cromosoma cromosoma){
		while(num != cromosoma.genes[puntDC1].getCiudad() && puntDC1 <= puntDC2)
			puntDC1++;
		if (puntDC1 > puntDC2) return -1;
		return puntDC1;
	}
}
