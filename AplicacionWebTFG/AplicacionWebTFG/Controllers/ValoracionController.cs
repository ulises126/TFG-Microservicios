using AplicacionWebTFG.Models;
using AplicacionWebTFG.Servicios;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Globalization;

namespace AplicacionWebTFG.Controllers
{
    public class ValoracionController : Controller
    {
        private readonly IServicio_API _servicioApi;

        public ValoracionController(IServicio_API servicioApi)
        {
            _servicioApi = servicioApi;
        }

        public async Task<IActionResult> ListaValoraciones(string usuario)
        {
            ListaValoraciones modelo = new ListaValoraciones();
            modelo.username = usuario;
            Usuario user = await _servicioApi.GetUsuario(usuario);
            if(user != null)
                modelo.nombre = user.nombre;
            //modelo.valoracionMedia = await _servicioApi.GetMediaValoraciones(usuario);
            modelo.valoracionMedia = user.mediaValoraciones;
            List<Valoracion> listaValoraciones = await _servicioApi.GetValoraciones(usuario);
            foreach (var item in listaValoraciones)
            {
                Publicacion pub = await _servicioApi.GetPublicacion(Convert.ToInt32(item.valoracionIdentity.idPublicacion));
                if (pub != null)
                {
                    item.tituloPublicacion = pub.titulo;
                    user = await _servicioApi.GetUsuario(item.valoracionIdentity.usuarioConsumidor);
                    if(user != null)
                    {
                        item.nombre = user.nombre;
                    }
                }
            }
            modelo.listaValoraciones = listaValoraciones;
            return View(modelo);
        }

        public async Task<IActionResult> AnadirValoracion(long id)
        {
            if (HttpContext.Session.GetString("username") == null)
                return NoContent();

            Publicacion pub = await _servicioApi.GetPublicacion(Convert.ToInt32(id));
            if(pub == null)
                return NoContent();

            if (HttpContext.Session.GetString("username").Equals(pub.usuario))
                return NoContent();

            Valoracion val = new Valoracion();
            val.tituloPublicacion = pub.titulo;
            val.valoracionIdentity = new ValoracionIdentity();
            val.valoracionIdentity.idPublicacion = Convert.ToInt32(id);
            val.valoracionIdentity.usuarioConsumidor = HttpContext.Session.GetString("username");

            return View(val);
        }

        public async Task<IActionResult> Create(string puntuacion, string comentario, long idPublicacion, string usuarioConsumidor)
        {
            if (String.IsNullOrEmpty(HttpContext.Session.GetString("token")))
                return NoContent();

            Valoracion val = new Valoracion();
            val.valoracionIdentity = new ValoracionIdentity();
            val.valoracionIdentity.idPublicacion = idPublicacion;
            val.valoracionIdentity.usuarioConsumidor = usuarioConsumidor;
            val.comentario = comentario;
            val.puntuacion = float.Parse(puntuacion, CultureInfo.InvariantCulture);

            if (!await _servicioApi.AnadirValoracion(val, HttpContext.Session.GetString("token")))
                return NoContent();

            return RedirectToAction("Publicacion", "Publicacion", new { id = idPublicacion });
        }
    }
}
