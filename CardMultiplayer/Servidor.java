package Projeto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Projeto.Carta1;
import Projeto.Jogador1;
import Projeto.Mesa1;

public class Servidor
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket sSocket = new ServerSocket(2000);
		ArrayList<String> logins = new ArrayList<String>();
		ArrayList<String> passwords = new ArrayList<String>();
		ArrayList<Integer> vitoriassueca = new ArrayList<Integer>();
		ArrayList<Integer> jogos = new ArrayList<Integer>();
		ArrayList<String> pontos = new ArrayList<String>();
		logins.add("joao1");
		passwords.add("sistemas");
		vitoriassueca.add(2);
		jogos.add(4);
		pontos.add("113");
		while (true)
		{
			Socket connectionsocket = sSocket.accept();
			OutputStream out = connectionsocket.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			InputStream in = connectionsocket.getInputStream();
			DataInputStream dataIn = new DataInputStream(in);
			// ciclo para jogar de novo
			boolean reiniciar = true;
			while (reiniciar == true)
			{
				dataOut.writeUTF("==>Bem Vindo ao Desafio da Sueca<==");
				dataOut.writeUTF(" ");
				dataOut.writeUTF("Para efetuar registo de novo nome introduza 'registar'");
				dataOut.writeUTF("Para efetuar login com conta ja existente introduza 'login'");
				dataOut.writeUTF("Deseja registar-se ou fazer login: ");
				// recebe
				String escolha = dataIn.readUTF();
				while (!escolha.equals("registar") && !escolha.equals("login")){
					dataOut.writeUTF("Deseja registar-se ou fazer login: ");
					escolha = dataIn.readUTF();
				}
				// registar
				int marcapass = 0;
				if (escolha.equals("registar"))
				{
					String envio = "LOGIN: ";
					dataOut.writeUTF(envio);

					// recebeu o login
					String receive = dataIn.readUTF();

					marcapass = -1;
					// ver se login ja existe
					for (int a = 0; a < logins.size(); a++)
					{
						if (receive.equals(logins.get(a)))
						{
							marcapass = a;
						}
					}
					if (marcapass == -1)
					{
						logins.add(receive);
						envio = "PASSWORD: ";
						dataOut.writeUTF(envio);

						receive = dataIn.readUTF();
						passwords.add(receive);
						vitoriassueca.add(0);
						jogos.add(0);
						pontos.add("");

						envio = "Registo efetuado com sucesso";
						dataOut.writeUTF(envio);
						marcapass = logins.size() - 1;

					}
					else
					{
						boolean flag = false;
						while (flag == false)
						{
							int contador = 0;
							envio = "Nome ja utilizado. LOGIN: ";
							dataOut.writeUTF(envio);

							receive = dataIn.readUTF();
							for (int a = 0; a < logins.size(); a++)
							{
								if (receive.equals(logins.get(a)))
								{
									contador = 1;
								}
							}
							if (contador == 0)
							{
								flag = true;
								logins.add(receive);
								envio = "PASSWORD: ";
								dataOut.writeUTF(envio);

								receive = dataIn.readUTF();
								passwords.add(receive);
								vitoriassueca.add(0);
								jogos.add(0);
								pontos.add("");

								envio = "Registo efetuado com sucesso";
								dataOut.writeUTF(envio);
								marcapass = logins.size() - 1;

							}
						}
					}
				}

				// login
				else if (escolha.equals("login"))
				{
					String envio = "LOGIN: ";
					dataOut.writeUTF(envio);

					// recebeu o login
					String receive = dataIn.readUTF();

					marcapass = -1;
					// ver se login ja existe
					for (int a = 0; a < logins.size(); a++)
					{
						if (receive.equals(logins.get(a)))
						{
							marcapass = a;
						}
					}

					// se estiver ver se a pass e a correspondente
					if (marcapass != -1)
					{
						boolean flag = false;
						envio = "PASSWORD: ";
						dataOut.writeUTF(envio);

						while (flag == false)
						{
							receive = dataIn.readUTF();
							if (passwords.get(marcapass).equals(receive))
							{
								envio = "Login efetuado com sucesso!";
								dataOut.writeUTF(envio);
								flag = true;
							}
							else
							{
								envio = "Password incorreta";
								dataOut.writeUTF(envio);

							}
						}
					}
					else
					{
						boolean flagy = false;
						while (flagy == false)
						{
							envio = "Nome nao existente. LOGIN: ";
							dataOut.writeUTF(envio);

							receive = dataIn.readUTF();
							marcapass = -1;
							// ver se login ja existe
							for (int a = 0; a < logins.size(); a++)
							{
								if (receive.equals(logins.get(a)))
								{
									marcapass = a;
								}
							}
							if (marcapass != -1)
							{
								flagy = true;
								boolean flag = false;
								envio = "PASSWORD: ";
								dataOut.writeUTF(envio);
								while (flag == false)
								{
									receive = dataIn.readUTF();
									if (passwords.get(marcapass).equals(receive))
									{
										envio = "Login efetuado com sucesso!";
										dataOut.writeUTF(envio);
										flag = true;
									}
									else
									{
										envio = "Password incorreta";
										dataOut.writeUTF(envio);

									}
								}
							}
						}
					}
				}
				int pontosganhos = 0;
				String comecar = "==>Bem Vindo ao Desafio da Sueca<==";
				dataOut.writeUTF(comecar);
				Mesa1 mesa = new Mesa1();
				// historico //
				String historico=mesa.historico(logins.get(marcapass), vitoriassueca.get(marcapass), jogos.get(marcapass), pontos.get(marcapass));
				dataOut.writeUTF(historico);
				comecar = "Esta pronto para comecar?(s/n)";
				dataOut.writeUTF(comecar);
				/////////////JOGO PROPRIAMENTE DITO//////////////////
				Carta1[] jogadas = new Carta1[4];
				Carta1[] baralho = mesa.fazbaralho();
				Jogador1 player0 = new Jogador1();
				player0.init(0);
				Jogador1 player1 = new Jogador1();
				player1.init(1);
				Jogador1 player2 = new Jogador1();
				player2.init(2);
				Jogador1 player3 = new Jogador1();
				player3.init(3);
				Jogador1[] jogadores = { player0, player1, player2, player3 };
				int vaza = 1;
				// Pontos no fim da vaza
				int pontos02 = 0;
				int pontos13 = 0;
				int vitorias02 = 0;
				int vitorias13 = 0;
				int vitorias02total = 0;
				int vitorias13total = 0;
				String trunfo = " ";
				int ganhou = 10;
				while (vitorias02total < 4 && vitorias13total < 4)
				{
					if ((vaza - 1) % 10 == 0)
					{ // 1 11 21 31
						dataOut.writeUTF("=>Nova ronda<=");
						baralho = mesa.fazbaralho();
						if (jogadores[0].getNumero() == 1)
						{
							dataOut.writeUTF("Foi a sua vez de baralhar");
							jogadores[0].baralha(baralho);
						}
						else
						{
							jogadores[0].baralha(baralho);
						}
						if (jogadores[2].getNumero() == 1)
						{
							dataOut.writeUTF("Foi a sua vez de cortar");
							jogadores[2].corta(baralho);
						}
						else
						{
							jogadores[2].corta(baralho);
						}
						if (jogadores[3].getNumero() == 1)
						{
							dataOut.writeUTF("Foi a sua vez de distribuir as cartas");
							jogadores[3].distribui(baralho, jogadores[0], jogadores[1], jogadores[2]);
						}
						else
						{
							jogadores[3].distribui(baralho, jogadores[0], jogadores[1], jogadores[2]);
						}
						trunfo = baralho[39].getNaipe();
					}
					else
					{
						Jogador1 guarda1 = jogadores[1];
						Jogador1 guarda2 = jogadores[1];
						Jogador1 guarda3 = jogadores[1];
						Jogador1 guarda4 = jogadores[1];
						if (ganhou == 0)
						{
							for (int a = 0; a < 4; a++)
							{
								if (jogadores[a].getNumero() == 0)
								{
									guarda1 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 1)
								{
									guarda2 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 2)
								{
									guarda3 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 3)
								{
									guarda4 = jogadores[a];
								}
							}
						}

						else if (ganhou == 1)
						{
							for (int a = 0; a < 4; a++)
							{
								if (jogadores[a].getNumero() == 1)
								{
									guarda1 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 2)
								{
									guarda2 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 3)
								{
									guarda3 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 0)
								{
									guarda4 = jogadores[a];
								}
							}
						}
						else if (ganhou == 2)
						{
							for (int a = 0; a < 4; a++)
							{
								if (jogadores[a].getNumero() == 2)
								{
									guarda1 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 3)
								{
									guarda2 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 0)
								{
									guarda3 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 1)
								{
									guarda4 = jogadores[a];
								}
							}
						}
						else if (ganhou == 3)
						{
							for (int a = 0; a < 4; a++)
							{
								if (jogadores[a].getNumero() == 3)
								{
									guarda1 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 0)
								{
									guarda2 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 1)
								{
									guarda3 = jogadores[a];
								}
								else if (jogadores[a].getNumero() == 2)
								{
									guarda4 = jogadores[a];
								}
							}
						}
						jogadores[0] = guarda1;
						jogadores[1] = guarda2;
						jogadores[2] = guarda3;
						jogadores[3] = guarda4;
					}
					Carta1 jogadaprimeiro = new Carta1();
					jogadaprimeiro.init("0", "0");
					int ref = 0;

					// se o utilizador for o primeiro a jogar
					if (jogadores[0].getNumero() == 1)
					{
						dataOut.writeUTF(">>>>>>>>>>>>>>>>>>>>>>>>>>Trunfo--> " + trunfo + " <<<<<<<<<<<<<<<<<<<");
						dataOut.writeUTF("================Cartas na sua mao na vaza " + vaza + " =============");
						// so mostrar as cartas que tem
						for (int a = 0; a < 10; a++)
						{
							if (!jogadores[0].getMao()[a].getNumero().equals("0"))
							{ // nao por carta ja retirada
								String teste=mesa.printcarta1(jogadores[0].getMao()[a], jogadores[0], a);
								dataOut.writeUTF(teste);
							}
						}
					}
					else
					{// saber onde ta o player1
						for (int g = 0; g < 4; g++)
						{
							if (jogadores[g].getNumero() == 1)
							{
								ref = g;
							}
						}
						dataOut.writeUTF("===============Cartas sobre a mesa na vaza " + vaza + " =============");
						String reff = Integer.toString(ref);
						dataOut.writeUTF(reff);
						for (int i = 0; i < ref; i++)
						{
							Carta1 jogada;
							if (i == 0)
							{ // IMPORTANTE: desta forma asseguro que por exemplo a carta jogada por player0 esta na posicao 0 da lista jogadas
								jogada = jogadores[i].joga(trunfo, " ");
								int numero = jogadores[i].getNumero();
								jogadas[numero] = jogada;
								jogadaprimeiro = jogada;
							}
							else
							{ // IMPORTANTE: desta forma asseguro que por exemplo a carta jogada por player1 esta
								// na posicao 1 da lista jogadas e o mesmo com o player2 player3
								jogada = jogadores[i].joga(trunfo, jogadaprimeiro.getNaipe());
								int numero = jogadores[i].getNumero();
								jogadas[numero] = jogada;
							}
							String cartaprintada = mesa.printcarta(jogada, jogadores[i], i);
							dataOut.writeUTF(cartaprintada);
						}
						dataOut.writeUTF(">>>>>>>>>>>>>>>>>>>>>>>>>>Trunfo--> " + trunfo + " <<<<<<<<<<<<<<<<<<<");
						dataOut.writeUTF("================Cartas na sua mao na vaza  " + vaza + " =================");
						for (int a = 0; a < 10; a++)
						{
							if (!jogadores[ref].getMao()[a].getNumero().equals("0"))
							{ // nao por carta ja retirado
								String possibilidade=mesa.printcarta1(jogadores[ref].getMao()[a],jogadores[ref],a);
								dataOut.writeUTF(possibilidade);
							}
						}
					}
					int carto = 0;
					dataOut.writeUTF("Qual a carta a jogar?");
					String cena = dataIn.readUTF();
					// Opcao de sair do jogo
					if (cena.equals("stop"))
					{
						System.out.println("jogo interrompido pelo utilizador");
						dataOut.close();
						dataIn.close();
						connectionsocket.close();
						reiniciar = false;
						break;
					}
					while (!cena.equals("0") && !cena.equals("1") && !cena.equals("2") && !cena.equals("3") && !cena.equals("4") && !cena.equals("5") && !cena.equals("6") && !cena.equals("7") && !cena.equals("8") && !cena.equals("9"))
					{
						dataOut.writeUTF("Qual a carta a jogar?");
						cena = dataIn.readUTF();
						// Opcao de sair do jogo
						if (cena.equals("stop"))
						{
							System.out.println("jogo interrompido pelo utilizador");
							dataOut.close();
							dataIn.close();
							connectionsocket.close();
							reiniciar = false;
							break;
						}
					}
					// verificar se e para parar o jogo
					if (cena.equals("stop"))
					{
						break;
					}
					carto = Integer.parseInt(cena);
					String o="";
					Carta1 jogada1 = jogadores[ref].getMao()[carto]; // jogada1 played pelo utilizador
					// ver se nao ha tramoia, ou se nao poe uma carta ja jogada quando e o primeiro a jogar
					if (!jogada1.getNaipe().equals(jogadaprimeiro.getNaipe()) && ref != 0)
					{
						for (int i = 0; i < 10; i++)
						{
							if (jogadores[ref].getMao()[i].getNaipe().equals(jogadaprimeiro.getNaipe()))
							{// entrando aqui e porque ha batota entao so se sai quando a carta tiver o naipe pretendido
								while (!jogada1.getNaipe().equals(jogadaprimeiro.getNaipe()) || jogada1.getNaipe().equals("0"))
								{
									dataOut.writeUTF("Deve jogar carta com mesmo naipe que a primeira, e que ainda nao tenha sido jogada");
									dataOut.writeUTF("Qual a carta a jogar?");
									o = dataIn.readUTF();
									// Opcao de sair do jogo
									if (o.equals("stop"))
									{
										System.out.println("jogo interrompido pelo utilizador");
										dataOut.close();
										dataIn.close();
										connectionsocket.close();
										reiniciar = false;
										break;
									}

									while (!o.equals("0") && !o.equals("1") && !o.equals("2") && !o.equals("3") && !o.equals("4") && !o.equals("5") && !o.equals("6") && !o.equals("7") && !o.equals("8") && !o.equals("9"))
									{
										dataOut.writeUTF("Qual a carta a jogar?");
										o = dataIn.readUTF();
										// Opcao de sair do jogo
										if (o.equals("stop"))
										{
											System.out.println("jogo interrompido pelo utilizador");
											dataOut.close();
											dataIn.close();
											connectionsocket.close();
											reiniciar = false;
											break;
										}	
									}
									// verificar se e para parar o jogo
									if (o.equals("stop"))
									{
										break;
									}
									carto = Integer.parseInt(o);
									jogada1 = jogadores[ref].getMao()[carto];
								}
								// verificar se e para parar o jogo
								if (o.equals("stop"))
								{
									break;
								}
								// se assistir deve sair do ciclo for
								i = 10;
							}
						}
						// verificar se e para parar o jogo
						if (o.equals("stop"))
						{
							break;
						}
					}
					if (jogada1.getNaipe().equals("0"))
					{
						while (jogada1.getNaipe().equals("0"))
						{
							dataOut.writeUTF("Nao pode jogar uma carta ja jogada");
							dataOut.writeUTF("Qual a carta a jogar?");
							o = dataIn.readUTF();
							// Opcao de sair do jogo
							if (o.equals("stop"))
							{
								System.out.println("jogo interrompido pelo utilizador");
								dataOut.close();
								dataIn.close();
								connectionsocket.close();
								reiniciar = false;
								break;
							}
							
							while (!o.equals("0") && !o.equals("1") && !o.equals("2") && !o.equals("3") && !o.equals("4") && !o.equals("5") && !o.equals("6") && !o.equals("7") && !o.equals("8") && !o.equals("9"))
							{
								dataOut.writeUTF("Qual a carta a jogar?");
								o = dataIn.readUTF();
								// Opcao de sair do jogo
								if (o.equals("stop"))
								{
									System.out.println("jogo interrompido pelo utilizador");
									dataOut.close();
									dataIn.close();
									connectionsocket.close();
									reiniciar = false;
									break;
								}
							}
							// verificar se e para parar o jogo
							if (o.equals("stop"))
							{
								break;
							}
							carto = Integer.parseInt(o);
							jogada1 = jogadores[ref].getMao()[carto];
						}
						// verificar se e para parar o jogo
						if (o.equals("stop"))
						{
							break;
						}
						
					}
					jogadas[1] = jogada1;
					jogadaprimeiro = jogadores[ref].getMao()[carto];
					// jogam os que faltam
					for (int i = ref + 1; i < 4; i++)
					{// IMPORTANTE: desta forma asseguro que por exemplo a carta jogada por player0 esta na posicao 0 da lista jogadas
						int numero = jogadores[i].getNumero();
						jogadas[numero] = jogadores[i].joga(trunfo, jogadaprimeiro.getNaipe());
					}
					int[] table = mesa.fimvaza(jogadas[0], jogadas[1], jogadas[2], jogadas[3], trunfo,
							jogadores[0].getNumero());
					ganhou = table[1];
					// Apresentar as cartas jogadas
					String cartasjogadas=mesa.jogado(jogadas, jogadores[0], jogadores[1], jogadores[2], jogadores[3]);
					dataOut.writeUTF(cartasjogadas);
//					
					// Retirar a carta (na mao do utilizador)
					jogadores[ref].getMao()[carto].setNaipe("0");
					jogadores[ref].getMao()[carto].setNumero("0");
					jogadores[ref].getMao()[carto].setPontos(0);
					dataOut.writeUTF("===================================================");
					dataOut.writeUTF("+++++ A vaza " + vaza + " foi ganha pelo jogador " + table[1] + " +++++");
					// adicionar pontos ganhos pelo utilizador
					if (table[1] == 1)
					{
						pontosganhos = pontosganhos + table[0];
					}
					if (table[1] == 0 || table[1] == 2)
					{
						pontos02 = pontos02 + table[0];
						dataOut.writeUTF("| " + table[0] + " Pontos a atribuir ao jogador 0 e ao jogador 2|");
					}
					else if (table[1] == 1 || table[1] == 3)
					{
						pontos13 = pontos13 + table[0];
						dataOut.writeUTF("| " + table[0] + " Pontos a atribuir ao jogador 1 e ao jogador 3|");
					}
					dataOut.writeUTF("===================================================");
					dataOut.writeUTF(" ");
					dataOut.writeUTF(" ");
					if (vaza % 10 == 0)
					{ // fim de um jogo
						vitorias02 = mesa.vitorias(pontos02);
						vitorias13 = mesa.vitorias(pontos13);
						vitorias02total = vitorias02total + vitorias02;
						vitorias13total = vitorias13total + vitorias13;
						// relatorio
						String array[][] = new String[5][15];
						array[0][0] = "P";
						array[4][0] = "P";
						array[0][1] = "a";
						array[4][1] = "a";
						array[0][2] = "r";
						array[4][2] = "r";
						array[0][4] = "1";
						array[4][4] = "2";
						array[0][5] = ":";
						array[4][5] = ":";
						dataOut.writeUTF("===================Relatorio do Jogo==================");
						if (pontos02 > pontos13)
						{
							dataOut.writeUTF("Voce e o seu parceiro perderam e conseguiram " + pontos13 + " pontos");
							dataOut.writeUTF("Os seus rivais ganharam, conseguindo " + pontos02 + " pontos");
						}
						else if (pontos02 < pontos13)
						{
							dataOut.writeUTF("Voce e seu parceiro ganharam e conseguiram " + pontos13 + " pontos");
							dataOut.writeUTF("Os seus rivais perderam, so conseguiram " + pontos02 + " pontos");
						}
						else if (pontos02 == pontos13)
						{
							dataOut.writeUTF("Voce e seu parceiro empataram conseguindo " + pontos02 + " pontos");
						}
						dataOut.writeUTF("======================================================");
						dataOut.writeUTF("===================Situacao Geral=====================");
						for (int a = 7; a < vitorias13total + 7; a++)
						{
							array[0][a] = "O";
							array[1][a] = "|";
							array[2][a] = "-";
						}
						for (int b = 7; b < vitorias02total + 7; b++)
						{
							array[3][b] = "|";
							array[4][b] = "O";
							array[2][b] = "-";
						}
						for (int linha = 0; linha < array.length; linha++)
						{
							for (int coluna = 0; coluna < array[0].length; coluna++)
							{
								if (array[linha][coluna] == null)
								{
									array[linha][coluna] = " ";
								}
							}
						}
						for (int coluna = 0; coluna < array[0].length; coluna++)
						{
							dataOut.writeUTF(array[0][coluna]);
						}
						dataOut.writeUTF(vitorias13total + " Vitorias");
						for (int coluna = 0; coluna < array[0].length; coluna++)
						{
							dataOut.writeUTF(array[1][coluna]);
						}
						dataOut.writeUTF(" ");
						for (int coluna = 0; coluna < array[0].length; coluna++)
						{
							dataOut.writeUTF(array[2][coluna]);
						}
						dataOut.writeUTF(" ");
						for (int coluna = 0; coluna < array[0].length; coluna++)
						{
							dataOut.writeUTF(array[3][coluna]);
						}
						dataOut.writeUTF(" ");
						for (int coluna = 0; coluna < array[0].length; coluna++)
						{
							dataOut.writeUTF(array[4][coluna]);
						}
						dataOut.writeUTF(vitorias02total + " Vitorias");
						dataOut.writeUTF("======================================================");
						// mudar o papel dos jogadores
						Jogador1 guarda1 = jogadores[1];
						Jogador1 guarda2 = jogadores[1];
						Jogador1 guarda3 = jogadores[1];
						Jogador1 guarda4 = jogadores[1];

						if (vaza == 10 || vaza == 50)
						{
							for (int f = 0; f < 4; f++)
							{
								if (jogadores[f].getNumero() == 3)
								{
									guarda1 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 0)
								{
									guarda2 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 1)
								{
									guarda3 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 2)
								{
									guarda4 = jogadores[f];
								}
							}
						}
						if (vaza == 20 || vaza == 60)
						{
							for (int f = 0; f < 4; f++)
							{
								if (jogadores[f].getNumero() == 2)
								{
									guarda1 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 3)
								{
									guarda2 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 0)
								{
									guarda3 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 1)
								{
									guarda4 = jogadores[f];
								}
							}
						}
						if (vaza == 30 || vaza == 70)
						{
							for (int f = 0; f < 4; f++)
							{
								if (jogadores[f].getNumero() == 1)
								{
									guarda1 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 2)
								{
									guarda2 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 3)
								{
									guarda3 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 0)
								{
									guarda4 = jogadores[f];
								}
							}
						}
						if (vaza == 40)
						{
							for (int f = 0; f < 4; f++)
							{
								if (jogadores[f].getNumero() == 0)
								{
									guarda1 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 1)
								{
									guarda2 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 2)
								{
									guarda3 = jogadores[f];
								}
								else if (jogadores[f].getNumero() == 3)
								{
									guarda4 = jogadores[f];
								}
							}
						}
						jogadores[0] = guarda1;
						jogadores[1] = guarda2;
						jogadores[2] = guarda3;
						jogadores[3] = guarda4;

						// no fim do desafio da sueca
						if (vitorias13total >= 4)
						{
							dataOut.writeUTF("*********************************");
							dataOut.writeUTF("*         Parabens Ganhou       *");
							dataOut.writeUTF("*********************************");
							dataOut.writeUTF("Deseja jogar novamente?(sim/nao): ");
							// mais uma vitoria no jogador em questao
							vitoriassueca.set(marcapass, vitoriassueca.get(marcapass) + 1);
							// mais um jogo jogado
							jogos.set(marcapass, jogos.get(marcapass) + 1);
							// pontosganhos
							String pontitos = Integer.toString(pontosganhos);
							pontos.set(marcapass, pontos.get(marcapass) + "  " + pontitos);
							// ver se e para parar ou nao
							String verificacao = dataIn.readUTF();
							if (verificacao.equals("nao"))
							{
								reiniciar = false;
							}
						}
						if (vitorias02total >= 4)
						{
							dataOut.writeUTF("*********************************");
							dataOut.writeUTF("*            Game Over          *");
							dataOut.writeUTF("*********************************");
							dataOut.writeUTF("Deseja jogar novamente?(sim/nao): ");
							// mais um jogo jogado
							jogos.set(marcapass, jogos.get(marcapass) + 1);
							// pontosganhos
							String pontitos = Integer.toString(pontosganhos);
							pontos.set(marcapass, pontos.get(marcapass) + " " + pontitos);
							// ver se e para parar ou nao
							String verificacao = dataIn.readUTF();
							if (verificacao.equals("nao"))
							{
								reiniciar = false;
							}
						}
						pontos02 = 0;
						pontos13 = 0;
					}
					vaza += 1;
				}
			}
			dataOut.close();
			dataIn.close();
			connectionsocket.close();
		}
	}
}
