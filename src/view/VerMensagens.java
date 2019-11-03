package view;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import dao.FIltroDAO;
import dto.FiltroDTO;

public class VerMensagens extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VerMensagens() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

//////////////////////////campos para mensagens recebidas/////////////////////								
		JLabel lblTexto = new JLabel("Mensagens Recebidas:");
		lblTexto.setBounds(12, 10, 322, 15);
		contentPane.add(lblTexto);
		
		String a = lerArquivo();
		
		System.out.println("sfa" + a);
		
		JTextArea area = new JTextArea();
		area.setText(a);

		JScrollPane jScrollPane = new JScrollPane(area); // jTextArea dentro do JScrollPane
		jScrollPane.setBounds(new Rectangle(12, 40, 420, 450)); // tamanho do jScrollPane
		jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // só mostra a barra vertical
		// se necessário
		jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // nunca mostra a barra de
		// rolagem horizontal
		area.setWrapStyleWord(true);
		area.setLineWrap(true); // quebra a linha
		// area.getText();

		contentPane.add(jScrollPane);
//////////////////////////fim campos para mensagens recebidas/////////////////////		

//////////////////////////botão Voltar/////////////////////			

		JButton btnVoltar = new JButton("Voltar");

		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home frame = new Home();
				frame.setVisible(true);
				dispose();
			}
		});

		btnVoltar.setBounds(175, 510, 114, 25);
		contentPane.add(btnVoltar);
//////////////////////////fim botão Voltar/////////////////////
		

	}

	public String lerArquivo() {
		String linha = "";
		String resultado = "";
		try {
			FileReader arq = new FileReader("arquivo.txt");
			BufferedReader lerArq = new BufferedReader(arq);

			// Lê primeira linha do arquivo
			linha = lerArq.readLine();
			resultado = linha + "\n";
			System.out.printf("%s\n", linha);
			// Lê arquivo até a última linha
			while (linha != null) {
				System.out.printf("%s\n", linha);
				linha = lerArq.readLine(); // lê da segunda até a última linha
				
				resultado += linha  + "\n";
			}

			// fecha arquivo
			arq.close();

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		
		return resultado;
	}

}
