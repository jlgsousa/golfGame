package Projeto;

public class Carta1
{
	private int pontos;
	private String numero;
	private String naipe;
	
	private int number; //para efeitos de distincao entre 2 e 6
	
	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public int getPontos()
	{
		return pontos;
	}

	public void setPontos(int pontos)
	{
		this.pontos = pontos;
	}

	public String getNumero()
	{
		return numero;
	}

	public void setNumero(String numero)
	{
		this.numero = numero;
	}

	public String getNaipe()
	{
		return naipe;
	}

	public void setNaipe(String naipe)
	{
		this.naipe = naipe;
	}

	public void init(String numero,String naipe){
		this.numero=numero;
		this.naipe=naipe;
		
		if (numero=="A"){
			this.pontos=11;
			this.number=11;
		}
		else if (numero=="7"){
			this.pontos=10;
			this.number=10;
		}
		else if (numero=="R"){
			this.pontos=4;
			this.number=9;
		}
		else if (numero=="V"){
			this.pontos=3;
			this.number=8;
		}
		else if (numero=="D"){
			this.pontos=2;
			this.number=7;
		}
		else if (numero=="6"){
			this.pontos=0;
			this.number=6;
		}
		else if (numero=="5"){
			this.pontos=0;
			this.number=5;
		}
		else if (numero=="4"){
			this.pontos=0;
			this.number=4;
		}
		else if (numero=="3"){
			this.pontos=0;
			this.number=3;
		}
		else if (numero=="2"){
			this.pontos=0;
			this.number=2;
		}
	}
	
}

