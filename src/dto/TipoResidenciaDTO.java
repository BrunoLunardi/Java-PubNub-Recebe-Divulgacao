package dto;

public class TipoResidenciaDTO {
	
	//atributos do BD da tabela Municipio
	int id;
	String tipoResidencia;
	
	//construtor utilizado para insert
	public TipoResidenciaDTO(String tipoResidencia) {
		this.tipoResidencia = tipoResidencia;
		
	}
	
	public TipoResidenciaDTO() {
	}	
	
	/////////////////Getters and Setters////////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipoResidencia() {
		return tipoResidencia;
	}
	public void setTipoResidencia(String tipoResidencia) {
		this.tipoResidencia = tipoResidencia;
	}
	
	
	@Override
	public String toString() {
		return tipoResidencia;
	}
	
	

}
