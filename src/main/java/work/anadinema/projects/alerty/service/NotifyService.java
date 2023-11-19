package work.anadinema.projects.alerty.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import work.anadinema.projects.alerty.mapper.ExchangeMessageBuilder;
import work.anadinema.projects.alerty.model.ExchangeApiResponse;
import java.util.Optional;

public class NotifyService {

    private static final String SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String SENDER_NUMBER = System.getenv("TWILIO_SENDER_NUMBER");
    private static final String RECEIVER_NUMBER = System.getenv("TWILIO_RECEIVER_NUMBER");

    public void sendAlert(ExchangeApiResponse apiResponse) {
        Twilio.init(SID, TOKEN);

        ExchangeMessageBuilder mapper = new ExchangeMessageBuilder();

        Message message = Message.creator(
                new PhoneNumber(RECEIVER_NUMBER),
                new PhoneNumber(SENDER_NUMBER),
                mapper.apply(apiResponse))
                .create();

        if (Optional.ofNullable(message.getErrorCode()).isPresent()) {
            throw new RuntimeException(message.getErrorMessage());
        }

    }
}
