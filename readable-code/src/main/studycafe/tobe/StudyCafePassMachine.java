package main.studycafe.tobe;

import main.studycafe.tobe.exception.AppException;
import main.studycafe.tobe.io.InputHandler;
import main.studycafe.tobe.io.OutputHandler;
import main.studycafe.tobe.io.StudyCafeFileHandler;
import main.studycafe.tobe.model.StudyCafeLockerPass;
import main.studycafe.tobe.model.StudyCafePass;
import main.studycafe.tobe.model.StudyCafePassType;

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
            outputHandler.askPassTypeSelection();

            StudyCafePass selectedPass = getPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = getLockerPass(selectedPass);

            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> outputHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }

    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass pass) {

        if (pass.getPassType() != StudyCafePassType.FIXED) {
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = getLockerPassCandidateBy(pass);

        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass getLockerPassCandidateBy(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
                .filter(lockerPass ->
                        lockerPass.getPassType() == pass.getPassType()
                                && lockerPass.getDuration() == pass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }

    private StudyCafePass getPass() {
        StudyCafePassType passType = inputHandler.getPassTypeSelectingUserAction();

        List<StudyCafePass> passCandidates = getPassesBy(passType);

        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private List<StudyCafePass> getPassesBy(StudyCafePassType type) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.stream()
                .filter(pass -> pass.getPassType() == type)
                .toList();
    }

}
