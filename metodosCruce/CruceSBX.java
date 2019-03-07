package metodosCruce;

import base.Cromosoma;
import base.GenReal;
import cromosoma.FactoriaCromosoma;

public class CruceSBX implements AlgoritmoCruce{

	public void cruce(Cromosoma[] pob, int tamPob, double probCruce) {
		Cromosoma hijo1, hijo2;
		int nGenes = pob[0].getnGenes();
		double tolerancia = pob[0].getTolerancia();
		int idFuncion = pob[0].getIdFuncion();
		int selCruce[] = new int[tamPob]; //seleccionados para reproducir
		int nSelCruce = 0; //contador seleccionados
		double prob1;

		//Se eligen los individuos a cruzar
		for (int i = 0; i < tamPob; i++) {
			prob1 = Math.random(); //se generan tam_pob números aleatorios en [0 1)
			//se eligen los individuos de las posiciones i si prob < probCruce
			if (prob1 < probCruce){
				selCruce[nSelCruce] = i;
				nSelCruce++;
			}
		}

		// el numero de seleccionados se hace par
		if ((nSelCruce % 2) == 1)
			nSelCruce--;

		for (int i = 0; i < nSelCruce; i+=2) {
			hijo1 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			hijo2 = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);

			cruceSBX(pob[selCruce[i]], pob[selCruce[i+1]], hijo1, hijo2);
			// los nuevos individuos sustituyen a sus progenitores
			// if (pob[selCruce[i]].getFitness() < hijo1.getFitness()) 
			pob[selCruce[i]] = hijo1;
			// if (pob[selCruce[i+1]].getFitness() < hijo2.getFitness()) 
			pob[selCruce[i+1]] = hijo2;
		}
	}


	private void cruceSBX(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		double beta;
		int n = 1;
		double prob;
		double hijo1Valor; 
		double hijo2Valor;
		
		for (int i = 0; i < padre1.getLcrom(); i++){
			prob = Math.random();
			if (prob < 0.5) 
				beta = 2 *(Math.pow(prob,(1/n+1)));
			else 
				beta =  Math.pow((1 / (2*(1-prob))),(1/n+1));

			hijo1Valor = 0.5 * ( (1+beta) * ((GenReal) padre1.genes[i]).getValor() + ( 1 - beta) * ((GenReal) padre2.genes[i]).getValor()  );
			hijo2Valor = 0.5 * ( (1-beta) * ((GenReal) padre1.genes[i]).getValor() + ( 1 + beta) * ((GenReal) padre2.genes[i]).getValor()  );

			((GenReal) hijo1.genes[i]).setValor(hijo1Valor);
			((GenReal) hijo2.genes[i]).setValor(hijo2Valor);
		}	

		// se evaluan
		hijo1.setFitness(hijo1.evaluaCromosoma());
		hijo2.setFitness(hijo2.evaluaCromosoma());
	}

}




