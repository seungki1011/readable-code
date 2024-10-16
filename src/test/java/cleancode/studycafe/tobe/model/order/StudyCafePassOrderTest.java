package cleancode.studycafe.tobe.model.order;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;

class StudyCafePassOrderTest {

	@Test
	@DisplayName("총 가격 계산 시, 선택한 패스와 사물함에 대한 총 결제 금액이 나온다")
	void calculateTotalPriceOfOrder() {
		// given
		StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);
		StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 30000);
		StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);
		int expectedTotalPrice = 625000;

		// when
		int totalPrice = passOrder.getTotalPrice();

		// then
		assertThat(totalPrice).isEqualTo(expectedTotalPrice);
	}

	@Test
	@DisplayName("총 가격 계산 시, 사물함을 선택하지 않는 경우 패스권만 계산한 결제 금액이 나온다")
	void calculateOnlyPassIfLockerIsNotSelected() {
		// given
		StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);
		StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, null);
		int expectedTotalPrice = 595000;

		// when
		int totalPrice = passOrder.getTotalPrice();

		// then
		assertThat(totalPrice).isEqualTo(expectedTotalPrice);
	}

}