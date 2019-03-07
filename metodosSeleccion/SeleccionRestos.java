package metodosSeleccion;

import base.Cromosoma;
import cromosoma.FactoriaCromosoma;

public class SeleccionRestos implements AlgoritmoSeleccion {

	public void seleccion(Cromosoma[] pob, Cromosoma[] nuevaPob, int tamPob) {
		int tamNuevaPob = 0, faltantes;		
		for (int i = 0; i < tamPob; i++){
			for (int j = 0; j < (int) (tamPob*pob[i].getPunt()); j++){
				nuevaPob[tamNuevaPob].copiaCromosoma(pob[i]);
				tamNuevaPob++;
			}
		}

		faltantes = tamPob-tamNuevaPob;

		if(faltantes > 0){
			int nGenes = pob[0].getnGenes();
			double tolerancia = pob[0].getTolerancia();
			int idFuncion = pob[0].getIdFuncion();
			Cromosoma[] pobFaltantes = new Cromosoma[tamPob];
			
			for (int i = 0; i < faltantes; i++)
				pobFaltantes[i] = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			
			AlgoritmoSeleccion algoSeleccion = FactoriaSeleccion.getAlgoritmoDeSeleccion("Ruleta", 0);
			algoSeleccion.seleccion(pob, pobFaltantes, tamPob);
			
			for (int i = 0; i < faltantes; i++){
				nuevaPob[tamNuevaPob].copiaCromosoma(pobFaltantes[i]);
				tamNuevaPob++;
			}
		}
	}
}
