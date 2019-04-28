package Projeto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Utilizador
{

	public static void main(String[] args) throws IOException
	{
		Scanner scan = new Scanner(System.in);
		Socket socket = new Socket("localhost", 2000);
		InputStream in = socket.getInputStream();
		DataInputStream dataIn = new DataInputStream(in);
		OutputStream out = socket.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		boolean inicio = true;
		while (inicio == true)
		{
			int vaza = 1;
			System.out.println(dataIn.readUTF());
			System.out.println(dataIn.readUTF());
			System.out.println(dataIn.readUTF());
			System.out.println(dataIn.readUTF());
			System.out.println(dataIn.readUTF());
			String escolha = scan.nextLine();
			dataOut.writeUTF(escolha);
			String receive = dataIn.readUTF();
			while (receive.equals("Deseja registar-se ou fazer login: "))
			{
				System.out.print(receive);
				escolha = scan.nextLine();
				dataOut.writeUTF(escolha);
				receive = dataIn.readUTF();
			}
			
			// LOGIN
			
			System.out.println(receive);
			String envio = scan.nextLine();
			dataOut.writeUTF(envio);

			receive = dataIn.readUTF();
			System.out.println(receive);
			while (receive.equals("Nome ja utilizado. LOGIN: "))
			{
				envio = scan.nextLine();
				dataOut.writeUTF(envio);
				receive = dataIn.readUTF();
				System.out.println(receive);
			}
			while (receive.equals("Nome nao existente. LOGIN: "))
			{
				envio = scan.nextLine();
				dataOut.writeUTF(envio);
				receive = dataIn.readUTF();
				System.out.println(receive);
			}
			// PASSWORD
			envio = scan.nextLine();
			// enviar a pass
			dataOut.writeUTF(envio);

			receive = dataIn.readUTF();
			System.out.println(receive);
			while (receive.equals("Password incorreta"))
			{
				System.out.println("Tente novamente");
				System.out.print("PASSWORD: ");
				envio = scan.nextLine();
				dataOut.writeUTF(envio);

				receive = dataIn.readUTF();
			}
			// Bem vindo
			String comecar = dataIn.readUTF();
			System.out.println(" ");
			System.out.println(comecar);
			// historico
			String historico=dataIn.readUTF();
			System.out.println(historico);
			System.out.println("Caso queira sair do jogo introduza 'stop' ");

			String pronto = dataIn.readUTF();
			System.out.println(pronto);
			envio = scan.nextLine();
			while (!envio.equals("s"))
			{
				System.out.println(pronto);
				envio = scan.nextLine();
			}
			receive = dataIn.readUTF();
			boolean fimdojogo = false;
			while (fimdojogo == false)
			{
				System.out.println(receive);
				receive = dataIn.readUTF();
				if (receive.contains("Foi a sua vez"))
				{
					System.out.println(receive);
					receive = dataIn.readUTF();
				}
				if (receive.contains("Cartas sobre a mesa"))
				{
					System.out.println(receive);
					// receber o ref
					String recebe = dataIn.readUTF();
					int ref = Integer.parseInt(recebe);
					for (int a = 0; a < ref; a++)
					{ 
						recebe = dataIn.readUTF();
						System.out.println(recebe);
					}
					receive = dataIn.readUTF();
				}
				if (receive.contains("Trunfo"))
				{
					System.out.println(receive);
					String receber = dataIn.readUTF();
					System.out.println(receber);
					while (!receber.equals("Qual a carta a jogar?"))
					{
						receber = dataIn.readUTF();
						System.out.println(receber);
					}
				}
				String cartaescolhida = scan.nextLine();
				dataOut.writeUTF(cartaescolhida);
				// Opcao de sair do jogo
				if (cartaescolhida.equals("stop"))
				{
					System.out.println("Jogo interrompido pelo utilizador");
					dataOut.close();
					dataIn.close();
					socket.close();
					scan.close();
					inicio = false;
					break;
				}

				receive = dataIn.readUTF();
				// caso o input seja errado
				while (receive.equals("Qual a carta a jogar?"))
				{
					System.out.println("Caracter invalido");
					System.out.println(receive);
					envio = scan.nextLine();
					dataOut.writeUTF(envio);
					// Opcao de sair do jogo
					if (envio.equals("stop"))
					{
						System.out.println("Jogo interrompido pelo utilizador");
						dataOut.close();
						dataIn.close();
						socket.close();
						scan.close();
						inicio = false;
						break;
					}
					receive = dataIn.readUTF();
				}
				if (envio.equals("stop"))
				{
					break;
				}
				while (receive.contains("Deve jogar carta") || receive.contains("Nao pode jogar"))
				{
					System.out.println(receive);
					envio = dataIn.readUTF();
					System.out.println(envio);
					envio = scan.nextLine();
					dataOut.writeUTF(envio);
					if (envio.equals("stop"))
					{
						System.out.println("Jogo interrompido pelo utilizador");
						dataOut.close();
						dataIn.close();
						socket.close();
						scan.close();
						inicio = false;
						break;
					}
					receive = dataIn.readUTF();
					while (receive.equals("Qual a carta a jogar?"))
					{
						System.out.println("Caracter invalido");
						System.out.println(receive);
						envio = scan.nextLine();
						dataOut.writeUTF(envio);
						if (envio.equals("stop"))
						{
							System.out.println("Jogo interrompido pelo utilizador");
							dataOut.close();
							dataIn.close();
							socket.close();
							scan.close();
							inicio = false;
							break;
						}
						receive = dataIn.readUTF();
					}
				}
				if (envio.equals("stop"))
				{
					break;
				}
				for (int a = 0; a < 6; a++)
				{
					System.out.println(receive);
					receive = dataIn.readUTF();
				}
				if (vaza % 10 == 0)
				{
					for (int a = 0; a < 6; a++)
					{
						System.out.println(receive);
						receive = dataIn.readUTF();
					}
					// primeira linha da grelha da situacao geral
					for (int a = 0; a < 15; a++)
					{
						System.out.print(receive);
						receive = dataIn.readUTF();
					}
					System.out.println(receive);
					for (int a = 0; a < 15; a++)
					{
						receive = dataIn.readUTF();
						System.out.print(receive);
					}
					System.out.println(dataIn.readUTF());
					for (int a = 0; a < 15; a++)
					{
						receive = dataIn.readUTF();
						System.out.print(receive);
					}
					System.out.println(dataIn.readUTF());
					for (int a = 0; a < 15; a++)
					{
						receive = dataIn.readUTF();
						System.out.print(receive);
					}
					System.out.println(dataIn.readUTF());
					// ultima linha da grelha
					for (int a = 0; a < 15; a++)
					{
						receive = dataIn.readUTF();
						System.out.print(receive);
					}
					receive = dataIn.readUTF();
					System.out.println(receive);
					receive = dataIn.readUTF();
					System.out.println(receive);
					// fim da situacao geral
					receive = dataIn.readUTF();
					// fim do jogo
					if (receive.contains("******"))
					{
						for (int a = 0; a < 3; a++)
						{
							System.out.println(receive);
							receive = dataIn.readUTF();
						}
						// Reiniciar o jogo SIM OU NAO
						String resposta = " ";
						while (!resposta.equals("sim") && !resposta.equals("nao"))
						{
							System.out.println(receive);
							resposta = scan.nextLine();
						}
						dataOut.writeUTF(resposta);
						if (resposta.equals("sim"))
						{
							fimdojogo = true;
						}
						else
						{
							fimdojogo = true;
							inicio = false;
						}
					}
				}
				vaza += 1;
			}
		}

		dataOut.close();
		dataIn.close();
		socket.close();
		scan.close();

	}
}
