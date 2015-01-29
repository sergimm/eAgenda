package ejb;

import java.util.Collection;

import javax.ejb.Local;

import jpa.OrderJPA;
import jpa.UserJPA;

/**
 * Session EJB Local Interfaces
 */
@Local
public interface OrderFacade {
	public Collection<?> findAllOrders();
	public OrderJPA showOrder(int id);
	public void addOrder(int idEvent, int idUser, int numTickets);
	public Collection<OrderJPA> findOrdersByUser(UserJPA user);
}
