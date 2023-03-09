package seedu.library.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.library.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;

import seedu.library.model.Model;

/**
 * Lists all bookmarks in the library to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all bookmarks";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
