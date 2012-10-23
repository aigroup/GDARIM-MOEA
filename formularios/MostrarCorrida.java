package formularios;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

import logica.ag.ConfigAG;

 
public class MostrarCorrida extends JFrame{	
	private JPanel jContentPane = null;
	private JPanel jPanelTop = null;
	private JPanel jPanelBottom = null;
	private static JPanel[] jPanelData;
	private static JButton jButtonMas;
	private JButton jButtonOK ;
	private JButton jButtonCancel = null;
	private int cantFilas;
	private static String[] campos;
	private static int CANT_CAMPOS=4; //cantidad de campos a mostrar inicialmente.cantidad de columnas a mostrar
	private static int anchoPanel=20;
	private static int largoPanel=800;
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			Box miBox = Box.createVerticalBox();
			jContentPane.add(miBox);
			miBox.add(getJPanelTop());
			jPanelData =new JPanel[cantFilas];			
			try {
				leerArchivoOut(miBox);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			miBox.add(getJPanelBottom(), null);										
		}
		return jContentPane;
	}
		
	private JPanel getJPanelTop() {
		if (jPanelTop == null) {			
			jPanelTop = new JPanel();
			jPanelTop.setLayout(new GridLayout(1,CANT_CAMPOS));
			jPanelTop.setBackground(Color.BLUE);
			JLabel aula=new  JLabel("Aula");
			aula.setForeground(Color.white);
			JLabel profesor=new JLabel("Profesor");
			profesor.setForeground(Color.white);
			JLabel materia=new JLabel("Materia");
			materia.setForeground(Color.white);			
			JLabel horario=new JLabel("Horario");
			horario.setForeground(Color.white);
			JLabel masinfo=new JLabel("Más Información");
			masinfo.setForeground(Color.white);
			jPanelTop.add(aula);			
			jPanelTop.add(profesor);
			jPanelTop.add(materia);
			jPanelTop.add(horario);
			jPanelTop.add(masinfo);
		}
		return jPanelTop;
	}
//que me agregue paneles segun 
//la cantidad de asignaciones de horario para esa aula (cantAulas)
	
	public static void leerArchivoOut(Box miBox) throws FileNotFoundException{
		//String archivo=FileUtil.pathData+nomArch;
		int nroLinea=0;
//		Creo un objeto de tipo dimensión, un objeto que contiene un par de valores enteros
		Dimension d = new Dimension();
//		Inicializo ese par de valores enteros
		d.height = anchoPanel; d.width = largoPanel;
		String patron="[\\]\\[,]";
		BufferedReader br=new BufferedReader(new FileReader(FileUtil.ARCHIVO_OUT));
		String linea, str;
		try {
		//TODO: quitar nroLinea==0
			while (((linea=br.readLine())!=null) &&
					(nroLinea < jPanelData.length)){
				campos = linea.split(patron);
				jPanelData[nroLinea] = new JPanel();
				jPanelData[nroLinea].setPreferredSize(d);
				//le doy color
				jPanelData[nroLinea].setLayout(new GridLayout(1,CANT_CAMPOS));
				if (nroLinea%2==0){
						jPanelData[nroLinea].setBackground(Color.lightGray);
						}else{
							jPanelData[nroLinea].setBackground(Color.yellow.brighter().brighter());					
					}
				
				for (int i = 2; i < (campos.length); i++){
					if (perteneceAlForm(i-2)) {
						str=FileUtil.getDescripcion(Integer.parseInt(campos[i].trim()), i);
						jPanelData[nroLinea].add(new JLabel(str));		
					}
				}				
				jPanelData[nroLinea].add(getJButtonMas(nroLinea));
				miBox.add(jPanelData[nroLinea]);
				nroLinea++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static boolean nuevoIndiv(String l) {
		return l.contains("]");
	}
	private static boolean perteneceAlForm(int par) {
		if ((par==0) || (par==1) || (par==3) || (par==7))
			return true;
		else
			return false;
	}
	
	public MostrarCorrida() {
		super();
		try {
			cantFilas=FileUtil.getLineas(ConfigAG.PATH_GRAY,FileUtil.ARCHIVO_GRAY_AULAS);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();		
	}
	
	private void initialize() {
        this.setTitle("Resultado corrida");
        this.setContentPane(getJContentPane());
        this.setSize(new Dimension(900, 600));	
	}
	
	private JPanel getJPanelBottom() {		
		if (jPanelBottom == null) {
			jPanelBottom = new JPanel();
			jPanelBottom.setLayout(new FlowLayout());
			jPanelBottom.add(getJButtonOK());
			jPanelBottom.add(getJButtonCancel());
		}
		return jPanelBottom;
	}
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton("Siguiente individuo");
			jButtonOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//TODO: mostrar individuo	
					//ACCION!!
					dispose();
				}
			});
		}
		return jButtonOK;
	}
	private static JButton getJButtonMas(final int nroLinea) {
			jButtonMas = new JButton("Mas Información");
			jButtonMas.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						JOptionPane.showMessageDialog((Component) e.getSource(),getAllInfo(nroLinea)/*"Poner\nel texto\ncon la desciripcion del individuo!"*/,"Mas Información", 1 );
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
			});
		
		return jButtonMas;
	}
	private static String getAllInfo(int nroLineaButton){
		String patron="[\\]\\[,]";
		String[] etiqueta={"\nAula: ",
				"\n Docente: ",
				"\n Sede: ",
				"\nMateria: ",
				"\nFacultad: ",
				"\nCarrera: ",
				"\nTipo de aula: ",
				"\nHorario: ",
				"\nPlan: ",
				"\nDias: ",
				"\nEstado: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: ",
				"\nRecurso: "};
		String linea;
		int nroLinea=-1;
		String str = "";
		BufferedReader br1 = null;
		try {
			br1 = new BufferedReader(new FileReader(FileUtil.ARCHIVO_OUT));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			try {
				while (((linea=br1.readLine())!=null) &&
						(nroLinea < nroLineaButton)){
					campos = linea.split(patron);
					nroLinea++;
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		for (int i = 2; i < (campos.length); i++){
			str+=etiqueta[i-2]+FileUtil.getDescripcion(Integer.parseInt(campos[i].trim()), i)/*+"i es: "+i+" nrolinea es: "+nroLinea*/;			
		}
		return str;				
	}
	
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton("Cancelar");
			jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButtonCancel;
	}
	
	public static void main(String[] args) {
		new MostrarCorrida().setVisible(true);
	}
}
