package main.studycafe.tobe.io;

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

    public void askPassTypeSelection() {
        outputHandler.askPassTypeSelection();
    }

    public void showPassListForSelection(List<StudyCafeSeatPass> passes) {
        outputHandler.showPassListForSelection(passes);
    }

    public void showPassOrderSummary(StudyCafeSeatPass selectedPass) {
        outputHandler.showPassOrderSummary(selectedPass, null);
    }

    public void showPassOrderSummary(StudyCafeSeatPass selectedPass, StudyCafeLockerPass lockerPass) {
        outputHandler.showPassOrderSummary(selectedPass, lockerPass);
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
