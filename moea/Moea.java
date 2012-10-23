package moea;

import java.io.*;

import log.Log;
import logica.ag.*;
//import logica.ag.Poblacion;

public class Moea {
	private Poblacion poblacion=new Poblacion();
	private Problema problema;
	private Estado etapa;
	private double semillaAzar;
	private TipoSemilla tipoSemilla;
	private static enum TipoSemilla { 
		AZAROSA,
	}
	public Moea(Problema p, int dia){
		problema = p;
		etapa = new EstadoInicial(dia);
	}
	public void configurarArgumentos(String[] args) throws ErrorDeArgumentosInvocacion{
		if (args.length>2)
			throw new ErrorDeArgumentosInvocacion();
		if (!evaluarArgumentos(args))
			throw new ErrorDeArgumentosInvocacion();
		evaluarArgumentos(args);
	}
	private boolean evaluarArgumentos(String[] args)throws ErrorDeArgumentosInvocacion{
		switch (args.length){
			case 0: //invocacion tipo 3
				tipoSemilla=TipoSemilla.AZAROSA;
				semillaAzar=Math.random();
				return true;
			case 1:try {
				semillaAzar=Double.parseDouble(args[0]);//invocacion tipo 1.
				} catch (NumberFormatException nfe){
					for (TipoSemilla ts: TipoSemilla.values()){
						if (args[0].equals(ts)) {//invocacion tipo 4.
							tipoSemilla=ts;
							semillaAzar=Math.random();
							return true;
						}
					}
				}
				return false;
			case 2:try { //invocacion tipo 2
				semillaAzar=Double.parseDouble(args[0]);
				} catch (NumberFormatException nfe){
					throw new ErrorDeArgumentosInvocacion();
				}
				for (TipoSemilla ts: TipoSemilla.values()){
					if (args[0].equals(ts)) {//invocacion tipo 4.
						tipoSemilla=ts;
						return true;
				}}
				return false;
			default: return false; 
		}
	}
	public void configurar(String[] args){
		try {
			configurarArgumentos(args);
			problema.configurarProblema();
			poblacion.crearPoblacion(problema);
			SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
			//AdmRelacion r = AdmRelacion.getInstance();
		} catch (ErrorDeArgumentosInvocacion e){
			System.err.println("Error al configurar parametros.\n"+e);
		}
	}
	public void procesarPoblacion(){
		etapa.procesarPoblacion(poblacion);
		etapa = new EstadoBusqueda();
		etapa.procesarPoblacion(poblacion);
	}
	public static void main(String[] args) throws ErrorDeArgumentosInvocacion{
		Moea m=new Moea(new ProblemaGdarim(), 1);
		m.configurar(args);
		m.procesarPoblacion();
	}
}
class ErrorDeArgumentosInvocacion extends Exception{
	ErrorDeArgumentosInvocacion(){
		usage();
	}
	private void usage(){
		System.out.println ("tiene 4 alternativas de invocacion:");
		System.out.println ("1.java moea semilla_azarosa (real entre (0, 1))");
		System.out.println ("2.java moea semilla_azarosa <archivo_params>");
		System.out.println ("3.java moea (esta opcion es equivalente a la 1)");
		System.out.println ("4.java moea <archivo_params>"); 
	}
}