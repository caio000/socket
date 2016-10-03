package br.edu.ifspcaraguatatuba.socket_servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Socket_Servidor implements Runnable {

	private ServerSocket servidor;
	private Socket cliente;
	
	public void connect() throws IOException {
		servidor = new ServerSocket(12345);
		System.err.println("Porta 12345 aberta!");
	}

	@Override
	public void run() {
		
		Scanner entrada;
		
		try {
			
			cliente = servidor.accept();
			entrada = new Scanner(cliente.getInputStream());
		
			while (entrada.hasNextLine()) {
				System.out.println("O cliente digitou: " + entrada.nextLine());
			}
			
			entrada.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}