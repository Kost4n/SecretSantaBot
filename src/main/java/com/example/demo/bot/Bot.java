package com.example.demo.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class Bot extends TelegramLongPollingBot {

    private Message requestMessage = new Message();
    private SendMessage response = new SendMessage();

    Map<String, Boolean> wrotes = new HashMap<>();

    ArrayList<Employee> workers = new ArrayList<Employee>();
    {
        workers.add(new Employee("Софа", false, "1"));
        workers.add(new Employee("Вика", false, "2"));
        workers.add(new Employee("Настя", false, "3"));
        workers.add(new Employee("Илья", false, "4"));
        workers.add(new Employee("Ксюша", false, "5"));
        workers.add(new Employee("Катя пиво", false, "6"));
        workers.add(new Employee("Ким нам джун (Лена)", false, "7"));
        workers.add(new Employee("Полина Wleper", false, "8"));
        workers.add(new Employee("Полина (Lina)", false, "9"));
        workers.add(new Employee("Саша", false, "10"));
        workers.add(new Employee("Света", false, "11"));
    }
//    @Value("${bot.name}")
    private static final String botName = "newSprintTestCFbot";

//    @Value("${bot.token}")
    private static final String botToken = "5853866920:AAH82gANWtylfFrXffNqztCF4quZRGpCpG0";

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        requestMessage = update.getMessage();
        response.setChatId(requestMessage.getChatId().toString());



        if (update.hasMessage() && requestMessage.hasText()) {
            //log.info("Working onUpdateREceived, text: " + requestMessage.getText());
            if (requestMessage.getText().equals("/start")) {
                defaultMsg(response, "Этот бот - это Тайный Санта");
                defaultMsg(response, "Чтобы выбрать кому дарить подарок нажми на это -> /choice");
            }
            if (requestMessage.getText().equals("/choice")) {
                wrotes.put(requestMessage.getChat().getFirstName(), true);
                if (wrotes.get(requestMessage.getChat().getFirstName()) == false) {
                    choiceWorker(response);
                } else {
                    defaultMsg(response, requestMessage.getChat().getFirstName() + ", ты должен дарить только одному, так что не нарушай");
                }
            }
        }
    }

    @SneakyThrows
    private void choiceWorker(SendMessage response) {
        int number;
        while (true) {
            number = (int) (Math.random() * 11);
            if (workers.get(number).isBusy() == false) {
                defaultMsg(response, "Ты даришь подарок человеку с именем - " + workers.get(number).getName());
                defaultMsg(response, "Его интересы: " + workers.get(number).getInterests() + ". А дальше думай сам :)");
                workers.get(number).setBusy(true);
                break;
            }
        }
    }

    private void defaultMsg(SendMessage response, String msg) throws TelegramApiException {
        response.setText(msg);
        execute(response);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
