package net.alphalightning.bedwars.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Deprecated(forRemoval = true)
/**
 * Utility-Klasse für das Arbeiten mit JSON-Dateien.
 */
public class JSONUtil {

    private static final Gson gson = new Gson();

    /**
     * Liest ein JSON-Objekt aus einer Datei.
     *
     * @param filePath Pfad zur JSON-Datei.
     * @return JsonObject, das aus der Datei gelesen wurde.
     * @throws IOException Wenn die Datei nicht gelesen werden kann.
     */
    public static JsonObject readJsonFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("Datei existiert nicht: " + filePath);
        }

        try (FileReader reader = new FileReader(path.toFile())) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    /**
     * Schreibt ein JSON-Objekt in eine Datei.
     *
     * @param filePath Pfad zur JSON-Datei.
     * @param jsonObject JSON-Daten, die in die Datei geschrieben werden sollen.
     * @throws IOException Wenn die Datei nicht geschrieben werden kann.
     */
    public static void writeJsonToFile(String filePath, JsonObject jsonObject) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(jsonObject, writer);
        }
    }

    /**
     * Fügt Daten zu einem bestehenden JSON-Objekt in einer Datei hinzu.
     *
     * @param filePath Pfad zur JSON-Datei.
     * @param key Schlüssel, der hinzugefügt oder aktualisiert werden soll.
     * @param value Wert, der dem Schlüssel zugewiesen werden soll.
     * @throws IOException Wenn die Datei nicht gelesen oder geschrieben werden kann.
     */
    public static void updateJsonFile(String filePath, String key, JsonElement value) throws IOException {
        JsonObject jsonObject;

        // Lese die bestehende Datei
        try {
            jsonObject = readJsonFromFile(filePath);
        } catch (IOException e) {
            // Falls die Datei nicht existiert, ein neues JSON-Objekt erstellen
            jsonObject = new JsonObject();
        }

        // Daten hinzufügen oder aktualisieren
        jsonObject.add(key, value);

        // Aktualisierte Daten in die Datei schreiben
        writeJsonToFile(filePath, jsonObject);
    }

    /**
     * Holt den Wert eines Schlüssels aus einer JSON-Datei.
     *
     * @param filePath Pfad zur JSON-Datei.
     * @param key Schlüssel, dessen Wert abgerufen werden soll.
     * @return Der Wert des Schlüssels als JsonElement, oder null, wenn der Schlüssel nicht existiert.
     * @throws IOException Wenn die Datei nicht gelesen werden kann.
     */
    public static JsonElement getValueFromJson(String filePath, String key) throws IOException {
        JsonObject jsonObject = readJsonFromFile(filePath);
        return jsonObject.has(key) ? jsonObject.get(key) : null;
    }
}
