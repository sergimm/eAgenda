package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import jpa.UserJPA;
import jpa.OrderJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface OrderFacadeRemote {
	public Collection<?> findAllOrders();
	public OrderJPA showOrder(int id);
	public void addOrder(int idEvent, int idUser, int numTickets);
	public Collection<OrderJPA> findOrdersByUser(UserJPA user);
}
