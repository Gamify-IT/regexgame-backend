package de.unistuttgart.finitequizbackend.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameResult {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private int questionCount;
  private float score;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<RoundResult> correctAnsweredQuestions;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<RoundResult> wrongAnsweredQuestions;

  private UUID configurationAsUUID;
  private String playerId;
  private LocalDateTime playedTime;

  public GameResult(
    int questionCount,
    float score,
    List<RoundResult> correctAnsweredQuestions,
    List<RoundResult> wrongAnsweredQuestions,
    UUID configurationAsUUID,
    String playerId
  ) {
    this.questionCount = questionCount;
    this.score = score;
    this.correctAnsweredQuestions = correctAnsweredQuestions;
    this.wrongAnsweredQuestions = wrongAnsweredQuestions;
    this.configurationAsUUID = configurationAsUUID;
    this.playerId = playerId;
    this.playedTime = LocalDateTime.now();
  }
}