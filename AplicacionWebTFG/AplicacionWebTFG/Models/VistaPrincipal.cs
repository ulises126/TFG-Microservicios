using AplicacionWebTFG.Models;

namespace AplicacionWebTFG.Models
{
    public class VistaPrincipal
    {
        public List<Publicacion> listaPublicaciones { get; set; }
        public List<Titulacion> listaTitulaciones { get; set; }
        public List<Asignatura> listaAsignaturas { get; set; }

        public int? tF { get; set; }
        public int? cF { get; set; }
        public int? aF { get; set; }
        public float? pMinF { get; set; }
        public float? pMaxF { get; set; }
        public string mF { get; set; }
        public string antF { get; set; }
    }
}
