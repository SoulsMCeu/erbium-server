package eu.soulsmc.erbium.properties;

import eu.soulsmc.erbium.api.json.Document;
import eu.soulsmc.erbium.properties.defaults.DefaultServerProperties;

import java.nio.file.Files;
import java.nio.file.Path;

public class ServerProperties {

    private final Document document;
    private final Path path;

    public ServerProperties() {
        Document loadedDocument;
        this.path = Path.of("config.json");

        if (Files.notExists(path)) {
            loadedDocument = DefaultServerProperties.get();
            loadedDocument.write(path);
        } else {
            loadedDocument = new Document(path);
        }
        this.document = loadedDocument;
    }

    public <T> T getProperty(String property, Class<T> clazz) {
        return this.document.get(property, clazz);
    }

    public void setProperty(String property, Object value) {
        this.document.set(property, value);
    }

    public void updateDocument() {
        this.document.write(this.path);
    }

    public void reload() {
        this.document.read(this.path);
    }
}
