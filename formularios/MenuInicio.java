package formularios;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.Constantes;
import moea.Moea;
import moea.ProblemaGdarim;


public class MenuInicio extends JFrame
{
	private static final long serialVersionUID = 1L;
	MenuItem procesar=new MenuItem("Correr proceso de asignación");
	MenuItem cerrar=new MenuItem("Cerrar");
	//MenuItem salvar=new MenuItem("Guardar");
	MenuItem salir=new MenuItem("Salir");

	MenuItem conversor=new MenuItem("Conversor");
	MenuItem materia=new MenuItem("Agregar materia");
	MenuItem recursos=new MenuItem("Agrgar recursos");
	MenuItem facultades=new MenuItem("Facultades");
	MenuItem docentes=new MenuItem("Docentes");
	MenuItem instalaciones=new MenuItem("Instalaciones");
	MenuItem resultados=new MenuItem("Resultados");

	MenuItem ayuda=new MenuItem("Índice");

	Menu principal= new Menu("Principal");
	Menu forms= new Menu("Formularios");
	Menu help= new Menu("Help");

	MenuBar barra = new MenuBar();

	JLabel resultado=new JLabel("Proceso detenido");

	@SuppressWarnings("deprecation")
	public MenuInicio()
	{
		setupSucesos();
		principal.add(cerrar);
		principal.addSeparator();
		principal.add(procesar);
		principal.addSeparator();
		principal.add(salir);

		forms.add(conversor);
		forms.addSeparator();
		forms.add(materia);
		forms.add(recursos);
		forms.add(facultades);
		forms.add(docentes);
		forms.add(instalaciones);
		forms.addSeparator();
		forms.add(resultados);
		
		help.add(ayuda);

		barra.add(principal);
		barra.add(forms);
		barra.setHelpMenu(help);

		setMenuBar(barra);

		add(resultado);

		pack();
		show();
//		addWindowListener(new VentanamenúWindowListener());
	}	

	void setupSucesos()
	{
		//abrir.addActionListener(new OyenteMenú());
		cerrar.addActionListener(new OyenteMenú());
			
		conversor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					JFrame form=(JFrame) new ConversorDom();
					form.setVisible(true);		
					JOptionPane.showMessageDialog((Component) form,"La conversión ha sido éxitosa\nahora se puede correr el proceso nuevamente","Convesion finalizada", 1 );
					form.dispose();
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		materia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					JFrame form=new FormAgregarMateria();
					form.setVisible(true);					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		recursos.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {					
					JFrame form=new AgregarRecursos();
					form.setVisible(true);					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});

		facultades.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					JFrame form=new FormularioFacultades();
					form.setVisible(true);					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		docentes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					JFrame form=new FormDocenteNuevo();
					form.setVisible(true);					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		instalaciones.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					JFrame form=new FormInstalNuevo();
					form.setVisible(true);					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		resultados.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					JFrame form=new MostrarCorrida();
					form.setVisible(true);					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});

		procesar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {	
					String[] args=new String[0];
					/*AQUI INSERTAR EJECUCION DEL PROCESO
					 * JFrame form=new FormInstalNuevo();
					   form.setVisible(true);   */
					Log l = Log.getInstance();
			    	resultado=l.loguear(Tipo_Log.CONSOLA, "Inicio del proceso");
			    	add(resultado);
			    	l.loguear(Tipo_Log.INFORMACION, "Inicio del proceso");
			    	l.loguear(Tipo_Log.INFORMACION, "==================");
			    	l.loguear(Tipo_Log.INSTANCIA, "Moea");
			    	Moea m = new Moea(new ProblemaGdarim(), Constantes.LUNES);
			    	l.loguear(Tipo_Log.NOMBRE_METODO, "Configurar Moea");
					m.configurar(args); 
					l.loguear(Tipo_Log.NOMBRE_METODO, "Procesar Moea");
					m.procesarPoblacion();
					l.loguear(Tipo_Log.CONSOLA, "Proceso finalizado");
					l.cerrar();
					
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		salir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				System.exit(0);					
			}
		});

		
//			ayuda.addActionListener(new OyenteMenú());
	}
	
	


	public static void main(String[] arg)	{
		Frame miMenu = new MenuInicio();
		miMenu.setTitle("Menu inicio");
		miMenu.setSize( 600,500 );
	}

	class OyenteMenú implements ActionListener{
		public void actionPerformed (ActionEvent e){
			resultado.setText("Buscaste "+e.getActionCommand());
			if (e.getActionCommand().compareTo("Salir")==0) System.exit(0);
		}

	}
	
} 

