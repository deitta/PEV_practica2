package base;

public class GenReal extends Gen{
	double valor;
	
	public GenReal(double max, double min, double tolerancia){
		super(max, min, tolerancia);
		//valor =  min2 + (max2 - min2)* Math.random();
	}

	public double fenotipo() {
		return valor;
	}

	public void inicializaGen() {
		valor =  super.min + (super.max - super.min)* Math.random();
	}
	
	public void copiaGen(Gen gen){
		this.longitud = gen.longitud;
		this.max = gen.max;
		this.min = gen.min;
		this.valor = ((GenReal) gen).valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getValor() {
		return valor ;
	}
}
