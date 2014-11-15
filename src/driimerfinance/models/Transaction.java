package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class Transaction implements IModel {
	
	private Integer id = null;
	private java.sql.Date date = null;
	private int fk_fromAccount = 0;
	private int fk_toAccount = 0;
	private String description = null;
	private Integer amount = null;
	private Integer receiptNumber = null;
	
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
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.sql.Date getDate() {
		return this.date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public int getFk_SollKonto() {
		return this.fk_fromAccount;
	}

	public void setFk_SollKonto(int sollKonto) {
		this.fk_fromAccount = sollKonto;
	}

	public int getFk_HabenKonto() {
		return this.fk_toAccount;
	}

	public void setFk_HabenKonto(int habenKonto) {
		this.fk_toAccount = habenKonto;
	}

	public String getBezeichnung() {
		return this.description;
	}

	public void setBezeichnung(String description) {
		this.description = description;
	}

	public Integer getBetrag() {
		return this.amount;
	}

	public void setBetrag(Integer betrag) {
		this.amount = betrag;
	}

	public Integer getBelegNr() {
		return this.receiptNumber;
	}

	public void setBelegNr(Integer receipt) {
		this.receiptNumber = receipt;
	}
}
