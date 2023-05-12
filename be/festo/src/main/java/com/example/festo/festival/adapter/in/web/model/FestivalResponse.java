package com.example.festo.festival.adapter.in.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


public class FestivalResponse {
    @Getter
    @Builder
    public static class MainPage {
        private Long festivalId;
        private String imageUrl;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @Builder
    public static class Search {
        private Long festivalId;
        private String imageUrl;
        private String name;
    }

    @Getter
    @Builder
    public static class Manager {
        private Long festivalId;
        private String imageUrl;
        private String name;
    }

    @Getter
    @Builder
    public static class Detail {
        private Long festivalId;
        private String imageUrl;
        private String name;
        private String address;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
        private LocalDate startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
        private LocalDate endDate;
        private String description;
    }

    @Getter
    public static class Creation {

        private final Long festivalId;

        public Creation(Long festivalId) {
            this.festivalId = festivalId;
        }
    }

    @Getter
    public static class Invitation {

        private final Long festivalId;

        public Invitation(Long festivalId) {
            this.festivalId = festivalId;
        }
    }
    @Getter
    public static class InviteCode {

        private final String inviteCode;

        public InviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }
    }
    @Getter
    public static class IsOpen {

        private final boolean isOpen;

        public IsOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }
    }
}
