package main.studycafe.tobe;

import main.studycafe.tobe.exception.AppException;
import main.studycafe.tobe.io.StudyCafeFileHandler;
import main.studycafe.tobe.io.StudyCafeIOHandler;
import main.studycafe.tobe.model.StudyCafeLockerPass;
import main.studycafe.tobe.model.StudyCafePass;
import main.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = getPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = getLockerPass(selectedPass);

            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> ioHandler.showPassOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }

    }

    private StudyCafePass getPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        List<StudyCafePass> passCandidates = getPassesBy(passType);

        ioHandler.showPassListForSelection(passCandidates);
        return ioHandler.askPassSelecting(passCandidates);
    }

    private List<StudyCafePass> getPassesBy(StudyCafePassType type) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.stream()
                .filter(pass -> pass.isSameType(type))
                .toList();
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass selectedPass) {

        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = getLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            ioHandler.askLockerPass(lockerPassCandidate);
            boolean isLockerSelected = ioHandler.askLockerPass(lockerPassCandidate);

            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass getLockerPassCandidateBy(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
                .filter(pass::isSameDurationType)
                .findFirst()
                .orElse(null);
    }

}
