package io;

import java.util.ArrayList;

public class TestDB {
	public static void main(String[] args) {
		FacadeDatos fd=FacadeDatos.getInstance();
		ArrayList lista=new ArrayList();
		lista.add(1);		//materia
		lista.add(2); 				//T_A
		lista.add(3);			//recurso
		lista.add(4);				//facultad
		lista.add(5);				//plan
		lista.add(6);			//cupo maximo
		lista.add(7);		//cupo probable
		//fd.agregarRelacion("RelMateria", lista);
		try {
			fd.insertarMateria(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
