package cromosoma;

import base.Cromosoma;

public class CromosomaFuncion1 extends Cromosoma{
	final double maxX1 = 12.1, maxX2 = 5.8, minX1 = -3.0, minX2 = 4.1;
	boolean maximizar = true;

	public CromosomaFuncion1(double tolerancia, int nGenes) {
		super(tolerancia, 1, nGenes);
		inicializaGenes(0, maxX1, minX1);
		inicializaGenes(1, maxX2, minX2);
		super.lcrom = getLongitud();
	}
	
	public double evaluaCromosoma() {
		double x1 = genes[0].fenotipo();
		double x2 = genes[1].fenotipo();
		return f1(x1, x2);
	}

	private double f1(double x1, double x2) {
		return 21.5 + x1*Math.sin(4*Math.PI*x1) + x2*Math.sin(20*Math.PI*x2);
	}

	public boolean isMaximizar() {
		return maximizar;
	}
}
