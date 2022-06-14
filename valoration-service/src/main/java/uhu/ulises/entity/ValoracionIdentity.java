package uhu.ulises.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionIdentity implements Serializable {

	private Long idPublicacion;
	
	@Size(min = 4, max = 32)
	private String usuarioConsumidor;
}
