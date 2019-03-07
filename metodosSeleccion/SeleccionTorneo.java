package metodosSeleccion;

import base.Cromosoma;
import cromosoma.FactoriaCromosoma;

public class SeleccionTorneo implements AlgoritmoSeleccion {
	int nParticipantes;

	public SeleccionTorneo(int nParticipantes) {
		this.nParticipantes = nParticipantes;
	}

	public void seleccion(Cromosoma[] pob, Cromosoma[] nuevaPob, int tamPob) {
		int participante; //individuo seleccionado para participar el torneo
		int posMejor;
		int nGenes = pob[0].getnGenes();
		double tolerancia = pob[0].getTolerancia();
		int idFuncion = pob[0].getIdFuncion();
		Cromosoma mejor = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
		
		for(int i = 0; i < tamPob; i++){
			posMejor = 0;
			mejor = pob[((int) (Math.random() * tamPob))];
			for (int j = 1; j < nParticipantes; j++){
				participante = ((int) (Math.random() * tamPob)); // si el azar lo quiere puede haber varios participantes iguales
				if (pob[participante].getFitness() > pob[posMejor].getFitness()){
					posMejor = participante;
					mejor = pob[participante];
				}
			}
			nuevaPob[i] = mejor;
		}
	}
	
	

}
