package metodosCruce;

import base.Cromosoma;

public class CrucePMX implements AlgoritmoCruce {
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		int nGenes = padre1.getnGenes();
		int puntDC1 = (int) (Math.random()*nGenes), puntDC2 = (int) (Math.random()*nGenes), puntDCAux = puntDC1;
		if (puntDC1 > puntDC2){
			puntDC1 = puntDC2;
			puntDC2 = puntDCAux;
		}

		for (int i = puntDC1; i <= puntDC2; i++){
			hijo1.genes[i].setCiudad(padre2.genes[i].getCiudad());
			hijo2.genes[i].setCiudad(padre1.genes[i].getCiudad());
		}

		for (int i = (puntDC2 + 1) % nGenes; i >= puntDC2 || i < puntDC1; i = (i+1) % nGenes) {
			//para el hijo1
			int pos = funcionaux(padre1.genes[i].getCiudad(), puntDC1, puntDC2, hijo1);
			if(pos == -1)
				hijo1.genes[i].setCiudad(padre1.genes[i].getCiudad());
			else
				hijo1.genes[i].setCiudad(padre1.genes[pos].getCiudad());
			
			//para el hijo2
			pos = funcionaux(padre2.genes[i].getCiudad(), puntDC1, puntDC2, hijo2);
			if(pos == -1)
				hijo2.genes[i].setCiudad(padre2.genes[i].getCiudad());
			else
				hijo2.genes[i].setCiudad(padre2.genes[pos].getCiudad());

			//	hijo2.genes[i].setCiudad(padre2.genes[pos==-1?i:pos].getCiudad());
		}
	}

	private int funcionaux(int ciudad, int pos, int puntDC2, Cromosoma cromosoma){
		while(pos <= puntDC2 && ciudad != cromosoma.genes[pos].getCiudad())
			pos++;
		if (pos > puntDC2) return -1;
		return pos;
	}
}
