package team5427.frc.robot.io;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import team5427.frc.robot.Constants.DriverConstants;
import team5427.frc.robot.Superstructure;
import team5427.frc.robot.Superstructure.DiffyStates;
import team5427.frc.robot.Superstructure.ElevatorStates;
import team5427.frc.robot.commands.diffy.FlickWrist;
import team5427.frc.robot.commands.diffy.RotateWrist;
import team5427.frc.robot.commands.elevator.MoveElevatorToPosition;
import team5427.frc.robot.subsystems.diffy.DiffyConstants;
import team5427.frc.robot.subsystems.elevator.ElevatorConstants;

public class OperatorControls {
  private CommandXboxController joy;

  public OperatorControls() {
    joy = new CommandXboxController(DriverConstants.kOperatorJoystickPort);
    initalizeTriggers();
  }

  public OperatorControls(CommandXboxController joy) {
    this.joy = joy;
    initalizeTriggers();
  }

  /** Made private to prevent multiple calls to this method */
  private void initalizeTriggers() {
    // // Use command factories instead of inline InstantCommands
    // joy.leftTrigger()
    //     .whileTrue(Superstructure.setIntakeStateCommand(IntakeStates.INTAKING))
    //     .onFalse(Superstructure.setIntakeStateCommand(IntakeStates.STOWED));

    // // Use class-level trigger factory methods instead of nested class references
    // Superstructure.intakeStateIs(IntakeStates.INTAKING)
    //
    // .and(Superstructure.swerveStateIs(Superstructure.SwerveStates.INTAKE_ASSISTANCE).negate())
    //     .whileTrue(new IntakeIntaking());

    // Superstructure.intakeStateIs(IntakeStates.STOWED).whileTrue(new IntakeStowed());

    // Superstructure.intakeStateIs(IntakeStates.DISABLED)
    //     .whileTrue(
    //         new InstantCommand(
    //             () -> {
    //               IntakeSubsystem.getInstance().disablePivotMotor(true);
    //               IntakeSubsystem.getInstance().disableRollerMotor(true);
    //             },
    //             IntakeSubsystem.getInstance()))
    //     .onFalse(
    //         new InstantCommand(
    //             () -> {
    //               IntakeSubsystem.getInstance().disablePivotMotor(false);
    //               IntakeSubsystem.getInstance().disableRollerMotor(false);
    //             }));
    joy.a()
        .onTrue(Superstructure.setElevatorStateCommand(ElevatorStates.SETPOINT1))
        .onTrue(Superstructure.setDiffyStateCommand(DiffyStates.SCORING));

    joy.x()
        .onTrue(Superstructure.setElevatorStateCommand(ElevatorStates.SETPOINT2))
        .onTrue(Superstructure.setDiffyStateCommand(DiffyStates.SCORING));

    joy.povDown()
        .onTrue(Superstructure.setElevatorStateCommand(ElevatorStates.STOWED))
        .onTrue(Superstructure.setDiffyStateCommand(DiffyStates.STOWED));

    joy.leftTrigger()
        .onTrue(Superstructure.setDiffyStateCommand(DiffyStates.STATION_INTAKING))
        .onFalse(Superstructure.setDiffyStateCommand(DiffyStates.STOWED));

    joy.leftBumper()
        .onTrue(Superstructure.setDiffyStateCommand(DiffyStates.GROUND_INTAKING))
        .onFalse(Superstructure.setDiffyStateCommand(DiffyStates.STOWED));

    Superstructure.elevatorStateIs(ElevatorStates.SETPOINT1)
        .whileTrue(new MoveElevatorToPosition(ElevatorConstants.kElevatorSetpoint1));
    Superstructure.elevatorStateIs(ElevatorStates.SETPOINT2)
        .whileTrue(new MoveElevatorToPosition(ElevatorConstants.kElevatorSetpoint2));
    Superstructure.elevatorStateIs(ElevatorStates.STOWED)
        .whileTrue(new MoveElevatorToPosition(ElevatorConstants.kElevatorStowPosition));
    Superstructure.diffyStateIs(DiffyStates.STOWED)
        .whileTrue(new RotateWrist(DiffyConstants.kAverageStowPosition))
        .whileTrue(new FlickWrist(DiffyConstants.kDifferenceStandardPosition));
    Superstructure.diffyStateIs(DiffyStates.STATION_INTAKING)
        .whileTrue(new RotateWrist(DiffyConstants.kAverageStationPosition))
        .whileTrue(new FlickWrist(DiffyConstants.kDifferenceHorizontalPosition));
    Superstructure.diffyStateIs(DiffyStates.GROUND_INTAKING)
        .whileTrue(new RotateWrist(DiffyConstants.kAverageGroundPosition))
        .whileTrue(new FlickWrist(DiffyConstants.kDifferenceHorizontalPosition));
    Superstructure.diffyStateIs(DiffyStates.SCORING)
        .whileTrue(new RotateWrist(DiffyConstants.kAverageScoringPosition))
        .whileTrue(new FlickWrist(DiffyConstants.kDifferenceStandardPosition));
  }
}
