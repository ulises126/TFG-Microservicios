using AplicacionWebTFG.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AplicacionWebTFG.Servicios
{
    public interface IServicio_API
    {
        #region Auth-service

        Task<Token> Autenticar(string username, string password);
        Task<Token> Registrarse(Usuario usuario);

        #endregion

        #region Publication.service

        Task<List<Publicacion>> GetListaPublicaciones();
        Task<List<Publicacion>> GetListaPublicacionesByUsuario(string usuario);
        Task<List<Publicacion>> GetListaPublicacionesParam(int? t, int? c, int? a, int? pMin, int? pMax, string m, string ant);
        Task<List<Asignatura>> GetListaAsignaturas(int titulacion, int curso);
        Task<List<Asignatura>> GetListaAsignaturas();
        Task<List<Titulacion>> GetListaTitulaciones();
        Task<Publicacion> GetPublicacion(int publicacion);
        Task<long> SavePublicacion(Publicacion publicacion, string token);
        Task<bool> UpdatePublicacion(Publicacion publicacion, string token);
        Task<bool> DeletePublicacion(int publicacion, string token);

        #endregion

        #region User-service

        Task<Usuario> GetUsuario(string usuario);

        Task<bool> UpdateUsuario(Usuario usuario, string token);

        #endregion

        #region Valoration-service

        Task<float> GetMediaValoraciones(string usuario);
        Task<List<Valoracion>> GetValoraciones(string usuario);
        Task<bool> AnadirValoracion(Valoracion valoracion, string token);
        #endregion

    }
}
