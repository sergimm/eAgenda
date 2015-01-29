package jpa;

import java.io.Serializable;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * JPA Class UserJPA
 */
//Table is named Users to avoid Postgre SQL name problems
@Entity
@Table(name="infogroup2eagenda.users")
public class UserJPA implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="infogroup2eagenda.users_id_seq", sequenceName="infogroup2eagenda.users_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="infogroup2eagenda.users_id_seq")
	@Column(name="id", unique=true, nullable=false)
	private int id;
	
	private String nif;
	
	private String name;
	
	private String surname;
	
	@Column(name="\"preferedLanguage\"")
	private String preferedLanguage;
	
	private String password;
	
	private String email;
	@ManyToOne
	private AddressJPA address;
	
	@OneToMany(mappedBy="userComment")
	private List<CommentJPA> commentsUser;
	
	@OneToMany(mappedBy="userRatting")
	private List<RattingJPA> rattingsUser;
	
	@OneToMany(mappedBy="user")
	private List<FavoriteJPA> favorites;
	
	@OneToMany(mappedBy="userOrder")
	private List<OrderJPA> ordersUser;
	
	public UserJPA() {
	}
	
	public UserJPA (String nif, String name, String surname, String language, String pass, 
			String mail, AddressJPA address){
		this.nif=nif;
		this.name=name;
		this.surname=surname;
		this.preferedLanguage=language;
		this.password=pass;
		this.email=mail;
		this.address=address;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AddressJPA getAddress() {
		return address;
	}

	public void setAddress(AddressJPA address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPreferedLanguage() {
		return this.preferedLanguage;
	}

	public void setPreferedLanguage(String preferedLanguage) {
		this.preferedLanguage = preferedLanguage;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public List<FavoriteJPA> getFavorites() {
		return this.favorites;
	}

	public void setFavorites(List<FavoriteJPA> favorites) {
		this.favorites = favorites;
	}

	public FavoriteJPA addFavorite(FavoriteJPA favorite) {
		getFavorites().add(favorite);
		favorite.setUser(this);

		return favorite;
	}

	public FavoriteJPA removeFavorite(FavoriteJPA favorite) {
		getFavorites().remove(favorite);
		favorite.setUser(null);

		return favorite;
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
		UserJPA other = (UserJPA) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
