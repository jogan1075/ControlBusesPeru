package ClasesYMetodos;

public class Pasajeros {
	private String estadoCheckIn;
	private String nombreFuncionario;
	private String empresaFuncionario;
	private String cambiarReserva;

	public Pasajeros(String estadoCheckIn, String nombreFuncionario,
			String empresaFuncionario, String cambiarReserva) {
		super();
		this.estadoCheckIn = estadoCheckIn;
		this.nombreFuncionario = nombreFuncionario;
		this.empresaFuncionario = empresaFuncionario;
		this.cambiarReserva = cambiarReserva;
	}

	public String getEstadoCheckIn() {
		return estadoCheckIn;
	}

	public String getNombreFuncionario() {
		return nombreFuncionario;
	}

	public String getEmpresaFuncionario() {
		return empresaFuncionario;
	}

	public String getCambiarReserva() {
		return cambiarReserva;
	}

}
