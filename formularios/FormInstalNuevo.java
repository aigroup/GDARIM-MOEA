package formularios;
//recursos por aula
import io.FacadeDatos;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.ConfigAG;
import logica.ag.GrayCode;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Dimension;

public class FormInstalNuevo extends JFrame{
	private JPanel jContentPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabelAulaNro = null;
	private JLabel jLabelAulaDesc = null;
	private JTextField jTextFieldAulaNro = null;
	private JTextField jTextFieldAulaDesc = null;
	private JPanel jPanel3 = null;
	private JLabel jLabelCapacidad = null;
	private JTextField jTextFieldCapacidad = null;
	private JPanel jPanelTipoAula = null;
	private JLabel jlbl_TipoAula = null;
	private JPanel jPanelAgregarRecursos = null;
	private JLabel jLabelAgregarRecurso = null;
	private JComboBox jComboBoxRecurso1 = null;
	private JButton jButtonAgregarRecurso = null;
	private JPanel jPanelRecursos = null;
	private JLabel jLabelRecursos = null;
	private JScrollPane jScrollPane = null;
	private JTextArea taRecursos = null;
	private JPanel jPanelBotones = null;
	private JButton jButtonAceptar = null;
	private JButton jButtonCancel = null;
	private java.util.Set recursosNecesarios=new java.util.TreeSet();  //  @jve:decl-index=0:
	private JButton jButtonQuitarRecurso = null;
	private JRadioButton[] tipoDeAulas;
	private JPanel jPanelSede = null;
	private JLabel jLabelSedePref = null;
	private JComboBox jComboBoxSede = null;
	private int aulaId;

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJPanelSede(), null);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanelTipoAula(), null);
			jContentPane.add(getJPanelAgregarRecursos(), null);
			jContentPane.add(getJPanelRecursos(), null);
			jContentPane.add(getJPanelBotones(), null);
		}
		return jContentPane;
	}
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
		}
		return jPanel1;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabelAulaNro = new JLabel();
			aulaId=FileUtil.getUltimoCodigo(FileUtil.ARCHIVO_DOM_DOCENTES)+1;					
			jLabelAulaNro.setText("Aula Nro: "+aulaId);
			
			jLabelAulaDesc = new JLabel();
			jLabelAulaDesc.setText("descripcion");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout());
			jPanel2.setPreferredSize(new Dimension(400, 30));
			jPanel2.add(jLabelAulaNro, null);
			jPanel2.add(jLabelAulaDesc, null);
			jPanel2.add(getJTextFieldAulaDesc(), null);
		}
		return jPanel2;
	}

	private JTextField getJTextFieldAulaNro() {
		if (jTextFieldAulaNro == null) {
			jTextFieldAulaNro = new JTextField();
			jTextFieldAulaNro.setColumns(10);
		}
		return jTextFieldAulaNro;
	}
	private JTextField getJTextFieldAulaDesc() {
		if (jTextFieldAulaDesc == null) {
			jTextFieldAulaDesc = new JTextField();
			jTextFieldAulaDesc.setColumns(10);
		}
		return jTextFieldAulaDesc;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabelCapacidad = new JLabel();
			jLabelCapacidad.setText("Capacidad");
			jPanel3 = new JPanel();
			jPanel3.setLayout(new FlowLayout());
			jPanel3.setPreferredSize(new Dimension(200, 30));
			jPanel3.add(jLabelCapacidad, null);
			jPanel3.add(getJTextFieldCapacidad(), null);
		}
		return jPanel3;
	}

	private JTextField getJTextFieldCapacidad() {
		if (jTextFieldCapacidad == null) {
			jTextFieldCapacidad = new JTextField();
			jTextFieldCapacidad.setColumns(10);
		}
		return jTextFieldCapacidad;
	}

	
	private JPanel getJPanelTipoAula() {
		if (jPanelTipoAula == null) {
			jlbl_TipoAula = new JLabel("Tipo de Aula");
			jPanelTipoAula = new JPanel();
			jPanelTipoAula.setLayout(new FlowLayout());
			jPanelTipoAula.setPreferredSize(new Dimension(400, 30));
			jPanelTipoAula.add(jlbl_TipoAula, null);
			agregarTipoDeAula();			
		}
		return jPanelTipoAula;
	}
	private void agregarTipoDeAula(){
		int lineas=0;
		ButtonGroup bg = new ButtonGroup();
		try {
			lineas = FileUtil.getLineas(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_TIPO_AULA);
			//creo los botones
			tipoDeAulas=new JRadioButton[lineas];
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i=0;i<lineas;i++){
			try {				
				tipoDeAulas[i]=new JRadioButton(FileUtil.getLine(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_TIPO_AULA,i));
				jPanelTipoAula.add(tipoDeAulas[i]);
				bg.add(tipoDeAulas[i]);
				if (i==1){
					tipoDeAulas[i].setSelected(true);
				}				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public FormInstalNuevo() {
		super();
		initialize();
	}

	private void initialize() {
		Dimension dimension = new Dimension();
		dimension.height = 200;
		dimension.width = 300;
		this.setSize(555, 368);
		this.setJMenuBar(getJMenuBar());
//		this.setPreferredSize(dimension);
		this.setContentPane(getJContentPane());
		this.setTitle("Formulario Instalaciones");
	}
	
	private JPanel getJPanelAgregarRecursos() {
		if (jPanelAgregarRecursos == null) {
			jLabelAgregarRecurso = new JLabel();
			jLabelAgregarRecurso.setText("Recurso");
			jPanelAgregarRecursos = new JPanel();
			jPanelAgregarRecursos.setLayout(new FlowLayout());
			jPanelAgregarRecursos.setPreferredSize(new Dimension(500, 50));
			jPanelAgregarRecursos.add(jLabelAgregarRecurso, jLabelAgregarRecurso.getName());
			jPanelAgregarRecursos.add(getJComboBoxRecurso1(), null);
			jPanelAgregarRecursos.add(getJButtonAgregarRecurso(), null);
			jPanelAgregarRecursos.add(getJButtonQuitarRecurso(), null);
		}
		return jPanelAgregarRecursos;
	}
	private JComboBox getJComboBoxRecurso1() {
		if (jComboBoxRecurso1 == null) {
			jComboBoxRecurso1 = new JComboBox();
			jComboBoxRecurso1.setPreferredSize(new Dimension(150, 20));
			agregarRecursos(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_RECURSOS);
		}
		return jComboBoxRecurso1;
	}

	private JButton getJButtonAgregarRecurso() {
		if (jButtonAgregarRecurso == null) {
			jButtonAgregarRecurso = new JButton();
			jButtonAgregarRecurso.setText("Agregar");
			jButtonAgregarRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//cargo en al las matrias que tengo asignadas
					if (!recursosNecesarios.contains((String)(getJComboBoxRecurso1().getSelectedItem()))) {
						taRecursos.append((String)getJComboBoxRecurso1().getSelectedItem()+"\n");
						recursosNecesarios.add((String)getJComboBoxRecurso1().getSelectedItem());
					}}});
		}
		return jButtonAgregarRecurso;
	}

	 
	private JPanel getJPanelRecursos() {
		if (jPanelRecursos == null) {
			jLabelRecursos = new JLabel();
			jLabelRecursos.setText("Recursos necesarios");
			jPanelRecursos = new JPanel();
			jPanelRecursos.setLayout(new FlowLayout());
			jPanelRecursos.setPreferredSize(new Dimension(500, 100));
			jPanelRecursos.add(jLabelRecursos, null);
			jPanelRecursos.add(getJScrollPane(), null);
		}
		return jPanelRecursos;
	}
	private JTextArea getTaRecursos() {
		if (taRecursos == null) {
			taRecursos = new JTextArea(20, 30);
			taRecursos.setEditable(false);
		}
		return taRecursos;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(getTaRecursos());
			jScrollPane.setPreferredSize(new Dimension(333, 92));
		}
		return jScrollPane;
	}

	
	private JPanel getJPanelBotones() {
		if (jPanelBotones == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jPanelBotones = new JPanel();
			jPanelBotones.setLayout(new GridBagLayout());
			jPanelBotones.setPreferredSize(new Dimension(300, 30));
			jPanelBotones.add(getJButtonAceptar(), gridBagConstraints);
			jPanelBotones.add(getJButtonCancel(), gridBagConstraints1);
		}
		return jPanelBotones;
	}

	private JButton getJButtonAceptar() {
		if (jButtonAceptar == null) {
			jButtonAceptar = new JButton("Aceptar");
			jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String info=new String();	
					int sedeInt,capacidad,tipoAulaInt,recursosInt;
					
					sedeInt=FileUtil.getCodigo((String)getJComboBoxSede().getSelectedItem(), FileUtil.ARCHIVO_DOM_SEDES);
					capacidad=Integer.parseInt(getJTextFieldCapacidad().getText());
					tipoAulaInt=FileUtil.getCodigo(geTipoAula(),FileUtil.ARCHIVO_DOM_TIPO_AULA);
					recursosInt=FileUtil.getCodigo((String)getTaRecursos().getText(),FileUtil.ARCHIVO_DOM_RECURSOS);
					//relaciones
					guardarRelaciones();
					//BD
					/*FacadeDatos fd = FacadeDatos.getInstance();
					int sede=0;
					try {
						sede = fd.getCodigoInt((String)getJComboBoxSede().getSelectedItem(), "Sedes");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String campos= "'"+Integer.parseInt(getJTextFieldAulaNro().getText())+"','"+getJTextFieldAulaDesc().getText()+"','T','"+sede+"'";
					try {
						fd.insertar(campos,"AulasTurnos");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
					info=
						" aulaid: "+getJTextFieldAulaNro().getText()+
						" sede: "+FileUtil.getCodigo((String)getJComboBoxSede().getSelectedItem(), FileUtil.ARCHIVO_DOM_SEDES)+
						" capacidad: "+getJTextFieldCapacidad().getText()+
						" tipoaula: "+geTipoAula()+ 
						" recursos: "+getTaRecursos().getText();
							
					//valido los campos 
					if (!((getJTextFieldAulaNro().getText().isEmpty())&&
							(getJTextFieldAulaNro().getText().isEmpty())&&
							(getJTextFieldCapacidad().getText().isEmpty()))){
							FileUtil.guardarDes(info,FileUtil.ARCHIVO_FORM_INSTAL_DES);	
							guardarInt(aulaId,sedeInt, capacidad,tipoAulaInt);
							guardarGray(aulaId, sedeInt, capacidad, tipoAulaInt);
						}else{JOptionPane.showMessageDialog((Component) e.getSource(),"ingrese todos los datos requeridos","Advertencia", 1 );
						}					
					dispose();
				}
			});
		}
		
		return jButtonAceptar;
	}
	private void guardarRelaciones(){
		guardarRelAula();	
	}	
	
	private void guardarRelAula() {
		int tipoAulaId;
		String campos;
		FacadeDatos fd = FacadeDatos.getInstance();
		try {
			tipoAulaId=FileUtil.getCodigo(geTipoAula(), FileUtil.ARCHIVO_DOM_TIPO_AULA);
			campos="'"+aulaId+"','"+getJTextFieldCapacidad().getText()+"','"+tipoAulaId+"'";
					fd.insertar(campos,"RelAula");									
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	private void guardarInt(int aulaInt,int sedeInt,int capacidad,int tipoAulaInt){
		String resultado="";
		resultado=aulaInt+"|"+sedeInt+"|"+capacidad+"|"+tipoAulaInt;
		FileUtil.guardarInt(resultado,FileUtil.ARCHIVO_FORM_INSTAL_INT);
	}
	
	private void guardarGray(int aulaInt,int sedeInt,int capacidad,int tipoAulaInt){
		String resultado="";
		resultado=new GrayCode(aulaInt).getResult().toString()+"|"+
		new GrayCode(sedeInt).getResult().toString()+"|"+
		new GrayCode(capacidad).getResult().toString()+"|"+
		new GrayCode(tipoAulaInt).getResult().toString();
		FileUtil.guardarGray(resultado,FileUtil.ARCHIVO_FORM_INSTAL_GRAY);		
	}

	String geTipoAula(){
		for (int i=0;i<tipoDeAulas.length;i++)
							if (tipoDeAulas[i].isSelected()){
								return tipoDeAulas[i].getText();
							}
		return null ;
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
	private void agregarRecursos(String path,String file){
		String archivo=path+file;
		try{
		BufferedReader br=new BufferedReader(new FileReader(archivo));
		//para leer
		String linea=" ";
		while ((linea=br.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(linea,"|\" ");
			st.nextToken();
			jComboBoxRecurso1.addItem(st.nextToken());
			}
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	private JButton getJButtonQuitarRecurso() {
		if (jButtonQuitarRecurso == null) {
			jButtonQuitarRecurso = new JButton();
			jButtonQuitarRecurso.setText("Quitar");
			jButtonQuitarRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//remuevo materias
					taRecursos.setText("");
					recursosNecesarios.clear();					
				}});
			}			
		return jButtonQuitarRecurso;
	}
		
	private JPanel getJPanelSede() {
		if (jPanelSede == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			jLabelSedePref = new JLabel("Sede");
			jPanelSede = new JPanel();
			jPanelSede.setLayout(new FlowLayout());
			jPanelSede.add(jLabelSedePref, gridBagConstraints3);
			jPanelSede.add(getJComboBoxSede(), null);
		}
		return jPanelSede;
	}
	/**
	 * This method initializes jComboBoxSede	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxSede() {
		if (jComboBoxSede == null) {
			jComboBoxSede = new JComboBox();
			jComboBoxSede.setPreferredSize(new Dimension(150, 20));
			FileUtil.agregarCombo(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_SEDES,jComboBoxSede);
		}
		return jComboBoxSede;
	}
	public static void main(String[] args) {
		new FormInstalNuevo().setVisible(true);
	}
} 
