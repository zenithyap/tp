package seedu.library.logic.commands;

import static seedu.library.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.library.testutil.TypicalBookmarks.getTypicalLibrary;

import org.junit.jupiter.api.Test;

import seedu.library.model.Library;
import seedu.library.model.Model;
import seedu.library.model.ModelManager;
import seedu.library.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyLibrary_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyLibrary_success() {
        Model model = new ModelManager(getTypicalLibrary(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLibrary(), new UserPrefs());
        expectedModel.setLibrary(new Library());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
