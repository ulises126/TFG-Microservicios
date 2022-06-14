package uhu.ulises.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "valoraciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Valoracion {
	
	@EmbeddedId
	private ValoracionIdentity valoracionIdentity;
	
	@Min(1)
	@Max(5)
	private int puntuacion;
	
	@Size(min = 0, max = 511)
	private String comentario;
	
	@Column(name = "fecha_valoracion")
	@NotNull
	private Date fechaValoracion;
}
