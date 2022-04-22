package ru.company;

public class Menu {
    private final String menu =  "==========\n1 - Список людей\n2 - Добавить человека\n3 " +
            "- Удалить человека\n4 - Обновить запись о человеке\n5 - Посмотреть человека\n0 - Выход" +
            "\n==========";

    public void showMenu() {
        System.out.println(menu);
    }
}
