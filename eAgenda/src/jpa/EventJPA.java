/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * JPA Class EventJPA
 */
@Entity
@Table(name="event", catalog="infogroup2eagenda")
public class EventJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	//OBLIGATORI SINO HIBERNATE NO UTILITZA ELS NUMEROS DE SEQUENCIA DE POSTGRE
	@Id
    @SequenceGenerator(name="infogroup2eagenda.event_id_seq", sequenceName="infogroup2eagenda.event_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.event_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	
	private String name;

	private String description;
	
	@Column(name="\"initDate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date initDate;

	@Column(name="\"endDate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	//Associacio bidireccional MaN amb categories
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="eventcategory", catalog="infogroup2eagenda",
		joinColumns = @JoinColumn(name="idevent", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name="idcategory", referencedColumnName = "id")
	)
	private List<CategoryJPA> categoriesEvent;
	//Associacio bidireccional Na1 amb address
	@ManyToOne
	private AddressJPA address;

	private float ratting;
	private String picture;
	
	@OneToMany(mappedBy="eventWords")
	private List<WordsJPA> wordsEvent;
	
	@OneToMany(mappedBy="eventComment")
	private List<CommentJPA> commentsEvent;
	
	@OneToMany(mappedBy="userRatting")
	private List<RattingJPA> rattingsEvent;
	

	@OneToMany(mappedBy="event")
	private List<FavoriteJPA> favorites;

	@OneToMany(mappedBy="eventOrder")
	private List<OrderJPA> ordersEvent;
	
	@ManyToOne
	private CompanyJPA company;

	
	//private Picture image;
	public EventJPA() {
		//TODO: Veure si podem reduir l'acoblament amb injeccio de dependencies
		this.categoriesEvent = new ArrayList<CategoryJPA>();
		//Quant creem un event el seu ratting sera 0
		this.ratting = 0;
		this.wordsEvent = new ArrayList<WordsJPA>();
	}

	/**
	 *  Getters/Setters
	 */
	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}


	public Date getEndDate() {
		return endDate;
	}


	public Date getInitDate() {
		return initDate;
	}


	public List<CategoryJPA> getCategoriesEvent() {
		return categoriesEvent;
	}
	
	public List<WordsJPA> getWordsEvent() {
		return wordsEvent;
	}


	public AddressJPA getAddress() {
		return address;
	}

	public float getRatting() {
		return ratting;
	}

	public String getPicture() {
		return picture;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
	
	public void setAddress(AddressJPA address) {
		this.address = address;
	}

	public void setRatting(float ratting) {
		this.ratting = ratting;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setCategoriesEvent(List<CategoryJPA> categoriesEvent) {
		this.categoriesEvent = categoriesEvent;
	}	
	
	public void setWordsEvent(List<WordsJPA> wordsEvent) {
		this.wordsEvent = wordsEvent;
	}

	public List<String> getNamesCategoriesEvent(){
		Iterator<CategoryJPA> iterador = this.categoriesEvent.iterator();
		List<String> list = new ArrayList<String>();
		while(iterador.hasNext()){
			list.add(iterador.next().getName());
		}
		return list;
	}
	
	public List<FavoriteJPA> getFavorites() {
		return this.favorites;
	}

	public void setFavorites(List<FavoriteJPA> favorites) {
		this.favorites = favorites;
	}

	public FavoriteJPA addFavorite(FavoriteJPA favorite) {
		getFavorites().add(favorite);
		favorite.setEvent(this);

		return favorite;
	}

	public FavoriteJPA removeFavorite(FavoriteJPA favorite) {
		getFavorites().remove(favorite);
		favorite.setEvent(null);

		return favorite;
	}	
	
	public CompanyJPA getCompany() {
		return company;
	}

	public void setCompany(CompanyJPA company) {
		this.company = company;
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
		EventJPA other = (EventJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}
		

	/**
	 *  toString() nomes per a debuggar
	 */	
	@Override
	public String toString() {
		return "EventJPA [id=" + id + ", name=" + name + ", description="
				+ description + ", endDate=" + endDate + ", initDate="
				+ initDate + ", categoriesEvent=" + categoriesEvent + "]";
	}

	
}
