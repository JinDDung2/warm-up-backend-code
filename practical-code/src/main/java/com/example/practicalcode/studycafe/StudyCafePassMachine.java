package com.example.practicalcode.studycafe;

import com.example.practicalcode.studycafe.exception.AppException;
import com.example.practicalcode.studycafe.io.StudyCafeFileHandler;
import com.example.practicalcode.studycafe.io.StudyCafeIOHandler;
import com.example.practicalcode.studycafe.model.locker.StudyCafeLockerPass;
import com.example.practicalcode.studycafe.model.locker.StudyCafeLockerPasses;
import com.example.practicalcode.studycafe.model.pass.StudyCafePassOrder;
import com.example.practicalcode.studycafe.model.pass.StudyCafePassType;
import com.example.practicalcode.studycafe.model.pass.StudyCafeSeatPass;
import com.example.practicalcode.studycafe.model.pass.StudyCafeSeatPasses;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = getPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = getLockerPass(selectedPass);
            StudyCafePassOrder passOrder = StudyCafePassOrder.of(
                    selectedPass,
                    optionalLockerPass.orElse(null));

            ioHandler.showPassOrderSummary(passOrder);
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }

    }

    private StudyCafeSeatPass getPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        List<StudyCafeSeatPass> passCandidates = getPassesBy(passType);

        ioHandler.showPassListForSelection(passCandidates);
        return ioHandler.askPassSelecting(passCandidates);
    }

    private List<StudyCafeSeatPass> getPassesBy(StudyCafePassType type) {
        StudyCafeSeatPasses allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.findPassBy(type);
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafeSeatPass selectedPass) {

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

    private Optional<StudyCafeLockerPass> getLockerPassCandidateBy(StudyCafeSeatPass pass) {
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.findLockerPassBy(pass);
    }

}
