package cromosoma;

import base.Cromosoma;

public abstract class CromosomaFuncion4 extends Cromosoma {
	final double maxX = Math.PI, minX = 0;
	boolean maximizar = false;
	
	
	public CromosomaFuncion4(double tolerancia, int idFuncion, int nGenes) {
		super(tolerancia, idFuncion, nGenes);
	}
	
	public double evaluaCromosoma() {
		double[] x = new double[super.getnGenes()];
		for (int i = 0; i < getnGenes(); i++)
			x[i] = genes[i].fenotipo();
		return f4(x);
	}
	
	private double f4(double x[]) {
		double sumatorio = 0.0;
		for(int i = 1; i <= super.getnGenes(); i++){
			double sen1 = Math.sin(x[i-1]);
			double sen2 = Math.sin((i + 1)*Math.pow(x[i-1], 2)/Math.PI);
			sumatorio += sen1*Math.pow(sen2, 20);
		}
		return -1*sumatorio;
	}
	
	public boolean isMaximizar() {
		return maximizar;
	}
}
