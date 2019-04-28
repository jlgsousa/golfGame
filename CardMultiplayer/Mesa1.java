package Projeto;

import Projeto.Jogador1;
import Projeto.Carta1;


public class Mesa1
{
	public Carta1[] fazbaralho(){
		String[] carta = {"A", "A", "A", "A", "7", "7", "7", "7", "R", "R", "R", "R", "V", "V", "V", "V", "D", "D","D", "D", "6", "6", "6", "6", "5", "5", "5", "5", "4", "4", "4", "4", "3", "3", "3", "3", "2", "2","2", "2"};
		String[] naipe = {"P", "C", "E", "O", "P", "C", "E", "O", "P", "C", "E", "O", "P", "C", "E", "O", "P", "C","E", "O", "P", "C", "E", "O", "P", "C", "E", "O", "P", "C", "E", "O", "P", "C", "E", "O", "P", "C","E", "O"};
		// criar baralho
		Carta1[] baralho = new Carta1[40];
		for (int i = 0; i < 40; i++)
		{
			Carta1 card = new Carta1();
			card.init(carta[i], naipe[i]);
			baralho[i] = card;
		}
		return baralho;
	}
	
	public int[] fimvaza(Carta1 um,Carta1 dois, Carta1 tres, Carta1 quatro,String trunfo,int primeira){
		int[] pontoslista={um.getPontos(),dois.getPontos(),tres.getPontos(),quatro.getPontos()};
		int[] numberlista={um.getNumber(),dois.getNumber(),tres.getNumber(),quatro.getNumber()};
		String[] naipeslista={um.getNaipe(),dois.getNaipe(),tres.getNaipe(),quatro.getNaipe()};
		int[] trunfos={-1,-1,-1,-1};
		int pontos=pontoslista[0]+pontoslista[1]+pontoslista[2]+pontoslista[3];
		int referencia=-1;
		int referencia2=1;
		boolean mark=false;
		int contador=0;
		//ver se ha trunfos
		for (int j=0;j<4;j++){
			if (naipeslista[j].equals(trunfo)){
				mark=true;
				trunfos[j]=1;
			}
		}
		//escolher o maior trunfo
		if (mark==true){
			for (int y=0;y<4;y++){
				if (trunfos[y]!=-1){ 
					if (pontoslista[y]>referencia){
						referencia=pontoslista[y];
						referencia2=numberlista[y];
						contador=y;
					}
					else{
						if (numberlista[y]>referencia2){
							referencia=pontoslista[y];
							referencia2=numberlista[y];
							contador=y;
						}
					}
				}
			}
		}
		//caso nao tenha trunfos na mesa 
		if (mark==false){
			//naipereferencia e o da primeira pessoa a jogar
			String naipereferencia=" ";
			if (primeira==0){
				naipereferencia=um.getNaipe();
			}
			else if (primeira==1){
				naipereferencia=dois.getNaipe();
			}
			else if (primeira==2){
				naipereferencia=tres.getNaipe();
			}
			else if (primeira==3){
				naipereferencia=quatro.getNaipe();
			}
			//ver a maior carta com naipe igual a primeira 
			for (int i=0;i<4;i++){
				if (pontoslista[i]>referencia && naipeslista[i].equals(naipereferencia)){
					referencia=pontoslista[i];
					referencia2=numberlista[i];
					contador=i;
				}
				else{
					if (naipeslista[i].equals(naipereferencia)){
						if (numberlista[i]>referencia2){
							referencia=pontoslista[i];
							referencia2=numberlista[i];
							contador=i;
						}
					}
				}
			}
		}
		int[] retornar={pontos, contador};
		return retornar;
	}
	
	public int vitorias(int pontos){
		int balor=0;
		if (pontos>=60 && pontos<90){
			balor=1;
		}
		else if (pontos>=90 && pontos<120){
			balor=2;
		}
		else if (pontos==120){
			balor=4;
		}
		return balor;
	}
	public String printcarta(Carta1 carta,Jogador1 player,int numero){
		String dar="|---|"+"\n|"+carta.getNumero()+"  |"+"\n"+"|" + carta.getNaipe() + " " + carta.getNaipe() + "|"+"\n"+"|  " + carta.getNumero() + "|"+"\n"+"|---| Jogada por " + player.getNumero()+"\n ";
		return dar;
	}
	public String printcarta1(Carta1 carta,Jogador1 player,int numero){
		String redar="|---|"+"\n|"+carta.getNumero()+"  |"+"\n"+"|" + carta.getNaipe() + " " + carta.getNaipe() + "|"+"\n"+"|  " + carta.getNumero() + "|"+"\n"+"|---| Carta-> " + numero+"\n ";
		return redar;
	}
	public String jogado(Carta1[] jogadas,Jogador1 player0,Jogador1 player1,Jogador1 player2,Jogador1 player3){
		//Apresentar as cartas jogadas
		String limite="================Cartas jogadas ==================="+"\n"+"\n"+"|---|              |---|"+"\n"+"|" + jogadas[player0.getNumero()].getNumero() + "  |              |"+jogadas[player1.getNumero()].getNumero()+"  |"
				+"\n"+"|" + jogadas[player0.getNumero()].getNaipe() + " " + jogadas[player0.getNumero()].getNaipe() + "|              |"+jogadas[player1.getNumero()].getNaipe()+" "+jogadas[player1.getNumero()].getNaipe()+"|"+"\n"
				+"|  " + jogadas[player0.getNumero()].getNumero() + "|              |  "+jogadas[player1.getNumero()].getNumero()+"|"+"\n"+"|---| Jogada por "+ player0.getNumero()+ " |---| Jogada por "+player1.getNumero()+"\n"+"\n"+
				"|---|              |---|"+"\n"+"|" + jogadas[player2.getNumero()].getNumero() + "  |              |"+jogadas[player3.getNumero()].getNumero()+"  |"+"\n"+
				"|" + jogadas[player2.getNumero()].getNaipe() + " " + jogadas[player2.getNumero()].getNaipe() + "|              |"+jogadas[player3.getNumero()].getNaipe()+" "+jogadas[player3.getNumero()].getNaipe()+"|"+"\n"
				+"|  " + jogadas[player2.getNumero()].getNumero() + "|              |  "+jogadas[player3.getNumero()].getNumero()+"|"+"\n"+"|---| Jogada por " +player2.getNumero()+ " |---| Jogada por "+player3.getNumero();
		
		return limite;
	}
	
	public String historico(String login,int vitorias,int jogos,String pontos){
		String resposta="**********Historico do jogador "+ login + "********** \n" +"*Numero de vitorias em desafios da sueca: " + vitorias+"\n*Numero de jogos efetuados: " +jogos;
		if (jogos != 0)
		{
			double percentagem = ((vitorias) * 1.0 / jogos) * 100;
			resposta=resposta+"\n*Percentagem de vitorias: " + percentagem + " %";
		}
		else
		{
			resposta=resposta+"\n*Percentagem de vitorias: 0 %";
		}
		resposta=resposta+"\n*Numero de pontos ganhos: " + pontos+"\n******************************************"+"\n ";
		return resposta;
	}
	
}

