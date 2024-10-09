package cleancode.studycafe.tobe.model;

public interface StudyCafePassable {

	StudyCafePassType getPassType();
	int getDuration();
	int getPrice();
	boolean passTypeIs(StudyCafePassType studyCafePassType);
}
