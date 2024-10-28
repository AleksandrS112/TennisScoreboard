package player;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import commons.validation.Error;
import commons.validation.ValidationResult;
import commons.validation.Validator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerDtoValidator implements Validator<PlayerDto> {

    private static final int MAX_LENGTH_NAME = 30;

    private static final PlayerDtoValidator INSTANCE = new PlayerDtoValidator();

    public static PlayerDtoValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(PlayerDto playerDto) {
        ValidationResult validationResult = new ValidationResult();
        if (playerDto.getName().isBlank()) {
            validationResult.add(Error.of("404", "Отсутствует имя игрока."));
            return validationResult;
        }
        if (playerDto.getName().length() > MAX_LENGTH_NAME)
            validationResult.add(Error.of("400", "Имя игрока больше " +MAX_LENGTH_NAME +" символов."));
        return validationResult;
    }
}
