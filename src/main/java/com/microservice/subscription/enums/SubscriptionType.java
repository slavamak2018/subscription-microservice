package com.microservice.subscription.enums;

public enum SubscriptionType {

    YOUTUBE ("YouTube Premium"),
    VK_MUSIC ("VK Музыка"),
    YANDEX_PLUS ("Яндекс.Плюс"),
    NETFLIX ("Netflix");

    private final String title;

    private SubscriptionType(String title) {
        this.title = title;
    }

}
