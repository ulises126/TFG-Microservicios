namespace AplicacionWebTFG.Models
{
    public class Valoracion
    {
        public string comentario { get; set; }
        public float puntuacion { get; set; }
        public ValoracionIdentity valoracionIdentity { get; set; }
        public DateTime fechaValoracion;
        public string tituloPublicacion { get; set; }
        public string nombre;
        
        public long idPublicacion
        {
            set
            {
                if (this.valoracionIdentity == null)
                    this.valoracionIdentity = new ValoracionIdentity();

                this.valoracionIdentity.idPublicacion = value;
            }
            get
            {
                if (this.valoracionIdentity == null)
                    return 0;

                return this.valoracionIdentity.idPublicacion;
            }
        }

        public string usuarioConsumidor
        {
            set
            {
                if (this.valoracionIdentity == null)
                    this.valoracionIdentity = new ValoracionIdentity();

                this.valoracionIdentity.usuarioConsumidor = value;
            }
            get
            {
                if (this.valoracionIdentity == null)
                    return null;

                return this.valoracionIdentity.usuarioConsumidor;
            }
        }
    }
}
