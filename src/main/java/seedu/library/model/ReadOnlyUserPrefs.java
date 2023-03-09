package seedu.library.model;

import java.nio.file.Path;

import seedu.library.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getLibraryFilePath();

}
