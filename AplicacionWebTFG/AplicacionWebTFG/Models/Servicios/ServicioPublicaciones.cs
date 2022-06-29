using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Newtonsoft.Json;
using System.Net.Http.Headers;
using System.Text;
using System.Net.Http;
using AplicacionWebTFG.Models;
using Microsoft.AspNetCore.WebUtilities;

namespace AplicacionWebTFG.Servicios
{
    public class ServicioPublicaciones : IServicio_API
    {


        #region Constructor y parámetros

        private static string _baseUrl;

        public ServicioPublicaciones()
        {
            var builder = new ConfigurationBuilder().SetBasePath(Directory.GetCurrentDirectory()).AddJsonFile("appsettings.json").Build();
            _baseUrl = builder.GetSection("ApiSettings:baseUrl").Value;
        }

        #endregion

        #region Auth-service

        public async Task<Token> Autenticar(string username, string password)
        {
            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var credenciales = new Credencial() { username = username, password = password };
            var content = new StringContent(JsonConvert.SerializeObject(credenciales), Encoding.UTF8, "application/json");
            var response = await cliente.PostAsync("auth/login", content);
            var json_response = await response.Content.ReadAsStringAsync();
            var resultado = JsonConvert.DeserializeObject<Token>(json_response);
            return resultado;
        }

        #endregion

        #region Publication-service

        public async Task<List<Publicacion>> GetListaPublicaciones()
        {
            List<Publicacion> lista = new List<Publicacion>();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync("publicaciones/sinparametros");

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                lista = JsonConvert.DeserializeObject<List<Publicacion>>(json_response);
            }

            if(lista != null)
                lista = lista.OrderByDescending(p => p.fechaPublicacion).Select(p => p).ToList();

            return lista;
        }

        public async Task<List<Publicacion>> GetListaPublicacionesParam(int? t, int? c, int? a, int? pMin, int? pMax, string m, string ant)
        { //los parametros no estan hechos correctamente
            List<Publicacion> lista = new List<Publicacion>();

            var cliente = new HttpClient();


            var query = new Dictionary<string, string>();

            if (t != 0 && t != null)
                query.Add("titulacion", t.ToString());
            if (c != 0 && c != null)
                query.Add("curso", c.ToString());
            if (a != 0 && a != null)
                query.Add("asignatura", a.ToString());
            if (pMin != 0 && pMin != null)
                query.Add("precioMin", pMin.ToString());
            if (pMax != 0 && pMax != null)
                query.Add("precioMax", pMax.ToString());
            if (!String.IsNullOrEmpty(m))
                query.Add("modalidad", m);
            if (!String.IsNullOrEmpty(ant))
                query.Add("antiguedad", ant);

            var uri = QueryHelpers.AddQueryString(_baseUrl + "publicaciones/", query);
            //cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync(uri);

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                lista = JsonConvert.DeserializeObject<List<Publicacion>>(json_response);
            }

            if (lista != null)
                lista = lista.OrderByDescending(p => p.fechaPublicacion).Select(p => p).ToList();

            return lista;
        }

        public async Task<List<Publicacion>> GetListaPublicacionesByUsuario(string usuario)
        {
            List<Publicacion> lista = new List<Publicacion>();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"publicaciones/usuario/{usuario}");

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                lista = JsonConvert.DeserializeObject<List<Publicacion>>(json_response);
            }

            if (lista != null)
                lista = lista.OrderByDescending(p => p.fechaPublicacion).Select(p => p).ToList();

            return lista;
        }

        public async Task<Publicacion> GetPublicacion(int publicacion)
        {
            Publicacion objetoPublicacion = new Publicacion();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"publicaciones/{publicacion}");

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                objetoPublicacion = JsonConvert.DeserializeObject<Publicacion>(json_response);
            }

            return objetoPublicacion;
        }

        public async Task<long> SavePublicacion(Publicacion publicacion, string token)
        {
            //this.Autenticar();
            long id = 0;

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            if (!String.IsNullOrEmpty(token))
            {
                cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            }
            var content = new StringContent(JsonConvert.SerializeObject(publicacion), Encoding.UTF8, "application/json");
            var response = await cliente.PostAsync($"publicaciones", content);

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                Publicacion nuevaPublicacion = JsonConvert.DeserializeObject<Publicacion>(json_response);
                if(nuevaPublicacion != null)
                    id = nuevaPublicacion.id;
            }

            return id;
        }

        public async Task<bool> UpdatePublicacion(Publicacion publicacion, string token)
        {
            //this.Autenticar();
            bool respuesta = false;

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            if (!String.IsNullOrEmpty(token))
            {
                cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            }
            var content = new StringContent(JsonConvert.SerializeObject(publicacion), Encoding.UTF8, "application/json");
            var response = await cliente.PutAsync($"publicaciones/{publicacion.id}", content);

            if (response.IsSuccessStatusCode)
            {
                respuesta = true;
            }

            return respuesta;
        }

        public async Task<bool> DeletePublicacion(int publicacion, string token)
        {
            //this.Autenticar();
            bool respuesta = false;

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            if (!String.IsNullOrEmpty(token))
            {
                cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            }
            var response = await cliente.DeleteAsync($"publicaciones/{publicacion}");

            if (response.IsSuccessStatusCode)
            {
                respuesta = true;
            }

            return respuesta;
        }

        public async Task<List<Asignatura>> GetListaAsignaturas(int titulacion, int curso)
        {
            List<Asignatura> lista = new List<Asignatura>();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"asignaturas/portitulacion/{titulacion}/{curso}");

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                lista = JsonConvert.DeserializeObject<List<Asignatura>>(json_response);
            }

            return lista;
        }

        public async Task<List<Asignatura>> GetListaAsignaturas()
        {
            List<Asignatura> lista = new List<Asignatura>();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"asignaturas");

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                lista = JsonConvert.DeserializeObject<List<Asignatura>>(json_response);
            }

            return lista;
        }

        public async Task<Asignatura> GetAsignatura(int a)
        {
            Asignatura asig = new Asignatura();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"asignaturas/{a}");

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                asig = JsonConvert.DeserializeObject<Asignatura>(json_response);
            }

            return asig;
        }

        public async Task<List<Titulacion>> GetListaTitulaciones()
        {
            List<Titulacion> lista = new List<Titulacion>();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"titulaciones");

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                lista = JsonConvert.DeserializeObject<List<Titulacion>>(json_response);
            }

            return lista;
        }

        #endregion

        #region User-service

        public async Task<Usuario> GetUsuario(string usuario)
        {
            Usuario user = new Usuario();

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"usuarios/{usuario}");

            if (!response.IsSuccessStatusCode)
            {
                return user;
            }

            var json_response = await response.Content.ReadAsStringAsync();
            user = JsonConvert.DeserializeObject<Usuario>(json_response);

            return user;
        }

        public async Task<Token> Registrarse(Usuario usuario)
        {
            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var content = new StringContent(JsonConvert.SerializeObject(usuario), Encoding.UTF8, "application/json");
            var response = await cliente.PostAsync($"usuarios", content);
            Token token = new Token();
            if (response.IsSuccessStatusCode)
            {
                token = await this.Autenticar(usuario.username, usuario.password);
            }

            return token;
        }

        public async Task<bool> UpdateUsuario(Usuario usuario, string token)
        {
            bool respuesta = false;

            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            if (!String.IsNullOrEmpty(token))
            {
                cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            }
            var content = new StringContent(JsonConvert.SerializeObject(usuario), Encoding.UTF8, "application/json");
            var response = await cliente.PutAsync($"usuarios/{usuario.username}", content);

            if (response.IsSuccessStatusCode)
            {
                respuesta = true;
            }

            return respuesta;
        }

        #endregion

        #region Valoration-service

        public async Task<float> GetMediaValoraciones(string usuario)
        {
            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"valoraciones/{usuario}/media");
            float media = 0f;
            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                media = JsonConvert.DeserializeObject<float>(json_response);
            }

            return media;
        }


        public async Task<List<Valoracion>> GetValoraciones(string usuario)
        {
            List<Valoracion> listaValoraciones = new List<Valoracion>();
            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            var response = await cliente.GetAsync($"valoraciones/{usuario}");
            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                List<Valoracion> cuerpo = JsonConvert.DeserializeObject<List<Valoracion>>(json_response);
                if (cuerpo != null)
                    listaValoraciones = cuerpo;
            }

            if (listaValoraciones != null)
                listaValoraciones = listaValoraciones.OrderByDescending(p => p.fechaValoracion).Select(p => p).ToList();

            return listaValoraciones;
        }

        public async Task<bool> AnadirValoracion(Valoracion valoracion, string token)
        {
            var cliente = new HttpClient();
            cliente.BaseAddress = new Uri(_baseUrl);
            if (!String.IsNullOrEmpty(token))
            {
                cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
            }
            var content = new StringContent(JsonConvert.SerializeObject(valoracion), Encoding.UTF8, "application/json");
            var response = await cliente.PostAsync($"valoraciones", content);

            if (response.IsSuccessStatusCode)
            {
                var json_response = await response.Content.ReadAsStringAsync();
                Valoracion valoracionDB = JsonConvert.DeserializeObject<Valoracion>(json_response);
                if (valoracionDB != null)
                    return true;
            }

            return false;
        }

        #endregion








    }
}
