package work.anadinema.projects.alerty.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeApiResponse {

    private Boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Rates rates;

}
