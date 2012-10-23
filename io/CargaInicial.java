package io;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaInicial {
	public static void main(String[] args) {
		SingletonDB base = SingletonDB.getInstance();
		base.setAutoCommit(true);
//		base.executeUpdate("drop table if exists RelDocente;");
//		base.executeUpdate("drop table if exists RelMateria;");
//		base.executeUpdate("drop table if exists RelFacultad;");
//		base.executeUpdate("drop table if exists RelCarrera;");
//		base.executeUpdate("drop table if exists RelAula;");
		
//		base.executeUpdate("create table RelDocente (idDoc int not null, " +
//				"idMat int not null, idDia int not null, idTur int not null);");
//		base.executeUpdate("create table RelAula (idAula int not null, " + 
//							"capacidad int not null," + 
//							"tipoAula int not null);"); 		
//		base.executeUpdate("create table RelMateria (idMat int not null," +
//							"idTA int not null, " +
//							"sedepref int not null, idRec int not null,"+
//							 "cupoMax int not null, cupoProb int not null);"); 
//		base.executeUpdate("create table RelRecursos (idRec int not null, " +
//		"cantidad int not null);");

		
//Deprecadas: ya no se usan		
//La tabla RelFacultad no tiene sentido que tenga la relacion entre faultad y sede, eso esta establecido por materia. Yo no usaria esa relacion ya que no hay una sede para cada facultad.
//		base.executeUpdate("create table RelFacultad (idFac int not null, " +
//				"idSed int not null, idCar int not null);");
//Con Rel carrera pasa lo mismo.. no es una restriccion que la carrera pertenezca a un plan en particular.
//		base.executeUpdate("create table RelCarrera (idCar int not null, " +
//		"idPla int not null);");		
		base.setAutoCommit(true);
		base.cerrar();
	}
}
