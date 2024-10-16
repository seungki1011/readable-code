package cleancode.studycafe.tobe.io.provider;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.tobe.provider.LockerPassProvider;

class LockerPassFileReaderTest {
	@Test
	@DisplayName("사물함 패스를 파일에서 읽어올 수 있다")
	void shouldReadLockerPassDetailsByAvailableFile() {
		// given
		LockerPassProvider lockerPassProvider = new LockerPassFileReader();
		StudyCafeSeatPass studyCafeSeatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);

		// when
		StudyCafeLockerPasses lockerPasses = lockerPassProvider.getLockerPasses();

		// then
		assertThat(lockerPasses).isNotNull();
		assertThat(lockerPasses.findLockerPassBy(studyCafeSeatPass)).isPresent();
	}
}