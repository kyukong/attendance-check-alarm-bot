package support;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;

import notification.SlackInfo;

public class ConfigReader {

    private static final Yaml YAML = new Yaml();

    public SlackInfo readSlackInfo() {
        final InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("application.yml");
        return new ObjectMapper().convertValue(YAML.load(inputStream), SlackInfo.class);
    }
}
