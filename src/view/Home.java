package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.FIltroDAO;
import dto.FiltroDTO;
import pub_sub.Subscriber;

public class Home extends JFrame {

	private JPanel contentPane;
	FIltroDAO filtroDAO;
	Subscriber sub = new Subscriber();
	

	/**
	 * Create the frame.
	 */
	public Home() {
		//inicia subscribe para receber filtros
		sub.subscribe("null", "null", "null", "null");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
//////////////////////////campos para Selecionar Filtro/////////////////////
		JLabel lbMunicipio = new JLabel("Filtro:");
		lbMunicipio.setBounds(126, 25, 322, 15);
		contentPane.add(lbMunicipio);

		JComboBox jcMunicipio = new JComboBox();
		jcMunicipio.addItem("Sem Filtro");
		try {
			filtroDAO = new FIltroDAO();
			
			for(FiltroDTO f: filtroDAO.listaFiltro()) {
			jcMunicipio.addItem(f.toString());
		}
		} catch (Exception e) {
			e.printStackTrace();
		}

		jcMunicipio.setBounds(126, 45, 230, 19);
		contentPane.add(jcMunicipio);
//////////////////////////fim campos para Selecionar Filtro/////////////////////		
		
//////////////////////////////////Botão Cadastrar Filtro/////////////////////////////		
		//Configurações do botão para chamar a tela de mensagem
		JButton button = new JButton("Cadastrar Filtro");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chama tela de cadastrar topicos
				CadastrarFiltro frame = new CadastrarFiltro();
				frame.setVisible(true);
				//fecha esta tela para deixar só a de cadastrar topicos aberta
				dispose();
			}
		});
		button.setBounds(126, 86, 230, 25);
		contentPane.add(button);		
		
//////////////////////////////////Fim Botão Cadastrar Filtro/////////////////////////////		
		
//////////////////////////////////Botão Filtrar/////////////////////////////		
//Configurações do botão para chamar a tela de mensagem
		JButton buttonFiltrar = new JButton("Filtrar");
		buttonFiltrar.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {

				if(jcMunicipio.getSelectedItem().equals("Sem Filtro")) {
//					sub = null;
//					sub = new Subscriber();
					sub.subscribe("null", "null", "null", "null");										
				}else {
					try {
						FiltroDTO filtroSelecionadoDTO = new FiltroDTO();
						
						String filtro = (String) jcMunicipio.getSelectedItem();
						filtroSelecionadoDTO = filtroDAO.dadosFiltro(filtro);
//						sub = null;
//						sub = new Subscriber();					
						sub.subscribe(filtroSelecionadoDTO.getTipo_residencia(), 
								filtroSelecionadoDTO.getMunicipio(),
								filtroSelecionadoDTO.getUf(),
								filtroSelecionadoDTO.getValor_minimo());
						
						System.out.println(filtroSelecionadoDTO.getNome_filtro());
						
					}catch(Exception error) {
						System.out.println("Erro"+ error);
					}
				}
			
			}
		});

		buttonFiltrar.setBounds(126, 138, 230, 25);
		contentPane.add(buttonFiltrar);		

//////////////////////////////////Fim Botão Filtrar/////////////////////////////		
		
	}

	
		
	
}
