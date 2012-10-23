package formularios;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MenuPpal extends Frame
{
	private static final long serialVersionUID = 1L;
	MenuItem abrir=new MenuItem("Abrir");
	MenuItem cerrar=new MenuItem("Cerrar");
	MenuItem salvar=new MenuItem("Guardar");
	MenuItem salir=new MenuItem("Salir");

	MenuItem conversor=new MenuItem("Conversor");
	MenuItem materia=new MenuItem("Agregar materia");
	MenuItem recursos=new MenuItem("Agrgar recursos");
	MenuItem facultades=new MenuItem("Facultades");
	MenuItem docentes=new MenuItem("Docentes");
	MenuItem instalaciones=new MenuItem("Instalaciones");
	MenuItem resultados=new MenuItem("Resultados");

	MenuItem buscar=new MenuItem("Buscar");
	MenuItem sustituir=new MenuItem("Sustituir");

	MenuItem ayuda=new MenuItem("Índice");


	Menu file= new Menu("File");
	Menu forms= new Menu("Formularios");
	Menu help= new Menu("Help");

	MenuBar barra = new MenuBar();

	Label resultado=new Label("Nada clickeado");

	public MenuPpal()
	{

		setupSucesos();
		file.add(cerrar);
		file.addSeparator();
		file.add("Menú Salir");
		file.addSeparator();
		file.add(salir);

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

		barra.add(file);
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
	//	abrir.addActionListener(new OyenteMenú());
	//	cerrar.addActionListener(new OyenteMenú());
	//	salir.addActionListener(new OyenteMenú());
	
		conversor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					JFrame form=new ConversorDom();
					form.setVisible(true);					
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

//			ayuda.addActionListener(new OyenteMenú());


	}


	public static void main(String[] arg)
	{

		new MenuPpal();

	}

	/*class OyenteMenú implements ActionListener
	{

		public void actionPerformed (ActionEvent e)
		{

			resultado.setText("Buscaste "+e.getActionCommand());
			if (e.getActionCommand().compareTo("Salir")==0) System.exit(0);

		}

	}
	class VentanamenúWindowListener implements WindowListener
	{

		public void windowActivated(WindowEvent e)
		{
			System.out.println("Escuchado un Window Activated");
		}

		public void windowClosed(WindowEvent e)
		{
			System.out.println("Escuchado un Window Closed");
		}

		public void windowClosing(WindowEvent e)
		{
			System.out.println("Escuchado un Window Closing");
			System.exit(0);
		}

		public void windowDeactivated(WindowEvent e)
		{
			System.out.println("Escuchado un Window Deactivaded");
		}

		public void windowDeiconified(WindowEvent e)
		{
			System.out.println("Escuchado un Window Deiconified");
		}

		public void windowIconified(WindowEvent e)
		{
			System.out.println("Escuchado un Window Iconified");
		}

		public void windowOpened(WindowEvent e)
		{
			System.out.println("Escuchado un Window Opened");
		}

	}*/

}  //  @jve:decl-index=0:visual-constraint="10,10"

