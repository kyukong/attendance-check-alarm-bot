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

public class Slack implements Alarm {

    private static final String URL = "https://slack.com/api/chat.postMessage";

    private final Logger log = LoggerFactory.getLogger(Slack.class);
    private final SlackInfo slackInfo;

    public Slack() {
        this.slackInfo = new ConfigReader().readSlackInfo();
        log.info("slack 관련 정보를 읽어왔습니다.");
    }

    @Override
    public void run(final String message) throws Exception {
        final HttpURLConnection connection = createConnection();

        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Authorization", "Bearer " + slackInfo.getToken());

        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channel", slackInfo.getChannel());
        parameters.put("text", message);

        connection.setRequestMethod("POST");
        setHeaders(connection, headers);
        setParameters(connection, parameters);
        connection.setConnectTimeout(5000);

        connection.getResponseMessage();

        log.info("메시지가 전송되었습니다. [{}]", message);
    }

    private HttpURLConnection createConnection() throws IOException {
        return (HttpURLConnection) new URL(URL).openConnection();
    }

    private void setHeaders(final HttpURLConnection connection, final Map<String, String> headers) {
        for (final Map.Entry<String, String> header : headers.entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }
    }

    private void setParameters(final HttpURLConnection connection, final Map<String, String> parameters)
        throws IOException {
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(convertParametersToString(parameters));
        out.flush();
        out.close();
    }

    private String convertParametersToString(final Map<String, String> parameters) {
        final StringBuilder string = new StringBuilder();
        for (final Map.Entry<String, String> parameter : parameters.entrySet()) {
            string.append(parameter.getKey());
            string.append("=");
            string.append(parameter.getValue());
            string.append("&");
        }
        return string.toString();
    }
}
