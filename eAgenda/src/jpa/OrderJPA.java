package jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JPA Class OrderJPA
 */
//Table is named Orders to avoid Postgre SQL name problems
@Entity
@Table(name="infogroup2eagenda.orders")
public class OrderJPA implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="infogroup2eagenda.orders_id_seq",  sequenceName="infogroup2eagenda.orders_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.orders_id_seq")
    @Column(name = "id", updatable=false)
	private int id;
	private Date data;
	private int numberoftickets;
	
	@ManyToOne	
	private EventJPA eventOrder;
	
	@ManyToOne	
	private UserJPA userOrder;
	
	
	/**
	 * Class constructor methods
	 */
	public OrderJPA() {
		this.data = new Date();
	}

	/**
	 *  Getters/Setters
	 */	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public int getNumberoftickets() {
		return numberoftickets;
	}
	
	public void setNumberoftickets(int tickets) {
		this.numberoftickets = tickets;
	}
	
	public EventJPA getEventOrder() {
		return eventOrder;
	}

	public void setEventOrder(EventJPA eventOrder) {
		this.eventOrder = eventOrder;
	}

	public UserJPA getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(UserJPA userOrder) {
		this.userOrder = userOrder;
	}
	
	/**
	 *  Metodes per acabar el POJO
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderJPA other = (OrderJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	/**
	 *  toString() nomes per a debuggar
	 */	
	@Override
	public String toString() {
		return "OrderJPA [id=" + id + ", data=" + data + ", numberOfTickets="
				+ numberoftickets + "]";
	}
}
