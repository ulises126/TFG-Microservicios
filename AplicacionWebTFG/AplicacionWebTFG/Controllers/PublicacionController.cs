using AplicacionWebTFG.Servicios;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using AplicacionWebTFG.Models;
using System.Globalization;

namespace AplicacionWebTFG.Controllers
{
    public class PublicacionController : Controller
    {
        private readonly IServicio_API _servicioApi;

        public PublicacionController(IServicio_API servicioApi)
        {
            _servicioApi = servicioApi;
        }

        public async Task<IActionResult> Publicacion(int id)
        {
            Publicacion pub = await _servicioApi.GetPublicacion(id);
            if (pub == null || pub.id == null || pub.id == 0 || pub.status.Equals("DELETED"))
                return View("PublicacionEliminada");
            return View(pub);
        }

        public async Task<IActionResult> EditarPublicacion(int id)
        {
            if (String.IsNullOrEmpty(HttpContext.Session.GetString("token")))
                return NoContent();
            Publicacion pub = await _servicioApi.GetPublicacion(id);
            return View(pub);
        }

        // GET: PublicacionController/Edit/5
        public async Task<IActionResult> Editar(int id, string titulo, string descripcion, string precioHora, string modalidad)
        {
            Publicacion pub = await _servicioApi.GetPublicacion(id);
            if (pub == null)
                return NoContent();

            if (HttpContext.Session.GetString("username") == null || !HttpContext.Session.GetString("username").Equals(pub.usuario))
                return NoContent();

            pub.id = id;
            pub.titulo = titulo;
            pub.descripcion = descripcion;
            precioHora = precioHora.Replace(",", ".");
            pub.precioHora = float.Parse(precioHora, CultureInfo.InvariantCulture);
            pub.modalidad = modalidad;

            bool resultado = await _servicioApi.UpdatePublicacion(pub, HttpContext.Session.GetString("token"));
            if (!resultado)
                return NoContent();

            return RedirectToAction("Publicacion", "Publicacion", new { id = id });
                
        }

        // GET: PublicacionController/Delete/5
        public async Task<IActionResult> Eliminar(int id)
        {
            bool resultado = await _servicioApi.DeletePublicacion(id, HttpContext.Session.GetString("token"));
            if (resultado)
                return RedirectToAction("Index", "Home");
            else
                return NoContent();
        }

        [HttpPost]
        public async Task<IActionResult> CrearPublicacion()
        {
            if (String.IsNullOrEmpty(HttpContext.Session.GetString("token")))
                return NoContent();
            Publicacion pub = new Publicacion();
            pub.listaAsignaturas = await _servicioApi.GetListaAsignaturas();
            pub.listaTitulaciones = await _servicioApi.GetListaTitulaciones();
            return View(pub);
        }

        [HttpPost]
        public async Task<IActionResult> Crear(string titulo, string descripcion, string precioHora, int asignatura, string modalidad)
        {
            if (String.IsNullOrEmpty(HttpContext.Session.GetString("token")) ||
                String.IsNullOrEmpty(HttpContext.Session.GetString("username")))
                return NoContent();

            Asignatura asig = new Asignatura();
            asig.id = asignatura;
            Publicacion pub = new Publicacion();
            pub.titulo = titulo;
            pub.descripcion = descripcion;
            pub.usuario = HttpContext.Session.GetString("username");
            precioHora = precioHora.Replace(",", ".");
            pub.precioHora = float.Parse(precioHora, CultureInfo.InvariantCulture);
            pub.modalidad = modalidad;
            pub.asignatura = asig;

            string token = HttpContext.Session.GetString("token");

            long id = await _servicioApi.SavePublicacion(pub, token);

            if(id != 0)
                return RedirectToAction("Publicacion", "Publicacion", new { id = Convert.ToInt32(id)});

            return NoContent();
        }

        public async Task<IActionResult> ListaPublicaciones(string usuario)
        {
            Usuario user = await _servicioApi.GetUsuario(usuario);
            if(user == null)
                return NoContent();
            ListaPublicaciones listaModelo = new ListaPublicaciones();
            listaModelo.listaPublicaciones = await _servicioApi.GetListaPublicacionesByUsuario(usuario);
            listaModelo.usuario = usuario;
            listaModelo.nombre = user.nombre;
            if (listaModelo.listaPublicaciones == null)
                listaModelo.listaPublicaciones = new List<Publicacion>();
            return View(listaModelo);
        }
    }
}
