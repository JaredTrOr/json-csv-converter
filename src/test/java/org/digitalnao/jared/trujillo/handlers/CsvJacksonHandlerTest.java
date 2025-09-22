package org.digitalnao.jared.trujillo.handlers;

import org.digitalnao.jared.trujillo.classes.User;
import org.digitalnao.jared.trujillo.exceptions.CsvHandlerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvJacksonHandlerTest {

    private final CsvJacksonHandler handler = new CsvJacksonHandler();

    @TempDir
    Path tempDir;

    // writeToCsv(T object, Class<T> type, String filename)

    @Test
    void writeToCsv_object_writesFileWithHeaderAndRow() throws IOException {
        User u = new User(1, "Juan", "juan@example.com");

        // your handler appends ".csv"
        Path base = tempDir.resolve("user");
        handler.writeToCsv(u, User.class, base.toString());

        Path csv = tempDir.resolve("user.csv");
        assertTrue(Files.exists(csv));

        String content = Files.readString(csv);
        assertTrue(content.startsWith("id,name,email"));
        assertTrue(content.contains("1,Juan,juan@example.com"));
    }

    @Test
    void writeToCsv_nullObject_throws() {
        Path base = tempDir.resolve("nullfile");
        CsvHandlerException ex = assertThrows(
                CsvHandlerException.class,
                () -> handler.writeToCsv((User) null, User.class, base.toString())
        );
        assertTrue(ex.getMessage().toLowerCase().contains("object"));
    }

    @Test
    void writeToCsv_nullType_throws() {
        Path base = tempDir.resolve("typefile");
        CsvHandlerException ex = assertThrows(
                CsvHandlerException.class,
                () -> handler.writeToCsv(new User(1,"A","a@a.com"), null, base.toString())
        );
        assertTrue(ex.getMessage().toLowerCase().contains("type"));
    }

    @Test
    void writeToCsv_blankFilename_throws() {
        CsvHandlerException ex = assertThrows(
                CsvHandlerException.class,
                () -> handler.writeToCsv(new User(1,"A","a@a.com"), User.class, " ")
        );
        assertTrue(ex.getMessage().toLowerCase().contains("filename"));
    }

    // writeToCsv(List<T> list, Class<T> type, String filename)

    @Test
    void writeToCsv_list_writesMultipleRows() throws IOException {
        List<User> list = List.of(
                new User(1, "Juan", "juan@example.com"),
                new User(2, "Maria", "maria@example.com")
        );

        Path base = tempDir.resolve("users");
        handler.writeToCsv(list, User.class, base.toString());

        Path csv = tempDir.resolve("users.csv");
        assertTrue(Files.exists(csv));

        String content = Files.readString(csv);
        assertTrue(content.contains("id,name,email"));
        assertTrue(content.contains("1,Juan,juan@example.com"));
        assertTrue(content.contains("2,Maria,maria@example.com"));
    }

    @Test
    void writeToCsv_emptyList_throws() {
        Path base = tempDir.resolve("empty");
        CsvHandlerException ex = assertThrows(
                CsvHandlerException.class,
                () -> handler.writeToCsv(List.<User>of(), User.class, base.toString())
        );
        assertTrue(ex.getMessage().toLowerCase().contains("list"));
    }
}
