package cleancode.studycafe.tobe.model;

public class StudyCafePass implements StudyCafePassable {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    public StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public boolean passTypeIs(StudyCafePassType studyCafePassType) {
        return this.passType == studyCafePassType;
    }

    public boolean passTypeIsNot(StudyCafePassType studyCafePassType) {
        return this.passType != studyCafePassType;
    }

    public boolean durationIs(int duration) {
        return this.duration == duration;
    }

    public int calculateDiscount() {
        return (int) (price * discountRate);
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
