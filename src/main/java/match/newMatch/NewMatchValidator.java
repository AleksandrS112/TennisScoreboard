package match.newMatch;

import player.PlayerDto;
import player.PlayerDtoValidator;
import commons.validation.Error;
import commons.validation.ValidationResult;
import commons.validation.Validator;

public class NewMatchValidator implements Validator<NewMatch> {

    private static final NewMatchValidator INSTANCE = new NewMatchValidator();

    private final PlayerDtoValidator playerDtoValidator;

    private NewMatchValidator() {
        playerDtoValidator = PlayerDtoValidator.getInstance();
    }

    public static NewMatchValidator getInstance(){
        return INSTANCE;
    }

    /*
     * Валидируем каждого игрока с помощью его валидатора, добавляем ошибки валидации каждого игрока в общий validationResult
     * путем их копирования с добавлением префикса, к какому игроку они относятся. Если какой-то игрок не прошел валидацию,
     * и, соответственно, общий validationResult не пуст, то сразу его возвращаем, потому что дальше проверять уже нет смысла,
     * т.к. дальше может вылететь NullPointerException и имена все равно должны поменяться. Если оба игрока прошли валидацию
     * проверяем, что они не эквивалентны путем сравнения их имени и возвращаем validationResult.
     */
    @Override
    public ValidationResult isValid(NewMatch newMatchDto) {
        ValidationResult validationResult = new ValidationResult();
        PlayerDto player1 = newMatchDto.getPlayer1();
        PlayerDto player2 = newMatchDto.getPlayer2();
        playerDtoValidator.isValid(player1).getErrors()
                .forEach(error -> validationResult.add(Error.of(error.getCode(), "Игрок №1: " +error.getMessage())));
        playerDtoValidator.isValid(player2).getErrors()
                .forEach(error -> validationResult.add(Error.of(error.getCode(), "Игрок №2: " +error.getMessage())));
        if (!validationResult.isValid())
            return validationResult;
        if (player1.getName().equals(player2.getName()))
           validationResult.add(Error.of("400", "Имена Игрока №1 и Игрока №2 совпадают."));
        return validationResult;
    }

}
