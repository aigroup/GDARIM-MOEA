package formularios;

import io.FacadeDatos;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.ConfigAG;
import logica.ag.Constantes;
import logica.ag.GrayCode;

//TODO: deberia retroalimentar 002_docentes.dom - archivoDocentes
public class FormDocenteNuevo extends JFrame {
//private static final int CANTIDAD_DIAS = 7;
	private int legajo;
	private JPanel jContentPane = null;
	private JLabel jlbl_nombre = null;
	private JTextField jtf_nombre = null;
	private JLabel jlbl_apellido = null;
	private JPanel jPanelDatosProfesor = null;
	private JTextField jtf_apellido = null;
	private JLabel jlbl_legajo = null;
	private JLabel jlbl_dni = null;
	private JTextField jtf_dni = null;
	private JPanel jPanelFacultad = null;
	private JLabel jlbl_facultad = null;
	private JPanel jPanelMaterias = null;
	private JLabel jLabel = null;
	private JComboBox jcb_materias_posibles = null;
	private JPanel jPanelMateriasAsignadas = null;
	private JButton jbtn_agregarMateria = null;
	private JLabel jlbl_materias_asignadas = null;
	private JButton jButton = null;
	private JPanel jPanelHorarios = null;
	private JPanel jPanelBotones = null;
	private JComboBox jComboBoxFacultad = null;
	private JButton jButtonAceptar = null;
	private JButton jButtonCancel = null;
	private JTextArea taMateriasAsignadas=new JTextArea(20,30);
	private java.util.Set materiasAsignadas=new java.util.TreeSet();  //  @jve:decl-index=0:
	private JScrollPane jScrollPane = null;
	private JPanel[] panelDia=new JPanel[Constantes.CANT_DIAS_SEMANA];
	private JCheckBox[] checkBoxDia=new JCheckBox[Constantes.CANT_DIAS_SEMANA];
	private JComboBox[] jComboBoxTurno =new JComboBox[Constantes.CANT_DIAS_SEMANA];
	private String[] dias={"Lunes","Martes","Miercoles"
			,"Jueves","Viernes","Sabado","Domingo"
		};
	
	public FormDocenteNuevo() {
		super();
		initialize();
	}
	
	private void initialize() {
		this.setSize(598, 682);
		this.setJMenuBar(getJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Formulario de carga de datos del docente");
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jlbl_apellido = new JLabel();
			jlbl_apellido.setText("Apellido");
			jlbl_nombre = new JLabel();
			jlbl_nombre.setText("Nombre");
			jContentPane = new JPanel();
			Box grillaCentral=Box.createVerticalBox();
			grillaCentral.add(getJPanelDatosProfesor());
			grillaCentral.add(getJPanelFacultad());
			grillaCentral.add(getJPanelMaterias());
			grillaCentral.add(getJPanelMateriasAsignadas());			
			grillaCentral.add(getJPanelBotones());
			grillaCentral.add(getJPanelBotones());			
			grillaCentral.add(getJPanelHorarios());
			jContentPane.setLayout(new FlowLayout());			
			jContentPane.add(grillaCentral, grillaCentral.getName());
		}
		return jContentPane;
	}

	private JTextField getJtf_nombre() {
		if (jtf_nombre == null) {
			jtf_nombre = new JTextField();
			jtf_nombre.setColumns(10);
		}
		return jtf_nombre;
	}

	private JPanel getJPanelDatosProfesor() {
		if (jPanelDatosProfesor == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			jlbl_dni = new JLabel();
			jlbl_dni.setText("DNI");
			jlbl_legajo = new JLabel();
			legajo=FileUtil.getUltimoCodigo(FileUtil.ARCHIVO_DOM_DOCENTES)+1;					
			jlbl_legajo.setText("Legajo : "+legajo);
			jPanelDatosProfesor = new JPanel();
			jPanelDatosProfesor.setLayout(new GridLayout(2,4, 10, 5));
			jPanelDatosProfesor.setPreferredSize(new Dimension(360, 50));
			jPanelDatosProfesor.setName("jPanel");
			jPanelDatosProfesor.add(jlbl_nombre);
			jPanelDatosProfesor.add(getJtf_nombre());
			jPanelDatosProfesor.add(jlbl_apellido);
			jPanelDatosProfesor.add(getJtf_apellido());
			jPanelDatosProfesor.add(jlbl_legajo);			
			jPanelDatosProfesor.add(jlbl_dni);
			jPanelDatosProfesor.add(getJtf_dni());
		}
		return jPanelDatosProfesor;
	}

	private JTextField getJtf_apellido() {
		if (jtf_apellido == null) {
			jtf_apellido = new JTextField();
			jtf_apellido.setColumns(10);
		}
		return jtf_apellido;
	}
	
	private JTextField getJtf_dni() {
		if (jtf_dni == null) {
			jtf_dni = new JTextField();
			jtf_dni.setColumns(10);
		}
		return jtf_dni;
	}
/////////////
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
	private JComboBox getJComboBoxFacultad() {
		if (jComboBoxFacultad == null) {
			jComboBoxFacultad = new JComboBox();
			jComboBoxFacultad.setPreferredSize(new Dimension(200, 20));
		}
		return jComboBoxFacultad;
	}		

	private JPanel getJPanelMaterias() {
		if (jPanelMaterias == null) {
			jLabel = new JLabel();
			jLabel.setText("Materias posibles");
			jPanelMaterias = new JPanel();
			jPanelMaterias.setLayout(new FlowLayout());
			jPanelMaterias.add(jLabel);
			jPanelMaterias.add(getJcb_materias_posibles());
			jPanelMaterias.add(getJbtn_agregarMateria());			
		}
		return jPanelMaterias;
	}
	private void agregarMaterias(String path,String file){
		String archivo=path+file;
		try{
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			//para leer
			String linea=" ";
			while ((linea=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(linea,"\"");
				//System.out.println("esta es la linea"+linea);
				st.nextToken();
				jcb_materias_posibles.addItem(st.nextToken());
				}
		}catch (IOException e) {
			e.printStackTrace();
			}
		}
	private JComboBox getJcb_materias_posibles() {
		if (jcb_materias_posibles == null) {
			jcb_materias_posibles = new JComboBox();
			jcb_materias_posibles.setPreferredSize(new Dimension(200, 20));
			agregarMaterias(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_MATERIAS);	
		}
		return jcb_materias_posibles;
	}

	private JPanel getJPanelMateriasAsignadas() {
		if (jPanelMateriasAsignadas == null) {
			jlbl_materias_asignadas = new JLabel("Materias asignadas");
			jPanelMateriasAsignadas = new JPanel();
			jPanelMateriasAsignadas.setLayout(new FlowLayout());
			jPanelMateriasAsignadas.add(jlbl_materias_asignadas);
			jPanelMateriasAsignadas.add(getJScrollPane(), getJScrollPane().getName());
			jPanelMateriasAsignadas.add(getJButton());
		}
		return jPanelMateriasAsignadas;
	}

	private JButton getJbtn_agregarMateria() {
		if (jbtn_agregarMateria == null) {
			jbtn_agregarMateria = new JButton();
			jbtn_agregarMateria.setText("Agregar");
			jbtn_agregarMateria.setPreferredSize(new Dimension(80, 20));
			taMateriasAsignadas.setEditable(false);
			jbtn_agregarMateria.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//cargo en al las matrias que tengo asignadas
					if (!materiasAsignadas.contains((String)(getJcb_materias_posibles().getSelectedItem()))) {
						taMateriasAsignadas.append((String)getJcb_materias_posibles().getSelectedItem()+"\n");
						materiasAsignadas.add((String)getJcb_materias_posibles().getSelectedItem());
					}				
			}});
		}
		return jbtn_agregarMateria;
	}

	
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Quitar");
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//remuevo materias
					taMateriasAsignadas.setText("");
					materiasAsignadas.clear();					
				}});
			}			
		return jButton;
	}
	
	
	private JPanel getJPanelHorarios() {
		if (jPanelHorarios == null) {
			jPanelHorarios = new JPanel();
			jPanelHorarios.setLayout(new GridLayout(7,5));
			Box grilla=Box.createVerticalBox();
			grilla.add(new JLabel("Horarios disponibles"));
			for (int i=0;i<7;i++)
				grilla.add(creoPaneles(i));
			jPanelHorarios.add(grilla);
			}
		return jPanelHorarios;
	}	

	private JPanel creoPaneles(final int i) {
		if (panelDia[i]== null) {
			panelDia[i] = new JPanel();
			panelDia[i].setLayout(new FlowLayout());
			checkBoxDia[i]=new JCheckBox(dias[i]);
			panelDia[i].add(checkBoxDia[i]);			
			agregarTurno(panelDia[i],i);			
			checkBoxDia[i].addActionListener(new ActionListener(){ 
				public void actionPerformed(ActionEvent e) {
					if (checkBoxDia[i].isSelected()){
						//ponemos elpanel en true			
						todoTrue(jComboBoxTurno[i]);
					}else {
						todoFalse(jComboBoxTurno[i]);
						}
					}});}
		return panelDia[i];
	}
	
	private void agregarTurno(JPanel panel, int i){
		jComboBoxTurno[i] = new JComboBox();
		FileUtil.agregarCombo(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_HORARIOS,jComboBoxTurno[i]);
		panel.add(jComboBoxTurno[i]);
		todoFalse(jComboBoxTurno[i]);			
		
		}
	private void todoFalse(JComboBox jComboBoxTurno){
				jComboBoxTurno.setEnabled(false);
		}
		private void todoTrue(JComboBox jComboBoxTurno){
				jComboBoxTurno.setEnabled(true);			
		}

	private JPanel getJPanelBotones() {
		if (jPanelBotones == null) {
			jPanelBotones = new JPanel();
			jPanelBotones.add(getJButtonAceptar());
			jPanelBotones.add(getJButtonCancel());
		}
		return jPanelBotones;
	}
	String getFacultad(){
			return (String)getJComboBoxFacultad().getSelectedItem();
	}
	private JButton getJButtonAceptar() {
		if (jButtonAceptar == null) {
			jButtonAceptar = new JButton("Aceptar");
			jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String horariosDia=getHorariosDias();
					String facu=getFacultad();
					String info;
					//String =(String) jComboBoxCarrera1.getSelectedItem();;
					//String carrera=(String) getFacultad();
					//String recursos=getTaRecursos().getText();
					int  facuInt,dniInt,materiasAsignInt;
					dniInt=Integer.parseInt(getJtf_dni().getText());
					facuInt=FileUtil.getCodigo(facu,FileUtil.ARCHIVO_DOM_FACULTADES);
					materiasAsignInt=FileUtil.getCodigo(taMateriasAsignadas.getText(),FileUtil.ARCHIVO_DOM_MATERIAS);
					
					String campos= "'"+legajo+"','"+getJtf_apellido().getText()+"','"+getJtf_nombre().getText()+"','"+getJtf_dni().getText()+"','"+getFacultad()+"'";
					
					info=" apellido: "+getJtf_apellido().getText()+
						"| nombre: "+getJtf_nombre().getText()+
						"| dni: "+getJtf_dni().getText()+
						"| legajo: "+legajo+
						//"| facultad: "+getFacultad(facult)+
						"| materiasasignadas: "+taMateriasAsignadas.getText();
					//valido los campos de texto
					if (!((getJtf_apellido().getText().isEmpty())&&
						(getJtf_nombre().getText().isEmpty())&&
						(getJtf_dni().getText().isEmpty()))){
						FileUtil.guardarDes(info, "docentes.des");
						FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_DOCENTES, legajo+"\""+getJtf_apellido().getText()+getJtf_nombre().getText()+"\"", true);
						//TODO: modficar para que arme el vector de materias y
						//TODO:   busque los id's int y los mande como ArrayList
						guardarInt(facuInt,dniInt,legajo,taMateriasAsignadas.getText());
						guardarGray(facuInt,dniInt,legajo,taMateriasAsignadas.getText());
						//relaciones
						guardarRelaciones();
						//BD
						/*FacadeDatos fd = FacadeDatos.getInstance();
						try {
							fd.insertar(campos,"Docentes");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
					}else{
						JOptionPane.showMessageDialog((Component) e.getSource(),"ingrese todos los datos requeridos","Advertencia", 1 );
						//System.out.println("ingrese todos los datos requeridos");
						}						
					dispose();
					}
			});
		}
		return jButtonAceptar;
	}
		
	private void guardarInt(int facuInt,int dniInt,
		int legajoInt, String materiasAsign){
			String resultado="";
			String materiasAsignInt=FileUtil.arrayParserInt(materiasAsign);
			resultado=facuInt+"|"+dniInt+"|"+legajoInt+"|{"+materiasAsignInt+"}";
			FileUtil.guardarInt(resultado,"formDocente.int");
			
			}
	private void guardarGray(int facuInt,int dniInt,
		int legajoInt, String materiasAsign){
			String resultado="";
			resultado=new GrayCode(facuInt).getResult().toString()+"|"+
			new GrayCode(dniInt).getResult().toString()+"|"+
			new GrayCode(legajoInt).getResult().toString()+"|{"+
			FileUtil.arrayParserGray(materiasAsign)+"}";
			FileUtil.guardarGray(resultado,"formDocente.gray");
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

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(taMateriasAsignadas);
			jScrollPane.setPreferredSize(new Dimension(333, 92));
		}
		return jScrollPane;
	}

	private void guardarRelaciones(){
		//guardarRelMatDoc();
		guardarRelDocente();
		//guardarRelHorDoc();
	}
	private void guardarRelDocente() {
		String materias=taMateriasAsignadas.getText();
		String materiasArray[];
		materiasArray = materias.split("\n"); 
		FacadeDatos fd = FacadeDatos.getInstance();
		String campos;
		int turnoId = 0;
		int materiaid;
		for (int i=0;i<materiasArray.length;i++){
			try {
				//reemplazar cuando se quiten la alimentacion por files
				//materiaid=fd.getCodigoInt(materiasArray[i], "Materias");
				materiaid=FileUtil.getCodigo(materiasArray[i], FileUtil.ARCHIVO_DOM_MATERIAS);
				for(int j=Constantes.LUNES;j<Constantes.CANT_DIAS_SEMANA;j++){
					if(checkBoxDia[j].isSelected()){
						turnoId=FileUtil.getCodigo((String)jComboBoxTurno[j].getSelectedItem(), FileUtil.ARCHIVO_DOM_HORARIOS);
						campos="'"+legajo+"','"+materiaid+"','"+j+"','"+turnoId+"'";
						System.out.println("insert into RelDocente values ("+campos+")");
						fd.insertar(campos,"RelDocente");					
					}
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
	
	public static void main(String[] args) {
		new FormDocenteNuevo().setVisible(true);		
	}
}  
