package team5427.frc.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Setter;
import org.littletonrobotics.junction.Logger;
import team5427.frc.robot.Constants;
import team5427.frc.robot.subsystems.elevator.io.ElevatorIO;
import team5427.frc.robot.subsystems.elevator.io.ElevatorIOInputsAutoLogged;
import team5427.frc.robot.subsystems.elevator.io.ElevatorIOTalonFX;

public class ElevatorSubsystem extends SubsystemBase {
  private static ElevatorSubsystem m_instance;

  private ElevatorIO io;
  private ElevatorIOInputsAutoLogged inputs;

  @Setter private Distance elevatorSetpoint;

  public static ElevatorSubsystem getInstance() {
    if (m_instance == null) {
      m_instance = new ElevatorSubsystem();
    }
    return m_instance;
  }

  private ElevatorSubsystem() {
    inputs = new ElevatorIOInputsAutoLogged();
    switch (Constants.currentMode) {
      case REAL:
        io = new ElevatorIOTalonFX();
        break;
      case SIM:
        io = new ElevatorIOTalonFX();
        break;
      default:
        break;
    }
    elevatorSetpoint = ElevatorConstants.kElevatorStowPosition;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);

    io.setElevatorPosition(elevatorSetpoint);

    Logger.processInputs("Elevator/Inputs", inputs);
    log();
  }

  public boolean atGoal() {
    return Math.abs(inputs.leaderMotorDistance.minus(elevatorSetpoint).in(Meters)) <= 0.02;
  }

  private void log() {
    Logger.recordOutput("Elevator/LinearPosition", inputs.leaderMotorDistance.in(Meters));
    Logger.recordOutput(
        "Elevator/LinearVelocity", inputs.leaderMotorLinearVelocity.in(MetersPerSecond));
    Logger.recordOutput(
        "Elevator/LinearAcceleration",
        inputs.leaderMotorLinearAcceleration.in(MetersPerSecondPerSecond));
    Logger.recordOutput("Elevator/CurrentDraw", inputs.leaderMotorCurrent.in(Amps));
    Logger.recordOutput("Elevator/VoltageDraw", inputs.leaderMotorVoltage.in(Volts));
  }
}
