package cromosoma;

import base.Cromosoma;

public class CromosomaFuncion2 extends Cromosoma{
	final double minX = -512, maxX = 512;
	boolean maximizar = false;

	public CromosomaFuncion2(double tolerancia, int nGenes) {
		super(tolerancia, 2, nGenes);
		inicializaGenes(0, maxX, minX);
		inicializaGenes(1, maxX, minX);
		super.lcrom = getLongitud();
	}
	
	public double evaluaCromosoma() {
		double x1 = genes[0].fenotipo();
		double x2 = genes[1].fenotipo();
		return f2(x1, x2);
	}

	private double f2(double x1, double x2) {
		return -(x2 + 47)* Math.sin(Math.sqrt(Math.abs(x2 + (x1 / 2) + 47))) - x1 * Math.sin(Math.sqrt(Math.abs(x1 - (x2 + 47))));
	}

	public boolean isMaximizar() {
		return maximizar;
	}
}
