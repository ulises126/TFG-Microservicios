using AplicacionWebTFG.Servicios;
using AplicacionWebTFG.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace AplicacionWebTFG.Controllers
{
    public class RegistrarseController : Controller
    {
        private readonly IServicio_API _servicioApi;

        public RegistrarseController(IServicio_API servicioApi)
        {
            _servicioApi = servicioApi;
        }

        public ActionResult Registrarse()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> RegistrarUsuario(string username, string password, string nombre, string email, string numeroTelefono, string descripcion, DateTime fechaNacimiento)
        {
            Token token = new Token();

            Usuario usuario = new Usuario();
            usuario.username = username;
            usuario.password = password;
            usuario.nombre = nombre;
            usuario.email = email;
            usuario.numeroTelefono = numeroTelefono;
            usuario.descripcion = descripcion;
            usuario.fechaNacimiento = fechaNacimiento;

            token = await _servicioApi.Registrarse(usuario);

            if (token != null && !string.IsNullOrEmpty(token.token))
            {
                HttpContext.Session.SetString("username", username);
                HttpContext.Session.SetString("token", token.token);
                return RedirectToAction("Index", "Home");
            }
            else
                return NoContent();
        }
    }
}
