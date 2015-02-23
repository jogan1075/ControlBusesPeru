package ClasesYMetodos;

public class ClaseLog {
private String rucNombre;
private String accion;
private String fecha;
private String hora;
public ClaseLog(String rucNombre, String accion, String fecha, String hora) {
	super();
	this.rucNombre = rucNombre;
	this.accion = accion;
	this.fecha = fecha;
	this.hora = hora;
}
public String getRucNombre() {
	return rucNombre;
}
public String getAccion() {
	return accion;
}
public String getFecha() {
	return fecha;
}
public String getHora() {
	return hora;
}

}
