package cleancode.studycafe.tobe.model;

public class StudyCafeLockerPass implements StudyCafePassable {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;

    public StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public boolean passTypeIs(StudyCafePassType lockerPassType) {
        return this.passType == lockerPassType;
    }

    public boolean isSameTypeAndDurationAs(StudyCafePass studyCafePass) {
        return studyCafePass.passTypeIs(passType) && studyCafePass.durationIs(duration);
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getPrice() {
        return price;
    }

}
