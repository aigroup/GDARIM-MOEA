package io;
import java.sql.ResultSet;

interface EstrategiaDB {
	void conectar();
	void cerrar();
	void executeUpdate(String s);
	ResultSet executeQuery(String s);
	boolean hayMasFilas(ResultSet rs);
	void cerrarQuery(ResultSet rs);
	void iniciarProcedure(String s);
	void setFieldProc(int pos, Object o);
	void iniciarSentencia();
	void agregarSentenciaProcedure();
	void setAutoCommit(boolean b);
	void executeProcedure();
}
