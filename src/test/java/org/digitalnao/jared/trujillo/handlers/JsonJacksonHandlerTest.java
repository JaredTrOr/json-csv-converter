package org.digitalnao.jared.trujillo.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.digitalnao.jared.trujillo.builders.JsonHandlerFactory;
import org.digitalnao.jared.trujillo.classes.User;
import org.digitalnao.jared.trujillo.exceptions.JsonHandlerException;
import org.digitalnao.jared.trujillo.interfaces.JsonHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonJacksonHandlerTest {

    private final JsonHandler handler = JsonHandlerFactory.createJsonHandler();

    @TempDir
    Path tempDir;

    // fromJson(String, Class<T>)

    @Test
    void fromJson_validObject_returnsUser() throws IOException {
        Path file = tempDir.resolve("user.json");
        Files.writeString(file, """
            { "id": 1, "name": "Juan", "email": "juan@example.com" }
        """);

        User u = handler.fromJson(file.toString(), User.class);

        assertEquals(1, u.getId());
        assertEquals("Juan", u.getName());
        assertEquals("juan@example.com", u.getEmail());
    }

    @Test
    void fromJson_wrongExtension_throws() {
        JsonHandlerException ex = assertThrows(
                JsonHandlerException.class,
                () -> handler.fromJson(tempDir.resolve("user.txt").toString(), User.class)
        );
        assertTrue(ex.getMessage().toLowerCase().contains(".json"));
    }

    @Test
    void fromJson_fileNotFound_throws() {
        String missing = tempDir.resolve("missing.json").toString();
        JsonHandlerException ex = assertThrows(
                JsonHandlerException.class,
                () -> handler.fromJson(missing, User.class)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("file not found"));
    }

    @Test
    void fromJson_emptyFile_throws() throws IOException {
        Path file = tempDir.resolve("empty.json");
        Files.writeString(file, "");

        JsonHandlerException ex = assertThrows(
                JsonHandlerException.class,
                () -> handler.fromJson(file.toString(), User.class)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("empty"));
    }

    @Test
    void fromJson_nullType_throws() throws IOException {
        Path file = tempDir.resolve("user.json");
        Files.writeString(file, "{ \"id\": 1 }");

        JsonHandlerException ex = assertThrows(
                JsonHandlerException.class,
                () -> handler.fromJson(file.toString(), (Class<User>) null)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("type"));
    }

    // fromJsonList(String, Class<T>)

    @Test
    void fromJsonList_validArray_returnsUsers() throws IOException {
        Path file = tempDir.resolve("users.json");
        Files.writeString(file, """
            [
              { "id": 1, "name": "Juan", "email": "juan@example.com" },
              { "id": 2, "name": "Maria", "email": "maria@example.com" }
            ]
        """);

        List<User> users = handler.fromJsonList(file.toString(), User.class);

        assertEquals(2, users.size());
        assertEquals("Maria", users.get(1).getName());
    }

    @Test
    void fromJsonList_objectInsteadOfArray_throwsMapping() throws IOException {
        Path file = tempDir.resolve("not-array.json");
        Files.writeString(file, "{ \"id\": 1 }"); // object, not array

        JsonHandlerException ex = assertThrows(
                JsonHandlerException.class,
                () -> handler.fromJsonList(file.toString(), User.class)
        );
        // your handler maps JsonMappingException -> "JSON structure does not match"
        assertTrue(ex.getMessage().toLowerCase().contains("structure"));
    }

    // fromJson(String, TypeReference<T>)

    @Test
    void fromJson_typeRef_readsListOfMaps() throws IOException {
        Path file = tempDir.resolve("weird.json");
        Files.writeString(file, """
            [ {"k":"v"}, {"n":123} ]
        """);

        List<Map<String, Object>> data = handler.fromJson(
                file.toString(),
                new TypeReference<List<Map<String, Object>>>() {}
        );

        assertEquals(2, data.size());
        assertEquals("v", data.get(0).get("k"));
        assertEquals(123, ((Number) data.get(1).get("n")).intValue());
    }

    @Test
    void fromJson_nullTypeRef_throws() {
        JsonHandlerException ex = assertThrows(
                JsonHandlerException.class,
                () -> handler.fromJson("anything.json", (TypeReference<Object>) null)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("typeref"));
    }
}
