package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.Passes;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPassChoice();
            Optional<StudyCafeLockerPass> lockerPass = findLockerPassBy(selectedPass);

            outputHandler.showPassOrderSummary(
                StudyCafePassOrder.of(selectedPass, lockerPass.orElse(null))
            );

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPassChoice() {
        outputHandler.askPassTypeSelection();
        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> studyCafePassChoices = filterStudyCafePassesBy(studyCafePassType);
        outputHandler.showPassListForSelection(studyCafePassChoices);

		return inputHandler.getSelectPass(studyCafePassChoices);
    }

    private Optional<StudyCafeLockerPass> findLockerPassBy(StudyCafePass selectedPass) {
        if (selectedPass.passTypeIsNot(StudyCafePassType.FIXED)) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> studyCafeLockerPass = filterMatchingLockerPassesBy(selectedPass);

        if (studyCafeLockerPass.isPresent()) {
            outputHandler.askLockerPass(studyCafeLockerPass.get());
            boolean lockerIsSelected = inputHandler.getLockerSelection();
            if (lockerIsSelected) {
                return studyCafeLockerPass;
            }
        }
        return Optional.empty();
    }

    private List<StudyCafePass> filterStudyCafePassesBy(StudyCafePassType studyCafePassType) {
        Passes<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses
            .filterStudyCafePassesBy(studyCafePass ->
                studyCafePass.passTypeIs(studyCafePassType));
    }

    private Optional<StudyCafeLockerPass> filterMatchingLockerPassesBy(StudyCafePass selectedPass) {
        Passes<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        return lockerPasses
            .filterLockerPassesBy(lockerPass ->
                lockerPass.isSameTypeAndDurationAs(selectedPass)
            );
    }

}
