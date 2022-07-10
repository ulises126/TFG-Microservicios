using AplicacionWebTFG.Servicios;
using AplicacionWebTFG.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace AplicacionWebTFG.Controllers
{

    public class IniciarSesionController : Controller
    {

        private readonly IServicio_API _servicioApi;

        public IniciarSesionController(IServicio_API servicioApi)
        {
            _servicioApi = servicioApi;
        }

        // GET: IniciarSesionController
        public ActionResult IniciarSesion()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> GetToken(string username, string password)
        {
            Token token = new Token();

            token = await _servicioApi.Autenticar(username, password);

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
