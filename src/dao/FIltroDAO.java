package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import configs.ConexaoUtil;
import dto.FiltroDTO;

public class FIltroDAO {

	//inserir topicos no BD
	public void insert(FiltroDTO filtroDTO) throws Exception {
		
			// ativa conexão com BD
			Connection connection = ConexaoUtil.getInstance().getConnection();
			// código sql a ser executado
			// o ? será trocado, em tempo de execução, pelo valor a ser inserido no BD
			String sql = "INSERT INTO filtro (nome_filtro, municipio, tipo_residencia, "
					+ "valor_minimo, uf) VALUES (?, ?, ?, ?, ?)";
			// realiza uma ponte entre o java e o BD
			PreparedStatement statement = connection.prepareStatement(sql);
			// faz a alteração do ? da variavel sql para o valor a ser passado para o insert
			// primeiro parâmetro indica qual o ponto de interrogação será alterado (1 é o
			// primeiro, 2 é o segundo...)
			// segundo parâmetro é o valor a ser inserido
			statement.setString(1, filtroDTO.getNome_filtro());
			statement.setString(2, filtroDTO.getMunicipio());
			statement.setString(3, filtroDTO.getTipo_residencia());
			statement.setString(4, filtroDTO.getValor_minimo());
			statement.setString(5, filtroDTO.getUf());
			// Executar o comando sql com os devidos valores
			statement.execute();
			// fechar conexao com bd
			statement.close();

	}
	
	//Recupera lista de tópicos no BD
    public List<FiltroDTO> listaFiltro() throws ClassNotFoundException, SQLException{

		// ativa conexão com BD
		Connection connection = ConexaoUtil.getInstance().getConnection();
        
		PreparedStatement statement = null;
        ResultSet rs = null;

        List<FiltroDTO> listaFiltro = new ArrayList<>();

        try {
        	
			String sql = "SELECT * FROM filtro ORDER BY nome_filtro";
			// realiza uma ponte entre o java e o BD
			statement = connection.prepareStatement(sql);        	
        	
            //stmt = connection.prepareStatement("SELECT * FROM topicos");
            rs = statement.executeQuery();

            while (rs.next()) {

            	FiltroDTO filtro = new FiltroDTO();
            		
            	//recupera valores de acordo com as colunas do BD
            	filtro.setId(rs.getInt("Id"));
            	filtro.setNome_filtro(rs.getString("nome_filtro"));
            	filtro.setMunicipio(rs.getString("municipio"));
            	filtro.setTipo_residencia(rs.getString("tipo_residencia"));
            	filtro.setValor_minimo(rs.getString("valor_minimo"));
            	filtro.setUf(rs.getString("uf"));
            	
            	//adiciona o municipio na lista de municipios
            	listaFiltro.add(filtro);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MunicipioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        	statement.close();
        }

        return listaFiltro;

    }	
    
///recupera dados do filtro selecioando
	//Recupera lista de tópicos no BD
    public FiltroDTO dadosFiltro(String nome_filtro) throws ClassNotFoundException, SQLException{

		// ativa conexão com BD
		Connection connection = ConexaoUtil.getInstance().getConnection();
        
		PreparedStatement statement = null;
        ResultSet rs = null;
        
    	FiltroDTO filtro = new FiltroDTO();
        
        try {
        	
			String sql = "SELECT * FROM filtro WHERE nome_filtro = (?) LIMIT 1";
			// realiza uma ponte entre o java e o BD
			statement = connection.prepareStatement(sql);      
			
			statement.setString(1, nome_filtro);
        	
            //stmt = connection.prepareStatement("SELECT * FROM topicos");
            rs = statement.executeQuery();
            
            while (rs.next()) {            		
            	//recupera valores de acordo com as colunas do BD
            	filtro.setId(rs.getInt("Id"));
            	filtro.setNome_filtro(rs.getString("nome_filtro"));
            	filtro.setMunicipio(rs.getString("municipio"));
            	filtro.setTipo_residencia(rs.getString("tipo_residencia"));
            	filtro.setValor_minimo(rs.getString("valor_minimo"));
            	filtro.setUf(rs.getString("uf"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MunicipioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        	statement.close();
        }

        return filtro;

    }	    
	
}
