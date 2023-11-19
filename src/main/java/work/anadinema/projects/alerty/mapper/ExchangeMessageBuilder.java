package work.anadinema.projects.alerty.mapper;

import work.anadinema.projects.alerty.model.ExchangeApiResponse;
import java.time.LocalDateTime;
import java.util.function.Function;

public class ExchangeMessageBuilder implements Function<ExchangeApiResponse, String> {

    @Override
    public String apply(ExchangeApiResponse exchangeApiResponse) {
        String response = """
                Exchange rates at %s is as follows:
                
                1 SEK = %s INR
                1 USD = %s SEK
                1 EUR = %s SEK
                """;

        Double inrRate = Double.valueOf(exchangeApiResponse.getRates().getInr());
        Double sekRate = Double.valueOf(exchangeApiResponse.getRates().getSek());
        Double usdRate = Double.valueOf(exchangeApiResponse.getRates().getUsd());

        return String.format(response, LocalDateTime.now(), inrRate / sekRate, sekRate / usdRate, sekRate);
    }
}
