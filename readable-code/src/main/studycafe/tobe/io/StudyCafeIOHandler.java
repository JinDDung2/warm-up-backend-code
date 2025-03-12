package main.studycafe.tobe.io;

import main.studycafe.tobe.model.pass.StudyCafePassOrder;
import main.studycafe.tobe.model.locker.StudyCafeLockerPass;
import main.studycafe.tobe.model.pass.StudyCafeSeatPass;
import main.studycafe.tobe.model.pass.StudyCafePassType;

import java.util.List;

public class StudyCafeIOHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void showWelcomeMessage() {
        outputHandler.showWelcomeMessage();
    }

    public void showAnnouncement() {
        outputHandler.showAnnouncement();
    }

    public void showPassListForSelection(List<StudyCafeSeatPass> passes) {
        outputHandler.showPassListForSelection(passes);
    }

    public void showPassOrderSummary(StudyCafePassOrder studyCafePassOrder) {
        outputHandler.showPassOrderSummary(studyCafePassOrder);
    }

    public void showSimpleMessage(String message) {
        outputHandler.showSimpleMessage(message);
    }

    public StudyCafeSeatPass askPassSelecting(List<StudyCafeSeatPass> passCandidates) {
        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    public boolean askLockerPass(StudyCafeLockerPass lockerPassCandidate) {
        outputHandler.askLockerPass(lockerPassCandidate);
        return inputHandler.getLockerSelection();
    }

    public StudyCafePassType askPassTypeSelecting() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }
}
