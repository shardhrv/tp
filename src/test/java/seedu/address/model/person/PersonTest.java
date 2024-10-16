package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void isSamePerson_differentConstructor() {
        Person person = new PersonBuilder().build();
        Person personCopy = new PersonBuilder(person).build();
        assertTrue(person.isSamePerson(personCopy));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));



        editedAlice = new PersonBuilder(ALICE).withRoles("vendor").build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new PersonBuilder(ALICE).withTelegramUsername("al1ice").build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", roles=" + ALICE.getRoles() + "}";

        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void compareTo_lowerLexicographicalOrder() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        Person bobCopy = new PersonBuilder(BOB).build();
        assertTrue(ALICE.compareTo(BOB) < 0);
    }

    @Test
    public void compareTo_higherLexicographicalOrder() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        Person bobCopy = new PersonBuilder(BOB).build();
        assertTrue(BOB.compareTo(ALICE) > 0);
    }

    @Test
    public void getRoles_oneRole() {
        Person person = new PersonBuilder().withRoles("attendee").build();
        try {
            assertTrue(person.getRoles().contains(RoleHandler.getRole("attendee")));

        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasRole_oneRole() {
        Person person = new PersonBuilder().withRoles("attendee").build();
        try {
            assertTrue(person.hasRole(RoleHandler.getRole("attendee")));

        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasRole_twoRole() {
        Person person = new PersonBuilder().withRoles("attendee", "speaker").build();
        try {
            assertTrue(person.hasRole(RoleHandler.getRole("attendee")));
            assertTrue(person.hasRole(RoleHandler.getRole("speaker")));

        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals_sameRole() {
        Person person = new PersonBuilder().withRoles("attendee").build();

        Person personCopy = new PersonBuilder().withRoles("attendee").build();
        assertTrue(person.equals(personCopy));

    }


}
