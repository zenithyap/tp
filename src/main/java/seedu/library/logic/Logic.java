package seedu.library.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.library.commons.core.GuiSettings;
import seedu.library.logic.commands.CommandResult;
import seedu.library.logic.commands.exceptions.CommandException;
import seedu.library.logic.parser.exceptions.ParseException;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.bookmark.Bookmark;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Library.
     *
     * @see seedu.library.model.Model#getLibrary()
     */
    ReadOnlyLibrary getLibrary();

    /** Returns an unmodifiable view of the filtered list of bookmarks */
    ObservableList<Bookmark> getFilteredBookmarkList();

    /**
     * Returns the user prefs' library file path.
     */
    Path getLibraryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
