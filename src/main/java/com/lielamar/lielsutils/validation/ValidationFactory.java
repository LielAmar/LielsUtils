package com.lielamar.lielsutils.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidationFactory
{
	//Container of factory methods
	private ValidationFactory(){}
	
	public static NumericalRangeBuilder<?> ofNumericalRange() {
		return new NumericalRangeBuilder<>();
	}

	public static PossibleCharactersBuilder<?> ofPossibleCharacters(Character... possibilities) {
		PossibleCharactersBuilder<?> builder = new PossibleCharactersBuilder<>();
		Arrays.stream(possibilities).forEach(builder::withPossibility);
		
		return builder;
	}

	public static PossibleCharactersBuilder<?> ofCharacterRange(char min, char max) {
		PossibleCharactersBuilder<?> builder = new PossibleCharactersBuilder<>();

		for(char letter = min; letter <= max; letter++)
			builder.withPossibility(letter);
		
		return builder;
	}
	
	
	/* =--=---=
	 * Builders
	 * =--=---=
	 */
	public static abstract class ValidationBuilder<T, B extends ValidationBuilder<T, B>> {

		protected String errorMessage = "";

		public B withErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
			return self();
		}

		@SuppressWarnings("unchecked")
		protected final B self() {
			return (B) this;
		}
		
		public abstract Validation<T> build();
	}

	public static class NumericalRangeBuilder<B extends NumericalRangeBuilder<B>> extends ValidationBuilder<Number, B> {
		
		protected double min = 0.0, max = Double.MAX_VALUE;

		public B withMin(double min) {
			this.min = min;
			return self();
		}
		
		public B withMax(double max) {
			this.max = max;
			return self();
		}

		@Override
		public Validation<Number> build() {
			
			return new AbstractValidation<Number>(this.errorMessage) {

				@Override
				public boolean test(Number number) {
					
					double doubleValue = number.doubleValue();

					return (doubleValue >= min && doubleValue <= max);
				}
			};
		}
	}

	public static class PossibleCharactersBuilder<B extends PossibleCharactersBuilder<B>> extends ValidationBuilder<Character, B>
	{
		private final Set<Character> allowedCharacters = new HashSet<>();

		public B withPossibility(char possibility) {
			this.allowedCharacters.add(possibility);
			return self();
		}

		@Override
		public Validation<Character> build() {
			
			return new AbstractValidation<Character>(this.errorMessage) {

				@Override
				public boolean test(Character character) {
					return allowedCharacters.contains(character);
				}
			};
		}
	}
}
