package mx.spm.servicio.users.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 20)
	private String user;
	
	@Column(length = 60)
	private String password;
	
	private Boolean enabled;
	
	private String nombre;
	
	private String apellido;
	
	@Column(unique = true, length = 100)
	private String email;
	
	@ManyToMany(fetch = FetchType.LAZY)
	//Personalizando tabla intermedia
	@JoinTable(name = "usuarios_roles", 
			   joinColumns = @JoinColumn(name = "usuario_id"),
			   inverseJoinColumns = @JoinColumn(name = "rol_id"),
			   //Configurando para que no haya roles repetidos
			   uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol_id"})})
			   
	private List<Rol> roles;
	
	private static final long serialVersionUID = -2325882570043795616L;
}
