package ru.yandex;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

public class LeaveCommentsTest extends BaseTest implements TestsInit {
    List<String> cities = new ArrayList<>();
    List<String> places = new ArrayList<>();
    Set<String> uniqPlaces = new HashSet<>();
    private String userLogin;
    private String userPassword;


    @BeforeClass
    public void setUp() {
        cities.addAll(Arrays.asList(
                "Москва",
                "Новосибирск",
                "Екатеринбург",
                "Казань",
                "Нижний Новгород",
                "Челябинск",
                "Омск",
                "Самара",
                "Ростов-на-Дону",
                "Уфа",
                "Красноярск",
                "Пермь",
                "Воронеж",
                "Волгоград",
                "Санкт-Петербург"
        ));
        places.addAll(Arrays.asList(
                "рестораны",
                "магазины",
                "достопримечательность"
        ));
        userLogin = "sprotyv2";
        userPassword = "Sprotyv_2";
//        login(userLogin, userPassword);
        collectPlacesUrls("Москва", "достопримечательность", 1);
    }

    @Test
    public void yandexCommentsTest() {
//        for (String place : places) {
//            for (String city : cities) {
//                collectPlaces(city, place, 600);
//            }
//        }
//
//        for (String placeHref : uniqPlaces) {
//            postComment(placeHref, "Good place!");
//        }

        String debugUrl = uniqPlaces.stream().findFirst().get();
        postComment(debugUrl, "Good place!");

    }

}
