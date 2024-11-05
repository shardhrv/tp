package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.person.predicates.PhoneNumberContainsKeywordPredicate;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;


public class SearchModeSearchCommandParserTest {

    private SearchModeSearchCommandParser parser = new SearchModeSearchCommandParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInput_success() {
        // whitespace only
        SearchModeSearchCommand expectedCommand = new SearchModeSearchCommand(
                new NameContainsKeywordsPredicate(Collections.singletonList("Amy")));

        assertParseSuccess(parser, " n/Amy", expectedCommand);

        // multiple whitespaces
        assertParseSuccess(parser, " n/  Amy  ", expectedCommand);

        // multiple keywords
        expectedCommand = new SearchModeSearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Amy", "Bob")));
        assertParseSuccess(parser, " n/Amy Bob", expectedCommand);

        // multiple keywords with leading and trailing whitespaces
        assertParseSuccess(parser, " n/  Amy Bob  ", expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {

        SearchModeSearchCommand expectedCommand = null;
        try {
            NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(
                    Collections.singletonList("Amy"));
            PhoneNumberContainsKeywordPredicate phonePredicate = new PhoneNumberContainsKeywordPredicate(
                    Collections.singletonList("1234567"));
            EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(
                    Collections.singletonList("test@gmail.com"));
            AddressContainsKeywordsPredicate addressPredicate = new AddressContainsKeywordsPredicate(
                    new ArrayList<>(Arrays.asList("123", "Road")));
            PersonIsRolePredicate rolePredicate = new PersonIsRolePredicate(
                    Collections.singletonList(RoleHandler.getRole("attendee")));
            Set<Predicate<Person>> predicates = new HashSet<>(Arrays.asList(namePredicate, phonePredicate,
                    emailPredicate, addressPredicate, rolePredicate));
            expectedCommand = new SearchModeSearchCommand(predicates);
        } catch (InvalidRoleException e) {
            assert(false);
        }

        assertParseSuccess(parser, " n/Amy p/1234567 e/test@gmail.com"
                + " r/attendee a/123 Road", expectedCommand);
    }
}