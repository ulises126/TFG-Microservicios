namespace AplicacionWebTFG.Models
{
    public class Usuario
    {
        public string username { get; set; }
        public string password { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public string email { get; set; }
        public DateTime fechaNacimiento { get; set; }
        public DateTime fechaRegistro { get; set; }
        public string numeroTelefono { get; set; }
        public int numeroValoraciones { get; set; }
        public float mediaValoraciones { get; set; }
    }
}
