package view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.FIltroDAO;
import dao.MunicipioDAO;
import dao.TipoResidenciaDAO;
import dto.FiltroDTO;
import dto.MunicipioDTO;
import dto.TipoResidenciaDTO;
import pub_sub.Subscriber;

public class CadastrarFiltro extends JFrame {

	private JPanel contentPane;
	private JTextField textValorMinimo, textNomeFiltro;
	private JComboBox jcTipoResidencia, jcMunicipio, jcUf;
	// objeto para executar sql de insert no bd
	MunicipioDAO municipiosDAO = new MunicipioDAO();
	// cria objeto do tipo TopicoDTO com o nome do topico passado
	MunicipioDTO municipiosDTO;
	// objeto para executar sql de insert no bd
	TipoResidenciaDAO tipoResidenciaDAO = new TipoResidenciaDAO();

	/**
	 * Create the frame.
	 */
	public CadastrarFiltro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

//////////////////////////Campos para Tipo Residencia/////////////////////		
		JLabel lbTipoResidencia = new JLabel("Tipo Residência:");
		lbTipoResidencia.setBounds(12, 15, 322, 15);
		contentPane.add(lbTipoResidencia);

		jcTipoResidencia = new JComboBox();
		jcTipoResidencia.addItem("null");
		try {
			for (TipoResidenciaDTO m : tipoResidenciaDAO.listaTiposResidencia()) {
				jcTipoResidencia.addItem(m.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		jcTipoResidencia.setBounds(12, 40, 426, 19);
		contentPane.add(jcTipoResidencia);
//////////////////////////fim campos para Residencia/////////////////////		

//////////////////////////campos para Municipio/////////////////////				
		JLabel lbMunicipio = new JLabel("Município:");
		lbMunicipio.setBounds(12, 65, 322, 15);
		contentPane.add(lbMunicipio);

		jcMunicipio = new JComboBox();
		jcMunicipio.addItem("null");
		try {
			for (MunicipioDTO m : municipiosDAO.listaMunicipios()) {
				jcMunicipio.addItem(m.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		jcMunicipio.setBounds(12, 85, 426, 19);
		contentPane.add(jcMunicipio);
//////////////////////////fim campos para Municipio/////////////////////			

//////////////////////////campos para UF/////////////////////				
		JLabel lbUf = new JLabel("UF:");
		lbUf.setBounds(12, 110, 322, 15);
		contentPane.add(lbUf);

		jcUf = new JComboBox();
		jcUf.addItem("null");
		try {
			for (MunicipioDTO m : municipiosDAO.listaUF()) {
				jcUf.addItem(m.getUf());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		jcUf.setBounds(12, 130, 426, 19);
		contentPane.add(jcUf);
//////////////////////////fim campos para UF/////////////////////

//////////////////////////campos para Valor Minimo Residência/////////////////////						
		JLabel lblValorMinimo = new JLabel("Valor mínimo:");
		lblValorMinimo.setBounds(12, 150, 322, 15);
		contentPane.add(lblValorMinimo);

		textValorMinimo = new JTextField("0");
		textValorMinimo.setBounds(12, 170, 426, 19);
		contentPane.add(textValorMinimo);
//		textValorMinimo.setColumns(10);
//////////////////////////fim campos para Valor Minimo Residência/////////////////////									        

//////////////////////////campos para nome do filtro/////////////////////						
		JLabel lblNomeFiltro = new JLabel("Nome Filtro");
		lblNomeFiltro.setBounds(12, 190, 322, 15);
		contentPane.add(lblNomeFiltro);

		textNomeFiltro = new JTextField();
		textNomeFiltro.setBounds(12, 210, 426, 19);
		contentPane.add(textNomeFiltro);
//////////////////////////fim campos para Valor Minimo Residência/////////////////////			

//////////////////////////botão Enviar Mensagem/////////////////////								        		
		JButton btnEnviarMensagem = new JButton("Cadastrar");

		btnEnviarMensagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// chamada para validar campos para aceitarem apenas número
				if (ValidarCampoNumerico(textValorMinimo)) {
					try {
						String tipo_residencia = (String) jcTipoResidencia.getSelectedItem();
						String municipio = (String) jcMunicipio.getSelectedItem();
						String valor_minimo = textValorMinimo.getText();
						String nome_filtro = textNomeFiltro.getText();
						String uf = (String) jcUf.getSelectedItem();

						FiltroDTO filtroDTO = new FiltroDTO(nome_filtro, municipio, 
								tipo_residencia, uf, valor_minimo);

						FIltroDAO filtroDAO = new FIltroDAO();
						filtroDAO.insert(filtroDTO);
					} catch (Exception e) {
						System.out.println("Erro:" + e);
					} finally {
						Home frame = new Home();
						frame.setVisible(true);
						dispose();
					}
				}

			}
		});

		btnEnviarMensagem.setBounds(242, 270, 170, 25);
		contentPane.add(btnEnviarMensagem);
//////////////////////////fim botão Enviar Mensagem/////////////////////								        			

//////////////////////////botão Cancelar/////////////////////			

		JButton btnCancelar = new JButton("Cancelar");

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home frame = new Home();
				frame.setVisible(true);
				dispose();
			}
		});

		btnCancelar.setBounds(53, 270, 114, 25);
		contentPane.add(btnCancelar);
//////////////////////////fim botão Cancelar/////////////////////					
	}

	// Método para validar JTextField como número
	public boolean ValidarCampoNumerico(JTextField TextoCampo) {
		Double valor;
		if (TextoCampo.getText().length() != 0) {
			try {
				valor = Double.parseDouble(TextoCampo.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Digite um valor válido!", "Erro de valor",
						JOptionPane.INFORMATION_MESSAGE);
				TextoCampo.grabFocus();
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Digite um número", "Erro de valor", JOptionPane.INFORMATION_MESSAGE);
			TextoCampo.grabFocus();
			return false;
		}
		return true;
	}

}
