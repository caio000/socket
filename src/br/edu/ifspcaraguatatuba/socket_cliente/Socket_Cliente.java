package br.edu.ifspcaraguatatuba.socket_cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Socket_Cliente {
	
	private Socket cliente;
	
	
	public boolean connect() throws UnknownHostException, IOException {
		
		cliente = new Socket("127.0.0.1", 12345);
		System.out.println("O cliente se conectou ao servidor!");
		
		return cliente.isConnected();
	}
	
	public void connect (String msg) throws UnknownHostException, IOException {

		Socket cliente = new Socket("127.0.0.1", 12345);

		

		PrintStream saida = new PrintStream(cliente.getOutputStream());

		saida.println(msg);

		saida.close();
		cliente.close();
	}
}
