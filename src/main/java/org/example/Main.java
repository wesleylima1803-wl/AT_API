package org.example;
import com.google.gson.*;
import com.google.gson.stream.*;
import io.javalin.Javalin;
import io.javalin.json.JavalinGson;
import java.io.IOException;
import java.time.LocalDate;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static EquipeRepository repository = new EquipeRepository();
    public static Javalin app;

    public static void iniciarServidor(int porta) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                    @Override
                    public void write(JsonWriter out, LocalDate value) throws IOException {
                        if (value == null) out.nullValue();
                        else out.value(value.toString());
                    }

                    @Override
                    public LocalDate read(JsonReader in) throws IOException {
                        String string = in.nextString();
                        return (string == null || string.equals("null")) ? null : LocalDate.parse(string);
                    }
                })
                .create();

        app = Javalin.create(config -> {
            config.jsonMapper(new JavalinGson(gson, true));

            config.routes.apiBuilder(() -> path("/equipes", () -> {
                get(ctx -> ctx.status(200)
                        .json(repository.buscarTodas()));

                post(ctx -> {
                    Equipe equipe = gson.fromJson(ctx.body(), Equipe.class);
                    ctx.status(201).json(repository.salvar(equipe));
                });

                path("/{id}", () -> {
                    get(ctx -> {
                        int id = Integer.parseInt(ctx.pathParam("id"));
                        Equipe equipe = repository.buscarPorId(id);

                        if (equipe != null) {
                            ctx.status(200).json(equipe);
                        } else {
                            ctx.status(404).result("Equipe " + id + " nao encontrada.");
                        }
                    });

                    put(ctx -> {
                        int id = Integer.parseInt(ctx.pathParam("id"));

                        Equipe equipeAtualizada = repository.atualizar(
                                id, gson.fromJson(ctx.body(), Equipe.class));

                        if (equipeAtualizada != null) {
                            ctx.status(200).json(equipeAtualizada);
                        } else {
                            ctx.status(404).result("Equipe " + id + " nao encontrada.");
                        }
                    });

                    delete(ctx -> {
                        int id = Integer.parseInt(ctx.pathParam("id"));
                        if (repository.remover(id)) {
                            ctx.status(204);
                        } else {
                            ctx.status(404).result("Equipe " + id + " nao encontrada.");
                        }
                    });

                });
            }));
        });

        app.start(porta);
        System.out.println("Inicializando Servidor na porta " + porta + "...");
    }

    public static void main(String[] args) {
        iniciarServidor(7070);
    }
}
