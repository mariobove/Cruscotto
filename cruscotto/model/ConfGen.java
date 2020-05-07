package it.cyberSec.cruscotto.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "confgen", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"chiave","valore"})
})
public class ConfGen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1693148049800595704L;

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotBlank
    @Size(min=3, max = 50)
	private String chiave;
	
    @NotBlank
    @Size(min=3, max = 50)
	private String valore;
    
    private Boolean deleted;

    public ConfGen(String chiave, String valore, Boolean deleted) {
    	this.chiave = chiave;
    	this.valore = valore;
    	this.deleted = deleted;
    }
    
    public ConfGen() {}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
    
    
    

    
    
    
}
