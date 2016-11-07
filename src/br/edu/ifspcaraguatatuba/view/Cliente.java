package br.edu.ifspcaraguatatuba.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.ifspcaraguatatuba.socket_cliente.Socket_Cliente;
import javax.swing.JScrollPane;

public class Cliente extends JFrame {

	private static final long serialVersionUID = 6294910249439867800L;
	private JPanel contentPane;
	
	private Socket_Cliente client;
	
	private ServerSocket server;
	
	private JLabel lblStatusServidor;
	private JButton btnDesconectar;
	private JTextField textField;
	private JTextField txtPorta;
	private JTextField txtIp;
	private JTextField txtPort1;
	private JTextArea textArea;

	public Cliente() throws ParseException {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 514);
		setTitle("Cliente");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() { // =================================================================================> BOTï¿½O ENVIAR
			public void actionPerformed(ActionEvent e) {
				try {
					String message = textField.getText();
					String hist = textArea.getText();
					hist += InetAddress.getLocalHost().getHostName() + "-> " + message + "\n";
					textArea.setText(hist);
					client.sendMessage(message);
					
					textField.setText(null); // limpa a caixa de texto
					textField.setCaretPosition(0);
				} catch (Exception exception) {
					exception.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, exception.getMessage());
				}
			}
		});
		btnEnviar.setBounds(335, 297, 89, 23);
		btnEnviar.setEnabled(false);
		contentPane.add(btnEnviar);
		
		JLabel lblDigiteSuaMensagem = new JLabel("Digite sua mensagem:");
		lblDigiteSuaMensagem.setBounds(10, 241, 414, 14);
		contentPane.add(lblDigiteSuaMensagem);
		
		JButton btnConectar = new JButton("<html>Conectar em <br>outro usu\u00E1rio</html>");
		btnConectar.addActionListener(new ActionListener() { // ===============================================================================> Cria conexÃ£o com o servidor
			public void actionPerformed(ActionEvent arg0) {
				
				String IP = txtIp.getText(); 
				int port = Integer.parseInt(txtPorta.getText());
				
				try {
					client = new Socket_Cliente(IP, port);
					if ( client.connect() ) {
						
						server = new ServerSocket(++port);
						Runnable r = new Runnable() {
							
							@Override
							public void run() {
								
								try {
									
									String text = "+----------------------------------------------------------------+" + "\n"
													+ "|\t" + "Aguardando conexão" + "\t|\n" 
													+ "|\t" + "IP: " + InetAddress.getLocalHost().getHostAddress() + "\t|\n"
												+ "+----------------------------------------------------------------+";
				
									textArea.setText(text);
									
									Socket clientCon = server.accept();
									
									textArea.setText(null);
									text = "+----------------------------------------------------------------+" + "\n"
														+ "|\t" + "Conexão estabelecida" + "\t|\n" 
										 + "+----------------------------------------------------------------+";
									textArea.setText(text);
									
									Thread.sleep(1000);
									textArea.setText(null);
									
									
									Scanner read = new Scanner(clientCon.getInputStream());
									
									
									while (read.hasNextLine()) {
										String msg = textArea.getText();
										msg += clientCon.getInetAddress().getHostName() + "-> " + read.nextLine() + "\n";
										textArea.setText(msg);
									}
									
									read.close();
									server.close();
									
								} catch (IOException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
							}
						};
						
						Thread thread = new Thread(r);
						thread.setPriority(Thread.MAX_PRIORITY);
						thread.start();
					}
					
					lblStatusServidor.setForeground(Color.green);
					lblStatusServidor.setText("Conectado");
					btnEnviar.setEnabled(true);
					btnConectar.setVisible(false);
					btnDesconectar.setVisible(true);
					textField.setEnabled(true);
					txtPort1.setEnabled(false);
					
					txtIp.setEnabled(false);
					txtPorta.setEnabled(false);
					
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage());
					e.printStackTrace();
				}
				
			}
		});
		btnConectar.setBounds(304, 425, 120, 49);
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
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() { // ==============================================================> Botão de desconectar
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.disconnect();
					
					btnDesconectar.setVisible(false);
					btnConectar.setVisible(true);
					btnEnviar.setEnabled(false);
					
					lblStatusServidor.setForeground(Color.red);
					lblStatusServidor.setText("Desconectado");
					
					textField.setEnabled(false);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDesconectar.setBounds(304, 431, 120, 23);
		btnDesconectar.setVisible(false);
		contentPane.add(btnDesconectar);
		
		textField = new JTextField();
		textField.setBounds(10, 266, 414, 20);
		textField.setEnabled(false);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) // Verifica se a tecla ENTER foi pressionada
					btnEnviar.doClick();
			}
		});
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 414, 159);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		txtPorta = new JTextField();
		txtPorta.setBounds(174, 431, 89, 23);
		contentPane.add(txtPorta);
		txtPorta.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta");
		lblPorta.setBounds(174, 416, 46, 14);
		contentPane.add(lblPorta);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setBounds(10, 416, 46, 14);
		contentPane.add(lblIp);
		
		txtIp = new JTextField();
		txtIp.setBounds(10, 432, 139, 22);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 402, 414, 14);
		contentPane.add(separator);
		
		JButton btnCriarConexo = new JButton("Criar conex\u00E3o");
		btnCriarConexo.addActionListener(new ActionListener() { // =====================================================================> BOTï¿½O PARA CRIAR UMA CONEXï¿½O
			public void actionPerformed(ActionEvent arg0) {
				try {
					int port = Integer.parseInt(txtPort1.getText());
					server = new ServerSocket(port);
					
					Runnable r = new Runnable() {
						
						@Override
						public void run() {
							
							try {
								
								String text = "+----------------------------------------------------------------+" + "\n"
															+ "|\t" + "Aguardando conexão" + "\t|\n" 
										    + "|\t" + "IP: " + InetAddress.getLocalHost().getHostAddress() + "\t|\n"
										    + "+----------------------------------------------------------------+";
								
								textArea.setText(text);
								Socket clientCon = server.accept();
								
								textArea.setText(null);
								text = "+----------------------------------------------------------------+" + "\n"
													+ "|\t" + "Conexão estabelecida" + "\t|\n" 
									 + "+----------------------------------------------------------------+";
								textArea.setText(text);
								
								Thread.sleep(1000);
								textArea.setText(null);
								
								/*
								 * Depois que o servidor aceitar a conexão com cliente
								 * é criado um cliente para se conectar com o outro 
								 * servidor.
								 */
								Thread.sleep(2000); // O sistema dorme por 2 segundos para que possa ser criado o servidor do outro lado
								int porta = port + 1;
								client = new Socket_Cliente(clientCon.getInetAddress().getHostAddress(), porta);
								client.connect();
								
								Scanner read = new Scanner (clientCon.getInputStream());
								
								
								while (read.hasNextLine()) {
									String msg = textArea.getText();
									msg += clientCon.getInetAddress().getHostName() + "-> " + read.nextLine() + "\n";
									textArea.setText(msg);
								}
								
								read.close();
							}catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					};
					
					Thread thread = new Thread(r);
					thread.setPriority(Thread.MAX_PRIORITY);
					thread.start();
					
					lblStatusServidor.setText("Conectado");
					lblStatusServidor.setForeground(Color.green);
					
					btnCriarConexo.setEnabled(false);
					txtIp.setEnabled(false);
					txtPort1.setEnabled(false);
					txtPorta.setEnabled(false);
					btnConectar.setEnabled(false);
					
					textField.setEnabled(true);
					btnEnviar.setEnabled(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnCriarConexo.setBounds(304, 350, 120, 23);
		contentPane.add(btnCriarConexo);
		
		txtPort1 = new JTextField();
		txtPort1.setBounds(174, 351, 86, 20);
		contentPane.add(txtPort1);
		txtPort1.setColumns(10);
		
		JLabel lblPorta_1 = new JLabel("Porta");
		lblPorta_1.setBounds(174, 336, 46, 14);
		contentPane.add(lblPorta_1);
	}
}
