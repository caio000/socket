package br.edu.ifspcaraguatatuba.socket_cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Socket_Cliente {
	
	// ============================================== Atributos ================================================
	private String IP;
	private int port;
	private Socket cliente;
	
	// ============================================== Construtores =============================================
	public Socket_Cliente(String IP, int port) {
		this.IP = IP;
		this.port = port;
	}
	
	// ============================================== Métodos ==================================================
	
	/**
	 * Cria a conexão com o servidor
	 * @author Caio de Freitas
	 * @since 2016/09/30
	 * @return Retorna um boolean TRUE caso a conexão seja realizada com sucesso.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public boolean connect() throws UnknownHostException, IOException {
		
		cliente = new Socket(this.IP, this.port);
		System.out.println("O cliente se conectou ao servidor!");
		
		return cliente.isConnected();
	}
	
	/**
	 * Disconecta o cliente do servidor
	 * @author Caio de Freitas
	 * @since 2016/09/30
	 * @return Retorna um boolean TRUE caso a conexão seja encerrada com sucesso.
	 * @throws IOException
	 */
	public boolean disconnect () throws IOException {
		cliente.close();
		return true;
	}
	
	/**
	 * Envia mensagem ao servidor
	 * @param message mensagem digitada pelo usuário.
	 * @throws IOException
	 */
	public void sendMessage (String message) throws IOException {
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		saida.println(message);
	}
	
}
