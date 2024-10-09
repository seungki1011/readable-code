package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.Passes;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public class StudyCafeFileHandler {

    private static final String PASS_LIST_FILE = "src/main/resources/cleancode/studycafe/pass-list.csv";
    private static final String LOCKER_PASS_LIST_FILE = "src/main/resources/cleancode/studycafe/pass-list.csv";

    public Passes<StudyCafePass> readStudyCafePasses() {
        return readFileAndParse(PASS_LIST_FILE, this::parseStudyCafePass);
    }

    public Passes<StudyCafeLockerPass> readLockerPasses() {
        return readFileAndParse(LOCKER_PASS_LIST_FILE, this::parseLockerPass);
    }

    private <T> Passes<T> readFileAndParse(String filePath, Function<String, T> parseFunction) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return Passes.of(lines.stream()
                .map(parseFunction)
                .toList());
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private StudyCafePass parseStudyCafePass(String line) {
        String[] values = line.split(",");
        StudyCafePassType passType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);
        double discountRate = Double.parseDouble(values[3]);

        return StudyCafePass.of(passType, duration, price, discountRate);
    }

    private StudyCafeLockerPass parseLockerPass(String line) {
        String[] values = line.split(",");
        StudyCafePassType passType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);

        return StudyCafeLockerPass.of(passType, duration, price);
    }
}
