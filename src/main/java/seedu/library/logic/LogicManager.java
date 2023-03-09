package seedu.library.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.library.commons.core.GuiSettings;
import seedu.library.commons.core.LogsCenter;
import seedu.library.logic.commands.Command;
import seedu.library.logic.commands.CommandResult;
import seedu.library.logic.commands.exceptions.CommandException;
import seedu.library.logic.parser.LibraryParser;
import seedu.library.logic.parser.exceptions.ParseException;
import seedu.library.model.Model;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final LibraryParser libraryParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        libraryParser = new LibraryParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = libraryParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveLibrary(model.getLibrary());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyLibrary getLibrary() {
        return model.getLibrary();
    }

    @Override
    public ObservableList<Bookmark> getFilteredBookmarkList() {
        return model.getFilteredBookmarkList();
    }

    @Override
    public Path getLibraryFilePath() {
        return model.getLibraryFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
