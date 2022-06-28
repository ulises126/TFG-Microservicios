using AplicacionWebTFG.Models;
using AplicacionWebTFG.Servicios;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace AplicacionWebTFG.Controllers
{
    public class UsuarioController : Controller
    {
        private readonly IServicio_API _servicioApi;

        public UsuarioController(IServicio_API servicioApi)
        {
            _servicioApi = servicioApi;
        }

        public async Task<IActionResult> Usuario(string usuario)
        {
            Usuario user = await _servicioApi.GetUsuario(usuario);
            //user.mediaValoraciones = await _servicioApi.GetMediaValoraciones(usuario);
            return View(user);
        }

        public async Task<IActionResult> EditarUsuario(string usuario)
        {
            Usuario user = await _servicioApi.GetUsuario(usuario);
            return View(user);
        }

        public async Task<IActionResult> Editar(string username, string nombre, string descripcion, string numeroTelefono)
        {
            if (HttpContext.Session.GetString("username") == null || !HttpContext.Session.GetString("username").Equals(username))
                return NoContent();

            Usuario user = await _servicioApi.GetUsuario(username);
            if(user == null)
                return NoContent();

            user.nombre = nombre;
            user.descripcion = descripcion;
            user.numeroTelefono = numeroTelefono;

            bool respuesta = await _servicioApi.UpdateUsuario(user, HttpContext.Session.GetString("token"));
            if(respuesta)
                return RedirectToAction("Usuario", "Usuario", new { usuario = username });

            return NoContent();
        }

    }
}
