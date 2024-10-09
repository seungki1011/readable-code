package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String HOURLY_INPUT = "1";
    private static final String WEEKLY_INPUT = "2";
    private static final String FIXED_INPUT = "3";
    private static final String LOCKER_SELECT_INPUT = "1";

    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = SCANNER.nextLine();

        if (HOURLY_INPUT.equals(userInput)) {
            return StudyCafePassType.HOURLY;
        }
        if (WEEKLY_INPUT.equals(userInput)) {
            return StudyCafePassType.WEEKLY;
        }
        if (FIXED_INPUT.equals(userInput)) {
            return StudyCafePassType.FIXED;
        }
        throw new AppException("잘못된 입력입니다.");
    }

    public StudyCafePass getSelectPass(List<StudyCafePass> passes) {
        String userInput = SCANNER.nextLine();

        int selectedIndex = Integer.parseInt(userInput) - 1;
        return passes.get(selectedIndex);
    }

    public boolean getLockerSelection() {
        String userInput = SCANNER.nextLine();

        return LOCKER_SELECT_INPUT.equals(userInput);
    }

}
