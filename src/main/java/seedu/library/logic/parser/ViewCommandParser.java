package seedu.library.logic.parser;
import static seedu.library.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.library.commons.core.index.Index;
import seedu.library.logic.commands.ViewCommand;
import seedu.library.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }
    }
}