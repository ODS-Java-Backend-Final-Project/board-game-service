package com.example.board_game_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name can´t be empty")
    @Size(max = 100, message = "Name can´t be longer than 100 characters")
    private String name;

    @NotBlank(message = "Category can´t be empty")
    private String category;

    @NotNull(message = "You must provide a minimum number of players")
    @Min(1)
    private int minPlayers;

    @NotNull(message = "You must provide a maximum number of players")
    @Min(1)
    private int maxPlayers;

    @NotNull(message = "You must provide a duration in minutes")
    @Min(1)
    private int duration;
}
