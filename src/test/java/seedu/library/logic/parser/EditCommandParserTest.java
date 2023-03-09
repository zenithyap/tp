package seedu.library.logic.parser;

import static seedu.library.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.library.logic.commands.CommandTestUtil.AUTHOR_DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.AUTHOR_DESC_BOB;
import static seedu.library.logic.commands.CommandTestUtil.GENRE_DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.GENRE_DESC_BOB;
import static seedu.library.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.library.logic.commands.CommandTestUtil.INVALID_GENRE_DESC;
import static seedu.library.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.library.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.library.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.library.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.library.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.library.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.library.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.library.logic.commands.CommandTestUtil.VALID_AUTHOR_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_GENRE_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_GENRE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.library.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.library.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.library.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.library.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.library.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.library.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;
import static seedu.library.testutil.TypicalIndexes.INDEX_SECOND_BOOKMARK;
import static seedu.library.testutil.TypicalIndexes.INDEX_THIRD_BOOKMARK;

import org.junit.jupiter.api.Test;

import seedu.library.commons.core.index.Index;
import seedu.library.logic.commands.EditCommand;
import seedu.library.model.bookmark.Author;
import seedu.library.model.bookmark.Genre;
import seedu.library.model.bookmark.Title;
import seedu.library.model.bookmark.Phone;
import seedu.library.model.tag.Tag;
import seedu.library.testutil.EditBookmarkDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_GENRE_DESC, Genre.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_AUTHOR_DESC, Author.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + GENRE_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Bookmark} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_GENRE_DESC + VALID_AUTHOR_AMY + VALID_PHONE_AMY,
                Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BOOKMARK;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + GENRE_DESC_AMY + AUTHOR_DESC_AMY + TITLE_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_AMY)
                .withPhone(VALID_PHONE_BOB).withGenre(VALID_GENRE_AMY).withAuthor(VALID_AUTHOR_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOKMARK;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + GENRE_DESC_AMY;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withGenre(VALID_GENRE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_BOOKMARK;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_AMY;
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withName(VALID_TITLE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditBookmarkDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + GENRE_DESC_AMY;
        descriptor = new EditBookmarkDescriptorBuilder().withGenre(VALID_GENRE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + AUTHOR_DESC_AMY;
        descriptor = new EditBookmarkDescriptorBuilder().withAuthor(VALID_AUTHOR_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditBookmarkDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_BOOKMARK;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + AUTHOR_DESC_AMY + GENRE_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + AUTHOR_DESC_AMY + GENRE_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + AUTHOR_DESC_BOB + GENRE_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withGenre(VALID_GENRE_BOB).withAuthor(VALID_AUTHOR_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_BOOKMARK;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + GENRE_DESC_BOB + INVALID_PHONE_DESC + AUTHOR_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditBookmarkDescriptorBuilder().withPhone(VALID_PHONE_BOB).withGenre(VALID_GENRE_BOB)
                .withAuthor(VALID_AUTHOR_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_BOOKMARK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditBookmarkDescriptor descriptor = new EditBookmarkDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
