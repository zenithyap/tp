package seedu.library.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.library.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.library.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_PROGRESS;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.library.logic.parser.CliSyntax.PREFIX_URL;

import seedu.library.logic.commands.exceptions.CommandException;
import seedu.library.model.Model;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.model.tag.Tag;

/**
 * Adds a bookmark to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bookmark to the library. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AUTHOR + "AUTHOR "
            + PREFIX_PROGRESS + "PROGRESS "
            + PREFIX_GENRE + "GENRE "
            + PREFIX_URL + "URL "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Hobbit "
            + PREFIX_AUTHOR + "J. R. R. Tolkien "
            + PREFIX_PROGRESS + "1 1 1 "
            + PREFIX_GENRE + "Fantasy "
            + PREFIX_URL + "https://allnovel.net/the-hobbit.html "
            + PREFIX_TAG + "Literature Reading "
            + PREFIX_TAG + "Holiday reading list";

    public static final String MESSAGE_SUCCESS = "New bookmark added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKMARK = "This bookmark already exists in the library";
    public static final String MESSAGE_ERROR_TAG = "Tag not in tag list.";

    private final Bookmark toAdd;
    private final boolean hasTags;

    /**
     * Creates an AddCommand to add the specified {@code Bookmark}
     */
    public AddCommand(Bookmark bookmark, boolean hasTags) {
        requireNonNull(bookmark);
        toAdd = bookmark;
        this.hasTags = hasTags;
    }

    /**
     * Creates an AddCommand to add the specified {@code Bookmark}
     * with default value false for hasTags
     */
    public AddCommand(Bookmark bookmark) {
        requireNonNull(bookmark);
        this.toAdd = bookmark;
        this.hasTags = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBookmark(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKMARK);
        }

        if (hasTags && !Tag.isValidTag(toAdd.getTags(), model)) {
            throw new CommandException(MESSAGE_ERROR_TAG);
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
