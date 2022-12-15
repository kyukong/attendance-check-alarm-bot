package notification;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import support.ConfigReader;
import support.ParameterMapper;

public class Slack implements Alarm {

    private static final String URL = "https://slack.com/api/chat.postMessage";

    private final Logger log = LoggerFactory.getLogger(Slack.class);

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> parameters = new HashMap<>();

    public Slack() {
        final SlackInfo slackInfo = ConfigReader.readSlackInfo();

        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Bearer " + slackInfo.getToken());
        parameters.put("channel", slackInfo.getChannel());
    }

    @Override
    public void run(final String message) throws Exception {
        final HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();

        connection.setRequestMethod("POST");
        setHeaders(connection);
        setParameters(connection, message);
        connection.setConnectTimeout(5000);

        connection.getResponseMessage();
        log.info("메시지가 전송되었습니다. [{}]", message);
    }

    private void setHeaders(final HttpURLConnection connection) {
        for (final Map.Entry<String, String> header : headers.entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }
    }

    private void setParameters(final HttpURLConnection connection, final String message) throws IOException {
        parameters.put("text", message);

        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(ParameterMapper.stringFromMap(parameters));
        out.flush();
        out.close();
    }
}
