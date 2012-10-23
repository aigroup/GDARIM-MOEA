package io;
import java.sql.*;

import log.Log;
import log.Tipo_Log;

public class EstrategiaSqlite implements EstrategiaDB{
	private String estado;
	private Connection conn;
	private Statement stat;
	private PreparedStatement prep;
	
	public void conectar() {
	   	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.CONSOLA, "Me conecto a la BD");
		try {
			Class.forName("org.sqlite.JDBC");
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			} catch (SQLException e) {
				l.loguear(Tipo_Log.EXCEPCION, "Error en conexion a la BD "+e.getErrorCode());
			}
			estado="conectado";
		} catch (ClassNotFoundException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error, no se encontró la clase");
		}
	}
	public void cerrar() {
		Log l = Log.getInstance();
		try {
			conn.close();
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error en close() a la BD. "+e.getErrorCode());
		}
	}
	public void agregarSentenciaProcedure() {
		Log l = Log.getInstance();
		try {
			prep.addBatch();
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error al agregar sentencia. "+e.getErrorCode());
		}
	}
	public void setFieldProc(int pos, Object o) {
		Log l = Log.getInstance();
		try {
			prep.setObject(pos, o);
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error al setear string. "+e.getErrorCode());
		}		
	}
	public void executeProcedure() {
		Log l = Log.getInstance();	
		try {
			prep.executeBatch();
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error en la eecución del Procedure. "+e.getErrorCode());
		}
	}

	public ResultSet executeQuery(String s) {
		Log l = Log.getInstance();
		ResultSet rs = null;
		try {
			rs = stat.executeQuery(s);
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error al ejecutar Query. "+e.getErrorCode());
		}
		return rs;
	}
	public boolean hayMasFilas(ResultSet rs) {
		Log l = Log.getInstance();
		try {
			return rs.next();
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error en proximo elemento. "+e.getErrorCode());
		}
		return false;
	}
	public void cerrarQuery(ResultSet rs) {
		Log l = Log.getInstance();	
		try {
			rs.close();
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error al cerrar cursor. "+e.getErrorCode());
		}
	}
	public void executeUpdate(String s) {
		Log l = Log.getInstance();	
		try {
			stat.executeUpdate(s);
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error al ejecutar comando. "+e.getErrorCode());
		}
	}
	public void iniciarProcedure(String s) {
		Log l = Log.getInstance();		
		try {
			prep = conn.prepareStatement(s);
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error al preparar sentencia. "+e.getErrorCode());
		}
	}
	public void setAutoCommit(boolean b) {
		Log l = Log.getInstance();		
		try {
			conn.setAutoCommit(b);
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error en AutoCommit. "+e.getErrorCode());
		}

	}
	public void iniciarSentencia() {
		Log l = Log.getInstance();
		try {
			stat = conn.createStatement();
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error al crear sentencia. "+e.getErrorCode());
		}
	}
}
