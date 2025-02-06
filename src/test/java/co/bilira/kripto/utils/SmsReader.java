package co.bilira.kripto.utils;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SmsReader {

    private static final String ACCOUNT_SID = ConfigReader.getSecretValue("ACCOUNT_SID");
    private static final String AUTH_TOKEN = ConfigReader.getSecretValue("AUTH_TOKEN");
    private static final String PHONE_NUMBER = ConfigReader.getSecretValue("PHONE_NUMBER");


    /**
     * Returns the verification code sent to the configured phone number.
     * The verification code is extracted from the most recent SMS message.
     * If the verification code is not found, a RuntimeException is thrown.
     *
     * @return the verification code
     * @throws RuntimeException if the verification code is not found
     */
    public static String getVerificationCode() {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Get the list of messages sent to the configured phone number
        ResourceSet<Message> messages = Message.reader()
                .setTo(PHONE_NUMBER)
                .read();

        // Convert the list of messages to a Stream
        Stream<Message> messageStream = StreamSupport.stream(messages.spliterator(), false);

        // Find the most recent message
        Optional<String> lastMessageBody = messageStream
                .findFirst()
                .map(Message::getBody);

        // Extract the verification code from the message body
        return lastMessageBody.map(SmsReader::extractVerificationCode)
                .orElseThrow(() -> new RuntimeException("Verification code not found!"));
    }

    /**
     * Extracts the verification code from the given message body.
     * The verification code is the only 6-digit number in the message.
     * If the verification code is not found, a RuntimeException is thrown.
     *
     * @param messageBody the SMS message body
     * @return the verification code
     * @throws RuntimeException if the verification code is not found
     */
    private static String extractVerificationCode(String messageBody) throws RuntimeException {

        return Arrays.stream(messageBody
                        .replaceAll("\\p{Punct}", "")
                        .split("\\s+"))
                .filter(s -> s.matches("\\d{6}"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No verification code found in message: " + messageBody));

    }
}
