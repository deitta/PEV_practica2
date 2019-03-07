package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

import base.AlgoritmoGenetico;
import interfaz.ConfigPanel.ChoiceOption;
import interfaz.ConfigPanel.ConfigListener;
import interfaz.ConfigPanel.DoubleOption;
import interfaz.ConfigPanel.IntegerOption;


public class Main extends JFrame {

	private static final long serialVersionUID = 5393378737313833016L;
	
	public Main() {
		super("Algoritmo genetico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel panelCentral = new JPanel(new BorderLayout());
		JPanel panelAuxiliar = new JPanel(new BorderLayout());
		add(panelCentral, BorderLayout.CENTER);
//		nononononono
		Plot2DPanel plot = new Plot2DPanel();
		plot.setAxisLabels("Generacion", "Fitness");
		panelCentral.add(plot, BorderLayout.CENTER);
		

		// crea Algoritmo Genetico
		AlgoritmoGenetico AG = new AlgoritmoGenetico();
		// crea un panel central y lo asocia con el AG
		final ConfigPanel<AlgoritmoGenetico> cp = creaPanelConfiguracion();
		cp.setTarget(AG); // asocia el panel con el AG
		cp.initialize(); // carga los valores del AG en el panel
		add(cp, BorderLayout.WEST);
		
		// crea una etiqueta que dice si todo es valido
		final String textoTodoValido = "Todos los campos OK";
		final String textoHayErrores = "Hay errores en algunos campos";
		final JLabel valido = new JLabel(textoTodoValido);
		// este evento se lanza cada vez que la validez cambia
		cp.addConfigListener(new ConfigListener() {
			@Override
			public void configChanged(boolean isConfigValid) {
				valido.setText(isConfigValid ? textoTodoValido: textoHayErrores);				
			}
		});
		add(valido, BorderLayout.SOUTH);

		final JLabel resultado = new JLabel("     Mejor resultado: ");
		final JLabel exiss = new JLabel("     Valores de las x:                  ");
		panelAuxiliar.add(resultado, BorderLayout.CENTER);
		panelAuxiliar.add(exiss, BorderLayout.EAST);
		
		JButton boton;
		// crea botones para mostrar el estado de las figuras por consola
		boton = new JButton("Calcula grafica");
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String xString = " ";
				plot.removeAllPlots();
				AG.AlgoritmoGeneticoFuncion();
				// define the legend position
				double[] x = new double[AG.getNumMaxGen()];
				for (int i = 0; i < x.length; i++){
					x[i] = i;
				}
				plot.addLegend("SOUTH");
				// add a line plot to the PlotPanel
				plot.addLinePlot("mejor de todas las generaciones", x, AG.getMejorAbsoluto());
				plot.addLinePlot("mejor de la generacion", x, AG.getGenMejor());
				plot.addLinePlot("media de la generacion", x, AG.getGenMedia());
				resultado.setText("     Mejor resultado: " + AG.getElMejor().getFitness());
				for (int i = 0; i < AG.getnGenes(); i++){
					xString += String.format("%.3f", AG.getElMejor().genes[i].fenotipo());
					if (i != AG.getnGenes() - 1) xString += "; ";
				}
				exiss.setText("     Valores de las x: " + xString);
			}
		});
		panelAuxiliar.add(boton, BorderLayout.WEST);

		panelCentral.add(panelAuxiliar, BorderLayout.SOUTH);
		panelCentral.add(plot);
	}
	
	public ConfigPanel<AlgoritmoGenetico> creaPanelConfiguracion() {
		String[] seleccion = new String[] { "Ruleta", "Estocastico", "Torneo", "Restos" };
		String[] cruceBinario = new String[] { "Monopunto", "Uniforme" };
		String[] cruceReal = new String[] { "Monopunto", "Uniforme", "Aritmetico", "SBX" };
		Integer[] funcion = new Integer[] { 1, 2, 3, 4, 5 };
		Double[] elitismo = new Double[] { 0.0, 0.01, 0.02, 0.03 };
		
		ConfigPanel<AlgoritmoGenetico> config = new ConfigPanel<AlgoritmoGenetico>();
		
		// se pueden añadir las opciones de forma independiente, o "de seguido"; el resultado es el mismo.
		config.addOption(new IntegerOption<AlgoritmoGenetico>(  // -- entero
				"Poblacion", // etiqueta del campo
				"tamaño de la poblacion",// 'tooltip' cuando pasas el puntero
				"tamPob", // campo (espera que haya un getGrosor y un setGrosor)
				1, 1000)) // min y max
			  .addOption(new IntegerOption<AlgoritmoGenetico>("Generaciones", "numero de generaciones", "numMaxGen", 1, 1000))
			  .addOption(new IntegerOption<AlgoritmoGenetico>("Participantes", "numero de participantes para el metodo de seleccion", "participantes", 0, 100))
			  .addOption(new IntegerOption<AlgoritmoGenetico>("N", "numero de x para la funcion 4", "nGenes", 1, 1000))
			  .addOption(new DoubleOption<AlgoritmoGenetico>("% Cruce", "porcentaje de cruce", "probCruce", 0, 1))
			  .addOption(new DoubleOption<AlgoritmoGenetico>("% Mutacion", "porcentaje de mutacion", "probMutacion", 0, 1))
			  .addOption(new DoubleOption<AlgoritmoGenetico>("Tolerancia", "nivel de tolerancia o de precision", "tolerancia", 0, 1))
			  .addOption(new ChoiceOption<AlgoritmoGenetico>("Cruce binario", "metodo de cruce para las funciones de 1 a 4", "cruceBinario", cruceBinario))
			  .addOption(new ChoiceOption<AlgoritmoGenetico>("Cruce real", "metodo de cruce para la funcion 5", "cruceReal", cruceReal))
			  .addOption(new ChoiceOption<AlgoritmoGenetico>("Seleccion", "metodo de seleccion", "seleccion", seleccion))  
			  .addOption(new ChoiceOption<AlgoritmoGenetico>("Funcion", "funcion a estudiar", "idFuncion", funcion))
			  .addOption(new ChoiceOption<AlgoritmoGenetico>("% Elitismo", "porcentaje de la poblacion que forma la elite", "elitismo", elitismo))

			  // y ahora ya cerramos el formulario
		  	  .endOptions();
		
		return config;
	}
	
	
	// construye y muestra la interfaz
	public static void main(String[] args) {
		Main p = new Main();
		p.setSize(1000, 600);
		p.setVisible(true);	
	}
}
