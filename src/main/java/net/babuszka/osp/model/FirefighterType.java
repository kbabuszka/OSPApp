package net.babuszka.osp.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "firefighter_types")
public class FirefighterType {

	@Autowired
	public FirefighterType() {
		super();
	}

	public FirefighterType(
			@Size(min = 3, message = "{firefightertype.name.size}") @NotEmpty(message = "{firefightertype.name.empty}") String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	@Size(min = 3, message = "{firefightertype.name.size}")
	@NotEmpty(message = "{firefightertype.name.empty}")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
