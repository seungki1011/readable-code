package cleancode.studycafe.tobe.model.pass;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeSeatPassTest {

	@Test
	@DisplayName("생성된 SeatPass의 할인 금액을 가져올 시, 기존 가격에 할인율이 정확히 계산된 가격이 나와야 한다")
	void calculateCorrectDiscountPrice() {
		// given
		StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 100000, 0.1);
		int expectedDiscountPrice = 10000;

		// when
		int discountPrice = seatPass.getDiscountPrice();

		// then
		assertThat(discountPrice).isEqualTo(expectedDiscountPrice);
	}

	@Test
	@DisplayName("고정석 패스 선택시 사물함 이용은 가능하다")
	void fixedPassCanUseLocker() {
		// given
		StudyCafeSeatPass fixedPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);

		// when
		boolean cannotUseLocker = fixedPass.cannotUseLocker();

		// then
		assertThat(cannotUseLocker).isFalse();
	}

	@Test
	@DisplayName("시간 단위 패스 선택시 사물함 이용은 불가능하다")
	void hourlyPassCannotUseLocker() {
		// given
		StudyCafeSeatPass hourlyPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 12, 13000, 0.0);

		// when
		boolean cannotUseLocker = hourlyPass.cannotUseLocker();

		// then
		assertThat(cannotUseLocker).isTrue();
	}

}