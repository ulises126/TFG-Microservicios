using AplicacionWebTFG.Servicios;
using AplicacionWebTFG.Models;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;

namespace AplicacionWebTFG.Controllers
{
    public class HomeController : Controller
    {
        //private readonly ILogger<HomeController> _logger;

        //public HomeController(ILogger<HomeController> logger)
        //{
        //    _logger = logger;
        //}

        private readonly IServicio_API _servicioApi;

        public HomeController(IServicio_API servicioApi)
        {
            _servicioApi = servicioApi;
        }

        public async Task<IActionResult> Index(int? t, int? c, int? a, int? pMin, int? pMax, string m, string ant)
        {
            VistaPrincipal vp = new VistaPrincipal();
            vp.listaPublicaciones = await _servicioApi.GetListaPublicacionesParam(t, c, a, pMin, pMax, m, ant);
            vp.listaTitulaciones = await _servicioApi.GetListaTitulaciones();
            vp.listaAsignaturas = await _servicioApi.GetListaAsignaturas();
            return View(vp);
        }

        public async Task<IActionResult> CerrarSesion()
        {
            HttpContext.Session.SetString("username", "");
            HttpContext.Session.SetString("token", "");
            return RedirectToAction("Index");
        }

        //public async Task<IActionResult> Publicacion(int idProducto)
        //{
        //    Publicacion producto = new Publicacion();
        //    ViewBag.Accion = "Nuevo producto";
        //    if(idProducto != 0)
        //    {
        //        producto = await _servicioApi.getPublicacion(idProducto);
        //        ViewBag.Accion = "Editar producto";
        //    }

        //    return View(producto);
        //}

        //[HttpPost]
        //public async Task<IActionResult> GuardarCambios(Publicacion publicacion)
        //{
        //    //bool respuesta;

        //    ////if(publicacion.id == 0)
        //    ////    respuesta = await _servicioApi.savePublicacion(publicacion);
        //    ////else
        //    ////    respuesta = await _servicioApi.updatePublicacion(publicacion);

        //    //if (respuesta)
        //    //    return RedirectToAction("Index");
        //    //else
        //        return NoContent();
        //}

        //[HttpGet]
        //public async Task<IActionResult> Eliminar(int publicacion)
        //{
        //    //var respuesta = await _servicioApi.deletePublicacion(publicacion);

        //    //if (respuesta)
        //    //    return RedirectToAction("Index");
        //    //else
        //        return NoContent();
        //}

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}