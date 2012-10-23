package io;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingletonDB implements EstrategiaDB {
	private EstrategiaDB e;
	private static SingletonDB db;
	private SingletonDB() {
		e=new EstrategiaSqlite();
		e.conectar();
		e.iniciarSentencia();
	}
	public static SingletonDB getInstance() {
		if (db==null) db = new SingletonDB();
		return db;
	}
	public void agregarSentenciaProcedure() {
		e.agregarSentenciaProcedure();
	}
	public void cerrar() {
		e.cerrar();
	}
	public void cerrarQuery(ResultSet rs) {
		e.cerrarQuery(rs);
	}
	public void conectar() {
		e.conectar();
	}
	public void executeProcedure() {
		e.executeProcedure();
	}
	public ResultSet executeQuery(String s) {
		return e.executeQuery(s);
	}
	public void executeUpdate(String s) {
		e.executeUpdate(s);
	}
	public void setFieldProc(int pos, Object o) {	
		e.setFieldProc(pos, o);
	}
	public boolean hayMasFilas(ResultSet rs) {
		return e.hayMasFilas(rs);
	}
	public void iniciarProcedure(String s) {
		e.iniciarProcedure(s);
	}
	public void iniciarSentencia() {
		e.iniciarSentencia();
	}
	public void setAutoCommit(boolean b) {
		e.setAutoCommit(b);
	}
	public static void main(String[] args) {
		SingletonDB base = SingletonDB.getInstance();
		base.executeUpdate("create table people (name, occupation);");
		base.iniciarProcedure("insert into people values (\"facundo\", \"investigador\");");
		base.executeProcedure();
		ResultSet rs= base.executeQuery("select * from people;");
		while (base.hayMasFilas(rs)) {
			try {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("ocupation"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		base.cerrarQuery(rs);
		base.cerrar();
	}
	
}
