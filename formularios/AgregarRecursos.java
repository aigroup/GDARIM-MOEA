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

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AgregarRecursos extends JFrame{
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabelIdRecurso = null;
	private JLabel jLabelIdDescripcionMateria = null;
	private JTextField jTextFieldIdDescripcionRecurso = null;
	private JPanel jPanel18 = null;
	private java.util.Set recursosNecesarios=new java.util.TreeSet();  //  @jve:decl-index=0:
	private JLabel jLabelCantidad = null;
	private JTextField jTextFieldCantidad = null;
	private JPanel jPanel3 = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;
	private int recursoInt;
	private JComboBox jComboBoxRecurso11 = null;
	private JTextArea taRecursos = null;
	private JComboBox jComboBoxRecurso1 = null;
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.setPreferredSize(new Dimension(1310, 1000));
			jContentPane.add(getJPanel());
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJPanel18(), null);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel1(), null);
		}
		return jContentPane;
	}

	private JPanel getJPanel() {
		recursoInt=FileUtil.getUltimoCodigo(FileUtil.ARCHIVO_DOM_RECURSOS)+1;					
		
		if (jPanel == null) {
			jLabelIdRecurso = new JLabel();
			jLabelIdRecurso.setText("Id Recurso:  "+recursoInt);
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setPreferredSize(new Dimension(400, 30));
			jPanel.add(jLabelIdRecurso, null);
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
			jLabelIdDescripcionMateria.setText("Descripcion recurso");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout());
			jPanel2.setPreferredSize(new Dimension(400, 30));
			jPanel2.add(jLabelIdDescripcionMateria, null);
			jPanel2.add(getJTextFieldIdDescripcionRecurso(), null);
		}
		return jPanel2;
	}

	private JTextField getJTextFieldIdDescripcionRecurso() {
		if (jTextFieldIdDescripcionRecurso == null) {
			jTextFieldIdDescripcionRecurso = new JTextField();
			jTextFieldIdDescripcionRecurso.setColumns(20);
		}
		return jTextFieldIdDescripcionRecurso;
	}

	private JPanel getJPanel18() {
		if (jPanel18 == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			jLabelCantidad = new JLabel();
			jLabelCantidad.setText("Cantidad disponible");
			jPanel18 = new JPanel();
			jPanel18.setLayout(new FlowLayout());
			jPanel18.setPreferredSize(new Dimension(400, 30));
			jPanel18.add(jLabelCantidad, gridBagConstraints4);
			jPanel18.add(getJTextFieldCantidad(), null);
		}
		return jPanel18;
	}

	private JTextField getJTextFieldCantidad() {
		if (jTextFieldCantidad == null) {
			jTextFieldCantidad = new JTextField();
			jTextFieldCantidad.setColumns(5);
		}
		return jTextFieldCantidad;
	}

	public AgregarRecursos() {
		super();
		initialize();
	}

	private void initialize() {
        this.setTitle("Formulario ingreso recursos nuevos");
        this.setContentPane(getJContentPane());
        this.setSize(new Dimension(560, 236));			
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
					String campos="'"+recursoInt+"','"+getJTextFieldIdDescripcionRecurso().getText()+"','"+getJTextFieldCantidad().getText()+"'";
					String info=
					" DescRecurso: "+getJTextFieldIdDescripcionRecurso().getText()+
					" cantidad: "+getJTextFieldCantidad().getText();
					FileUtil.guardarDes(info,"recursos.des");		
					FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_RECURSOS, recursoInt+"\""+getJTextFieldIdDescripcionRecurso().getText()+"\"", true);
					String cantidad= getJTextFieldCantidad().getText();
					guardarInt(recursoInt,Integer.parseInt(cantidad));
					guardarGray(recursoInt,Integer.parseInt(cantidad));
					guardarRelaciones();
					//en BD
					//guardarRecurso(recursoInt,getJTextFieldIdDescripcionRecurso().getText(),Integer.parseInt(cantidad));
					/*FacadeDatos fd = FacadeDatos.getInstance();
					try {
						fd.insertar(campos,"Recursos");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
					dispose();
				}
			});
		}		
		return jButtonOK;
	}
	private void guardarRelaciones(){
		//guardarRelMatDoc();
		guardarRelRecursos();
		//guardarRelHorDoc();
	}
	private void guardarRelRecursos() {
		FacadeDatos fd = FacadeDatos.getInstance();
		String campos="'"+recursoInt+"','"+getJTextFieldCantidad().getText()+"'";
		try {
			fd.insertar(campos,"RelRecursos");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private void guardarInt(int recursoInt,int cantidad){
		String resultado="";		
		resultado=recursoInt+"|"+cantidad;
		FileUtil.guardarInt(resultado,"formRecurso.int");
	}		
	
	//metodo nuevo!
	private void guardarGray(int recursoInt,int cantidad){
		String resultado="";		
		resultado=new GrayCode(recursoInt).getResult().toString()+"|"+
				  new GrayCode(cantidad).getResult().toString();
		//facundo
		FileUtil.guardarGray(resultado,"formRecurso.gray");
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

	
	public static void main(String[] args) {
		new AgregarRecursos().setVisible(true);
	}
}
