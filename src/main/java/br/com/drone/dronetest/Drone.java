package br.com.drone.dronetest;

/**
 *
 * @author Alexsandro Felix da Silva
 *
 */
public  class Drone {
	private int x;
	private int y;
	private int posicaoDrone[] = {x,y};
		
    public String changePosition(String str){
    	try {
        	String coordenada = str.toUpperCase();
        	zerarPosicaoDoDrone();
        	
        	if(stringValida(coordenada)) {
        		mover(coordenada);
        	}else {
        		erro();
        	} 
		} catch (Exception e) {
			erro();
		}

        return stringPosicaoDoDrone();
    }
    
    
    public void mover(String coordenada) {
    	if(contemNumero(coordenada)) {
    		formatacoordenadaComNumero(coordenada);
    	}else {
        	coordenada = coordenada.contains("X") ?
        			formatacoordenada(coordenada) : coordenada;
    		calculacoordenada(coordenada);
    	}
    }
    
    /**
     * Caso a coordenada não conter caractere 'X' e numeros esse
     * método possui o algoritmo que resolve a mesma.
     * @param coordenada
     */
    public void calculacoordenada(String coordenada) {
    	for(Character caractere : coordenada.toCharArray()) {
            
        	switch(caractere) {
        	case 'N':
        		this.y++;
        		break;
        	case 'S':
        		this.y--;
        		break;
        	case 'L':
        		this.x++;
        		break;
        	case 'O':
        		this.x--;
        		break;
        	}	   						  				
        }
    }
	
    /**
     * Verifica se o número está entre 1 e 2147483647 (Que representa o máximo de um número inteiro)
     * caso o parse da string der erro cai na Exception setando a posicao de falha/erro ou se o numero
     *  for menor que 0 retorna true e segue com a validação dos outros caracteres.
     * @param coordenada
     * @return
     */
	public boolean verificaSeNumeroValido(String coordenada) {
				String strNumeros = coordenada.replaceAll("[^0-9]", "");
		
		try {
			int numeros = Integer.parseInt(strNumeros);
			if(numeros > 0) {
				return true;
			}
		} catch (Exception e) {
			erro();
		}
		return false;
	}
	
	public boolean contemNumero(String coordenada) {
		
		String soNumeros = coordenada.replaceAll("[^0-9]", "");
		
		boolean contemNumero = soNumeros.length() > 0 ? true : false;
				
		return contemNumero;
	}
	
	
	/**
	 * Formata coordenada que contenha o caractere 'X'
	 * @param coordenada
	 * @return
	 */
	public String formatacoordenada(String coordenada) {
		
		String coordenasdaFormatada = "";
		
		for (int i = 0; i < coordenada.length(); i++ ) {
			
			Character caractere = ' ';
			
			if(i != (coordenada.length() -1 )) {
				
				caractere = i != coordenada.length()? 
						coordenada.toCharArray().clone()[i +1] : coordenada.toCharArray().clone()[i];
			}
			
			if(caractere.equals('X')) {
				coordenasdaFormatada += "";
				i++;
				
				Character proximacaractere = ' ';
				
				proximacaractere = (i +1) >= coordenada.length() ?
						coordenada.toCharArray().clone()[i -1] : coordenada.toCharArray().clone()[i +1];
				
				if(proximacaractere.equals('X')) {
					coordenasdaFormatada = coordenasdaFormatada.substring(0, coordenasdaFormatada.length() -1);
				}
			}else {
				coordenasdaFormatada += caractere;
			}
		}
		return coordenasdaFormatada;
	}
	
	public void formatacoordenadaComNumero(String coordenada) {
		
		if(verificaSeNumeroValido(coordenada)) {

			String coordenadaFormata = "";
			Character charAntesDosPassos = ' ';
			Character proximoCaracter = ' ';
			
			if(coordenada.contains("X")) {
				for (int i = 0 ; i < coordenada.length() ; i++) {
					
					Character caracterAtual = coordenada.toCharArray()[i];
					
					proximoCaracter = (i+1 < coordenada.length()) ? coordenada.toCharArray()[i +1] : ' ';
					
					if(!caracterAtual.equals('X')) {
						if(Character.isDigit(caracterAtual)) {
							if (charAntesDosPassos.equals(' ')) {
								
								charAntesDosPassos = coordenada.toCharArray()[i -1];
								
								coordenadaFormata += proximoCaracter.equals('X') ? ' ' : caracterAtual;
							} else {
								coordenadaFormata += caracterAtual;
							}
						} else {
							if(Character.isDigit(caracterAtual)) {
								coordenadaFormata.substring(coordenadaFormata.length());
							}else if (!proximoCaracter.equals('X')) {
								coordenadaFormata += caracterAtual.charValue();
							}
						}
					} 
					if(Character.isDigit(caracterAtual) && proximoCaracter == 'X') {
						coordenadaFormata = coordenadaFormata.substring(0, i-1);
					}
				}
			}else {
				calculacoordenadaComNumeros(coordenada);
			}
			calculacoordenadaComNumeros(coordenadaFormata);
		}
		
 	}
	
	public void calculacoordenadaComNumeros(String coordenada){
		
		Character proximoCaracter = ' ';
		Character charAntesDosPassos = ' ';
		String coordenadaFormata = "";
		int passos = 0;
		
		for(int i = 0 ; i < coordenada.length() ; i++) {
			Character caracterAtual = coordenada.toCharArray()[i];
			
			if (i+1 < coordenada.length()) {
				proximoCaracter = coordenada.toCharArray()[i +1];
			}else {
				proximoCaracter = 'X';
			}
			
			if(Character.isDigit(caracterAtual)) {
				
				coordenadaFormata += caracterAtual;
				if (charAntesDosPassos.equals(' ')) {
					charAntesDosPassos = coordenada.toCharArray()[i -1];
				}
			}else if (proximoCaracter == ' ' || caracterAtual == 'N' || caracterAtual == 'S' ||
					caracterAtual == 'L' || caracterAtual == 'O') {
				charAntesDosPassos = caracterAtual;
				passos = 1;
				proximoCaracter = ' ';
			}
			if(!Character.isDigit(proximoCaracter) || proximoCaracter == ' ') {
				if(proximoCaracter != 'X') {
					passos = proximoCaracter.equals(' ') ? passos : Integer.parseInt(coordenadaFormata);
					
		        	switch(charAntesDosPassos) {
		        	case 'N':
		        		this.y = passos;
		        		break;
		        	case 'S':
		        		this.y -= passos;
		        		break;
		        	case 'L':
		        		this.x = passos;
		        		break;
		        	case 'O':
		        		this.x -= passos;
		        		break;
		        	}
		        	
		        	charAntesDosPassos = ' ';
		        	coordenadaFormata = "";
				}
			}
		}
	}	
	
	/**
	 * Condições que invalidam a coordenada (String nula, vazia, só com números, 
	 * caractereInvalido ou passosInvalidos).
	 * @param str
	 * @return
	 */
	public boolean stringValida(String str) {
				
		if (str.equals(null) || 
				str.isEmpty() ||
				str.trim().isEmpty() ||
				str.matches("[0-9]+") ||
				caractereInvalido(str) ||
				passosInvalidos(str)) return false;
		
		return true;
	}
	
	/**
	 * Verifica se os caracteres da coordenada são válidos, caso não
	 * seja a coordenada é considerada inválida.
	 * @param str
	 * @return
	 */
	public boolean caractereInvalido(String str) {
		
		String caracteresInvalidos= "";
		
		for (Character caractere : str.toCharArray()) {
			if (Character.isLetter(caractere)) {
				if(!caractere.equals('N') &&
						!caractere.equals('S') &&
						!caractere.equals('L') &&
						!caractere.equals('O') &&
						!caractere.equals('X')) {
					
					caracteresInvalidos += caractere;
				}
			} else if (Character.isDigit(caractere)) {
				/* 
				 * esta condição está aqui apenas para ignorar numeros,
				 * caso o caractere for uma letra inválida ele é passado
				 * para a string inválida, caso não for número e nem letra
				 * resta apenas os caracteres especiais que se existirem
				 * também serão inseridos na String inválida.
				 */
			} else {
				caracteresInvalidos += caractere;
			}
		}
		
		boolean caracterInvalido = caracteresInvalidos.length() > 0 ? true : false; 
		
		return caracterInvalido;
	}
	
	
	
	/**
	 * Se o indexDoNumero for '0' é porque a coordenada já iniciou errada
	 * pois necessita da direção antes dos passos, o 'X' antes do passo
	 * também inválida a coordenada, este método verifica se os passos
	 * são válidos ou não.
	 * @param str
	 * @return
	 */
	public boolean passosInvalidos(String str) {
		
		for (int i=0; i < str.length() ; i++) {
			
			if (Character.isDigit(str.toCharArray()[i])) {
				
				int indexDoNumero = str.indexOf(str.toCharArray()[i]);
				char charArterior = str.toCharArray().clone()[i -1];
				
				if (indexDoNumero == 0 || charArterior == 'X') {
					return true;
				}
			}
		}
		return false;
	}
	

	public void zerarPosicaoDoDrone() {
		this.x = 0;
		this.y = 0;
	}
	
	public void erro() {
		this.x = 999;
		this.y = 999;
	}
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int[] getPosicaoDrone() {
		return posicaoDrone;
	}
	
	public String stringPosicaoDoDrone() {
		return String.format("(%d, %d)", this.x, this.y);
	}
    
}
