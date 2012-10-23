package formularios;
//recursos por materia 
import io.FacadeDatos;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import logica.ag.*;

import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

public class FormularioFacultades extends JFrame{  
	private JRadioButton[] facultades;
	private JPanel jContentPane = null;  
	private JPanel jPanelFacultad = null;
	private JLabel jlbl_facultad = null;
	private JPanel jPanel141 = null;
	private JLabel jLabelCarrera1 = null;
	private JComboBox jComboBoxCarrera1 = null;
	private JPanel jPanelMateria = null;
	private JLabel jLabelMateria = null;
	private JComboBox jComboBoxMateria = null;
	private JTextArea taRecursos = null;
	private JPanel jPanelBotones = null;
	private JButton jButtonAceptar = null;
	private JButton jButtonCancel = null;
	private java.util.Set recursosNecesarios=new java.util.TreeSet();
	private JComboBox jComboBoxFacultad = null;
	private JPanel jPanelSede = null;
	private JLabel jLabelSedePref = null;
	private JComboBox jComboBoxSede = null;

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new FlowLayout());
			//jContentPane.setSize(new Dimension(546, 342));
			jContentPane.add(getJPanelFacultad(), null);
			jContentPane.add(getJPanel141(), null);
			jContentPane.add(getJPanelMateria(), null);
			jContentPane.add(getJPanelBotones(), null);
		}
		return jContentPane;
	}

	private JPanel getJPanelFacultad() {
		if (jPanelFacultad == null) {
			jlbl_facultad = new JLabel();
			jlbl_facultad.setText("Facultad");
			jPanelFacultad = new JPanel();
			jPanelFacultad.setLayout(new FlowLayout());
			jPanelFacultad.setPreferredSize(new Dimension(350, 30));
			jPanelFacultad.add(jlbl_facultad, null);
			FileUtil.agregarCombo(ConfigAG.PATH_DOM, FileUtil.ARCHIVO_DOM_FACULTADES, getJComboBoxFacultad());
			//agregarFacultades();
			jPanelFacultad.add(getJComboBoxFacultad(), null);
		}
		return jPanelFacultad;
	}
	
	private JPanel getJPanel141() {
		if (jPanel141 == null) {
			jLabelCarrera1 = new JLabel();
			jLabelCarrera1.setText("Carrera");
			jPanel141 = new JPanel();
			jPanel141.setLayout(new FlowLayout());
			jPanel141.setPreferredSize(new Dimension(350, 30));
			jPanel141.add(jLabelCarrera1);
			jPanel141.add(getJComboBoxCarrera1(), null);
		}
		return jPanel141;
	}

	private JComboBox getJComboBoxCarrera1() {
		if (jComboBoxCarrera1 == null) {
			jComboBoxCarrera1 = new JComboBox();
			jComboBoxCarrera1.setPreferredSize(new Dimension(200, 20));
			agregarCarrera(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_CARRERAS);
		}
		return jComboBoxCarrera1;
	}

	private void agregarCarrera(String path,String file){
		String archivo=path+file;
		try{			
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			//para leer
			String linea=" ";
			while ((linea=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(linea,"\"");
				st.nextToken();
				jComboBoxCarrera1.addItem(st.nextToken());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JPanel getJPanelMateria() {
		if (jPanelMateria == null) {			
			jLabelMateria = new JLabel();
			jLabelMateria.setText("Materia");
			jPanelMateria = new JPanel();
			jPanelMateria.setLayout(new FlowLayout());
			jPanelMateria.setPreferredSize(new Dimension(450,30));
			jPanelMateria.add(jLabelMateria, null);
			jPanelMateria.add(getJComboBoxMateria());
		}
		return jPanelMateria;
	}

	private JComboBox getJComboBoxMateria() {
		if (jComboBoxMateria == null) {
			jComboBoxMateria = new JComboBox();
			jComboBoxMateria.setPreferredSize(new Dimension(300, 20));
			agregarMateria(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_MATERIAS);
		}
		return jComboBoxMateria;
	}

	private void agregarMateria(String path,String file){
		String archivo=path+file;
		try{
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			//para leer		
			String linea=" ";
			while ((linea=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(linea,"\"");
				st.nextToken();
				jComboBoxMateria.addItem(st.nextToken());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JTextArea getTaRecursos() {
		if (taRecursos == null) {
			taRecursos = new JTextArea(20, 30);
			
		}
		return taRecursos;
	}

	private JPanel getJPanelBotones() {
		if (jPanelBotones == null) {			
			jPanelBotones = new JPanel();
			jPanelBotones.setLayout(new GridBagLayout());
			jPanelBotones.setPreferredSize(new Dimension(300, 30));
			jPanelBotones.add(getJButtonAceptar());
			jPanelBotones.add(getJButtonCancel());
		}
		return jPanelBotones;
	}
	
	private JButton getJButtonAceptar() {
		if (jButtonAceptar == null) {
			jButtonAceptar = new JButton();
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String info="";
					String infoInt="";
					String facu=(String) jComboBoxFacultad.getSelectedItem();
					String materia=(String) jComboBoxCarrera1.getSelectedItem();
					String carrera=(String) getJComboBoxMateria().getSelectedItem();
					String sede=(String) getJComboBoxSede().getSelectedItem();
					
					int  facuInt,carreraInt,materiaInt,sedeInt;
					facuInt=FileUtil.getCodigo(facu,FileUtil.ARCHIVO_DOM_FACULTADES);
					carreraInt=FileUtil.getCodigo(carrera,FileUtil.ARCHIVO_DOM_CARRERAS);
					materiaInt=FileUtil.getCodigo(materia,FileUtil.ARCHIVO_DOM_MATERIAS);
					sedeInt=FileUtil.getCodigo(sede,FileUtil.ARCHIVO_DOM_SEDES);
					
					info="facultad: "+facu+
						" carrera: "+carrera+
						" materia: "+materia+
						" sede preferida: "+sede;
					
				//valido los campos 
					if (!((getJComboBoxSede().getSelectedItem().equals("N/A")))
						&&!((getJComboBoxFacultad().getSelectedItem().equals("N/A")))){
						FileUtil.guardarDes(info,"formFacultades.des");
						guardarInt(facuInt,carreraInt,materiaInt,sedeInt);
						guardarGray(facuInt,carreraInt,materiaInt,sedeInt);
//						BD
						FacadeDatos fd = FacadeDatos.getInstance();
						try {
							//fd.insertar(camposF,"Facultad");
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						dispose();
					}else{JOptionPane.showMessageDialog((Component) e.getSource(),"ingrese todos los datos requeridos","Advertencia", 1 );
					}					
					//
				}
				
				private void guardarInt(int facuInt,int carreraInt,int materiaInt,int sedeInt){
					String resultado="";
					//recursosInt=FileUtil.arrayParserInt(recursos);
					resultado=facuInt+"|"+carreraInt+"|"+materiaInt+"|"+sedeInt;
					FileUtil.guardarInt(resultado,"formFacultades.int");
					}
				
				private void guardarGray(int facuInt,int carreraInt,int materiaInt,int sedeInt){
					String resultado="";
					String recursosGray;
					resultado=new GrayCode(facuInt).getResult().toString()+"|"+
					new GrayCode(carreraInt).getResult().toString()+"|"+
					new GrayCode(materiaInt).getResult().toString()+"|"+ 
					new GrayCode(sedeInt).getResult().toString();
					FileUtil.guardarGray(resultado,"formRecursos.gray");
					}
			});
		}		

		return jButtonAceptar;
	}
		
	public  FormularioFacultades() {
		super();
		initialize();
	}	

	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setText("Cancelar");
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButtonCancel;
	}

	

	private void initialize() {
        this.setTitle("Formulario Facultades");
        this.setContentPane(getJContentPane());
        //this.setLayout(new FlowLayout());
        this.setSize(new Dimension(500, 250));			
	}

	/**
	 * This method initializes jComboBoxFacultad	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxFacultad() {
		if (jComboBoxFacultad == null) {
			jComboBoxFacultad = new JComboBox();
			jComboBoxFacultad.setPreferredSize(new Dimension(200, 20));
		}
		return jComboBoxFacultad;
	}

	
	private JComboBox getJComboBoxSede() {
		if (jComboBoxSede == null) {
			jComboBoxSede = new JComboBox();
			jComboBoxSede.setPreferredSize(new Dimension(250, 20));
			FileUtil.agregarCombo(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_SEDES,jComboBoxSede);
		}
		return jComboBoxSede;
	}

	public static void main(String[] args) {
		new FormularioFacultades().setVisible(true);
	}
}
