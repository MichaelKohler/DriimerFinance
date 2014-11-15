package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class Transaction {
	
	private Integer id = null;
	private java.sql.Date date = null;
	private int Fk_sollKonto = 0;
	private int Fk_habenKonto = 0;
	private String bezeichnung = null;
	private Integer betrag = null;
	private Integer belegNr = null;
	
    public Transaction() {
        // TODO: create ID automatically from DB
    }
    
    public Transaction(Account positionFrom, Account positionTo, int amount) {

    }
    
    public void setAmount(int amount) {
        
    }
    
    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
    
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public int getFk_SollKonto() {
		return Fk_sollKonto;
	}

	public void setFk_SollKonto(int sollKonto) {
		this.Fk_sollKonto = sollKonto;
	}

	public int getFk_HabenKonto() {
		return Fk_habenKonto;
	}

	public void setFk_HabenKonto(int habenKonto) {
		this.Fk_habenKonto = habenKonto;
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
