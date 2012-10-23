package logica.ag;
 
public interface PuedeProcesarFormularios {
	double getCapacidadMateria(GrayCode mat);
	double getCapacidadAula(GrayCode aula);
	double getCantidadRecursos(GrayCode rec);
	double getCapacidadMateriaIndice(int indice);
	double getCapacidadAulaIndice(int indice);
	double getCantidadRecursosIndice(int indice);
}
