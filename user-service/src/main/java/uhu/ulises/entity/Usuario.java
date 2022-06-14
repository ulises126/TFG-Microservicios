package uhu.ulises.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

	@Id
	@Size(min = 4, max = 32)
	private String username;
	
	@NotBlank
	private String nombre;
	
	private String descripcion;
	
	@NotBlank
	@Column(unique=true)
	@Email
	private String email;
	
	@NotNull
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	
	@NotNull
	@Column(name = "fecha_registro")
	private Date fechaRegistro;
	
	@Size(min = 9, max = 9)
	@Column(name = "numero_telefono")
	private String numeroTelefono;
	
}
