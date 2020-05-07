package it.cyberSec.cruscotto.dto;

import java.io.Serializable;

public class RequestRegistrazione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3320725817390526709L;
	
	private Long idUtenteDaregistrare;
	
	private int operazione;
	
	private String ruoloselezionato;
		
	
	

	public int getOperazione() {
		return operazione;
	}

	public void setOperazione(int operazione) {
		this.operazione = operazione;
	}

	public Long getIdUtenteDaregistrare() {
		return idUtenteDaregistrare;
	}

	public void setIdUtenteDaregistrare(Long idUtenteDaregistrare) {
		this.idUtenteDaregistrare = idUtenteDaregistrare;
	}

	public String getRuoloselezionato() {
		return ruoloselezionato;
	}

	public void setRuoloselezionato(String ruoloselezionato) {
		this.ruoloselezionato = ruoloselezionato;
	}


}
