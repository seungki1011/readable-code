package cleancode.studycafe.tobe.model.order;

import java.util.Optional;
import java.util.stream.Stream;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;

public class StudyCafePassOrder {

	private final StudyCafePass studyCafePass;
	private final StudyCafeLockerPass lockerPass;

	public StudyCafePassOrder(StudyCafePass studyCafePass, StudyCafeLockerPass lockerPass) {
		this.studyCafePass = studyCafePass;
		this.lockerPass = lockerPass;
	}

	public static StudyCafePassOrder of(StudyCafePass studyCafePass, StudyCafeLockerPass lockerPass) {
		return new StudyCafePassOrder(studyCafePass, lockerPass);
	}

	public int discountPrice() {
		return studyCafePass.calculateDiscount();
	}

	public int totalPrice() {
		return studyCafePass.getPrice()
			+ Stream.ofNullable(lockerPass)
				.map(StudyCafeLockerPass::getPrice)
				.reduce(0, Integer::sum)
			- discountPrice();
	}

	public StudyCafePass getStudyCafePass() {
		return studyCafePass;
	}

	public Optional<StudyCafeLockerPass> getLockerPass() {
		return Optional.ofNullable(lockerPass);
	}
}
