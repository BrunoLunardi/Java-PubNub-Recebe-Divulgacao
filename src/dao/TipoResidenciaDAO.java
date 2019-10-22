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
import dto.MunicipioDTO;
import dto.TipoResidenciaDTO;

public class TipoResidenciaDAO {

	//inserir topicos no BD
	public void insert(TipoResidenciaDTO tipoResidenciaDTO) throws Exception {
		
			// ativa conexão com BD
			Connection connection = ConexaoUtil.getInstance().getConnection();
			// código sql a ser executado
			// o ? será trocado, em tempo de execução, pelo valor a ser inserido no BD
			String sql = "INSERT INTO tipo_residencia (tipo_residencia) VALUES (?)";
			// realiza uma ponte entre o java e o BD
			PreparedStatement statement = connection.prepareStatement(sql);
			// faz a alteração do ? da variavel sql para o valor a ser passado para o insert
			// primeiro parâmetro indica qual o ponto de interrogação será alterado (1 é o
			// primeiro, 2 é o segundo...)
			// segundo parâmetro é o valor a ser inserido
			statement.setString(1, tipoResidenciaDTO.getTipoResidencia());
			// Executar o comando sql com os devidos valores
			statement.execute();
			// fechar conexao com bd
			statement.close();

	}
	
	//Recupera lista de tópicos no BD
    public List<TipoResidenciaDTO> listaTiposResidencia() throws ClassNotFoundException, SQLException{

		// ativa conexão com BD
		Connection connection = ConexaoUtil.getInstance().getConnection();
        
		PreparedStatement statement = null;
        ResultSet rs = null;

        List<TipoResidenciaDTO> listaTipoResidencia = new ArrayList<>();

        try {
        	
			String sql = "SELECT * FROM tipo_residencia ORDER BY tipo_residencia";
			// realiza uma ponte entre o java e o BD
			statement = connection.prepareStatement(sql);        	
        	
            //stmt = connection.prepareStatement("SELECT * FROM topicos");
            rs = statement.executeQuery();

            while (rs.next()) {

            	TipoResidenciaDTO tipoResidencia = new TipoResidenciaDTO();
            		
            	//recupera valores de acordo com as colunas do BD
            	tipoResidencia.setId(rs.getInt("Id"));
            	tipoResidencia.setTipoResidencia(rs.getString("tipo_residencia"));
            	//adiciona o municipio na lista de municipios
            	listaTipoResidencia.add(tipoResidencia);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MunicipioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        	statement.close();
        }

        return listaTipoResidencia;

    }		
	
}
