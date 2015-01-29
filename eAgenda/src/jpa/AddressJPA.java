/*
 * Copyright UOC.  All rights reserved.
 */
package jpa;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * JPA Class AddressJPA
 */
@Entity
@Table(name="infogroup2eagenda.address")
public class AddressJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	//OBLIGATORI SINO HIBERNATE NO UTILITZA ELS NUMEROS DE SEQUENCIA DE POSTGRE
	@Id
    @SequenceGenerator(name="infogroup2eagenda.address_id_seq", sequenceName="infogroup2eagenda.address_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.address_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	private String street;
	private String number;
	private String city;
	private String state;
	private String country;
	private String zip;
	
	//Associacio bidireccional 1aN amb Event
	//S'escull Set com a colleccio ja que l'ordre no es important
	@OneToMany(mappedBy="address")
	private Set<EventJPA> events;
	
	@OneToMany(mappedBy="address")
	private Set<UserJPA> users;
	/**
	 * Class constructor methods
	 */
	public AddressJPA() {
		
	}
	
	public AddressJPA(String street, String number, String city, String state, String country, String zip) {		
		this.street = street;
		this.number = number;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
	}
	

	public int getId() {
		return id;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Set<EventJPA> getEvents() {
		return events;
	}

	public void setEvents(Set<EventJPA> events) {
		this.events = events;
	}
	
	public String getFullAdress(){
		return this.street + " " + this.number + " " + this.city + " " + this.state + " " + this.country + " " + this.zip;
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
		AddressJPA other = (AddressJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 *  toString() nomes per a debuggar
	 */	
	@Override
	public String toString() {
		return   street + " " + number + " " + city + " " + state + " " + country + " " + zip;
				
	}
}