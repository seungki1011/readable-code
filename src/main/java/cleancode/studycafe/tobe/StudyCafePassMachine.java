package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    /*
     * StudyCafeFileHandler: 이용권의 종류의 정보가 적힌 파일을 읽는 클래스
     * StudyCafePass: 이용권 자체를 나타낸 클래스
     * - passType(이용권 종류)
     * - duration(기간)
     * - price(가격)
     * - discountRate(할인률)
     */

    public void run() {
        try {
            /*
             * 환영 메세지와 공지사항 출력
             */
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            /*
             * 이용권 종류(Pass Type) 선택 사항 출력
             */
            outputHandler.askPassTypeSelection();

            /*
             * 구매할 이용권의 종류를 선택한다
             * 1: 시간 이용권(HOURLY)
             * 2: 주간 이용권(WEEKLY)
             * 3: 고정 이용권(FIXED)
             * 기타: 예외 발생
             */
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            /*
             * 1. StudyCafeFileHandler 객체를 만들어서(studyCafeFileHandler) 이용권의 종류의 리스트를 가져온다
             * 2. 해당 리스트(studyCafePasses)를 스트림으로 변환시킨다
             * 3. 필터를 사용해서 사용자가 선택한 이용권의 종류만 리스트로 뽑아낸다
             */
            List<StudyCafePass> studyCafePassChoices = filterStudyCafePassesByType(studyCafePassType);

            /*
             * 필터링한 이용권의 리스트를 출력한다
             */
            outputHandler.showPassListForSelection(studyCafePassChoices);

            /*
             * 이용권 선택: 사용자는 기간을 선택한다
             * 이용권 선택 -> StudyCafePass 객체인 selectedPass(선택한 이용권) 사용
             */
            StudyCafePass selectedPass = inputHandler.getSelectPass(studyCafePassChoices);

            /*
             * 이용권 선택까지 완료하면 selectedPass(선택한 이용권에 대한 객체)를 사용해서 주문 요약을 출력한다
             * - 선택한 이용권의 종류
             * - 사물함 여부(고정 이용권 선택 -> 사물함까지 선택한 경우)
             * - 할인 금액
             * - 총 결제 금액
             */

            /*
             * 시간 사용권와 주간 사용권은 사물함 선택 불가
             * lockerPass에 null이 들어간다
             */
            if (studyCafePassType == StudyCafePassType.HOURLY) {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }

            if (studyCafePassType == StudyCafePassType.WEEKLY) {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }

            /*
             * 고정 사용권은 사물함 선택 가능
             */
            if (studyCafePassType == StudyCafePassType.FIXED) {

                /*
                 * 1. 사물함 선택지가 적힌 파일을 읽어와서 리스트로 만든다
                 * 2. 해당 리스트를 스트림으로 변환한다
                 * 3. selectedPass(선택한 이용권)의 종류와 기간을 통해 필터링한다 (사물함의 기간과 선택한 이용권의 기간은 동일)
                 * 4. 첫 번째 결과를 반환한다 (없으면 null)
                 */
                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                    .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                            && option.getDuration() == selectedPass.getDuration()
                    )
                    .findFirst()
                    .orElse(null);

                /*
                 * 사물함 선택에 대한 처리
                 * lockerPass가 null이 아니면(고정 이용권을 골라서 사물한 선택이 가능하면)
                 */
                boolean lockerSelection = false;
                if (lockerPass != null) {
                    /*
                     * 사물함 이용 여부를 물어보는 메세지 출력
                     */
                    outputHandler.askLockerPass(lockerPass);

                    /*
                     * 사용자는 사물함의 이용 여부를 선택한다
                     * 만약 사물함 이용을 고르면 lockerSelection은 false가 아니게 됨
                     */
                    lockerSelection = inputHandler.getLockerSelection();
                }

                /*
                 * 만약 사물함을 사용하기로 선택했으면 이용권 주문 내역의 요약을 선택한 사물함 정보와 보여준다
                 */
                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                    return;
                }
                /*
                 * 만약 사물함 사용을 안하기로 하면 사물 정보 없이 기존 처럼 주문 요약을 보여준다
                 */
                outputHandler.showPassOrderSummary(selectedPass, null);
            }

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private List<StudyCafePass> filterStudyCafePassesByType(
        StudyCafePassType studyCafePassType) {

        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
            .toList();
    }

}
