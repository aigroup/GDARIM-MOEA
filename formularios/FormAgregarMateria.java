package formularios;

import io.FacadeDatos;
import io.SingletonDB;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.ConfigAG;
import logica.ag.Gen;
import logica.ag.GrayCode;
import logica.ag.OriDes;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FormAgregarMateria extends JFrame{
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabelIdMateria = null;
	private JLabel jLabelIdDescripcionMateria = null;
	private JTextField jTextFieldIdDescripcionMateria = null;
	private JPanel jPanel18 = null;
	private java.util.Set recursosNecesarios=new java.util.TreeSet();  //  @jve:decl-index=0:
	private JLabel jLabelCupoMax = null;
	private JTextField jTextFieldCupoMax = null;
	private JLabel jLabelCupoProb = null;
	private JTextField jTextFieldCupoProb = null;
	private JPanel jPanel211 = null;
	private JLabel jLabelSedePref = null;
	private JComboBox jComboBoxRecurso = null;
	private JComboBox jComboBoxSede = null;
	private JPanel jPanel3 = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private int materiaId;
	private JPanel jPanelTipoAula = null;
	private JLabel jlbl_TipoAula = null;
	private JPanel jPanelAgregarRecursos = null;
	private JLabel jLabelAgregarRecurso = null;
	private JComboBox jComboBoxRecurso11 = null;
	private JButton jButtonAgregarRecurso = null;
	private JButton jButtonQuitarRecurso = null;
	private JPanel jPanelRecursos = null;
	private JLabel jLabelRecursos = null;
	private JScrollPane jScrollPane = null;
	private JTextArea taRecursos = null;
	private JRadioButton[] tipoDeAulas;
	private JComboBox jComboBoxRecurso1 = null;
	int cupoMax,cupoProb,sedePref,recurso,ultimoCodigoMateria = 0;
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.setPreferredSize(new Dimension(1310, 1000));
			jContentPane.add(getJPanel());
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJPanel18(), null);
			jContentPane.add(getJPanel211(), null);
			jContentPane.add(getJPanelTipoAula(), null);
			jContentPane.add(getJPanelAgregarRecursos(), null);
			jContentPane.add(getJPanelRecursos(), null);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel1(), null);
		}
		return jContentPane;
	}

	private JPanel getJPanel() {
		materiaId=FileUtil.getUltimoCodigo(FileUtil.ARCHIVO_DOM_MATERIAS)+1;					
		if (jPanel == null) {
			jLabelIdMateria = new JLabel();
			jLabelIdMateria.setText("Id Materia:  "+materiaId);
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setPreferredSize(new Dimension(400, 30));
			jPanel.add(jLabelIdMateria, null);
			//jPanel.add(getJTextFieldIdMateria(), null);
		}
		return jPanel;
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
			jLabelIdDescripcionMateria = new JLabel();
			jLabelIdDescripcionMateria.setText("Descripcion Materia");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout());
			jPanel2.setPreferredSize(new Dimension(400, 30));
			jPanel2.add(jLabelIdDescripcionMateria, null);
			jPanel2.add(getJTextFieldIdDescripcionMateria(), null);
		}
		return jPanel2;
	}

	/*private JTextField getJTextFieldIdMateria() {
		if (jTextFieldIdMateria == null) {
			jTextFieldIdMateria = new JTextField();
			jTextFieldIdMateria.setColumns(8);
		}
		return jTextFieldIdMateria;
	}*/

	private JTextField getJTextFieldIdDescripcionMateria() {
		if (jTextFieldIdDescripcionMateria == null) {
			jTextFieldIdDescripcionMateria = new JTextField();
			jTextFieldIdDescripcionMateria.setColumns(20);
		}
		return jTextFieldIdDescripcionMateria;
	}

	private JPanel getJPanel18() {
		if (jPanel18 == null) {
			jLabelCupoProb = new JLabel();
			jLabelCupoProb.setText("Cupo Probable");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			jLabelCupoMax = new JLabel();
			jLabelCupoMax.setText("Cupo Máximo");
			jPanel18 = new JPanel();
			jPanel18.setLayout(new FlowLayout());
			jPanel18.setPreferredSize(new Dimension(400, 30));
			jPanel18.add(jLabelCupoMax, gridBagConstraints4);
			jPanel18.add(getJTextFieldCupoMax(), null);
			jPanel18.add(jLabelCupoProb, null);
			jPanel18.add(getJTextFieldCupoProb(), null);
		}
		return jPanel18;
	}

	private JTextField getJTextFieldCupoMax() {
		if (jTextFieldCupoMax == null) {
			jTextFieldCupoMax = new JTextField();
			jTextFieldCupoMax.setColumns(5);
		}
		return jTextFieldCupoMax;
	}

	private JTextField getJTextFieldCupoProb() {
		if (jTextFieldCupoProb == null) {
			jTextFieldCupoProb = new JTextField();
			jTextFieldCupoProb.setColumns(5);
		}
		return jTextFieldCupoProb;
	}

	private JPanel getJPanel211() {
		if (jPanel211 == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			jLabelSedePref = new JLabel("Sede Preferida");
			jPanel211 = new JPanel();
			jPanel211.setLayout(new FlowLayout());
			//jPanel211.setPreferredSize(new Dimension(400, 50));
			jPanel211.add(jLabelSedePref, gridBagConstraints3);
			jPanel211.add(getJComboBoxSede(), null);
		}
		return jPanel211;
	}
	
	private JComboBox getJComboBoxSede() {
		if (jComboBoxSede == null) {
			jComboBoxSede = new JComboBox();
			jComboBoxSede.setPreferredSize(new Dimension(150, 20));
			FileUtil.agregarCombo(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_SEDES,jComboBoxSede);
		}
		return jComboBoxSede;
	}
	public FormAgregarMateria() {
		super();
		initialize();
	}

	private void initialize() {
        this.setTitle("Formulario ingreso materias nuevas");
        this.setContentPane(getJContentPane());
        this.setSize(new Dimension(560, 536));			
	}
	
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new FlowLayout());
			jPanel3.setPreferredSize(new Dimension(400, 30));
			jPanel3.add(getJButtonOK(), null);
			jPanel3.add(getJButtonCancel(), null);
		}
		return jPanel3;
	}

	private JButton getJButtonOK() {
		//Log l = Log.getInstance();
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setPreferredSize(new Dimension(80, 20));
			jButtonOK.setText("Crear");
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//materiaInt=Integer.parseInt(getJTextFieldIdMateria().getText());
					cupoMax=Integer.parseInt(getJTextFieldCupoMax().getText());
					cupoProb=Integer.parseInt(getJTextFieldCupoProb().getText());
					sedePref=FileUtil.getCodigo((String)getJComboBoxSede().getSelectedItem(), FileUtil.ARCHIVO_DOM_SEDES);
					recurso=FileUtil.getCodigo((String)getJComboBoxRecurso().getSelectedItem(), FileUtil.ARCHIVO_DOM_MATERIAS);
					String campos= "'"+materiaId+"','"+getJTextFieldIdDescripcionMateria().getText()+"','"+cupoMax+"','"+cupoProb+"','"+sedePref+"'";
					String info=
					//"materiaid: "+getJTextFieldIdMateria().getText()+
					" descmateria: "+getJTextFieldIdDescripcionMateria().getText()+
					" cupomax: "+getJTextFieldCupoMax().getText()+
					" cupo probable: "+getJTextFieldCupoProb().getText()+
					" sede preferida: "+FileUtil.getCodigo((String)getJComboBoxSede().getSelectedItem(), FileUtil.ARCHIVO_DOM_SEDES)+
					" recursos: "+getTaRecursos().getText();
					FileUtil.guardarDes(info,"materias.des");		
					FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_MATERIAS, materiaId+"\""+getJTextFieldIdDescripcionMateria().getText()+"\"", true);
					guardarInt(materiaId,cupoMax,cupoProb,sedePref,recurso);
					guardarGray(materiaId,cupoMax,cupoProb,sedePref,recurso);
					//Relaciones
					guardarRelaciones();
					//BD
					FacadeDatos fd = FacadeDatos.getInstance();
					try {
						fd.insertar(campos,"Materias");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
			});
		}
		return jButtonOK;
	}
	
	String geTipoAula(){		
		for (int k=0;k<tipoDeAulas.length;k++){
			if (tipoDeAulas[k].isSelected()){
					return tipoDeAulas[k].getText();
					}
		}
		return null ;
}
	private void guardarInt(int materiaInt,int cupoMax,int cupoProb,int sedePref,int recurso){
		String resultado="";
		resultado=materiaInt+"|"+cupoMax+"|"+cupoProb+"|"+sedePref+"|"+recurso;
		FacadeDatos fd = FacadeDatos.getInstance();
		ArrayList lista=new ArrayList();
	
	}
	
	
	private void guardarGray(int materiaInt,int cupoMax,int cupoProb,int sedePref,int recurso){
		String resultado="";		
		resultado=new GrayCode(materiaInt).getResult().toString()+"|"+
		new GrayCode(cupoMax).getResult().toString()+"|"+
		new GrayCode(cupoProb).getResult().toString()+"|"+
		new GrayCode(sedePref).getResult().toString()+"|"+
		new GrayCode(recurso).getResult().toString();
		//facundo
		FileUtil.guardarGray(resultado,"formMateria.gray");
	}
	
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setPreferredSize(new Dimension(85, 20));
			jButtonCancel.setText("Cancelar");
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButtonCancel;
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
	
	private JPanel getJPanelAgregarRecursos() {
		if (jPanelAgregarRecursos == null) {
			jLabelAgregarRecurso = new JLabel();
			jLabelAgregarRecurso.setText("Recurso requerido");
			jPanelAgregarRecursos = new JPanel();
			jPanelAgregarRecursos.setLayout(new FlowLayout());
			jPanelAgregarRecursos.setPreferredSize(new Dimension(500, 50));
			jPanelAgregarRecursos.add(jLabelAgregarRecurso, jLabelAgregarRecurso.getName());
			jPanelAgregarRecursos.add(getJComboBoxRecurso(), null);
			jPanelAgregarRecursos.add(getJButtonAgregarRecurso(), null);
			jPanelAgregarRecursos.add(getJButtonQuitarRecurso(), null);
		}
		return jPanelAgregarRecursos;
	}

	
	private JButton getJButtonAgregarRecurso() {
		if (jButtonAgregarRecurso == null) {
			jButtonAgregarRecurso = new JButton();
			jButtonAgregarRecurso.setText("Agregar");
			jButtonAgregarRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//cargo en al las matrias que tengo asignadas
					if (!recursosNecesarios.contains((String)(getJComboBoxRecurso().getSelectedItem()))) {
						taRecursos.append((String)getJComboBoxRecurso().getSelectedItem()+"\n");
						recursosNecesarios.add((String)getJComboBoxRecurso().getSelectedItem());
					}}});
		}
		return jButtonAgregarRecurso;
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

	private JComboBox getJComboBoxRecurso() {
		if (jComboBoxRecurso == null) {
			jComboBoxRecurso = new JComboBox();
			jComboBoxRecurso.setPreferredSize(new Dimension(150, 20));
			//comentar y descomentar esto para que aparezca el comboBox
			FileUtil.agregarCombo(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_RECURSOS,jComboBoxRecurso);
		}
		return jComboBoxRecurso;
	}
	
	
	private void guardarRelaciones(){
		//guardarRelMatTaula();
		guardarRelMateria();
		//guardarRelMatRec();
	}
	
	private void guardarRelMateria() {
		String recursos=taRecursos.getText();
		String recursosArray[];
		recursosArray = recursos.split("\n"); 
		FacadeDatos fd = FacadeDatos.getInstance();
		String campos;
		int recursoId;
		int tipoAulaId;
		tipoAulaId=FileUtil.getCodigo(geTipoAula(), FileUtil.ARCHIVO_DOM_TIPO_AULA);
		for (int i=0;i<recursosArray.length;i++){
			try {
				//reemplazar cuando se quiten la alimentacion por files
				//materiaid=fd.getCodigoInt(materiasArray[i], "Materias");
				recursoId=FileUtil.getCodigo(recursosArray[i], FileUtil.ARCHIVO_DOM_RECURSOS);
				campos="'"+materiaId+"','"+tipoAulaId+"','"+sedePref+"','"+recursoId+"','"+cupoMax+"','"+cupoProb+"'";
				fd.insertar(campos,"RelMateria");								
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}	
		

private void guardarRelMatRec() {
	String recurso;
	//System.out.println(taRecursos);
	String materias=taRecursos.getText();
	FacadeDatos fd = FacadeDatos.getInstance();
	String campos;
	int recursoId;
	StringTokenizer st = new StringTokenizer(materias,"\"");
	while (taRecursos!=null) {
		recurso = st.nextToken();
		try {
			//reemplazar cuando se quiten la alimentacion por files
			//recursoId=fd.getCodigoInt(recurso, "Recursos");
			recursoId=FileUtil.getCodigo(recurso, FileUtil.ARCHIVO_DOM_RECURSOS);
			campos="'"+materiaId+"','"+recursoId+"'";
			fd.insertar(campos,"RelMatDoc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}								
	}	
}
	
	public static void main(String[] args) {
		new FormAgregarMateria().setVisible(true);
	}
}
