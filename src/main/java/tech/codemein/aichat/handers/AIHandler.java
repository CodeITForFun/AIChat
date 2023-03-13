package tech.codemein.aichat.handers;

import io.github.jetkai.openai.api.CreateChatCompletion;
import io.github.jetkai.openai.api.data.completion.chat.ChatCompletionData;
import io.github.jetkai.openai.api.data.completion.chat.message.ChatCompletionMessageData;
import io.github.jetkai.openai.net.OpenAIEndpoints;
import io.github.jetkai.openai.openai.OpenAI;
import tech.codemein.aichat.managers.FileManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AIHandler {
    private final List<ChatCompletionMessageData> messageHistory = new ArrayList<>();
    public String exampleBuilders(String message) {
        //Create the Message Data object with the message we wish to send
        ChatCompletionMessageData messageData = ChatCompletionMessageData.builder()
                .setRole("user")
                .setContent(message)
                .build();

        //Store the message that we want to send, to the MessageHistory List
        messageHistory.add(messageData);

        //Build the data structure which contains the message history and model information
        ChatCompletionData completionData = ChatCompletionData.builder()
                .setModel("gpt-3.5-turbo")
                .setMessages(messageHistory)
                .build();

        //Sends the request to OpenAI's endpoint & parses the response data
        //Instead of openAI.sendRequest(); you can initialize the request
        //for the class manually - openAI.createChatCompletion().initialize();
        OpenAI openAI = OpenAI.builder()
                .setApiKey(FileManager.config.getString("openaiKey"))
                .createChatCompletion(completionData)
                .build()
                .sendRequest();

        //Check to see if there is a valid response from OpenAI
        //You can also call Optional<CreateChatCompletion> createChatCompletion = openAI.chatCompletion();
        CreateChatCompletion createChatCompletion = openAI.getChatCompletion();

        //Store chat response from AI, this allows the AI to see the full history of our chat
        //Including both our messages and the AI's messages
        messageHistory.addAll(createChatCompletion.asChatResponseDataList());

        //Parse the response back as plain-text
        return createChatCompletion.asText();
    }
}
