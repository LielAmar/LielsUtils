import com.lielamar.lielsutils.validation.CharValidation;
import com.lielamar.lielsutils.validation.DoubleValidation;
import com.lielamar.lielsutils.validation.IntValidation;
import com.lielamar.lielsutils.validation.Validation;

import java.util.Arrays;
import java.util.List;

public class ValidationTest {

    public static void main(String[] args) {

        List<Validation> violations = Validation.validateParameters(Arrays.asList(
                new IntValidation(0, "Radius must be greater than 0", 1),
                new IntValidation(18, "Secondvar must be greater than/equals to 0 and less than/equals to 15", 0, 15),
                new DoubleValidation(0.7, "Percentage must be greater than 0", 1),
                new DoubleValidation(6.123, "PercentageTwo must be greater than 0", 0, 6),
                new CharValidation('o', "Axis must be either 'x' or 'z'", new Character[] { 'x', 'z' }),
                new CharValidation('6', "Axis must be either 'x', 'z' or 'Y'", new Character[] { 'x', 'z', 'Y' })
        ));

        for(Validation violation : violations) {
            System.out.println("VIOLATION: " + violation.getMessage());
        }
    }
}
