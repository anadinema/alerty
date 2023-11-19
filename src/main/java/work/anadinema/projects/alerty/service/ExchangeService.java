package work.anadinema.projects.alerty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import work.anadinema.projects.alerty.model.ExchangeApiResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeService {

    private static final String EXCHANGE_API_URL = System.getenv("EXCHANGE_API_URL");
    private static final String SEND_ALERT_API_URL = System.getenv("SEND_ALERT_API_URL");
    private static final String ACCESS_KEY = System.getenv("EXCHANGE_API_ACCESS_KEY");
    private static final String SYMBOLS = System.getenv("EXCHANGE_API_SYMBOLS");
    private static final String THRESHOLD = System.getenv("SEK_TO_INR_THRESHOLD");

    public void checkExchangeRateAboveThreshold() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(EXCHANGE_API_URL + this.getQueryParams()))
                .GET()
                .build();

        HttpResponse<String> exchangeStringResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (exchangeStringResponse.statusCode() != HttpStatus.SC_OK || exchangeStringResponse.body().isEmpty()) {
            throw new RuntimeException("Exchange API returned/concluded with non-successful response!");
        }

        ObjectMapper mapper = new ObjectMapper();
        ExchangeApiResponse exchangeApiResponse = mapper
                .readValue(exchangeStringResponse.body(), ExchangeApiResponse.class);

        if (isAboveThreshold(exchangeApiResponse)) {
            request = HttpRequest.newBuilder()
                    .uri(new URI(SEND_ALERT_API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(exchangeStringResponse.body()))
                    .build();

            HttpResponse<String> alertResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (alertResponse.statusCode() != HttpStatus.SC_OK
                    || alertResponse.statusCode() != HttpStatus.SC_NO_CONTENT) {
                throw new RuntimeException("Alert send failed with non-successful response!");
            }

        }
    }

    private String getQueryParams() {
        return "?access_key=" + ACCESS_KEY + "&symbols=" + SYMBOLS;
    }

    private boolean isAboveThreshold(ExchangeApiResponse exchangeApiResponse) {
        Double inrRate = Double.valueOf(exchangeApiResponse.getRates().getInr());
        Double sekRate = Double.valueOf(exchangeApiResponse.getRates().getSek());

        return sekRate / inrRate >= Double.parseDouble(THRESHOLD);
    }
}
