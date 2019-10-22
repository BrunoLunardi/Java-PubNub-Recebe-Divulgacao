package dto;

public class FiltroDTO {
	
	int id;
	String nome_filtro, municipio, tipo_residencia, valor_minimo, uf;
	
	public FiltroDTO(String nome_filtro, String municipio, String tipo_residencia, 
			String uf, String valor_minimo) {
		this.nome_filtro = nome_filtro;
		this.municipio = municipio;
		this.tipo_residencia = tipo_residencia;
		this.valor_minimo = valor_minimo;
		this.uf = uf;
	}
	
	public FiltroDTO() {
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome_filtro() {
		return nome_filtro;
	}
	public void setNome_filtro(String nome_filtro) {
		this.nome_filtro = nome_filtro;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getTipo_residencia() {
		return tipo_residencia;
	}
	public void setTipo_residencia(String tipo_residencia) {
		this.tipo_residencia = tipo_residencia;
	}
	public String getValor_minimo() {
		return valor_minimo;
	}
	public void setValor_minimo(String valor_minimo) {
		this.valor_minimo = valor_minimo;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return nome_filtro;
	}
	
	

}
