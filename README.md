# JSON/CSV – Sprint 2

- Name: Jared Alexader Trujillo Ortiz
- NaoID: 3347
- Date: 21 September 2025

Desktop Java program to **read JSON** into typed objects and **write CSV** from objects/lists using Jackson library.  
Includes strict **input validation**, **consistent exception mapping**, **unit tests**, and **factories** that hide implementations to encourage correct usage.

---

## ✨ Features

- JSON → `T`, `List<T>`, and complex types via `TypeReference<T>`
- CSV ← single object or `List<T>` with a header row (ordered via `@JsonPropertyOrder`)
- Consistent exceptions: `JsonHandlerException`, `CsvHandlerException`
- Factories: `JsonHandlerFactory`, `CsvHandlerFactory` 
- JUnit 5 tests for happy paths and error scenarios
- Minimal, clear Javadocs

---

## 🗂️ Project Structure

```
src/
├─ main/
│  └─ java/
│     └─ org/digitalnao/jared/trujillo/
│        ├─ interfaces/
│        │  ├─ JsonHandler.java
│        │  └─ CsvHandler.java
│        ├─ handlers/
│        │  ├─ JsonJacksonHandler.java         (package-private, final)
│        │  ├─ CsvJacksonHandler.java          (package-private, final)
│        │  ├─ JsonHandlerFactory.java         (public factory)
│        │  └─ CsvHandlerFactory.java          (public factory)
│        ├─ exceptions/
│        │  ├─ JsonHandlerException.java
│        │  └─ CsvHandlerException.java
│        └─ classes/
│           └─ User.java
└─ test/
   └─ java/
      └─ org/digitalnao/jared/trujillo/handlers/
         ├─ JsonJacksonHandlerTest.java
         └─ CsvJacksonHandlerTest.java
```

> Implementations are **not public** and are **final**; always get instances via the factories.

---

## ✅ Requirements

- **Java 17+**
- **Maven 3.9+**

Key dependencies (declared in `pom.xml`):
- `com.fasterxml.jackson.core:jackson-databind`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-csv`
- `org.junit.jupiter:junit-jupiter` (test scope)

---

## 🔧 Usage

Always use the **factories**; do not instantiate handlers directly.

### Read JSON

```java
import org.digitalnao.jared.trujillo.handlers.JsonHandlerFactory;
import org.digitalnao.jared.trujillo.interfaces.JsonHandler;
import org.digitalnao.jared.trujillo.classes.User;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Map;

public static void main(String[] args) {
    JsonHandler json = JsonHandlerFactory.createJsonHandler();
    
    try {
        // 1) Object
        User u = json.fromJson("data/user.json", User.class);

        // 2) List<T>
        List<User> users = json.fromJsonList("data/users.json", User.class);

        // 3) Complex types (TypeReference)
        List<Map<String, Object>> weird = json.fromJson(
                "data/weird.json",
                new TypeReference<List<Map<String, Object>>>() {}
        );
    } catch(Exception e) {
        System.err.out(e);
    }
}
```
---

### Write CSV

```java
import org.digitalnao.jared.trujillo.handlers.CsvHandlerFactory;
import org.digitalnao.jared.trujillo.interfaces.CsvHandler;
import org.digitalnao.jared.trujillo.classes.User;
import java.util.List;

public static void main(String[] args) {
    CsvHandler csv = CsvHandlerFactory.createCsvHandler();

    User u = new User(1, "Juan", "juan@example.com");
    
    try {
        // 1) Single object → writes to <filename>.csv with header
        csv.writeToCsv(u, User.class, "out/user");

        // 2) List<T>
        csv.writeToCsv(List.of(
                new User(1, "Juan", "juan@example.com"),
                new User(2, "Maria", "maria@example.com")
        ), User.class, "out/users");
    } catch(Exception e) {
        System.err.out(e);
    }  
}

```
---

## 🧪 Considerations

1. **Public**  
   - Interfaces: `JsonHandler`, `CsvHandler`  
   - Factories: `JsonHandlerFactory`, `CsvHandlerFactory`
2. **Implementation details**  
   - In `handlers/` and intentionally not public to encourage factory usage.  
3. **Run tests**  
   - `mvn test` runs JUnit 5 tests in `src/test/java/.../handlers/`.

---

