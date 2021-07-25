import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;

import com.lielamar.lielsutils.validation.Validation;
import com.lielamar.lielsutils.validation.ValidationFactory;
import com.lielamar.lielsutils.validation.ValidationUtils;

public class ValidationTest {

	private static final List<Validation<Character>> CHARACTER_VALIDATIONS = Arrays.asList(
			ValidationFactory.ofCharacterRange('C', 'Z')
			.withErrorMessage("'%s' out of range")
			.build(),

			ValidationFactory.ofPossibleCharacters('A', 'B', 'C')
			.withErrorMessage("'%s' isn't included")
			.build());

	public static void main(String[] args) 
	{
		String errorMessages = ValidationUtils.buildErrorMessages('a', CHARACTER_VALIDATIONS).collect(joining(" + ", "[", "]"));

		System.out.println("Errors: " + errorMessages);
	}
}
