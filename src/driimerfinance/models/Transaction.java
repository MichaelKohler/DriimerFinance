package driimerfinance.models;

import java.util.Date;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class Transaction {
	
	private Date date = null;
	private String sollKonto = null;
	private String habenKonto = null;
	private String bezeichnung = null;
	private Integer betrag = null;
	private Integer belegNr = null;
	
    public Transaction() {
        
    }
    
    public Transaction(Account positionFrom, Account positionTo, int amount) {

    }
    
    public void setFromAccount(Account account) {
        
    }
    
    public void setToAccount(Account account) {
        
    }
    
    public void setAmount(int amount) {
        
    }
    
    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
    
	private Integer id = null;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSollKonto() {
		return sollKonto;
	}

	public void setSollKonto(String sollKonto) {
		this.sollKonto = sollKonto;
	}

	public String getHabenKonto() {
		return habenKonto;
	}

	public void setHabenKonto(String habenKonto) {
		this.habenKonto = habenKonto;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public Integer getBetrag() {
		return betrag;
	}

	public void setBetrag(Integer betrag) {
		this.betrag = betrag;
	}

	public Integer getBelegNr() {
		return belegNr;
	}

	public void setBelegNr(Integer belegNr) {
		this.belegNr = belegNr;
	}
}
