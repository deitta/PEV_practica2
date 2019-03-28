package metodosMutacion;

import base.Cromosoma;

public class MutacionHeuristica implements AlgoritmoMutacion {

	@Override
	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado;
		double prob;
		int genParaMutar, pos, numPermutaciones;
		int[] elementos = new int[pob[0].getnGenes()];

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			for (int j = 0; j < pob[0].getnGenes(); j++) {
				// se genera un numero aleatorio entre [0 1]
				prob = Math.random();
				// mutan los genes con prob<probMutacion
				if (prob < probMutacion)
					elementos[j] = pob[i].genes[j].getCiudad();
			}
		 numPermutaciones = factorial(elementos.length);
//
//		 
//		 
//		 

			if (mutado)
				pob[i].setFitness(pob[i].evaluaCromosoma());
		}

	}

	public static int factorial(int numero){
    if ( numero <= 1 )
        return 1;
    else
        return numero*factorial(numero-1);
    }
}
