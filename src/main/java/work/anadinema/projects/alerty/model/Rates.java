package work.anadinema.projects.alerty.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rates {

    @SerializedName("SEK")
    private String sek;

    @SerializedName("INR")
    private String inr;

    @SerializedName("USD")
    private String usd;

}
