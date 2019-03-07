package metodosSeleccion;

import base.Cromosoma;

public class SeleccionUniversalEstocastica implements AlgoritmoSeleccion{

	public void seleccion(Cromosoma[] pob, Cromosoma[] nuevaPob, int tamPob) {
		int selSuper[] = new int[tamPob];
		int posSuper;
		double intervalo = 1/tamPob;
		double prob = Math.random()*intervalo;
		
		for (int i = 0; i < tamPob; i++){
			posSuper = 0;
			while ((prob > pob[posSuper].getPuntAcu()) && (posSuper < tamPob-1)) posSuper++;
			selSuper[i] = posSuper;
			prob += intervalo;
		}
		
		for(int i = 0; i < tamPob; i++) {
			nuevaPob[i] = pob[selSuper[i]];
		}
	}
	
}
