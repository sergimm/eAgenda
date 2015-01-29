package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.EventJPA;
import jpa.OrderJPA;
import jpa.UserJPA;

@Stateless
public class OrderFacadeBean implements OrderFacadeRemote, OrderFacade{

	//Persistence Unit Context
	@PersistenceContext(unitName="infogroup2eagenda") 
	private EntityManager entman;

	@SuppressWarnings("unchecked")
	@Override
	public Collection<?> findAllOrders() {
		try{
			Collection<OrderJPA> allOrder= entman.createQuery("from OrderJPA").getResultList();
			return allOrder;	
		}catch (PersistenceException e) {
			throw e;
		}
	}

	@Override
	public OrderJPA showOrder(int id) {
		OrderJPA order = null;
		try	{
			order = entman.find(OrderJPA.class, id);
		}catch (PersistenceException e) {
			throw e;
		}
	    return order;
	}

	@Override
	public void addOrder(int idEvent, int idUser, int numTickets) {
		EventJPA event = null;
		UserJPA user = null;
		OrderJPA order = new OrderJPA();
		try	{
				event = entman.find(EventJPA.class, idEvent);
				user = entman.find(UserJPA.class, idUser);
				order.setUserOrder(user);
				order.setEventOrder(event);
				order.setNumberoftickets(numTickets);
				entman.joinTransaction();
				entman.persist(order);
				entman.flush();				
		}catch (PersistenceException e) {
			throw e;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<OrderJPA> findOrdersByUser(UserJPA user) {
		Collection <OrderJPA> orders = entman.createQuery(
				"Select o from OrderJPA o where o.userOrder = :user")
				.setParameter("user", user)
				.getResultList();
		return orders;
	}

}