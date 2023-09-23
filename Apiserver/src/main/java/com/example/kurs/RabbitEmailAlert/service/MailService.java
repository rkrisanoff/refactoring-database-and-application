package com.example.kurs.RabbitEmailAlert.service;

import com.example.kurs.RabbitEmailAlert.DTO.Message;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.User;
import com.example.kurs.exceptions.UserNotFoundException;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.messaging.support.GenericMessage;
import org.json.JSONObject;

import java.util.Date;


@Service
public class MailService {
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;
    @Autowired
    MqttReceiveHandle mqttReceiveHandle;
    @Autowired
    ObjectMapper objectMapper;



    public void statusСhangeRecipeEmailAlert(Recipe recipe) throws UserNotFoundException, JsonProcessingException {
        Message message = makeMessageStatusСhangeRecipe(recipe);
        String jsonForSend= messageToJson(message);
        byte[] payload = jsonForSend.getBytes();
        org.springframework.messaging.Message<byte[]> ms = new GenericMessage<>(payload);
        mqttReceiveHandle.handle(ms);

    }


    private String messageToJson(Message message) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(message);
        return jsonString;
    }

    private Message makeMessageStatusСhangeRecipe(Recipe recipe) throws UserNotFoundException {
        User user = userService.getUser(recipe.getAuthorId());

        String subject = String.format("Смена статуса у рецепта: %s", recipe.getTitle());
        String emailAddress = user.getEmail();
        String text = String.format("Привет %s! Мы отправляем это письмо, " +
                "чтоб оповестить тебя о том, что твой рецепт %s был" +
                " проверен администратором и ему был установлен статус %s", user.getUsername(), recipe.getTitle(), recipe.getStatus(), new Date());
        return new Message(subject, emailAddress, text);
    }


}
