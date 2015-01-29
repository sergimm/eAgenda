package jpa;

import java.io.Serializable;

import javax.persistence.*;


/**
 * JPA Class FavoritesJPA
 */
@Entity
@Table(name="infogroup2eagenda.favorites")
public class FavoriteJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="infogroup2eagenda.favorites_id_seq", sequenceName="infogroup2eagenda.favorites_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.favorites_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	//bi-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name="event_id")
	private EventJPA event;

	//bi-directional many-to-one association to User
	@ManyToOne
	private UserJPA user;

	public FavoriteJPA() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EventJPA getEvent() {
		return this.event;
	}

	public void setEvent(EventJPA event) {
		this.event = event;
	}

	public UserJPA getUser() {
		return this.user;
	}

	public void setUser(UserJPA user) {
		this.user = user;
	}
	
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
		FavoriteJPA other = (FavoriteJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}

}