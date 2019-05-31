package sendero;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Sendero {

	private int precioBlanco;
	private int precioGris;
	private int precioNegro;
	private int costoSendero;
	private char[] baldozas;

	public Sendero(Scanner entrada) {
		this.precioBlanco = entrada.nextInt();
		this.precioGris = entrada.nextInt();
		this.precioNegro = entrada.nextInt();
		this.costoSendero = 0;
		entrada.nextLine();
		String palabra = entrada.nextLine();
		this.baldozas = new char[palabra.length()];
		for (int i = 0; i < palabra.length(); i++) {
			this.baldozas[i] = palabra.charAt(i);
		}
	}

	private char determinarBaldoza(int i) {
		if (this.baldozas[i] == 'N') {
			if (this.precioBlanco < this.precioGris)
				return 'B';
			if (this.precioBlanco > this.precioGris)
				return 'G';
			if (this.precioBlanco == this.precioGris) {
				if (Math.random() >= 0.5)
					return 'G';
				else
					return 'B';
			}
		}
		if (this.baldozas[i] == 'B') {
			if (this.precioNegro < this.precioGris)
				return 'N';
			if (this.precioNegro > this.precioGris)
				return 'G';
			if (this.precioNegro == this.precioGris) {
				if (Math.random() >= 0.5)
					return 'G';
				else
					return 'N';
			}
		}
		if (this.baldozas[i] == 'G') {
			if (this.precioBlanco < this.precioNegro)
				return 'B';
			if (this.precioBlanco > this.precioNegro)
				return 'N';
			if (this.precioBlanco == this.precioNegro) {
				if (Math.random() >= 0.5)
					return 'B';
				else
					return 'N';
			}
		}
		return 0;
	}
	
	private char baldozaAPonerSenda(int i) {
		if (this.baldozas[i - 1] != this.baldozas[i + 1] && this.baldozas[i + 1] != 'R') {
			if (this.baldozas[i - 1] == 'N' && this.baldozas[i + 1] == 'B')
				return 'G';
			if (this.baldozas[i - 1] == 'N' && this.baldozas[i + 1] == 'G') 
				return 'B';
			if(this.baldozas[i-1] == 'B' && this.baldozas[i+1] == 'N' )
				return 'G';
			if(this.baldozas[i-1] == 'B' && this.baldozas[i+1] == 'G')
				return 'N';
			if(this.baldozas[i-1] == 'G' && this.baldozas[i+1] == 'B')
				return 'N';
			if(this.baldozas[i-1] == 'G' && this.baldozas[i+1] == 'N')
				return 'B';
		}
		if (this.baldozas[i - 1] != this.baldozas[i + 1] && this.baldozas[i + 1] == 'R') 
			return this.determinarBaldoza(i-1);
		if (this.baldozas[i - 1] == this.baldozas[i + 1])
			return this.determinarBaldoza(i-1);
		return 0;
	}

	private char baldozaAPonerInicioFin(int i) {
		if (i == 0) {
			if (this.baldozas[i + 1] == 'R') {
				if (this.precioBlanco < this.precioGris && this.precioBlanco < this.precioNegro)
					return 'B';
				if (this.precioGris < this.precioBlanco && this.precioGris < this.precioNegro)
					return 'G';
				return 'N';
			} else 
				return this.determinarBaldoza(i+1);
		} else 
			return this.determinarBaldoza(i-1);
	}

	private void calcularCosto() {
		int tamanio = this.baldozas.length;
		for (int i = 0; i < tamanio; i++) {
			if (this.baldozas[i] == 'N')
				this.costoSendero += this.precioNegro;
			else if (this.baldozas[i] == 'G')
				this.costoSendero += this.precioGris;
			else
				this.costoSendero += this.precioBlanco;
		}
	}

	public void resolver() {
		int tamanio = this.baldozas.length;
		for (int i = 0; i < tamanio; i++) {
			if(this.baldozas[i] == 'R') {
				if( i == 0 || i == tamanio-1)
					this.baldozas[i] = this.baldozaAPonerInicioFin(i);
				else
					this.baldozas[i] = this.baldozaAPonerSenda(i);
			}
		}
		this.calcularCosto();
		System.out.println(String.valueOf(baldozas));
		System.out.println(this.costoSendero);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner entrada = new Scanner(new FileReader("sendero.in"));
		Sendero sendero = new Sendero(entrada);
		entrada.close();
		sendero.resolver();
	}

}
