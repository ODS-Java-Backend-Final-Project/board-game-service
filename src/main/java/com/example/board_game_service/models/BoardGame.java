package com.example.board_game_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private int minPlayers;
    private int maxPlayers;
    private int duration;

    public BoardGame(String name, String category, int minPlayers, int maxPlayers, int duration) {
        this.name = name;
        this.category = category;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.duration = duration;
    }
}
