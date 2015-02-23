package ClasesYMetodos;

public class Personas {

	
	String nombre = null;
	String apellido=null;
	String rut=null;
	String idprogramacion=null;
	boolean selected = false;

	public Personas(String nombre, String Apellido, String Rut, String Idprogramacion, boolean selected) {
		super();
		
		this.nombre = nombre;
		this.selected = selected;
		this.apellido=Apellido;
		this.rut = Rut;
		this.idprogramacion = Idprogramacion;
	}

		
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getIdprogramacion() {
		return idprogramacion;
	}

	public void setIdprogramacion(String idprogramacion) {
		this.idprogramacion = idprogramacion;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
