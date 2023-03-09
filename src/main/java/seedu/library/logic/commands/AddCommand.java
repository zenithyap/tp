package seedu.library.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.library.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.library.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.library.logic.commands.exceptions.CommandException;
import seedu.library.model.Model;
import seedu.library.model.bookmark.Bookmark;

/**
 * Adds a bookmark to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bookmark to the library. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_GENRE + "GENRE "
            + PREFIX_AUTHOR + "AUTHOR "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_GENRE + "johnd@example.com "
            + PREFIX_AUTHOR + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New bookmark added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKMARK = "This bookmark already exists in the library";

    private final Bookmark toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Bookmark}
     */
    public AddCommand(Bookmark bookmark) {
        requireNonNull(bookmark);
        toAdd = bookmark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBookmark(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKMARK);
        }

        model.addBookmark(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
