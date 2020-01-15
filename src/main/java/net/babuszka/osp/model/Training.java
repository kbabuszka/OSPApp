package net.babuszka.osp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "training_names")
@Getter
@Setter
public class Training {

	@Autowired
	public Training() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	@Size(min = 3, message = "{training.name.size}")
	@NotEmpty(message = "{training.name.empty}")
	private String name;

	@OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
	private List<FirefighterTraining> trainings;

	@Override
	public String toString() {
		return "Training [id=" + id + ", name=" + name + "]";
	}

}
