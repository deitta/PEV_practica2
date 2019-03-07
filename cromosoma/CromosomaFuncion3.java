package cromosoma;

import base.Cromosoma;

public class CromosomaFuncion3 extends Cromosoma{
	final double minX = -10, maxX = 10;
	boolean maximizar = false;

	public CromosomaFuncion3(double tolerancia, int nGenes) {
		super(tolerancia, 3, nGenes);
		inicializaGenes(0, maxX, minX);
		inicializaGenes(1, maxX, minX);
		super.lcrom = getLongitud();
	}
	
	public double evaluaCromosoma() {
		double x1 = genes[0].fenotipo();
		double x2 = genes[1].fenotipo();
		return f3(x1, x2);
	}

	private double f3(double x1, double x2) {
		double sumatorio1 = 0.0;
		double sumatorio2 = 0.0;
		for (int i = 1; i <= 5; i++){
			sumatorio1 += i*Math.cos((i+1)*x1 + i);
		}
		for (int i = 1; i <= 5; i++){
			sumatorio2 += i*Math.cos((i+1)*x2 + i);
		}
		return sumatorio1*sumatorio2;
	}

	public boolean isMaximizar() {
		return maximizar;
	}
}
