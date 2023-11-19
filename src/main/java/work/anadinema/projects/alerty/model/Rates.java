package work.anadinema.projects.alerty.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rates {

    @JsonProperty("SEK")
    private String sek;

    @JsonProperty("INR")
    private String inr;

    @JsonProperty("USD")
    private String usd;

}
