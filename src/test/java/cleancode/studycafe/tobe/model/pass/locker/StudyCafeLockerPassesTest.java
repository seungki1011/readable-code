package cleancode.studycafe.tobe.model.pass.locker;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cleancode.studycafe.tobe.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.provider.LockerPassProvider;

class StudyCafeLockerPassesTest {
	@Test
	@DisplayName("사물함 패스를 읽어올 수 있어야 하며, 유효한 좌석 패스로 사물함 패스를 찾을 수 있어야 한다")
	void shouldFindLockerPassBySeatPass() {
		// given
		LockerPassProvider lockerPassProvider = new LockerPassFileReader();
		StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);
		StudyCafeLockerPasses lockerPasses = lockerPassProvider.getLockerPasses();

		// when
		Optional<StudyCafeLockerPass> optionalFoundLockPass = lockerPasses.findLockerPassBy(seatPass);

		// then
		assertThat(optionalFoundLockPass).isPresent();
		StudyCafeLockerPass foundLockerPass = optionalFoundLockPass.get();
		assertThat(foundLockerPass.getPassType()).isEqualTo(seatPass.getPassType());
		assertThat(foundLockerPass.getDuration()).isEqualTo(seatPass.getDuration());
	}
}