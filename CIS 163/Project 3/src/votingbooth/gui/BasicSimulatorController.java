package votingbooth.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import votingbooth.*;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by kylef_000 on 10/12/2016.
 */
public class BasicSimulatorController implements Initializable {

    // Top-half of the text field variables
    @FXML private TextField secondsToNext;
    @FXML private TextField avgCheckInTime;
    @FXML private TextField totalTime;
    @FXML private TextField avgVotingTime;
    @FXML private TextField secondsBeforeLeave;
    @FXML private TextField boothCount;

    // Bottom-half of the text field variables
    @FXML private TextField throughPut;
    @FXML private TextField peopleInLine;
    @FXML private TextField voterVoteTime;
    @FXML private TextField checkInOne;
    @FXML private TextField checkInTwo;
    @FXML private TextField votingBoothQ;

    // Back-end logic
    private Clock clk;
    private BoothLine boothQ;
    private Booth[] booths;

    // Right now, we control the amount of check-in tables
    private CheckInTable tableOne;
    private CheckInTable tableTwo;

    private LinkedList<CheckInTable> tables;

    // Data inputs from the top text fields
    private int nextPerson = 0;
    private int avgSecondsCheckIn = 0;
    private int totalTimeSec = 0;
    private int avgSecToVote = 0;
    private int secondsBeforeLeaves = 0;
    private int boothNum = 0;

    // Statistics
    private int throughput;
    private double avgVoteTime;
    private int peopleLeft;
    private int votingLineQ;
    private int stillVoting;

    private int normalPissed;
    private int limitedPissed;
    private int specialPissed;
    private int superPissed;
    private int totalPissed;
    private int normalVoted;
    private int limitedVoted;
    private int specialVoted;
    private int superVoted;
    private int totalVoted;
    private int avgLimitedVoteTime;
    private int avgSpecialVoteTime;
    private int avgNormalVoteTime;
    private int avgSuperVoteTime;
    private int avgTotalVoteTime;
    private double avgLimitedCheckInTime;
    private double avgSpecialCheckInTime;
    private double avgSuperCheckInTime;
    private double avgNormalCheckInTime;
    private double avgTotalCheckInTime;
    private double normalComplete;
    private double limitedComplete;
    private double specialComplete;
    private double superComplete;
    private double totalComplete;

    private VoterProducer produce;

    // EDIT THESE METHODS. THEY SHOULD NEVER BE LESS THAN 0
    private int validateVoterGen(int value) {
        return value;
    }

    private int validateCheckIn(int value) {
        return value;
    }

    private int validateTotalTime(int value) {
        return value;
    }

    private int validateVoteTime(int value) {
        return value;
    }

    private int validateLeaveTime(int value) {
        return value;
    }

    private int validateBoothNum(int value) {
        return value;
    }
    // END METHODS YOU'RE SUPPOSED TO EDIT

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /***********************************************
     * This will link the Start Button to the Logic in the back end
     * For this we are re-assigning the variables to what is
     * stored in the text fields
     * @return void
     ***********************************************/
    @FXML
    public void startSimulation(){

        try {
            nextPerson = validateVoterGen(Integer.parseInt(secondsToNext.getText()));
            avgSecondsCheckIn = validateCheckIn(Integer.parseInt(avgCheckInTime.getText()));
            totalTimeSec = validateTotalTime(Integer.parseInt(totalTime.getText()));
            avgSecToVote = validateVoteTime(Integer.parseInt(avgVotingTime.getText()));
            secondsBeforeLeaves = validateLeaveTime(Integer.parseInt(secondsBeforeLeave.getText()));
            boothNum = validateBoothNum(Integer.parseInt(boothCount.getText()));

            clk = new Clock();
            booths = new Booth[boothNum];

            for (int i = 0; i < boothNum; i++) {
                booths[i] = new Booth(avgSecToVote);
                clk.add(booths[i]);
            }

            boothQ = new BoothLine(booths);
            tableOne = new CheckInTable(avgSecondsCheckIn, boothQ);
            tableTwo = new CheckInTable(avgSecondsCheckIn, boothQ);
            produce = new VoterProducer(booths, nextPerson, secondsBeforeLeaves, (totalTimeSec / nextPerson));

            tables = new LinkedList<>();

            tables.add(tableOne);
            tables.add(tableTwo);

            produce.addTable(tableOne);
            produce.addTable(tableTwo);

            clk.add(produce);
            clk.add(tableOne);
            clk.add(tableTwo);
            clk.add(boothQ);

            clk.start(totalTimeSec);

            resetStatistics();
            calculateStatistics();
            addStatistics();
            outputInformation();
        } catch (NumberFormatException nfe) {
            System.out.println("Can't convert.");
        }
    }

    /*********************************************
     * Quits the simulator, this is linked to the FXML GUI button
     * @return void
     *********************************************/
    @FXML
    private void quitSimulation() {
        System.exit(0);
    }

    /**********************************************
     * Prints the output information
     * @return void
     *********************************************/
    private void outputInformation() {
        throughPut.setText(Statistics.getStatistic("throughput") + " people with Max = " + Statistics.getStatistic("maxVoters"));
        peopleInLine.setText(Statistics.getStatistic("peopleLeft") + "");
        voterVoteTime.setText(Statistics.getStatistic("avgVoteTime") + " seconds");
        checkInOne.setText(tableOne.getMaxQlength() + "");
        checkInTwo.setText(tableTwo.getMaxQlength() + "");
        votingBoothQ.setText(Statistics.getStatistic("votingLineQ") + "");
	}

    private void calculateStatistics() {
        for (Booth b : booths) {
            if (b.inUse()) {
                stillVoting++;
            }
            for (double time : b.getCompletedTimes()) {
                avgVoteTime += time;
            }
        }
        for (Voter v : produce.getVoters()) {
            if (v instanceof LimitedTimeVoter) {
                if (v.isPissed()) {
                    limitedPissed++;
                }
                if (v.hasVoted()) {
                    limitedVoted++;
                    avgLimitedVoteTime += v.getBoothTime();
                    limitedComplete += v.getTimeSpent();
                }
                if (v.hasCheckedIn()) {
                    avgLimitedCheckInTime+= v.getCheckInTime();
                }
            } else if (v instanceof SpecialNeedsVoter) {
                if (v instanceof SuperSpecialNeedsVoter) {
                    if (v.isPissed()) {
                        superPissed++;
                    }
                    if (v.hasVoted()) {
                        superVoted++;
                        avgSuperVoteTime+= v.getBoothTime();
                        superComplete += v.getTimeSpent();
                    }
                    if (v.hasCheckedIn()) {
                        avgSuperCheckInTime+= v.getCheckInTime();
                    }
                } else {
                    if (v.isPissed()) {
                        specialPissed++;
                    }
                    if (v.hasVoted()) {
                        specialVoted++;
                        avgSpecialVoteTime+= v.getBoothTime();
                        specialComplete += v.getTimeSpent();
                    }
                    if (v.hasCheckedIn()) {
                        avgSpecialCheckInTime+= v.getCheckInTime();
                    }
                }
            } else {
                if (v.isPissed()) {
                    normalPissed++;
                }
                if (v.hasVoted()) {
                    normalVoted++;
                    avgNormalVoteTime+= v.getBoothTime();
                    normalComplete += v.getTimeSpent();
                }
                if (v.hasCheckedIn()) {
                    avgNormalCheckInTime+= v.getCheckInTime();
                }
            }
        }

        peopleLeft+= tableOne.getVoterQ() + tableTwo.getVoterQ();

        avgTotalCheckInTime = (avgNormalCheckInTime + avgLimitedCheckInTime + avgSpecialCheckInTime + avgSuperCheckInTime) / produce.getVoters().size();
        totalPissed = normalPissed + limitedPissed + specialPissed + superPissed;
        totalVoted = normalVoted + limitedVoted + specialVoted + superVoted;
        votingLineQ = boothQ.getMaxQlength();
        throughput = produce.getAllThrougPut();
        peopleLeft += boothQ.getLeft() + stillVoting;
        if (totalVoted != 0) {
            avgVoteTime = avgVoteTime / totalVoted;
            avgTotalVoteTime = (avgLimitedVoteTime + avgSpecialVoteTime + avgNormalVoteTime + avgSuperVoteTime) / totalVoted;
            totalComplete = (limitedComplete + specialComplete + normalComplete + superComplete) / totalVoted;
        } else {
            avgVoteTime = 0;
        }
        if (limitedVoted != 0) {
            avgLimitedVoteTime = avgLimitedVoteTime / limitedVoted;
            limitedComplete = limitedComplete / limitedVoted;
        } else {
            avgLimitedVoteTime = 0;
        }
        if (specialVoted != 0) {
            avgSpecialVoteTime = avgSpecialVoteTime / specialVoted;
            specialComplete = specialComplete / specialVoted;
        } else {
            avgSpecialVoteTime = 0;
        }
        if (normalVoted != 0) {
            avgNormalVoteTime = avgNormalVoteTime / normalVoted;
            normalComplete = normalComplete / normalVoted;
        } else {
            avgNormalVoteTime = 0;
        }
        if (superVoted != 0) {
            avgSuperVoteTime = avgSuperVoteTime / superVoted;
            superComplete = superComplete / superVoted;
        } else {
            avgSuperVoteTime = 0;
        }
    }

    private void addStatistics() {
        Statistics.clearStatistics();
        Statistics.addStatistic("avgVoteTime", avgVoteTime);
        Statistics.addStatistic("votingLineQ", votingLineQ);
        Statistics.addStatistic("throughput", throughput);
        Statistics.addStatistic("peopleLeft", peopleLeft);
        Statistics.addStatistic("avgVoteTime", avgVoteTime);
        Statistics.addStatistic("avgNormalVoteTime", avgNormalVoteTime);
        Statistics.addStatistic("avgLimitedVoteTime", avgLimitedVoteTime);
        Statistics.addStatistic("avgSpecialVoteTime", avgSpecialVoteTime);
        Statistics.addStatistic("avgSuperVoteTime", avgSuperVoteTime);
        Statistics.addStatistic("avgTotalVoteTime", avgTotalVoteTime);
        Statistics.addStatistic("maxVoters", (totalTimeSec / nextPerson));
        Statistics.addStatistic("normalTotal", produce.getNormalVoters().size());
        Statistics.addStatistic("limitedTotal", produce.getLimitedVoters().size());
        Statistics.addStatistic("specialTotal", produce.getSpecialVoters().size());
        Statistics.addStatistic("superTotal", produce.getSuperSpecialVoters().size());
        Statistics.addStatistic("totalTotal", produce.getNormalVoters().size() + produce.getLimitedVoters().size() + produce.getSpecialVoters().size() + produce.getSuperSpecialVoters().size());
        Statistics.addStatistic("normalVoted", normalVoted);
        Statistics.addStatistic("limitedVoted", limitedVoted);
        Statistics.addStatistic("specialVoted", specialVoted);
        Statistics.addStatistic("superVoted", superVoted);
        Statistics.addStatistic("totalVoted", totalVoted);
        Statistics.addStatistic("normalPissed", normalPissed);
        Statistics.addStatistic("limitedPissed", limitedPissed);
        Statistics.addStatistic("specialPissed", specialPissed);
        Statistics.addStatistic("superPissed", superPissed);
        Statistics.addStatistic("totalPissed", totalPissed);
        Statistics.addStatistic("normalCheckIn", avgNormalCheckInTime / produce.getNormalVoters().size());
        Statistics.addStatistic("limitedCheckIn", avgLimitedCheckInTime / produce.getLimitedVoters().size());
        Statistics.addStatistic("specialCheckIn", avgSpecialCheckInTime / produce.getSpecialVoters().size());
        Statistics.addStatistic("superCheckIn", avgSuperCheckInTime / produce.getSuperSpecialVoters().size());
        Statistics.addStatistic("totalCheckIn", avgTotalCheckInTime);
        Statistics.addStatistic("normalComplete", normalComplete);
        Statistics.addStatistic("limitedComplete", limitedComplete);
        Statistics.addStatistic("specialComplete", specialComplete);
        Statistics.addStatistic("superComplete", superComplete);
        Statistics.addStatistic("totalComplete", totalComplete);
        Statistics.addStatistic("booths", booths);
        Statistics.addStatistic("tables", tables);
        Statistics.addStatistic("leftInLine", boothQ.getLeft());
    }

    private void resetStatistics() {
        stillVoting = 0;
        avgVoteTime = 0;
        normalPissed = 0;
        limitedPissed = 0;
        specialPissed = 0;
        superPissed = 0;
        totalPissed = 0;
        normalVoted = 0;
        limitedVoted = 0;
        specialVoted = 0;
        superVoted = 0;
        totalVoted = 0;
        avgNormalCheckInTime = 0;
        avgLimitedCheckInTime = 0;
        avgSpecialCheckInTime = 0;
        avgTotalCheckInTime = 0;
        avgSuperCheckInTime = 0;
        avgNormalVoteTime = 0;
        avgLimitedVoteTime = 0;
        avgSpecialVoteTime = 0;
        avgSuperVoteTime = 0;
        avgTotalVoteTime = 0;
        peopleLeft = 0;
    }

    @FXML
    private void showMoreData() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GraphicalData.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();

            newStage.setScene(scene);
            newStage.setTitle("More Data");
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
