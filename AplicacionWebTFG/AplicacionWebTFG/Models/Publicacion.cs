using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AplicacionWebTFG.Models
{
    public class Publicacion
    {
        public long id { get; set; }
        public string titulo { get; set; }
        public string descripcion { get; set; }
        public float precioHora { get; set; }
        public DateTime fechaPublicacion { get; set; }
        public string modalidad { get; set; }
        public string status { get; set; }
        public string usuario { get; set; }
        public Asignatura asignatura { get; set; }
        public List<Titulacion> listaTitulaciones { get; set; }
        public List<Asignatura> listaAsignaturas { get; set; }
    }
}
