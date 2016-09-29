package br.edu.ifspcaraguatatuba.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import br.edu.ifspcaraguatatuba.socket_cliente.Socket_Cliente;

public class Cliente extends JFrame {

	private static final long serialVersionUID = 6294910249439867800L;
	private JPanel contentPane;
	
	private JLabel lblStatusServidor;

	public Cliente() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(335, 228, 89, 23);
		btnEnviar.setEnabled(false);
		contentPane.add(btnEnviar);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 113, 414, 104);
		textPane.setEnabled(false);
		textPane.setText("Faça a conexão com o servidor");
		contentPane.add(textPane);
		
		JLabel lblDigiteSuaMensagem = new JLabel("Digite sua mensagem:");
		lblDigiteSuaMensagem.setBounds(10, 94, 414, 14);
		contentPane.add(lblDigiteSuaMensagem);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() { // Cria conexão com o servidor
			public void actionPerformed(ActionEvent arg0) {
				
				String msg = textPane.getText();
				
				Socket_Cliente cliente = new Socket_Cliente();
				try {
					cliente.connect(msg);
					
					lblStatusServidor.setText("Conectado");
					lblStatusServidor.setForeground(Color.GREEN);
					
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, e.getMessage());
				}
				
			}
		});
		btnConectar.setBounds(236, 228, 89, 23);
		contentPane.add(btnConectar);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStatus.setBounds(10, 11, 59, 14);
		contentPane.add(lblStatus);
		
		lblStatusServidor = new JLabel("Desconectado");
		lblStatusServidor.setForeground(Color.RED);
		lblStatusServidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStatusServidor.setBounds(64, 11, 100, 14);
		contentPane.add(lblStatusServidor);
	}
}
