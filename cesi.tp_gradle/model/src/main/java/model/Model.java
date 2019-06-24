package model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Model implements IModel{

    @Override
    public String getMessage() {
        try {
            Path path = Paths.get(Model.class.getResource("message.txt").toURI());
            byte[] fileBytes = Files.readAllBytes(path);
            return new String(fileBytes);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
