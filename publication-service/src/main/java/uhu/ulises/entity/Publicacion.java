package uhu.ulises.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="publicaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Publicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=3, max=64)
	private String titulo;
	
	@Size(min=0, max=512)
	private String descripcion;
	
	@Column(name = "precio_hora")
	@Min(0)
	@Max(50)
	private float precioHora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	private Asignatura asignatura;
	
	@Column(name = "fecha_publicacion")
	private Date fechaPublicacion; 
	
	@Enumerated(value = EnumType.STRING)
	private Modalidad modalidad;
	
	@NotBlank
	private String status;
	
	@Size(min = 1, max = 32)
	private String usuario;
	
}
