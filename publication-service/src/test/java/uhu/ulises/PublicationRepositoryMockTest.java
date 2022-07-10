package uhu.ulises;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import uhu.ulises.entity.Asignatura;
import uhu.ulises.entity.Modalidad;
import uhu.ulises.entity.Publicacion;
import uhu.ulises.entity.Titulacion;
import uhu.ulises.repository.PublicacionRepository;

@DataJpaTest
@ActiveProfiles("test")
public class PublicationRepositoryMockTest {

	@Autowired
	private PublicacionRepository publicationRepository;
	
	@Test
	public void whenFindByStatus_thenReturnListPublicacion() {
		Publicacion publicacion01 = Publicacion.builder()
				.titulo("Clases de Matemáticas II")
				.descripcion("Doy clases de matemáticas II. Soy estudiante aprobado con 9.")
				.fechaPublicacion(new Date())
				.precioHora(7.5f)
				.asignatura(
						Asignatura.builder().id(1L).titulacion(
								Titulacion.builder().id(1L).nombre("Grado en Ingeniería Informática").build()
						).build())
				.modalidad(Modalidad.MIXTA)
				.status("DELETED")
				.build();
		publicationRepository.save(publicacion01);
		
		List<Publicacion> founds = publicationRepository.findByStatus("DELETED");
		Assertions.assertThat(founds.size()).isEqualTo(1);
	}
	
	@Test
	public void whenFindAll_thenReturnListPublication() {
		Publicacion publicacion01 = Publicacion.builder()
				.titulo("Clases de Matemáticas II")
				.descripcion("Doy clases de matemáticas II. Soy estudiante aprobado con 9.")
				.fechaPublicacion(new Date())
				.precioHora(7.5f)
				.asignatura(
						Asignatura.builder().id(1L).titulacion(
								Titulacion.builder().id(1L).nombre("Grado en Ingeniería Informática").build()
						).build())
				.modalidad(Modalidad.MIXTA)
				.status("CREATED")
				.build();
		publicationRepository.save(publicacion01);
		
		List<Publicacion> founds = publicationRepository.findAll();
		Assertions.assertThat(founds.size()).isEqualTo(2);
	}
	
	@Test
	public void whenFindAllByParameters_thenReturnListPublication() {
		Publicacion publicacion01 = Publicacion.builder()
				.titulo("Clases de FISICA")
				.descripcion("Doy clases de FISICA. Soy estudiante aprobado con 9.")
				.fechaPublicacion(new Date())
				.precioHora(7.5f)
				.asignatura(
						Asignatura.builder().id(2L).titulacion(
								Titulacion.builder().id(1L).nombre("Grado en Ingeniería Informática").build()
						).build())
				.modalidad(Modalidad.ONLINE)
				.status("CREATED")
				.build();
		Titulacion t = Titulacion.builder().id(1L).build();
		Asignatura a = Asignatura.builder().id(2L).build();
		publicationRepository.save(publicacion01);
		//List<Publicacion> founds = publicationRepository.findAllByParameters(t, 1, 1L, 7f, 8f, Modalidad.MIXTA, "D");
		Date d = new Date( new Date().getTime() - 86400000);
		List<Publicacion> founds = publicationRepository.findAllByParameters(t, 1, a, 7f, 8f, Modalidad.ONLINE.toString(), d);
		Assertions.assertThat(founds.size()).isEqualTo(0);
	}
}
