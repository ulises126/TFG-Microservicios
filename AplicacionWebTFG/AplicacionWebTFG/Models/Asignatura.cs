using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AplicacionWebTFG.Models
{
    public class Asignatura
    {
        public long id { get; set; }
        public string nombre { get; set; }
        public int curso { get; set; }
        public Titulacion titulacion { get; set; }
    }
}
