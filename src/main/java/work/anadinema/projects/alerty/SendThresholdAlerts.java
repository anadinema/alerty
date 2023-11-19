package work.anadinema.projects.alerty;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import work.anadinema.projects.alerty.model.ExchangeApiResponse;
import work.anadinema.projects.alerty.service.NotifyService;

public class SendThresholdAlerts {

    @FunctionName("SendThresholdAlerts")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<ExchangeApiResponse>> request,
            final ExecutionContext context) {
        context.getLogger().info("Starting execution to send threshold alerts at : " + LocalDateTime.now());

        if (request.getBody().isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).build();
        }

        try {
            NotifyService service = new NotifyService();
            service.sendAlert(request.getBody().get());
            return request.createResponseBuilder(HttpStatus.OK).build();
        } catch (Exception exception) {
            context.getLogger().log(Level.SEVERE,
                    "Error occurred while sending alerts : " + exception.getMessage());
            return request.createResponseBuilder(HttpStatus.NOT_IMPLEMENTED).body(exception.getMessage()).build();
        }

    }
}
