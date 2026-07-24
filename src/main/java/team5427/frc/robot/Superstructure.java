package team5427.frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.BooleanSupplier;
import org.littletonrobotics.junction.Logger;

public final class Superstructure {
  public static final String dashboardKey = "/Superstructure";

  // State Variables, accessible only with getters

  private static SwerveStates kSelectedSwerveState = SwerveStates.DISABLED;
  private static SwerveStates kPreviousSwerveState = SwerveStates.DISABLED;

  private static IntakeStates kSelectedIntakeState = IntakeStates.STOWED;
  private static IntakeStates kPreviousIntakeState = IntakeStates.STOWED;

  private static ElevatorStates kSelectedElevatorState = ElevatorStates.STOWED;
  private static ElevatorStates kPreviousElevatorState = ElevatorStates.DISABLED;

  private static DiffyStates kSelectedDiffyState = DiffyStates.STOWED;
  private static DiffyStates kPreviousDiffyState = DiffyStates.STOWED;

  // Swerve States Enum
  public static enum SwerveStates {
    RAW_DRIVING,
    CONTROLLED_DRIVING,
    AUTO_ALIGN,
    INTAKE_ASSISTANCE,
    AUTON,
    DISABLED
  }

  // Intake States Enum
  public static enum IntakeStates {
    INTAKING,
    DISABLED,
    STOWED,
    OUTAKING
  }

  // Elevator States Enum
  public static enum ElevatorStates {
    STOWED,
    SETPOINT1,
    SETPOINT2,
    DISABLED
  }

  // Diffy States Enum
  public static enum DiffyStates {
    STOWED,
    STATION_INTAKING,
    GROUND_INTAKING,
    SCORING
  }

  // Getter Methods

  public static synchronized SwerveStates getSelectedSwerveState() {
    return kSelectedSwerveState;
  }

  public static synchronized SwerveStates getPreviousSwerveState() {
    return kPreviousSwerveState;
  }

  public static synchronized IntakeStates getSelectedIntakeState() {
    return kSelectedIntakeState;
  }

  public static synchronized IntakeStates getPreviousIntakeState() {
    return kPreviousIntakeState;
  }

  public static synchronized ElevatorStates getSelectedElevatorState() {
    return kSelectedElevatorState;
  }

  public static synchronized ElevatorStates getPreviousElevatorState() {
    return kPreviousElevatorState;
  }

  public static synchronized DiffyStates getSelectedDiffyState() {
    return kSelectedDiffyState;
  }

  public static synchronized DiffyStates getPreviousDiffyState() {
    return kPreviousDiffyState;
  }

  // State Request Methods

  /**
   * Allows you to request a new Swerve State and if it is different than the current one, the
   * current and previous state will be replaced accordingly.
   *
   * @param newState The SwerveState that you are requesting
   */
  public static synchronized void requestSwerveState(SwerveStates newState) {
    if (kSelectedSwerveState != newState) {
      kPreviousSwerveState = kSelectedSwerveState;
      kSelectedSwerveState = newState;
      Logger.recordOutput(dashboardKey + "/SwerveState", newState.toString());
      Logger.recordOutput(dashboardKey + "/PreviousSwerveState", kPreviousSwerveState.toString());
    }
  }

  /**
   * Allows you to request a new Intake State and if it is different than the current one, the
   * current and previous state will be replaced accordingly.
   *
   * @param newState The IntakeState that you are requesting
   */
  public static synchronized void requestIntakeState(IntakeStates newState) {
    if (kSelectedIntakeState != newState) {
      kPreviousIntakeState = kSelectedIntakeState;
      kSelectedIntakeState = newState;
      Logger.recordOutput(dashboardKey + "/IntakeState", newState.toString());
      Logger.recordOutput(dashboardKey + "/PreviousIntakeState", kPreviousIntakeState.toString());
    }
  }

  /**
   * Allows you to request a new Intake State and if it is different than the current one, the
   * current and previous state will be replaced accordingly.
   *
   * @param newState The ElevatorState that you are requesting
   */
  public static synchronized void requestElevatorState(ElevatorStates newState) {
    if (kSelectedElevatorState != newState) {
      kPreviousElevatorState = kSelectedElevatorState;
      kSelectedElevatorState = newState;
      Logger.recordOutput(dashboardKey + "/ElevatorState", newState.toString());
      Logger.recordOutput(
          dashboardKey + "/PreviousElevatorState", kPreviousElevatorState.toString());
    }
  }

  /**
   * Allows you to request a new Diffy State and if it is different than the current one, the
   * current and previous state will be replaced accordingly.
   *
   * @param newState The DiffyState that you are requesting
   */
  public static synchronized void requestDiffyState(DiffyStates newState) {
    if (kSelectedDiffyState != newState) {
      kPreviousDiffyState = kSelectedDiffyState;
      kSelectedDiffyState = newState;
      Logger.recordOutput(dashboardKey + "/DiffyState", newState.toString());
      Logger.recordOutput(dashboardKey + "/PreviousDiffyState", kPreviousDiffyState.toString());
    }
  }

  // Command factories return command that change state
  /**
   * Builds a {@link Command} that switches the swerve subsystem to the supplied state.
   *
   * @param state Desired {@link SwerveStates} target.
   * @return One-shot command that applies the new state.
   */
  public static synchronized Command setSwerveStateCommand(SwerveStates state) {
    return Commands.runOnce(() -> requestSwerveState(state))
        .withName("SetSwerveState(" + state.toString() + ")");
  }

  /**
   * Builds a {@link Command} that switches the intake subsystem to the supplied state.
   *
   * @param state Desired {@link IntakeStates} target.
   * @return One-shot command that applies the new state.
   */
  public static synchronized Command setIntakeStateCommand(IntakeStates state) {
    return Commands.runOnce(() -> requestIntakeState(state))
        .withName("SetIntakeState(" + state.toString() + ")");
  }

  /**
   * Builds a {@link Command} that switches the elevator subsystem to the supplied state.
   *
   * @param state Desired {@link ElevatorStates} target.
   * @return One-shot command that applies the new state.
   */
  public static synchronized Command setElevatorStateCommand(ElevatorStates state) {
    return Commands.runOnce(() -> requestElevatorState(state))
        .withName("SetElevatorState(" + state.toString() + ")");
  }

  /**
   * Builds a {@link Command} that switches the diffy subsystem to the supplied state.
   *
   * @param state Desired {@link DiffyStates} target.
   * @return One-shot command that applies the new state.
   */
  public static synchronized Command setDiffyStateCommand(DiffyStates state) {
    return Commands.runOnce(() -> requestDiffyState(state))
        .withName("SetDiffyState(" + state.toString() + ")");
  }

  // Trigger factory creates trigger for any state condition
  /**
   * Creates a trigger that is active when the current swerve state equals the supplied state.
   *
   * @param state State to compare against the currently selected swerve state.
   * @return Trigger that reflects the state match.
   */
  public static synchronized Trigger swerveStateIs(SwerveStates state) {
    return new Trigger(() -> kSelectedSwerveState == state);
  }

  /**
   * Creates a trigger that is active when the current swerve state matches any of the supplied
   * states.
   *
   * @param states Acceptable {@link SwerveStates} values.
   * @return Trigger that fires while the current state is any of the provided values.
   */
  public static synchronized Trigger swerveStateIsAnyOf(SwerveStates... states) {
    return new Trigger(
        () -> {
          for (SwerveStates state : states) {
            if (kSelectedSwerveState == state) return true;
          }
          return false;
        });
  }

  /**
   * Creates a trigger that is active when the current intake state equals the supplied state.
   *
   * @param state State to compare against the currently selected intake state.
   * @return Trigger that reflects the state match.
   */
  public static synchronized Trigger intakeStateIs(IntakeStates state) {
    return new Trigger(() -> kSelectedIntakeState == state);
  }

  /**
   * Creates a trigger that is active when the current intake state matches any of the supplied
   * states.
   *
   * @param states Acceptable {@link IntakeStates} values.
   * @return Trigger that fires while the current state is any of the provided values.
   */
  public static synchronized Trigger intakeStateIsAnyOf(IntakeStates... states) {
    return new Trigger(
        () -> {
          for (IntakeStates state : states) {
            if (kSelectedIntakeState == state) return true;
          }
          return false;
        });
  }

  /**
   * Creates a trigger that is active when the current intake state equals the supplied state.
   *
   * @param state State to compare against the currently selected elevator state.
   * @return Trigger that reflects the state match.
   */
  public static synchronized Trigger elevatorStateIs(ElevatorStates state) {
    return new Trigger(() -> kSelectedElevatorState == state);
  }

  /**
   * Creates a trigger that is active when the current elevator state matches any of the supplied
   * states.
   *
   * @param states Acceptable {@link ElevatorStates} values.
   * @return Trigger that fires while the current state is any of the provided values.
   */
  public static synchronized Trigger elevatorStateIsAnyOf(ElevatorStates... states) {
    return new Trigger(
        () -> {
          for (ElevatorStates state : states) {
            if (kSelectedElevatorState == state) return true;
          }
          return false;
        });
  }

  /**
   * Creates a trigger that is active when the current diffy state equals the supplied state.
   *
   * @param state State to compare against the currently selected diffy state.
   * @return Trigger that reflects the state match.
   */
  public static synchronized Trigger diffyStateIs(DiffyStates state) {
    return new Trigger(() -> kSelectedDiffyState == state);
  }

  /**
   * Creates a trigger that is active when the current diffy state matches any of the supplied
   * states.
   *
   * @param states Acceptable {@link DiffyStates} values.
   * @return Trigger that fires while the current state is any of the provided values.
   */
  public static synchronized Trigger diffyStateIsAnyOf(DiffyStates... states) {
    return new Trigger(
        () -> {
          for (DiffyStates state : states) {
            if (kSelectedDiffyState == state) return true;
          }
          return false;
        });
  }

  // Transition Triggers detect state changes

  /**
   * Trigger that becomes active the first cycle a new swerve state is selected.
   *
   * @param state Destination state to monitor.
   * @return Trigger that detects the transition into the supplied state.
   */
  public static synchronized Trigger swerveStateChangedTo(SwerveStates state) {
    return new Trigger(() -> kSelectedSwerveState == state && kPreviousSwerveState != state);
  }

  /**
   * Trigger that becomes active when a previously selected swerve state is exited.
   *
   * @param state Source state to monitor.
   * @return Trigger that detects the transition away from the supplied state.
   */
  public static synchronized Trigger swerveStateChangedFrom(SwerveStates state) {
    return new Trigger(() -> kPreviousSwerveState == state && kSelectedSwerveState != state);
  }

  /**
   * Trigger that becomes active the first cycle a new intake state is selected.
   *
   * @param state Destination state to monitor.
   * @return Trigger that detects the transition into the supplied state.
   */
  public static synchronized Trigger intakeStateChangedTo(IntakeStates state) {
    return new Trigger(() -> kSelectedIntakeState == state && kPreviousIntakeState != state);
  }

  /**
   * Trigger that becomes active when a previously selected intake state is exited.
   *
   * @param state Source state to monitor.
   * @return Trigger that detects the transition away from the supplied state.
   */
  public static synchronized Trigger intakeStateChangedFrom(IntakeStates state) {
    return new Trigger(() -> kPreviousIntakeState == state && kSelectedIntakeState != state);
  }

  /**
   * Trigger that becomes active the first cycle a new elevator state is selected.
   *
   * @param state Destination state to monitor.
   * @return Trigger that detects the transition into the supplied state.
   */
  public static synchronized Trigger elevatorStateChangedTo(ElevatorStates state) {
    return new Trigger(() -> kSelectedElevatorState == state && kPreviousElevatorState != state);
  }

  /**
   * Trigger that becomes active when a previously selected elevator state is exited.
   *
   * @param state Source state to monitor.
   * @return Trigger that detects the transition away from the supplied state.
   */
  public static synchronized Trigger elevatorStateChangedFrom(ElevatorStates state) {
    return new Trigger(() -> kPreviousElevatorState == state && kSelectedElevatorState != state);
  }

  /**
   * Trigger that becomes active the first cycle a new diffy state is selected.
   *
   * @param state Destination state to monitor.
   * @return Trigger that detects the transition into the supplied state.
   */
  public static synchronized Trigger diffyStateChangedTo(DiffyStates state) {
    return new Trigger(() -> kSelectedDiffyState == state && kPreviousDiffyState != state);
  }

  /**
   * Trigger that becomes active when a previously selected diffy state is exited.
   *
   * @param state Source state to monitor.
   * @return Trigger that detects the transition away from the supplied state.
   */
  public static synchronized Trigger diffyStateChangedFrom(DiffyStates state) {
    return new Trigger(() -> kPreviousDiffyState == state && kSelectedDiffyState != state);
  }

  // Compound - Combine multiple conditions

  /**
   * Wraps an arbitrary boolean supplier in a {@link Trigger}.
   *
   * @param condition Supplier evaluated each cycle.
   * @return Trigger that mirrors the supplier's value.
   */
  public static synchronized Trigger when(BooleanSupplier condition) {
    return new Trigger(condition);
  }

  // Validation - Prevent invalid state combinations

  /**
   * Determines whether the swerve subsystem may transition between the supplied states.
   *
   * @param from Current state.
   * @param to Desired target state.
   * @return {@code true} if the transition is allowed, {@code false} otherwise.
   */
  public static synchronized boolean canTransitionSwerve(SwerveStates from, SwerveStates to) {
    if (from == SwerveStates.DISABLED && to == SwerveStates.AUTO_ALIGN) {
      return false;
    }
    return true;
  }

  /**
   * Requests a new swerve state only if {@link #canTransitionSwerve(SwerveStates, SwerveStates)}
   * approves the transition. Invalid requests are logged to the dashboard.
   *
   * @param newState Desired target state.
   */
  public static synchronized void requestSwerveStateValidated(SwerveStates newState) {
    if (canTransitionSwerve(kSelectedSwerveState, newState)) {
      requestSwerveState(newState);
    } else {
      Logger.recordOutput(
          dashboardKey + "/InvalidTransition",
          kSelectedSwerveState.toString() + " -> " + newState.toString());
    }
  }

  // Logging

  /** Publishes the current and previous swerve and intake states to AdvantageScope. */
  public static synchronized void logStates() {
    Logger.recordOutput(dashboardKey + "/SwerveState", kSelectedSwerveState.toString());
    Logger.recordOutput(dashboardKey + "/IntakeState", kSelectedIntakeState.toString());
    Logger.recordOutput(dashboardKey + "/ElevatorState", kSelectedElevatorState.toString());
    Logger.recordOutput(dashboardKey + "/DiffyState", kSelectedDiffyState.toString());
    Logger.recordOutput(dashboardKey + "/PreviousSwerveState", kPreviousSwerveState.toString());
    Logger.recordOutput(dashboardKey + "/PreviousIntakeState", kPreviousIntakeState.toString());
    Logger.recordOutput(dashboardKey + "/PreviousElevatorState", kPreviousElevatorState.toString());
    Logger.recordOutput(dashboardKey + "/PreviousDiffyState", kPreviousDiffyState.toString());
  }

  // Static Trigger Constants

  public static final class SwerveTriggers {
    public static final Trigger kRawDriving = swerveStateIs(SwerveStates.RAW_DRIVING);
    public static final Trigger kControlledDriving = swerveStateIs(SwerveStates.CONTROLLED_DRIVING);
    public static final Trigger kAutoAlign = swerveStateIs(SwerveStates.AUTO_ALIGN);
    public static final Trigger kIntakeAssistance = swerveStateIs(SwerveStates.INTAKE_ASSISTANCE);
    public static final Trigger kAuton = swerveStateIs(SwerveStates.AUTON);
    public static final Trigger kDisabled = swerveStateIs(SwerveStates.DISABLED);
  }

  public static final class IntakeTriggers {
    public static final Trigger kIntaking = intakeStateIs(IntakeStates.INTAKING);
    public static final Trigger kDisabled = intakeStateIs(IntakeStates.DISABLED);
    public static final Trigger kStowed = intakeStateIs(IntakeStates.STOWED);
    public static final Trigger kOutaking = intakeStateIs(IntakeStates.OUTAKING);
  }

  public static final class ElevatorTriggers {
    public static final Trigger kStowed = elevatorStateIs(ElevatorStates.STOWED);
    public static final Trigger kDisabled = elevatorStateIs(ElevatorStates.DISABLED);
    public static final Trigger kSetpoint1 = elevatorStateIs(ElevatorStates.SETPOINT1);
    public static final Trigger kSetpoint2 = elevatorStateIs(ElevatorStates.SETPOINT2);
  }

  public static final class DiffyTriggers {
    public static final Trigger kStowed = diffyStateIs(DiffyStates.STOWED);
    public static final Trigger kStationIntaking = diffyStateIs(DiffyStates.STATION_INTAKING);
    public static final Trigger kGroundIntaking = diffyStateIs(DiffyStates.GROUND_INTAKING);
    public static final Trigger kScoring = diffyStateIs(DiffyStates.SCORING);
  }
}
