package site.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static utils.FileUtils.RESOURCES_PATH;

public class User {
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static List<User> getFromScv(String dataFile) throws IOException {
        Path path = Paths.get(RESOURCES_PATH,  dataFile);

        CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<User> orders = csvMapper.readerFor(User.class)
                .with(orderLineSchema)
                .readValues(path.toFile());

        return new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .convertValue(orders.readAll(), new TypeReference<List<User>>() {
                });
    }
}
