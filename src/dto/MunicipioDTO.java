package dto;

public class MunicipioDTO {

	//atributos do BD da tabela Municipio
	int id;
	int codigo;
	String nome;
	String uf;
	
	/////////////////Getters and Setters////////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	@Override
	public String toString() {
		return nome;
	}
	

	
}
