package it.cyberSec.cruscotto.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "statologin")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StatoLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3936494120566827449L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String stato;
    
	public StatoLogin(String stato) {
		this.stato = stato;
	}
  
	public StatoLogin() {
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


    

}
