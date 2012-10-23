package io;

import java.sql.*;
import java.util.*;

import javax.swing.JComboBox;

import log.Log;
import log.Tipo_Log;
import logica.ag.Constantes;
import logica.ag.SingletonAdminDatos;
import moea.Individuo;
import moea.Poblacion;
import moea.Problema;
import moea.TFitness;

public class FacadeDatos {
	private static FacadeDatos fd;
	Connection conn=null;
	Statement stat=null;
	private FacadeDatos() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	    stat = conn.createStatement();
	    conn.setAutoCommit(false);
	    conn.setReadOnly(true);
	}
	public static FacadeDatos getInstance() {
		if (fd == null)
			try {
				fd = new FacadeDatos();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return fd;
	}
	public int mutarXRestriccion(Poblacion poblacion, Problema problema) {
		//la logica del método es barrer por restriccion.
		//leo 1 restriccion, barro a todo el individuo modificandolo para bajar el
		//  nivel de restriccion.
		int retorno=0;
		int indMutacion=0;
		String campo=null;
		TFitness tr=null;
		//lee las restricciones
		Iterator<TFitness> itRest = problema.dameIteratorFitness(); 
		Log l = Log.getInstance();
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
		while (itRest.hasNext()) {
		//lee una restriccion o funcion de fitness
			tr=itRest.next();
			indMutacion=problema.buscarIndiceXCampo(tr.getMuta());
			// valida que sea restriccion
			if (((TFitness)tr).getValorPenalizadoInt()==Constantes.PENAL_RESTRIC) {
				l.loguear(Tipo_Log.INFORMACION, "Se muta por Restriccion");
//				tr=null;
				//leo a los individuos de la poblacion
				for (Individuo i: poblacion.getPoblacion()) {
					//relacionValida(tr,, problema);
					for(int j=0;j<i.getGen().length;j++) { // por cada aula
						//verifico si un aula cumple con 1 restriccion
						if (!(cumpleRestriccion(tr,i.getXBin()[j], problema))) {
						//hago mutar a algun alelo del aula que intervenga en la
						//  restriccion
//HAY QUE CAMBIAR NEW BITSET() POR UN PARAMETRO AL AZAR DE ESE LUGAR!!!!
				//			(i.getGen()[j]).setAlelo(new BitSet(), problema.buscarIndiceXCampo(tr.getMuta()));
				//			(i.getGen()[j]).setAlelo(datos.getParamAzar(problema.buscarIndiceXCampo(tr.getMuta())), problema.buscarIndiceXCampo(tr.getMuta())));
							//(i.getGen()[j]).setAlelo(datos.getParamAzar(indMutacion, new BitSet()), indMutacion);
							(i.getGen()[j]).setAlelo(datos.getParamAzar(indMutacion, i.getGen()[j].getAlelo(indMutacion)), indMutacion);
							retorno++;
						}
//	   					for (int indCampo=0;indCampo<tr.getCampos().size();indCampo++) {
//	   						//obtengo el string del campo de la restriccion
//	   						campo=tr.getCampos().get(indCampo);
//	   						//busco el indice del campo dentro del gen
//	   						indAlelo=problema.buscarIndiceXCampo(campo);
//	   					}
//	   				//	if (cumpleRestriccion()) {
//	   						//el segundo indice es el campo clave del query
//	   						//i.getGen()[j][tr.getCampos().get(0)]=1;
//	   						(i.getGen()[j]).setAlelo(new BitSet(), 0);
//	   				//	}            
	   						              
	   				}
				//ver si cada docente cumple con la materia con un query
				//si no cumple, asignarle 1 al azar del query.
					i.decodificar();
				}
			}
		}	
		return retorno;
	}
	public boolean cumpleRestriccion(TFitness tf, double genAula[], Problema p) {
		boolean ret=false;
		try {
			ret=relacionValida(tf, genAula, p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	public boolean relacionValida(TFitness tf, double genAula[], Problema p) throws SQLException {
		ResultSet rs=null;
		boolean retorno=false;
		//Class.forName("org.sqlite.JDBC");
		String query=null;
	    //Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	    //Statement stat = conn.createStatement();
		stat = conn.createStatement();
	    query="select * from "+tf.getTablaRel()+" where ";
	    for(int a=0;a<tf.getCampos().size();a++) {
	    	query+=tf.getCampos().get(a)+"="+
	    	genAula[p.buscarIndiceXCampo(tf.getCampos().get(a))]+" and ";
	    }
	    query+="1 = 1;";
	    rs = stat.executeQuery(query);
	    retorno=rs.next();
//	    conn.close();
	    return retorno;
	}
	public void insertarMateria(ArrayList<Integer> lista) throws Exception {
		//Class.forName("org.sqlite.JDBC");
	    //Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	    //Statement stat = conn.createStatement();
		stat = conn.createStatement();
	    //PreparedStatement prep = conn.prepareStatement(
	    stat.executeUpdate(	"insert into RelMateria values ("+lista.get(0)+","+
	    	lista.get(1)+","+lista.get(2)+","+lista.get(3)+","+
	    	lista.get(4)+","+lista.get(5)+","+lista.get(6)+");");
	    //prep.addBatch();
	    //prep.executeBatch();
	    //conn.setAutoCommit(true);
	    conn.close();
	}
	public void agregarRelacion(String s, ArrayList<Integer> lista) {
		SingletonDB base = SingletonDB.getInstance();
		String cabeceraSelect, cabeceraUpdate, cabeceraInsert, cuerpo, setUpdate;
		cabeceraSelect = "Select * from "+s;
		cabeceraUpdate = "Update "+s;
		cabeceraInsert = "Insert into "+s+" values (";
		if (s.equals("RelMateria")) {
			cuerpo=" where ";
			if (lista.get(0)!=0) cuerpo+="idMat="+lista.get(0)+" and ";
			if (lista.get(1)!=0) cuerpo+="idTA="+lista.get(1)+" and ";;
			if (lista.get(2)!=0) cuerpo+="idRec="+lista.get(2)+" and ";
			if (lista.get(3)!=0) cuerpo+="idFac="+lista.get(3)+" and ";
			if (lista.get(4)!=0) cuerpo+="idPla="+lista.get(4)+" and ";
			if (lista.get(5)!=0) cuerpo+="cupoMax="+lista.get(5)+" and ";
			if (lista.get(6)!=0) cuerpo+="cupoProb="+lista.get(6)+" and ";
			cuerpo+="1 = 1;";
			ResultSet rs = base.executeQuery(cabeceraSelect+cuerpo);
			if (base.hayMasFilas(rs)) {
				base.iniciarProcedure(cabeceraUpdate+" set idMat="+lista.get(0)+
						", idTA="+lista.get(1)+", idRec="+lista.get(2)+
						", idFac="+lista.get(3)+", idPla="+lista.get(4)+
						", cupoMax="+lista.get(5)+", cupoProb="+lista.get(6)+
						cuerpo+";");
			}
			else {
				base.iniciarProcedure(cabeceraInsert+lista.get(0)+
						", "+lista.get(1)+", "+lista.get(2)+
						", "+lista.get(3)+", "+lista.get(4)+
						", "+lista.get(5)+", "+lista.get(6)+");");
			}
			base.cerrarQuery(rs);
		}
		if (s.equals("RelDocente")) {
			base.iniciarProcedure("insert into RelDocente values (?, ?, ?);");
			base.setFieldProc(1, lista.get(0));	//docente
			base.setFieldProc(2, lista.get(1));	//materia
			base.setFieldProc(3, lista.get(2));	//horario
		}
		if (s.equals("RelFacultad")) {
			base.iniciarProcedure("insert into RelFacultad values (?, ?, ?);");
			base.setFieldProc(1, lista.get(0));	//facultad
			base.setFieldProc(2, lista.get(1));	//sede
			base.setFieldProc(3, lista.get(2));	//carrera
		}
		if (s.equals("RelCarrera")) {
			base.iniciarProcedure("insert into RelCarrera values (?, ?);");
			base.setFieldProc(1, lista.get(0));	//carrera
			base.setFieldProc(2, lista.get(1));	//plan
		}
		base.agregarSentenciaProcedure();
		base.executeProcedure();
		base.setAutoCommit(true);
		base.cerrar();
	}
	public ResultSet seleccionar(String tabla, String campo, int valor) throws ClassNotFoundException, SQLException {
		//ResultSet rs=null;
		//Class.forName("org.sqlite.JDBC");
		String query=null;
	    //Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	    //Statement stat = conn.createStatement();
		stat = conn.createStatement();
	    query="select * from "+ tabla +" where " + campo + " = " + valor + ";";
	    return stat.executeQuery(query);
	}
//	private List<String> armarRel(ArrayList<String> lista) {
//		Iterator<String> it = lista.iterator();
//		List<String> listaRel = new ArrayList<String>();
//		while (it.hasNext()) {
//			listaRel.add((it.next()!=null)? "")
//		}
//		return new ArrayList();
//	}
////////////////////////////////////////////////////
//	Procedimientos necesarios para formularios
//////////////////////////////////////////////////	//
//		Dada la tabla y el string de insercion, inserta
		public void insertar(String campos, String tabla) throws Exception {
			Class.forName("org.sqlite.JDBC");
		    Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		    Statement stat = conn.createStatement();
		    stat.executeUpdate(	"insert into "+tabla+" values ("+campos+");");
		    conn.close();
		}	
		//Dada una tabla, devuelve  todas las diferentes descripciones 
		public void agregarCombo(String tabla,JComboBox jComboBox)throws Exception {
			Class.forName("org.sqlite.JDBC");
		    Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		    Statement stat = conn.createStatement();
		    ResultSet rs=stat.executeQuery("select descripcion from "+tabla+");");
		    while (rs.next()) {
		    	jComboBox.addItem(rs.getString(1));
		    }
		    conn.close();	    
		}
		
		//Dada una tabla y una descripción, devuleve el codigo int (ID)
		public int getCodigoInt(String descripcion,String tabla)throws Exception{
			int codigo;
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement stat = conn.createStatement();
			ResultSet rs=stat.executeQuery("select id from "+tabla+"where descripcion="+descripcion+"");
			//ojo! aca puede ser 2 el nro de columna, es decir rs.getInt(2): PROBAR!
			codigo = rs.getInt(1); 
			conn.close();
		  	return codigo;			
		}
		//Dada una tabla y un int, devuelve la descripcion
		public String getDescripcion(int codigoInt,String tabla)throws Exception{
				String descripcion = null;
				Class.forName("org.sqlite.JDBC");
				Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
				Statement stat = conn.createStatement();
				ResultSet rs=stat.executeQuery("select id from "+tabla+"where descripcion="+descripcion+"");
				//ojo! aca puede ser 2 el nro de columna, es decir rs.getString(2): PROBAR!
				descripcion = rs.getString(1); 
				conn.close();
			  	return descripcion;			
			}
}
	
	

