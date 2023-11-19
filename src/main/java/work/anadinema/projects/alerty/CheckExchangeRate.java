package work.anadinema.projects.alerty;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import work.anadinema.projects.alerty.service.ExchangeService;
import java.time.LocalDateTime;
import java.util.logging.Level;

public class CheckExchangeRate {

    @FunctionName("CheckExchangeRate")
    public void checkExchangeWithThreshold(
        @TimerTrigger(name = "timerInfo", schedule = "0 15 */8 * * *") String timerInfo,
        final ExecutionContext context
    ) {
        context.getLogger().info("Exchange rate check execution started at: " + LocalDateTime.now());

        try {
            ExchangeService service = new ExchangeService();
            service.checkExchangeRateAboveThreshold();
        } catch (Exception exception) {
            context.getLogger().log(Level.SEVERE,
                    "Error occurred during exchange check execution : " + exception.getMessage());
        }

    }

}
