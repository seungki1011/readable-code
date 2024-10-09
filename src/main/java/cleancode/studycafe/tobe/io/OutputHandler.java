package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import cleancode.studycafe.tobe.model.StudyCafePassable;
import cleancode.studycafe.tobe.model.order.StudyCafePassOrder;

import java.util.List;
import java.util.Optional;

public class OutputHandler {

    private static final String WELCOME_MESSAGE = "*** 프리미엄 스터디카페 ***";
    // 할인률 변동에 따라 동적으로 변하도록 수정
    private static final String ANNOUNCEMENT_MESSAGE = "* 사물함은 고정석 선택 시 이용 가능합니다. (추가 결제)\\n"
        + "* !오픈 이벤트! 2주권 이상 결제 시 10% 할인, 12주권 결제 시 15% 할인! (결제 시 적용)";
    private static final String PASS_SELECTION_PROMPT = "사용하실 이용권을 선택해 주세요.\n"
        + "1. 시간 이용권(자유석) | 2. 주단위 이용권(자유석) | 3. 1인 고정석";

    public void showWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void showAnnouncement() {
        System.out.println(ANNOUNCEMENT_MESSAGE);
        System.out.println();
    }

    public void askPassTypeSelection() {
        System.out.println(PASS_SELECTION_PROMPT);
    }

    public void showPassListForSelection(List<StudyCafePass> passes) {
        System.out.println();
        System.out.println("이용권 목록");
        for (int index = 0; index < passes.size(); index++) {
            StudyCafePass pass = passes.get(index);
            System.out.println(String.format("%s. ", index + 1) + display(pass));
        }
    }

    public void askLockerPass(StudyCafeLockerPass lockerPass) {
        System.out.println();
        String askMessage = String.format(
            "사물함을 이용하시겠습니까? (%s)",
            display(lockerPass)
        );

        System.out.println(askMessage);
        System.out.println("1. 예 | 2. 아니오");
    }

    public void showPassOrderSummary(StudyCafePassOrder order) {

        StudyCafePass selectedPass = order.getStudyCafePass();
        Optional<StudyCafeLockerPass> lockerPass = order.getLockerPass();

        System.out.println();
        System.out.println("이용 내역");
        System.out.println("이용권: " + display(selectedPass));

        lockerPass.ifPresent(pass -> {
            System.out.println("사물함: " + display(pass));
        });


        if (order.discountPrice() > 0) {
            System.out.println("이벤트 할인 금액: " + order.discountPrice() + "원");
        }

        System.out.println("총 결제 금액: " + order.totalPrice() + "원");
        System.out.println();
    }

    public String display(StudyCafePassable pass) {
        if (pass.passTypeIs(StudyCafePassType.HOURLY)) {
            return String.format("%s시간권 - %d원", pass.getDuration(), pass.getPrice());
        }
        if (pass.passTypeIs(StudyCafePassType.WEEKLY)) {
            return String.format("%s주권 - %d원", pass.getDuration(), pass.getPrice());
        }
        if (pass.passTypeIs(StudyCafePassType.FIXED)) {
            return String.format("%s주권 - %d원", pass.getDuration(), pass.getPrice());
        }
        return "";
    }

    public void showSimpleMessage(String message) {
        System.out.println(message);
    }

}
