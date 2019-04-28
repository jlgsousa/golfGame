package Projeto;

import java.util.Random;

import Projeto.Carta1;
import Projeto.Jogador1;

public class Jogador1
{
	private int numero;
	private Carta1[] mao = new Carta1[10];
	public void init(int numero)
	{
		this.numero = numero;
	}

	public int getNumero()
	{
		return numero;
	}

	public void setNumero(int numero)
	{
		this.numero = numero;
	}

	public Carta1[] getMao()
	{
		return mao;
	}

	public void setMao(Carta1[] mao)
	{
		this.mao = mao;
	}
	// o baralhar
	public void baralha(Carta1 array[])
	{
		Random rand = new Random();
		for (int i = array.length - 1; i > 0; i--)
		{
			int value = rand.nextInt(i + 1);
			Carta1 a = array[value];
			array[value] = array[i];
			array[i] = a;
		}
	}
	// escolhe entre a carta 2 e a 37 para cortar
	public void corta(Carta1 array[])
	{
		Random rand = new Random();
		int cortar = rand.nextInt(36) + 2;
		Carta1[] nova1 = new Carta1[40];
		for (int i = 0; i < cortar; i++)
		{
			nova1[i] = array[i];
		}
		for (int j = 0; j < array.length - cortar; j++)
		{
			array[j] = array[j + cortar];

		}
		for (int k = 0; k < cortar; k++)
		{
			array[array.length - cortar + k] = nova1[k];
		}
	}
	// distribuir uma carta para cada jogador
	public void distribui(Carta1[] array, Jogador1 jog, Jogador1 jogi, Jogador1 joge)
	{
		int v = 0;
		for (int l = 0; l < 40; l = l + 4)
		{
			this.mao[v] = array[l];
			jog.mao[v] = array[l + 1];
			jogi.mao[v] = array[l + 2];
			joge.mao[v] = array[l + 3];
			v += 1;
		}

	}
	// jogar
	public Carta1 joga(String trunfo, String devolve)
	{
		Carta1 value;
		int contador;
		if (devolve.equals(" "))
		{
			// tendo trunfos
			contador = 0;
			// tendo algum trunfo escolher o maior
			int referencia = -1;
			for (int x = 0; x < 10; x++)
			{
				if (this.mao[x].getNaipe().equals(trunfo) && this.mao[x].getPontos() > referencia && !this.mao[x].getNaipe().equals("0"))
				{
					referencia = this.mao[x].getPontos();
					contador = x;
				}
			}
			// nao tendo trunfos
			if (referencia == -1)
			{
				for (int i = 0; i < 10; i++)
				{
					if (this.mao[i].getPontos() > referencia && !this.mao[i].getNaipe().equals("0"))
					{
						referencia = this.mao[i].getPontos();
						contador = i;
					}
				}
			}
			value = this.mao[contador];
			Carta1 apaga = new Carta1();
			apaga.init("0", "0");
			this.mao[contador] = apaga;

		}
		else
		{
			// tendo carta para assistir escolher a maior
			contador = 0;
			int referencia = -1;

			for (int x = 0; x < 10; x++)
			{
				if (this.mao[x].getNaipe().equals(devolve) && this.mao[x].getPontos() > referencia && !this.mao[x].getNaipe().equals("0"))
				{
					referencia = this.mao[x].getPontos();
					contador = x;
				}
			}
			// tendo trunfos (nao tem para assistir) joga o maior
			if (referencia == -1)
			{
				for (int x = 0; x < 10; x++)
				{
					if (this.mao[x].getNaipe().equals(trunfo) && this.mao[x].getPontos() > referencia
							&& !this.mao[x].getNaipe().equals("0"))
					{
						referencia = this.mao[x].getPontos();
						contador = x;
					}
				}
			}
			// nao tendo trunfos (nem carta para assistir)
			if (referencia == -1)
			{
				for (int i = 0; i < 10; i++)
				{
					if (this.mao[i].getPontos() > referencia && !this.mao[i].getNaipe().equals("0"))
					{
						referencia = this.mao[i].getPontos();
						contador = i;
					}
				}
			}
			value = this.mao[contador]; // carta
			Carta1 apaga = new Carta1();
			apaga.init("0", "0");
			this.mao[contador] = apaga;
		}
		return value;
	}

}
