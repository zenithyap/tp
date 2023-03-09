package seedu.library.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.library.model.Library;
import seedu.library.model.Model;

/**
 * Clears the librart.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Library has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setLibrary(new Library());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
