package main.studycafe.tobe;

import main.studycafe.tobe.exception.AppException;
import main.studycafe.tobe.io.StudyCafeFileHandler;
import main.studycafe.tobe.io.StudyCafeIOHandler;
import main.studycafe.tobe.model.*;

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
        StudyCafePasses allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.findPassBy(type);
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass selectedPass) {

        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidate = getLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate.isPresent()) {
            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();
            ioHandler.askLockerPass(lockerPass);

            boolean isLockerSelected = ioHandler.askLockerPass(lockerPass);

            if (isLockerSelected) {
                return Optional.of(lockerPass);
            }
        }

        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> getLockerPassCandidateBy(StudyCafePass pass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.findLockerPassBy(pass);
    }

}
