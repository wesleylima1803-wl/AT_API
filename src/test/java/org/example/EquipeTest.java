package org.example;

import com.google.gson.*;
import org.junit.jupiter.api.*;
import java.net.URI;
import java.net.http.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.example.Main.*;


public class EquipeTest {
    private static final String urlBase = "http://localhost:7070/equipes";
    private static HttpClient http;

    private static final Gson gson = new Gson();

    private static final String Brasil = """
            {
              "nome": "Brasil",
              "sigla": "BRA",
              "modalidade": "Futebol",
              "categoria": "Profissional",
              "tecnico": "Carlo Ancelotti",
              "capitao": "Marquinhos",
              "quantidadeAtletas": 26,
              "dataFundacao": "1914-07-14",
              "status": "ATIVA"
            }
            """;
    private static final String Argentina = """
            {
              "nome": "Argentina",
              "sigla": "ARG",
              "modalidade": "Futebol",
              "categoria": "Profissional",
              "tecnico": "Scaloni",
              "capitao": "Messi",
              "quantidadeAtletas": 26,
              "dataFundacao": "1901-05-16",
              "status": "ATIVA"
            }
            """;

    @BeforeAll
    public static void iniciar() {
        iniciarServidor(7070);
        http = HttpClient.newHttpClient();
    }

    @AfterAll
    public static void encerrar() {
        if (app != null) {
            app.stop();
            System.out.println("Desligando Servidor...");
        }
    }

    @BeforeEach
    public void limparBanco() {
        repository.limpar();
    }

    private HttpResponse<String> post(String json) throws Exception {
        return http.send(HttpRequest.newBuilder()
                        .uri(URI.create(urlBase))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build(),
                        HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get(String path) throws Exception {
        return http.send(HttpRequest.newBuilder()
                        .uri(URI.create(urlBase + path))
                        .GET()
                        .build(),
                        HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> put(int id, String json) throws Exception {
        return http.send(HttpRequest.newBuilder()
                        .uri(URI.create(urlBase + "/" + id))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(json))
                        .build(),
                        HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> delete(int id) throws Exception {
        return http.send(HttpRequest.newBuilder()
                        .uri(URI.create(urlBase + "/" + id))
                        .DELETE()
                        .build(),
                        HttpResponse.BodyHandlers.ofString());
    }


    @Test
    public void deveRetornar201ComIdGerado() throws Exception {
        var resp = post(Brasil);
        JsonObject body = gson.fromJson(resp.body(), JsonObject.class);

        assertEquals(201, resp.statusCode());
        assertEquals(1, body.get("id").getAsInt());
        assertEquals("Brasil", body.get("nome").getAsString());
    }

    @Test
    public void deveRetornar200ComIdCadastrado() throws Exception {
        post(Brasil);
        var resp = get("/1");
        JsonObject body = gson.fromJson(resp.body(), JsonObject.class);

        assertEquals(200, resp.statusCode());
        assertEquals(1, body.get("id").getAsInt());
        assertEquals("Brasil", body.get("nome").getAsString());
    }

    @Test
    public void deveRetornar200ComTamanhoDaListaDeIdsCadastrados() throws Exception {
        post(Brasil);
        post(Argentina);
        var resp = get("");

        assertEquals(200, resp.statusCode());
        assertEquals(2, gson.fromJson(resp.body(), JsonArray.class).size());
    }

    @Test
    public void deveRetornar200ComIdAtualizado() throws Exception {
        post(Brasil);
        String brasilAtualizado = """
                {
                  "nome": "Brasil Atualizado",
                  "sigla": "BRA",
                  "modalidade": "Futebol",
                  "categoria": "Profissional",
                  "tecnico": "Guardiola",
                  "capitao": "Neymar",
                  "quantidadeAtletas": 30,
                  "dataFundacao": "2010-10-10",
                  "status": "ATIVA"
                }
                """;

        var resp = put(1, brasilAtualizado);
        JsonObject body = gson.fromJson(resp.body(), JsonObject.class);

        assertEquals(200, resp.statusCode());
        assertEquals(1, body.get("id").getAsInt());
        assertEquals("Brasil Atualizado", body.get("nome").getAsString());
        assertEquals(30, body.get("quantidadeAtletas").getAsInt());
        assertEquals("Neymar", body.get("capitao").getAsString());
    }

    @Test
    public void deveRetornar204QuandoDeletarId() throws Exception {
        post(Brasil);

        assertEquals(204, delete(1).statusCode());
        assertEquals(404, get("/1").statusCode());
    }
}